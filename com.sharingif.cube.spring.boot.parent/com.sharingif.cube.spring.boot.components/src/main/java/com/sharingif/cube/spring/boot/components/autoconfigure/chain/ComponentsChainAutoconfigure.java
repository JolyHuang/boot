package com.sharingif.cube.spring.boot.components.autoconfigure.chain;

import com.sharingif.cube.components.handler.chain.RequestLocalContextHolderChain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ComponentsChainAutoconfigure
 * 2017/6/7 下午1:15
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class ComponentsChainAutoconfigure {

    @Bean(name="requestLocalContextHolderChain")
    @ConditionalOnMissingBean(name = "requestLocalContextHolderChain")
    public RequestLocalContextHolderChain createRequestLocalContextHolderChain() {
        return new RequestLocalContextHolderChain();
    }

}
