package com.sharingif.cube.spring.boot.communication.autoconfigure.components;

import com.sharingif.cube.communication.exception.JsonModelBusinessCommunicationExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CommunicationHttpComponentsAutoconfigure
 * 2017年5月12日 下午5:42:05
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class CommunicationHttpComponentsAutoconfigure {

    @Bean("jsonModelBusinessCommunicationExceptionHandler")
    public JsonModelBusinessCommunicationExceptionHandler createJsonModelBusinessCommunicationExceptionHandler() {

        return new JsonModelBusinessCommunicationExceptionHandler();
    }


}
