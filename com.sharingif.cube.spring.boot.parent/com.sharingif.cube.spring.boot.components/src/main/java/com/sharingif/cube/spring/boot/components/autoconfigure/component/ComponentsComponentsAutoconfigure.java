package com.sharingif.cube.spring.boot.components.autoconfigure.component;

import com.sharingif.cube.components.json.IJsonService;
import com.sharingif.cube.components.json.JacksonJsonService;
import com.sharingif.cube.components.sequence.ISequenceHandler;
import com.sharingif.cube.components.sequence.SequenceHandlerImpl;
import com.sharingif.cube.components.sequence.UUIDSequenceGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Joly on 2017/5/19.
 */
@Configuration
public class ComponentsComponentsAutoconfigure {

    @Bean(name="sequenceHandler")
    public ISequenceHandler createSequenceHandler() {
        SequenceHandlerImpl sequenceHandler = new SequenceHandlerImpl();

        return sequenceHandler;
    }

    @Bean(name="uuidSequenceGenerator")
    public UUIDSequenceGenerator createUUIDSequenceGenerator() {
        UUIDSequenceGenerator uuidSequenceGenerator = new UUIDSequenceGenerator();

        return uuidSequenceGenerator;
    }

    @Bean(name="jsonService")
    @ConditionalOnMissingBean(name = "jsonService")
    public IJsonService createJsonService() {
        return new JacksonJsonService();
    }

}
