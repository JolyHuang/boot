package com.sharingif.cube.spring.boot.web.vert.x.chain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sharingif.cube.core.handler.chain.AnnotationHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.security.web.handler.chain.CoreUserContextHolderChain;
import com.sharingif.cube.web.vert.x.handler.chain.VertXDispatcherHandlerExceptionChain;

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
    public MultiHandlerMethodChain createSpringMCVChains(
            CoreUserContextHolderChain coreUserContextHolderChain
            ,MonitorPerformanceChain controllerMonitorPerformanceChain
            ,AnnotationHandlerMethodChain annotationHandlerMethodChain
    ) {

        List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>(3);
        chains.add(coreUserContextHolderChain);
        chains.add(controllerMonitorPerformanceChain);
        chains.add(annotationHandlerMethodChain);

        MultiHandlerMethodChain springMVCHandlerMethodContent = new MultiHandlerMethodChain();
        springMVCHandlerMethodContent.setChains(chains);

        return  springMVCHandlerMethodContent;
    }

}
