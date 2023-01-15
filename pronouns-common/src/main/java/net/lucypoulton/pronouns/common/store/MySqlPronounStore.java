package net.lucypoulton.pronouns.common.store;

import com.zaxxer.hikari.HikariDataSource;
import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.api.PronounSupplier;
import net.lucypoulton.pronouns.api.impl.PronounParser;
import net.lucypoulton.pronouns.common.ProNouns;
import net.lucypoulton.pronouns.common.platform.config.Config;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MySqlPronounStore implements CachedPronounStore, AutoCloseable {

    private final HikariDataSource dataSource;
    private final Map<UUID, List<PronounSet>> cache = new ConcurrentHashMap<>();
    private final ProNouns plugin;
    private static final PronounParser parser = new PronounParser(PronounSet.builtins);

    public MySqlPronounStore(final ProNouns plugin, final Config.MySqlConnectionInfo connectionInfo) {
        this.plugin = plugin;
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(connectionInfo.jdbcUrl());
        dataSource.setUsername(connectionInfo.username());
        dataSource.setPassword(connectionInfo.password());

        dataSource.addDataSourceProperty("cachePrepStmts", "true");
        dataSource.addDataSourceProperty("prepStmtCacheSize", "250");
        dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource.addDataSourceProperty("useServerPrepStmts ", "true");

        try (final var con = dataSource.getConnection()) {
            con.prepareStatement("""
                    CREATE TABLE IF NOT EXISTS pronouns (
                        player UUID PRIMARY KEY,
                        pronouns TEXT NOT NULL
                    )
                    """).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        plugin.platform().logger().info("Connected to MySQL");
    }

    private void push(UUID uuid, List<PronounSet> sets) {
        try (final var con = dataSource.getConnection()) {
            if (sets.size() == 0) {
                final var stmt = con.prepareStatement("DELETE FROM pronouns WHERE player=?");
                stmt.setString(1, uuid.toString());
                stmt.execute();
                return;
            }
            final var stmt = con.prepareStatement("REPLACE INTO pronouns (player, pronouns) VALUES (?, ?)");
            stmt.setString(1, uuid.toString());
            stmt.setString(2, parser.toString(sets));
            stmt.execute();
        } catch (SQLException e) {
            plugin.platform().logger().severe("Failed to write pronouns to MySQL: " + e.getMessage());
        }
    }

    @Override
    public PronounSupplier predefined() {
        return PronounSet.builtins;
    }

    @Override
    public List<PronounSet> sets(UUID player) {
        return cache.getOrDefault(player, UNSET_LIST);
    }

    @Override
    public void set(UUID player, @NotNull List<PronounSet> sets) {
        cache.put(player, sets);
        plugin.executorService().submit(() -> push(player, sets));
    }

    @Override
    public Map<UUID, List<PronounSet>> dump() {
        throw new RuntimeException("L + ratio + get better");
    }

    @Override
    public void onPlayerJoin(UUID uuid) {
        try (final var con = dataSource.getConnection()) {
            final var stmt = con.prepareStatement("SELECT pronouns FROM pronouns WHERE player=?");
            stmt.setString(1, uuid.toString());
            final var resultSet = stmt.executeQuery();
            if (!resultSet.next()) return;
            cache.put(uuid, parser.parse(resultSet.getString("pronouns")));
        } catch (SQLException e) {
            plugin.platform().logger().severe("Failed to fetch pronouns from MySQL: " + e.getMessage());
        }
    }

    @Override
    public void onPlayerLeave(UUID uuid) {
        cache.remove(uuid);
    }

    @Override
    public void close() {
        dataSource.close();
    }
}
