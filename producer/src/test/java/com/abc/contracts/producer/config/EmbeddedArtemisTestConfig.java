package com.abc.contracts.producer.config;

import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EmbeddedArtemisTestConfig {

    private static EmbeddedActiveMQ broker = new EmbeddedActiveMQ();
    private static volatile boolean isStarted = false; // Track broker status

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EmbeddedActiveMQ embeddedActiveMQ() throws Exception {
        synchronized (EmbeddedArtemisTestConfig.class) {
            if (isStarted) {
                System.out.println("🚀 Broker already running! Skipping startup.");
                return broker;
            }

            System.out.println("🚀 Starting embedded Artemis broker...");

            Configuration config = new ConfigurationImpl()
                    .setPersistenceEnabled(false)
                    .setSecurityEnabled(false)
                    .addAcceptorConfiguration("invm", "vm://0?serverId=1");

            broker.setConfiguration(config);
            broker.start();

            isStarted = true;
            System.out.println("✅ Embedded Artemis broker started successfully!");
            System.out.println("Broker is running: " + broker.getActiveMQServer().isStarted());

            return broker;
        }
    }

    public static void stopBroker() {
        synchronized (EmbeddedArtemisTestConfig.class) {
            if (isStarted) {
                try {
                    System.out.println("🛑 Stopping embedded Artemis broker...");
                    broker.stop();
                    isStarted = false; // Reset flag
                    System.out.println("✅ Embedded Artemis broker stopped successfully!");
                } catch (Exception e) {
                    System.err.println("❌ Error stopping Embedded Artemis broker: " + e.getMessage());
                }
            }
        }
    }
}