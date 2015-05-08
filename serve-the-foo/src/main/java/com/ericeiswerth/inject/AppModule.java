package com.ericeiswerth.inject;

import com.ericeiswerth.server.Server;
import com.ericeiswerth.server.ServerImpl;
import com.ericeiswerth.server.ServerThread;
import com.ericeiswerth.server.ServerThreadImpl;
import com.ericeiswerth.server.message.MessageReceiver;
import com.ericeiswerth.server.message.MessageReceiverImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/**
 * @author Eric Eiswerth
 */
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Server.class).to(ServerImpl.class);

        install(new FactoryModuleBuilder().implement(ServerThread.class, ServerThreadImpl.class)
                .build(ServerThreadFactory.class));

        bind(MessageReceiver.class).to(MessageReceiverImpl.class).in(Scopes.SINGLETON);
    }
}
