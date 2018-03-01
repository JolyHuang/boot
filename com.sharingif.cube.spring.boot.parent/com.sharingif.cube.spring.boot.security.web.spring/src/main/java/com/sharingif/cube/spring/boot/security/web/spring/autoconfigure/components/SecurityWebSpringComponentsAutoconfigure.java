package com.sharingif.cube.spring.boot.security.web.spring.autoconfigure.components;

import com.sharingif.cube.security.web.spring.access.SessionExpireHandlerImpl;
import com.sharingif.cube.security.web.spring.authentication.SessionConcurrentHandlerImpl;
import com.sharingif.cube.security.web.spring.authentication.SignOutHandlerImpl;
import com.sharingif.cube.security.web.spring.authentication.session.ExtendedConcurrentSessionControlAuthenticationStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * SecurityWebSpringComponentsAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/2/27 下午6:51
 */
@Configuration
public class SecurityWebSpringComponentsAutoconfigure {

    @Bean(name="bcryptPasswordEncoder")
    public BCryptPasswordEncoder createBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name="extendedConcurrentSessionControlAuthenticationStrategy")
    @ConditionalOnMissingBean(name="extendedConcurrentSessionControlAuthenticationStrategy")
    public ExtendedConcurrentSessionControlAuthenticationStrategy createExtendedConcurrentSessionControlAuthenticationStrategy(SessionRegistry sessionRegistryImpl) {
        ExtendedConcurrentSessionControlAuthenticationStrategy extendedConcurrentSessionControlAuthenticationStrategy = new ExtendedConcurrentSessionControlAuthenticationStrategy(sessionRegistryImpl);
        extendedConcurrentSessionControlAuthenticationStrategy.setMaximumSessions(1);
        extendedConcurrentSessionControlAuthenticationStrategy.setExceptionIfMaximumExceeded(false);


        return extendedConcurrentSessionControlAuthenticationStrategy;
    }

    @Bean(name="sessionConcurrentHandlerImpl")
    @ConditionalOnMissingBean(name="sessionConcurrentHandlerImpl")
    public SessionConcurrentHandlerImpl createSessionConcurrentHandlerImpl(SessionAuthenticationStrategy extendedConcurrentSessionControlAuthenticationStrategy) {
        SessionConcurrentHandlerImpl sessionConcurrentHandlerImpl = new SessionConcurrentHandlerImpl();
        sessionConcurrentHandlerImpl.setSessionAuthenticationStrategy(extendedConcurrentSessionControlAuthenticationStrategy);

        return sessionConcurrentHandlerImpl;
    }

    @Bean(name="securityContextLogoutHandler")
    public SecurityContextLogoutHandler createSecurityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean(name="signOutHandlerImpl")
    @ConditionalOnMissingBean(name="signOutHandlerImpl")
    public SignOutHandlerImpl createSignOutHandlerImpl(
            SessionRegistry sessionRegistryImpl
            ,LogoutHandler securityContextLogoutHandler
    ) {
        SignOutHandlerImpl signOutHandlerImpl = new SignOutHandlerImpl();
        signOutHandlerImpl.setSessionRegistry(sessionRegistryImpl);
        signOutHandlerImpl.setLogoutHandler(securityContextLogoutHandler);

        return signOutHandlerImpl;
    }

    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(
                new HttpSessionEventPublisher());
    }

}
