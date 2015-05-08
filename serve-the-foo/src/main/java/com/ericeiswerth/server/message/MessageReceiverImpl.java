package com.ericeiswerth.server.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Eric Eiswerth
 */
public class MessageReceiverImpl extends Thread implements MessageReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiverImpl.class);

    private final BlockingQueue<Message> queue = new ArrayBlockingQueue<>(10);
    private final AtomicBoolean ready = new AtomicBoolean(true);

    @Override
    public void receive(Message message) {
        if (message == null) {
            LOGGER.warn("Received null message: {}", message.getSource());
            return;
        }
        try {
            queue.put(message);
        } catch (InterruptedException ie) {
            LOGGER.warn("Message receiver interrupted. About to shutdown.");
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                LOGGER.info("Waiting for a message.");
                final Message message = queue.take();
                LOGGER.info("Processing message...{}", message);
            } catch (InterruptedException ie) {
                LOGGER.error("Message queue interrupted. Shutting down message receiver.", ie);
                return;
            }
        }
    }
}
