package com.sharingif.cube.spring.boot.web.vert.x.chain;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.web.vert.x.handler.chain.VertXDispatcherHandlerExceptionChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean("vertXDispatcherHandlerExceptionChain")
    public VertXDispatcherHandlerExceptionChain createVertXDispatcherHandlerExceptionChain() {
        VertXDispatcherHandlerExceptionChain vertXDispatcherHandlerExceptionChain = new VertXDispatcherHandlerExceptionChain();

        return vertXDispatcherHandlerExceptionChain;
    }

    @Bean("vertxWebHandlerMethodChain")
    public MultiHandlerMethodChain<HttpHandlerMethodContent> createVertxWebHandlerMethodChain(
            MultiHandlerMethodChain<HttpHandlerMethodContent> webHandlerMethodChain
           ,VertXDispatcherHandlerExceptionChain vertXDispatcherHandlerExceptionChain
            ) {

        webHandlerMethodChain.getChains().add(vertXDispatcherHandlerExceptionChain);

        return webHandlerMethodChain;
    }

}
