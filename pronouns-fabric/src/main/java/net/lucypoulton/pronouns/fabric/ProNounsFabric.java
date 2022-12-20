package net.lucypoulton.pronouns.fabric;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.lucypoulton.pronouns.api.PronounSet;
import net.lucypoulton.pronouns.api.PronounStore;
import net.lucypoulton.pronouns.api.impl.PronounParser;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class ProNounsFabric implements ModInitializer {

    private final PronounStore store = new InMemoryPronounStore();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(
                literal("pn")
                        .requires(ServerCommandSource::isExecutedByPlayer)
                        .then(literal("set").then(argument("sets", StringArgumentType.greedyString())
                                .executes(c -> {
                                    final var arg = c.getArgument("sets", String.class);
                                    final var sets = new PronounParser(PronounSet.builtins).parse(arg);
                                    store.set(c.getSource().getEntity().getUuid(), sets);
                                    c.getSource().sendMessage(Text.literal(PronounSet.format(sets)));
                                    return 0;
                                }))
                        ).then(
                                literal("show").executes(c -> {
                                    final var sets = PronounSet.format(store.sets(c.getSource().getEntity().getUuid()));
                                    c.getSource().sendMessage(Text.literal(sets));
                                    return 0;
                                })
                        )
        ));
    }
}
