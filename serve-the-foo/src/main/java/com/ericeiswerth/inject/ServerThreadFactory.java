package com.ericeiswerth.inject;

import com.ericeiswerth.server.ServerThreadImpl;

import java.net.Socket;

/**
 * @author Eric Eiswerth
 */
public interface ServerThreadFactory {

    public ServerThreadImpl create(Socket socket);
    
}
