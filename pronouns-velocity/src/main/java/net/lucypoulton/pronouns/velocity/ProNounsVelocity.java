package net.lucypoulton.pronouns.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(
        id = "pronouns-velocity",
        name = "ProNouns",
        version = BuildConstants.VERSION
)
public class ProNounsVelocity {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public ProNounsVelocity(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;

        logger.info("Hello there! I made my first plugin with Velocity.");
    }
}
