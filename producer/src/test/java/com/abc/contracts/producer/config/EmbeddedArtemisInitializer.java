package com.abc.contracts.producer.config;

import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class EmbeddedArtemisInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedArtemisInitializer.class);
    private static EmbeddedActiveMQ embeddedActiveMQ;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        logger.info("Starting embedded Artemis broker...");

        // Programmatic configuration
        Configuration config = null; // Use a unique serverId
        try {
            config = new ConfigurationImpl()
                    .setPersistenceEnabled(false) // In-memory only
                    .setSecurityEnabled(false)    // Disable security for simplicity
                    .addAcceptorConfiguration("invm", "vm://0?serverId=" + System.currentTimeMillis());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Create and configure the embedded broker
        embeddedActiveMQ = new EmbeddedActiveMQ();
        embeddedActiveMQ.setConfiguration(config);

        // Start the broker
        try {
            embeddedActiveMQ.start();
            logger.info("Embedded Artemis broker started successfully!");

            // Add a small delay to ensure the broker is fully initialized
            Thread.sleep(1000); // 1 second delay
        } catch (Exception e) {
            logger.error("Failed to start embedded Artemis broker", e);
            throw new RuntimeException("Failed to start embedded Artemis broker", e);
        }
    }

    public static void stopBroker() {
        if (embeddedActiveMQ != null) {
            try {
                embeddedActiveMQ.stop();
                logger.info("Embedded Artemis broker stopped successfully!");
            } catch (Exception e) {
                logger.error("Failed to stop embedded Artemis broker", e);
            }
        }
    }
}