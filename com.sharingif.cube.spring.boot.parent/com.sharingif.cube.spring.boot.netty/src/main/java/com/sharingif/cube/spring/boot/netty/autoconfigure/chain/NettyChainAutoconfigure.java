package com.sharingif.cube.spring.boot.netty.autoconfigure.chain;

import com.sharingif.cube.core.handler.chain.AnnotationHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class NettyChainAutoconfigure {

    @Bean("nettyHandlerMethodChain")
    public MultiHandlerMethodChain createNettyHandlerMethodChain(
            MultiHandlerMethodChain webHandlerMethodChain
    ) {

        return webHandlerMethodChain;
    }

    @Bean(name="nettyControllerChains")
    @ConditionalOnMissingBean(name="nettyControllerChains")
    public MultiHandlerMethodChain createNettyControllerChains(
            MonitorPerformanceChain controllerMonitorPerformanceChain
            , AnnotationHandlerMethodChain annotationHandlerMethodChain
    ) {

        List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>();
        chains.add(controllerMonitorPerformanceChain);
        chains.add(annotationHandlerMethodChain);

        MultiHandlerMethodChain nettyControllerChains = new MultiHandlerMethodChain();
        nettyControllerChains.setChains(chains);

        return  nettyControllerChains;
    }

}
