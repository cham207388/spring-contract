package com.abc.contracts.producer;

import com.abc.contracts.producer.config.MessagingTestConfig;
import com.abc.contracts.producer.config.StreamConfig;
import com.abc.contracts.producer.domains.Post;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@Import({MessagingTestConfig.class, StreamConfig.class}) // Import StreamConfig
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public abstract class BaseMessageTest {

    @Autowired
    private ContractVerifierMessaging<MessageChannel> contractVerifierMessaging;

    protected void triggerPostMessage()  {
        Post post = new Post(1, "Test title", "Test content", 1, LocalDateTime.parse("2025-01-28T21:58:21"));
        ContractVerifierMessage message = new ContractVerifierMessage(post, Map.of("Content-Type", "application/json"));
        contractVerifierMessaging.send(message, "outputChannel");
    }
}