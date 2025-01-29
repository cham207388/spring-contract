//package com.abc.contracts.producer.jms;
//
//import jakarta.jms.*;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.support.converter.MessageConverter;
//import org.springframework.jms.support.converter.SimpleMessageConverter;
//import org.springframework.cloud.contract.verifier.messaging.MessageVerifierReceiver
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
//public class JmsMessageVerifier implements MessageVerifier<String> {
//
//    private final JmsTemplate jmsTemplate;
//    private final MessageConverter messageConverter;
//
//    public JmsMessageVerifier(JmsTemplate jmsTemplate) {
//        this.jmsTemplate = jmsTemplate;
//        this.messageConverter = new SimpleMessageConverter();
//    }
//
//    @Override
//    public void send(String message, Map<String, Object> headers, String destination) {
//        System.out.println("Sending message to {}: {}" + destination+ " " + message);
//        jmsTemplate.convertAndSend(destination, message);
//    }
//
//    @Override
//    public String receive(String destination, long timeout, TimeUnit timeUnit) {
//        log.info("Receiving message from {} with timeout {} {}", destination, timeout, timeUnit);
//        jmsTemplate.setReceiveTimeout(timeUnit.toMillis(timeout));
//        Message message = jmsTemplate.receive(destination);
//        return convertMessage(message);
//    }
//
//    @Override
//    public String receive(String destination) {
//        log.info("Receiving message from {}", destination);
//        Message message = jmsTemplate.receive(destination);
//        return convertMessage(message);
//    }
//
//    private String convertMessage(Message message) {
//        try {
//            if (message == null) {
//                return null;
//            }
//            return (String) messageConverter.fromMessage(message);
//        } catch (JMSException e) {
//            throw new RuntimeException("Error converting JMS message", e);
//        }
//    }
//}