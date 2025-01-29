package com.abc.contracts.producer.config;

import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EmbeddedArtemisTestConfig {

    @Bean
    public EmbeddedActiveMQ embeddedActiveMQ() throws Exception {
        EmbeddedActiveMQ embeddedActiveMQ = new EmbeddedActiveMQ();
        Configuration config = new ConfigurationImpl()
                .setPersistenceEnabled(false) // In-memory only
                .setSecurityEnabled(false)
                .addAcceptorConfiguration("invm", "vm://0");

        embeddedActiveMQ.setConfiguration(config);
        embeddedActiveMQ.start();
        return embeddedActiveMQ;
    }
}
