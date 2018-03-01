package com.sharingif.cube.spring.boot.security.web.autoconfigure.chain.command;

import com.sharingif.cube.security.web.handler.chain.command.user.InvalidateHttpSessionWebCommand;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SecurityWebCommandAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2018/3/1 下午1:17
 */
@Configuration
public class SecurityWebCommandAutoconfigure {

    @Bean(name="invalidateHttpSessionWebCommand")
    @ConditionalOnMissingBean(name="invalidateHttpSessionWebCommand")
    public InvalidateHttpSessionWebCommand createInvalidateHttpSessionWebCommand() {
        InvalidateHttpSessionWebCommand invalidateHttpSessionWebCommand = new InvalidateHttpSessionWebCommand();

        return invalidateHttpSessionWebCommand;
    }

}
