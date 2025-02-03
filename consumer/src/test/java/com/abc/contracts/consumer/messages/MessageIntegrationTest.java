package com.abc.contracts.consumer.messages;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureStubRunner(
        ids = {"com.abc.contracts:producer:0.0.1-RELEASE:stubs"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)
@ActiveProfiles("test")
class MessageIntegrationTest {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private StubFinder stubFinder; // ✅ Inject StubFinder

    @Test
    void should_receive_message_from_stub() {
        System.out.println("🔄 Waiting for message from order.queue...");

        // ✅ Dynamically get the correct queue name
        String queue = stubFinder.findStubUrl("com.abc.contracts:producer").toString();
        System.out.println("✅ Stub queue discovered: " + queue);

        // ✅ Send a test message manually
        String message = """
            {
                "orderId": "123ABC7890",
                "product": "Laptop",
                "quantity": 1
            }
        """;
        jmsTemplate.convertAndSend("order.queue", message);

        // ✅ Receive the message from ActiveMQ
        String receivedMessage = (String) jmsTemplate.receiveAndConvert("order.queue");
        System.out.println(receivedMessage);

        // ✅ Assertions
        assertThat(receivedMessage).isNotNull().contains("orderId", "product", "quantity");

        System.out.println("✅ Received message: " + receivedMessage);
    }
}
