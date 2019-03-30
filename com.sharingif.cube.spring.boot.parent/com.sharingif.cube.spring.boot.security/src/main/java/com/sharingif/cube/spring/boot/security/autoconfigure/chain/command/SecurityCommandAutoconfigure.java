package com.sharingif.cube.spring.boot.security.autoconfigure.chain.command;

import com.sharingif.cube.components.channel.IChannelContext;
import com.sharingif.cube.core.user.ICoreUser;
import com.sharingif.cube.security.authentication.AuthenticationHander;
import com.sharingif.cube.security.authentication.role.IRoleAuthenticationHandler;
import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.security.handler.chain.command.authentication.RoleAuthenticationCommand;
import com.sharingif.cube.security.handler.chain.command.authentication.SecurityAuthenticationCommand;
import com.sharingif.cube.security.handler.chain.command.password.PasswordEncryptorCommand;
import com.sharingif.cube.security.handler.chain.command.user.RemoveUserPasswordCommand;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * SecurityCommandAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/2/27 下午6:22
 */
@Configuration
public class SecurityCommandAutoconfigure {

    @Bean(name="passwordEncryptorCommand")
    @ConditionalOnMissingBean(name="passwordEncryptorCommand")
    @ConditionalOnBean(name="bcryptPasswordEncoder")
    public PasswordEncryptorCommand createPasswordEncryptorCommand(TextEncryptor bcryptTextEncryptor) {
        PasswordEncryptorCommand passwordEncryptorCommand = new PasswordEncryptorCommand();
        passwordEncryptorCommand.setTextEncryptor(bcryptTextEncryptor);

        return passwordEncryptorCommand;
    }

    @Bean(name="removeUserPasswordCommand")
    public RemoveUserPasswordCommand createRemoveUserPasswordCommand() {

        return new RemoveUserPasswordCommand();
    }

    @Bean(name="securityAuthenticationCommand")
    @ConditionalOnBean(name = "authenticationHanders")
    public SecurityAuthenticationCommand createSecurityAuthenticationCommand(List<AuthenticationHander<? super ICoreUser, ? super IChannelContext>> authenticationHanders) {
        SecurityAuthenticationCommand securityAuthenticationCommand = new SecurityAuthenticationCommand();
        securityAuthenticationCommand.setAuthenticationHanders(authenticationHanders);

        return securityAuthenticationCommand;
    }

    @Bean(name="roleAuthenticationCommand")
    @ConditionalOnBean(name = "roleAuthenticationHandlers")
    public RoleAuthenticationCommand createRoleAuthenticationCommand(List<IRoleAuthenticationHandler<? super ICoreUser>> roleAuthenticationHandlers) {
        RoleAuthenticationCommand roleAuthenticationCommand = new RoleAuthenticationCommand();
        roleAuthenticationCommand.setRoleAuthenticationHandlers(roleAuthenticationHandlers);

        return roleAuthenticationCommand;
    }

}
