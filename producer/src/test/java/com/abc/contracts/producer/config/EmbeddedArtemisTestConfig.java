package com.abc.contracts.producer.config;

import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EmbeddedArtemisTestConfig {

    private static final Logger logger = LoggerFactory.getLogger(EmbeddedArtemisTestConfig.class);

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EmbeddedActiveMQ embeddedActiveMQ() throws Exception {
        logger.info("Starting embedded Artemis broker...");

        // Programmatic configuration
        Configuration config = new ConfigurationImpl()
                .setPersistenceEnabled(false) // In-memory only
                .setSecurityEnabled(false)    // Disable security for simplicity
                .addAcceptorConfiguration("invm", "vm://0?serverId=1"); // Use in-vm connector with a unique serverId

        // Create and configure the embedded broker
        EmbeddedActiveMQ embeddedActiveMQ = new EmbeddedActiveMQ();
        embeddedActiveMQ.setConfiguration(config);

        // Start the broker
        embeddedActiveMQ.start();
        logger.info("Embedded Artemis broker started successfully!");
        return embeddedActiveMQ;
    }
}