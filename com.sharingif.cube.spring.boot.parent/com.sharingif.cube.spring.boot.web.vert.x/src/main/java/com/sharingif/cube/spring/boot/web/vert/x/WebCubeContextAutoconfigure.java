package com.sharingif.cube.spring.boot.web.vert.x;

import com.sharingif.cube.communication.view.MultiViewResolver;
import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import com.sharingif.cube.web.vert.x.VertXServer;
import com.sharingif.cube.web.vert.x.handler.VertXDispatcherHandler;
import com.sharingif.cube.web.vert.x.request.VertXRequestInfoResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WebCubeContextAutoconfigure
 * 2017/5/20 下午11:00
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class WebCubeContextAutoconfigure {

	@Bean("vertXDispatcherHandler")
	public VertXDispatcherHandler createVertXDispatcherHandler(
            MultiHandlerMethodChain vertxWebHandlerMethodChain
            ,VertXRequestInfoResolver vertXRequestInfoResolver
            ,MultiHandlerMapping multiHandlerMapping
            ,MultiHandlerMethodAdapter multiHandlerMethodAdapter
            ,MultiCubeExceptionHandler multiCubeExceptionHandler
            ,MultiViewResolver multiViewResolver
         ) {
        VertXDispatcherHandler vertXDispatcherHandler = new VertXDispatcherHandler();
        vertXDispatcherHandler.setHandlerMethodChain(vertxWebHandlerMethodChain);
        vertXDispatcherHandler.setRequestInfoResolver(vertXRequestInfoResolver);
        vertXDispatcherHandler.setMultiHandlerMapping(multiHandlerMapping);
        vertXDispatcherHandler.setMultiHandlerMethodAdapter(multiHandlerMethodAdapter);
        vertXDispatcherHandler.setMultiCubeExceptionHandler(multiCubeExceptionHandler);
        vertXDispatcherHandler.setMultiViewResolver(multiViewResolver);

        return vertXDispatcherHandler;
    }

    @Bean(name="vertXServer")
    public VertXServer createVertXServer(
            @Value("${vertx.server.host}")String host
            ,@Value("${vertx.server.port}") int port
            ,@Value("${vertx.server.contextPath}")String contextPath
            ,VertXDispatcherHandler vertXDispatcherHandler
            ) {

        VertXServer vertXServer = new VertXServer();
        vertXServer.setHost(host);
        vertXServer.setPort(port);
        vertXServer.setContextPath(contextPath);
        vertXServer.setDispatcherHandler(vertXDispatcherHandler);

        return vertXServer;

    }

}
