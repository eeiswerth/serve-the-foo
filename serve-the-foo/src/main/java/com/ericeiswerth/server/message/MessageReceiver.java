package com.ericeiswerth.server.message;

/**
 * @author Eric Eiswerth
 */
public interface MessageReceiver extends Runnable {

    public void receive(Message message);

}
