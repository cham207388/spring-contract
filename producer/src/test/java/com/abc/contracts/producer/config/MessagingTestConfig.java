package com.abc.contracts.producer.config;

import com.abc.contracts.producer.verifier.StreamMessageVerifierReceiver;
import com.abc.contracts.producer.verifier.StreamMessageVerifierSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierObjectMapper;
import org.springframework.context.annotation.Bean;

public class MessagingTestConfig {

    private final StreamMessageVerifierSender messageVerifierSender;
    private final StreamMessageVerifierReceiver messageVerifierReceiver;

    public MessagingTestConfig(StreamMessageVerifierSender messageVerifierSender, StreamMessageVerifierReceiver messageVerifierReceiver) {
        this.messageVerifierSender = messageVerifierSender;
        this.messageVerifierReceiver = messageVerifierReceiver;
    }

    @Bean
    public ContractVerifierObjectMapper contractVerifierObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_SELF_REFERENCES_AS_NULL, true);

        return new ContractVerifierObjectMapper(objectMapper);
    }


    @Bean
    public ContractVerifierMessaging<?> contractVerifierMessaging() {
        return new ContractVerifierMessaging<>(messageVerifierSender, messageVerifierReceiver);
    }
}
