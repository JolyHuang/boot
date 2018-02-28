package com.sharingif.cube.spring.boot.security.autoconfigure.components;

import com.sharingif.cube.security.confidentiality.encrypt.BCryptTextEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

}
