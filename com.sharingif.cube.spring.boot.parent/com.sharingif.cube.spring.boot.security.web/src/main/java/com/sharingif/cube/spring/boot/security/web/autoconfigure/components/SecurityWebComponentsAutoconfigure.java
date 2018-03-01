package com.sharingif.cube.spring.boot.security.web.autoconfigure.components;

import com.sharingif.cube.web.user.CoreUserHttpSessionManage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SecurityWebComponentsAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/3/1 下午1:25
 */
@Configuration
public class SecurityWebComponentsAutoconfigure {

    @Bean(name="coreUserHttpSessionManage")
    public CoreUserHttpSessionManage createCoreUserHttpSessionManage() {
        return new CoreUserHttpSessionManage();
    }

}
