package com.ericeiswerth.server;

import com.ericeiswerth.server.message.Message;
import com.ericeiswerth.server.message.MessageReceiver;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Eric Eiswerth
 */
public class ServerThreadImpl extends Thread implements ServerThread {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerThreadImpl.class);

    private final MessageReceiver messageReceiver;

    private final Socket socket;

    @Inject
    ServerThreadImpl(MessageReceiver messageReceiver, @Assisted Socket socket) {
        Validate.notNull(messageReceiver, "Message receiver cannot be null.");
        Validate.notNull(socket, "Socket cannot be null.");
        this.messageReceiver = messageReceiver;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final String content = br.readLine();
                final Gson gson = new Gson();
                final Message message = gson.fromJson(content, Message.class);
                messageReceiver.receive(message);
            } catch (IOException ioe) {
                LOGGER.error("Error reading from client.");
            }
        }
    }
}
