package com.abc.contracts.producer.config;

import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EmbeddedArtemisTestConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EmbeddedActiveMQ embeddedActiveMQ() throws Exception {
        System.out.println("Starting embedded Artemis broker...");

        // Programmatic configuration
        Configuration config = new ConfigurationImpl()
                .setPersistenceEnabled(false) // In-memory only
                .setSecurityEnabled(false)    // Disable security for simplicity
                .addAcceptorConfiguration("invm", "vm://0?serverId=" + System.currentTimeMillis()); // Use a unique serverId

        // Create and configure the embedded broker
        EmbeddedActiveMQ embeddedActiveMQ = new EmbeddedActiveMQ();
        embeddedActiveMQ.setConfiguration(config);

        // Start the broker
        embeddedActiveMQ.start();
        System.out.println("Embedded Artemis broker started successfully!");

        // Log broker status
        System.out.println("Broker is running: " + embeddedActiveMQ.getActiveMQServer().isStarted());

        // Add a small delay to ensure the broker is fully initialized
        Thread.sleep(3000); // 1 second delay
        return embeddedActiveMQ;
    }
}