package com.sharingif.cube.spring.boot.security.autoconfigure.components;

import com.sharingif.cube.security.authentication.user.CoreUserUniqueIdHandler;
import com.sharingif.cube.security.binary.Base64Coder;
import com.sharingif.cube.security.confidentiality.encrypt.BCryptTextEncryptor;
import com.sharingif.cube.security.confidentiality.encrypt.digest.SHA256Encryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Bean(name="coreUserUniqueIdHandler")
    public CoreUserUniqueIdHandler createCoreUserUniqueIdHandler() {

        return new CoreUserUniqueIdHandler();
    }

    @Bean(name="sessionRegistryImpl")
    public SessionRegistryImpl createSessionRegistryImpl() {

        return new SessionRegistryImpl();
    }

}
