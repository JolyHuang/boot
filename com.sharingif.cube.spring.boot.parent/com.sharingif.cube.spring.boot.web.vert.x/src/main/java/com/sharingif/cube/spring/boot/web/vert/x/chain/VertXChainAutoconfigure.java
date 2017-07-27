package com.sharingif.cube.spring.boot.web.vert.x.chain;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.AnnotationHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.security.web.handler.chain.CoreUserContextHolderChain;
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
    public MultiHandlerMethodChain<HttpHandlerMethodContent> createVertxWebHandlerMethodChain(
            MultiHandlerMethodChain<HttpHandlerMethodContent> webHandlerMethodChain
           ,VertXDispatcherHandlerExceptionChain vertxDispatcherHandlerExceptionChain
            ) {

        webHandlerMethodChain.getChains().add(vertxDispatcherHandlerExceptionChain);

        return webHandlerMethodChain;
    }

    @Bean(name="vertxControllerChains")
    @ConditionalOnMissingBean(name="vertxControllerChains")
    public MultiHandlerMethodChain<HttpHandlerMethodContent> createSpringMCVChains(
            CoreUserContextHolderChain coreUserContextHolderChain
            ,MonitorPerformanceChain controllerMonitorPerformanceChain
            ,AnnotationHandlerMethodChain<HandlerMethodContent> annotationHandlerMethodChain
    ) {

        List<HandlerMethodChain<? super HttpHandlerMethodContent>> chains = new ArrayList<HandlerMethodChain<? super HttpHandlerMethodContent>>(3);
        chains.add(coreUserContextHolderChain);
        chains.add(controllerMonitorPerformanceChain);
        chains.add(annotationHandlerMethodChain);

        MultiHandlerMethodChain<HttpHandlerMethodContent> springMVCHandlerMethodContent = new MultiHandlerMethodChain<HttpHandlerMethodContent>();
        springMVCHandlerMethodContent.setChains(chains);

        return  springMVCHandlerMethodContent;
    }

}
