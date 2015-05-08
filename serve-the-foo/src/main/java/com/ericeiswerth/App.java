package com.ericeiswerth;

import com.ericeiswerth.inject.AppModule;
import com.ericeiswerth.server.Server;
import com.ericeiswerth.server.message.MessageReceiver;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) {
        LOGGER.info("Initializing...");
        final Injector injector = Guice.createInjector(new AppModule());

        LOGGER.info("Starting message receiver...");
        final MessageReceiver messageReceiver = injector.getInstance(MessageReceiver.class);
        final Thread messageReceiverThread = new Thread(messageReceiver);
        messageReceiverThread.setDaemon(true);
        messageReceiverThread.start();
        LOGGER.info("Message receiver started.");

        final Server server = injector.getInstance(Server.class);
        LOGGER.info("Initialization complete!");
        server.start();
    }
}
