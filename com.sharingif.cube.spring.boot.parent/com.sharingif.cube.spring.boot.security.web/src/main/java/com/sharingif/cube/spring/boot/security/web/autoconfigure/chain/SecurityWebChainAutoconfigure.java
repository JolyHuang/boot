package com.sharingif.cube.spring.boot.security.web.autoconfigure.chain;

import com.sharingif.cube.core.chain.ChainImpl;
import com.sharingif.cube.core.chain.command.Command;
import com.sharingif.cube.core.handler.chain.HandlerMethodContent;
import com.sharingif.cube.security.web.access.ISessionExpireHandler;
import com.sharingif.cube.security.web.handler.chain.command.authentication.SignOutWebCommand;
import com.sharingif.cube.security.web.handler.chain.session.SessionExpireChain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * SecurityWebChainAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/3/1 下午7:18
 */
@Configuration
public class SecurityWebChainAutoconfigure {

    @Bean(name="sessionExpireChain")
    @ConditionalOnMissingBean(name="sessionExpireChain")
    public SessionExpireChain createSessionExpireChain(ISessionExpireHandler sessionExpireHandlerImpl) {
        SessionExpireChain sessionExpireChain = new SessionExpireChain();
        sessionExpireChain.setSessionExpireHandler(sessionExpireHandlerImpl);

        return sessionExpireChain;
    }

    @Bean(name="signOutChain")
    public ChainImpl<HandlerMethodContent> createSignOutChain(SignOutWebCommand signOutWebCommand) {
        List<Command<? super HandlerMethodContent>> commands = new ArrayList<Command<? super HandlerMethodContent>>();
        commands.add(signOutWebCommand);

        ChainImpl signOutChain = new ChainImpl();
        signOutChain.setCommands(commands);

        return signOutChain;
    }

}
