package com.sharingif.cube.spring.boot.security.autoconfigure.components;

import com.sharingif.cube.components.channel.IChannelContext;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.authentication.AuthenticationHander;
import com.sharingif.cube.security.authentication.authority.tree.TreeMapAuthorityHandler;
import com.sharingif.cube.security.authentication.role.IRoleAuthenticationHandler;
import com.sharingif.cube.security.binary.Base64Coder;
import com.sharingif.cube.security.confidentiality.encrypt.BCryptTextEncryptor;
import com.sharingif.cube.security.confidentiality.encrypt.digest.SHA256Encryptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * SecurityComponentsAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/6/21 下午8:22
 */
@Configuration
public class SecurityComponentsAutoconfigure {

    @Bean(name="bcryptTextEncryptor")
    @ConditionalOnBean(name="bcryptPasswordEncoder")
    public BCryptTextEncryptor createBCryptTextEncryptor(BCryptPasswordEncoder bcryptPasswordEncoder) {
        BCryptTextEncryptor bcryptTextEncryptor = new BCryptTextEncryptor();
        bcryptTextEncryptor.setBcryptPasswordEncoder(bcryptPasswordEncoder);

        return bcryptTextEncryptor;
    }

    @Bean(name="sha256Encryptor")
    public SHA256Encryptor createSHA256Encryptor() {
        SHA256Encryptor sha256Encryptor = new SHA256Encryptor();

        return sha256Encryptor;
    }

    @Bean(name="base64Coder")
    public Base64Coder createBase64Coder() {
        Base64Coder base64Coder = new Base64Coder();

        return base64Coder;
    }

    @Bean(name="sessionRegistryImpl")
    public SessionRegistryImpl createSessionRegistryImpl() {

        return new SessionRegistryImpl();
    }

    @Bean(name="authenticationHanders")
    @ConditionalOnMissingBean(name="authenticationHanders")
    public List<AuthenticationHander<? super ICoreUser, ? super IChannelContext>> createAuthenticationHanders(AuthenticationHander<? super ICoreUser, ? super IChannelContext> passwordAuthenticationHandler) {
        List<AuthenticationHander<? super ICoreUser, ? super IChannelContext>> authenticationHanders = new ArrayList<>();
        authenticationHanders.add(passwordAuthenticationHandler);

        return authenticationHanders;
    }

    @Bean(name="roleAuthenticationHandlers")
    @ConditionalOnMissingBean(name="roleAuthenticationHandlers")
    public List<IRoleAuthenticationHandler<? super ICoreUser>> createRoleAuthenticationHandlers(IRoleAuthenticationHandler<? super ICoreUser> roleAuthenticationHandler) {
        List<IRoleAuthenticationHandler<? super ICoreUser>> roleAuthenticationHandlers = new ArrayList<>();
        roleAuthenticationHandlers.add(roleAuthenticationHandler);

        return roleAuthenticationHandlers;
    }

    @Bean(name="treeMapAuthorityHandler")
    public TreeMapAuthorityHandler createTreeMapAuthorityHandler() {
        return new TreeMapAuthorityHandler();
    }

}
