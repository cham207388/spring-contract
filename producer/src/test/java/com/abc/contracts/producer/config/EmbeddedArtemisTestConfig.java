package com.abc.contracts.producer.config;

import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EmbeddedArtemisTestConfig {

    private static EmbeddedActiveMQ broker = new EmbeddedActiveMQ();
    private static volatile boolean isStarted = false;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EmbeddedActiveMQ embeddedActiveMQ() throws Exception {
        synchronized (EmbeddedArtemisTestConfig.class) {
            if (isStarted) {
                return broker;
            }

            Configuration config = new ConfigurationImpl()
                    .setPersistenceEnabled(false)
                    .setSecurityEnabled(false)
                    .addAcceptorConfiguration("invm", "vm://0?serverId=1");

            broker.setConfiguration(config);
            broker.start();

            isStarted = true;
            return broker;
        }
    }

    public static void stopBroker() {
        synchronized (EmbeddedArtemisTestConfig.class) {
            if (isStarted) {
                try {
                    broker.stop();
                    isStarted = false;
                } catch (Exception e) {
                    System.err.println("Error stopping Embedded Artemis broker: " + e.getMessage());
                }
            }
        }
    }
}