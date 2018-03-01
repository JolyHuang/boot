package com.sharingif.cube.spring.boot.security.web.spring.autoconfigure.chain.command;

import com.sharingif.cube.security.web.authentication.ISessionConcurrentHandler;
import com.sharingif.cube.security.web.handler.chain.command.authentication.SessionConcurrentWebCommand;
import com.sharingif.cube.security.web.spring.handler.chain.command.session.SessionRegistryCommand;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;

/**
 * SecurityWebSpringCommandAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/3/1 上午11:04
 */
@Configuration
public class SecurityWebSpringCommandAutoconfigure {

    @Bean(name="sessionConcurrentWebCommand")
    public SessionConcurrentWebCommand createSessionConcurrentWebCommand(ISessionConcurrentHandler sessionConcurrentHandlerImpl) {
        SessionConcurrentWebCommand sessionConcurrentWebCommand = new SessionConcurrentWebCommand();
        sessionConcurrentWebCommand.setSessionConcurrentHandler(sessionConcurrentHandlerImpl);

        return sessionConcurrentWebCommand;
    }

    @Bean(name="sessionRegistryCommand")
    public SessionRegistryCommand createSessionRegistryCommand(SessionRegistry sessionRegistryImpl) {
        SessionRegistryCommand sessionRegistryCommand = new SessionRegistryCommand();
        sessionRegistryCommand.setSessionRegistry(sessionRegistryImpl);

        return sessionRegistryCommand;
    }

}
