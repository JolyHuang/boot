package com.sharingif.cube.spring.boot.web.vert.x.chain;

import com.sharingif.cube.core.handler.chain.AnnotationHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.web.vert.x.handler.chain.VertXDispatcherHandlerExceptionChain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * VertXChainAutoconfigure
 * 2017/5/20 下午11:27
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class VertXChainAutoconfigure {

    @Bean("vertxDispatcherHandlerExceptionChain")
    public VertXDispatcherHandlerExceptionChain createVertxDispatcherHandlerExceptionChain() {
        VertXDispatcherHandlerExceptionChain vertXDispatcherHandlerExceptionChain = new VertXDispatcherHandlerExceptionChain();

        return vertXDispatcherHandlerExceptionChain;
    }

    @Bean("vertxWebHandlerMethodChain")
    public MultiHandlerMethodChain createVertxWebHandlerMethodChain(
            MultiHandlerMethodChain webHandlerMethodChain
           ,VertXDispatcherHandlerExceptionChain vertxDispatcherHandlerExceptionChain
            ) {

        webHandlerMethodChain.getChains().add(vertxDispatcherHandlerExceptionChain);

        return webHandlerMethodChain;
    }

    @Bean(name="vertxControllerChains")
    @ConditionalOnMissingBean(name="vertxControllerChains")
    public MultiHandlerMethodChain createVertxControllerChains(
            MonitorPerformanceChain controllerMonitorPerformanceChain
            ,AnnotationHandlerMethodChain annotationHandlerMethodChain
    ) {

        List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>();
        chains.add(controllerMonitorPerformanceChain);
        chains.add(annotationHandlerMethodChain);

        MultiHandlerMethodChain vertxControllerChains = new MultiHandlerMethodChain();
        vertxControllerChains.setChains(chains);

        return  vertxControllerChains;
    }

}
