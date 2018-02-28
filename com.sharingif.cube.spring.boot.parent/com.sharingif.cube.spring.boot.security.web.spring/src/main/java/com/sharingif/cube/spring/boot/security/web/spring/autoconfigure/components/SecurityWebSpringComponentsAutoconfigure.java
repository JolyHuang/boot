package com.sharingif.cube.spring.boot.security.web.spring.autoconfigure.components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

}
