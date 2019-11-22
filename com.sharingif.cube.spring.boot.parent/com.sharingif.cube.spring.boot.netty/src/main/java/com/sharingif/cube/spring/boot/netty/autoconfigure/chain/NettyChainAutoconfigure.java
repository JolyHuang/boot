package com.sharingif.cube.spring.boot.netty.autoconfigure.chain;

import com.sharingif.cube.components.handler.chain.RequestLocalContextHolderChain;
import com.sharingif.cube.core.handler.chain.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class NettyChainAutoconfigure {

    @Bean("nettyHandlerMethodChain")
    @ConditionalOnMissingBean(name = "nettyHandlerMethodChain")
    public MultiHandlerMethodChain createNettyHandlerMethodChain(
            RequestLocalContextHolderChain requestLocalContextHolderChain
            , MDCChain mdcChain
            , MonitorPerformanceChain transactionMonitorPerformanceChain
    ) {

        List<HandlerMethodChain> chains = new ArrayList<>();
        chains.add(requestLocalContextHolderChain);
        chains.add(mdcChain);
        chains.add(transactionMonitorPerformanceChain);

        MultiHandlerMethodChain nettyHandlerMethodChain = new MultiHandlerMethodChain();
        nettyHandlerMethodChain.setChains(chains);

        return  nettyHandlerMethodChain;
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
