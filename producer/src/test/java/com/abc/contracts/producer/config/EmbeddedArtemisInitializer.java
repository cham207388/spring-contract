package com.abc.contracts.producer.config;

import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

@Component
public class EmbeddedArtemisInitializer implements SmartLifecycle {

    private final EmbeddedActiveMQ embeddedActiveMQ;
    private boolean running = false;

    public EmbeddedArtemisInitializer() {
        this.embeddedActiveMQ = new EmbeddedActiveMQ();
    }

    @Override
    public void start() {
        if (!running) {
            try {
                System.out.println("Starting embedded Artemis broker...");
                embeddedActiveMQ.start();
                running = true;
                System.out.println("Embedded Artemis broker started successfully!");
            } catch (Exception e) {
                throw new RuntimeException("Failed to start embedded Artemis broker", e);
            }
        }
    }

    @Override
    public void stop() {
        if (running) {
            try {
                System.out.println("Stopping embedded Artemis broker...");
                embeddedActiveMQ.stop();
                running = false;
                System.out.println("Embedded Artemis broker stopped successfully!");
            } catch (Exception e) {
                throw new RuntimeException("Failed to stop embedded Artemis broker", e);
            }
        }
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE; // Ensure this runs before other components
    }

    public static void stopBroker() {
        // This method can be called statically to stop the broker
        // Useful for cleanup in tests
        EmbeddedActiveMQ embeddedActiveMQ = new EmbeddedActiveMQ();
        try {
            System.out.println("Stopping embedded Artemis broker...");
            embeddedActiveMQ.stop();
            System.out.println("Embedded Artemis broker stopped successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Failed to stop embedded Artemis broker", e);
        }
    }
}