package com.sharingif.cube.spring.boot.components.autoconfigure.components;

import com.sharingif.cube.components.sequence.UUIDSequenceGenerator;
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

}
