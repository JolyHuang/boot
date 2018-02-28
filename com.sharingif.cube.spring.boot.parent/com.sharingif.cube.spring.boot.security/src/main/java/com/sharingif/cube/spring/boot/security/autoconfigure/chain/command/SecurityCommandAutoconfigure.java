package com.sharingif.cube.spring.boot.security.autoconfigure.chain.command;

import com.sharingif.cube.security.confidentiality.encrypt.TextEncryptor;
import com.sharingif.cube.security.handler.chain.command.password.PasswordEncryptorCommand;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    public PasswordEncryptorCommand createPasswordEncryptorCommand(TextEncryptor bcryptTextEncryptor) {
        PasswordEncryptorCommand passwordEncryptorCommand = new PasswordEncryptorCommand();
        passwordEncryptorCommand.setTextEncryptor(bcryptTextEncryptor);

        return passwordEncryptorCommand;
    }

}
