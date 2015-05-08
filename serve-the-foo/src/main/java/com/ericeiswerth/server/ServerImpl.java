package com.ericeiswerth.server;

import com.ericeiswerth.inject.ServerThreadFactory;
import com.google.inject.Inject;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Eric Eiswerth
 */
public class ServerImpl implements Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);

    private final ServerThreadFactory serverThreadFactory;

    @Inject
    public ServerImpl(ServerThreadFactory serverThreadFactory) {
        Validate.notNull(serverThreadFactory, "Server thread factory cannot be null.");
        this.serverThreadFactory = serverThreadFactory;
    }

    @Override
    public void start() {
        LOGGER.info("Starting server...");

        try {
            final ServerSocket serverSocket = new ServerSocket(9190);

            LOGGER.info("Server started!");
            LOGGER.info("Awaiting client connection.");

            while (true) {
                // Listen for incoming clients forever.
                try {
                    final Socket socket = serverSocket.accept();
                    LOGGER.info("Received connection on port: " + socket.getPort());

                    final ServerThread serverThread = serverThreadFactory.create(socket);
                    serverThread.start();
                } catch (IOException ioe) {
                    LOGGER.error("Error while accepting incoming connection.");
                }
            }
        } catch (IOException ioe) {
            LOGGER.error("Server failed to start.", ioe);
        }
    }
}
