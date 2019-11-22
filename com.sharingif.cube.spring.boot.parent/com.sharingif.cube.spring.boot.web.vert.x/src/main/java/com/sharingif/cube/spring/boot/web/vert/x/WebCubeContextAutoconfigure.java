package com.sharingif.cube.spring.boot.web.vert.x;

import com.sharingif.cube.core.view.MultiViewResolver;
import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import com.sharingif.cube.web.vert.x.VertXServer;
import com.sharingif.cube.web.vert.x.handler.VertXDispatcherHandler;
import com.sharingif.cube.web.vert.x.request.VertXRequestContextResolver;
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

	@SuppressWarnings("rawtypes")
	@Bean("vertXDispatcherHandler")
	public VertXDispatcherHandler createVertXDispatcherHandler(
            MultiHandlerMethodChain vertxWebHandlerMethodChain
            ,VertXRequestContextResolver vertXRequestContextResolver
            ,MultiHandlerMapping vertXMultiHandlerMapping
            ,MultiHandlerMethodAdapter vertXMultiHandlerMethodAdapter
            ,MultiCubeExceptionHandler vertxCubeExceptionHandler
            ,MultiViewResolver vertMultiViewResolver
         ) {
        VertXDispatcherHandler vertXDispatcherHandler = new VertXDispatcherHandler();
        vertXDispatcherHandler.setHandlerMethodChain(vertxWebHandlerMethodChain);
        vertXDispatcherHandler.setRequestContextResolver(vertXRequestContextResolver);
        vertXDispatcherHandler.setMultiHandlerMapping(vertXMultiHandlerMapping);
        vertXDispatcherHandler.setMultiHandlerMethodAdapter(vertXMultiHandlerMethodAdapter);
        vertXDispatcherHandler.setMultiCubeExceptionHandler(vertxCubeExceptionHandler);
        vertXDispatcherHandler.setMultiViewResolver(vertMultiViewResolver);

        return vertXDispatcherHandler;
    }

    @Bean(name="vertXServer")
    public VertXServer createVertXServer(
            @Value("${vertx.server.port}") int port
            ,@Value("${vertx.server.contextPath}")String contextPath
            ,VertXDispatcherHandler vertXDispatcherHandler
            ) {

        VertXServer vertXServer = new VertXServer();
        vertXServer.setPort(port);
        vertXServer.setContextPath(contextPath);
        vertXServer.setDispatcherHandler(vertXDispatcherHandler);

        return vertXServer;

    }

}
