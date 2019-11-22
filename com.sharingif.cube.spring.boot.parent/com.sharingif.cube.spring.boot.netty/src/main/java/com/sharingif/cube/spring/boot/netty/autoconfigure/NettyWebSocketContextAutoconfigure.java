package com.sharingif.cube.spring.boot.netty.autoconfigure;

import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import com.sharingif.cube.core.view.MultiViewResolver;
import com.sharingif.cube.netty.websocket.WebSocketServerBootstrap;
import com.sharingif.cube.netty.websocket.handler.HttpRequestHandler;
import com.sharingif.cube.netty.websocket.handler.TextWebSocketFrameHandler;
import com.sharingif.cube.netty.websocket.handler.WebSocketDispatcherHandler;
import com.sharingif.cube.netty.websocket.handler.WebSocketServerChannelInitializer;
import com.sharingif.cube.netty.websocket.request.WebSocketRequestContextResolver;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyWebSocketContextAutoconfigure {

    @Bean("webSocketDispatcherHandler")
    public WebSocketDispatcherHandler createWebSocketDispatcherHandler(
            MultiHandlerMethodChain webHandlerMethodChain
            , WebSocketRequestContextResolver webSocketRequestContextResolver
            , MultiHandlerMapping nettyMultiHandlerMapping
            , MultiHandlerMethodAdapter nettyMultiHandlerMethodAdapter
            , MultiCubeExceptionHandler nettyMultiCubeExceptionHandler
            , MultiViewResolver nettyMultiViewResolver
    ) {
        WebSocketDispatcherHandler webSocketDispatcherHandler = new WebSocketDispatcherHandler();
        webSocketDispatcherHandler.setHandlerMethodChain(webHandlerMethodChain);
        webSocketDispatcherHandler.setRequestContextResolver(webSocketRequestContextResolver);
        webSocketDispatcherHandler.setMultiHandlerMapping(nettyMultiHandlerMapping);
        webSocketDispatcherHandler.setMultiHandlerMethodAdapter(nettyMultiHandlerMethodAdapter);
        webSocketDispatcherHandler.setMultiCubeExceptionHandler(nettyMultiCubeExceptionHandler);
        webSocketDispatcherHandler.setMultiViewResolver(nettyMultiViewResolver);

        return webSocketDispatcherHandler;
    }

    @Bean(name = "webSocketServerChannelInitializer")
    public WebSocketServerChannelInitializer createWebSocketChannelInitializer(
            @Value("${websocket.path}")String path
            , @Value("${websocket.use.ssl}")boolean useSSL
            , HttpRequestHandler httpRequestHandler
            , TextWebSocketFrameHandler textWebSocketFrameHandler
    ) {
        WebSocketServerChannelInitializer webSocketServerChannelInitializer = new WebSocketServerChannelInitializer();
        webSocketServerChannelInitializer.setPath(path);
        webSocketServerChannelInitializer.setUseSSL(useSSL);
        webSocketServerChannelInitializer.setHttpRequestHandler(httpRequestHandler);
        webSocketServerChannelInitializer.setTextWebSocketFrameHandler(textWebSocketFrameHandler);


        return webSocketServerChannelInitializer;
    }

    @Bean(name = "webSocketServerBootstrap")
    public WebSocketServerBootstrap createWebSocketServerBootstrap(
            @Value("${websocket.port}")int port
            , @Value("${websocket.worker.group.thread.number}")int workerGroupThreadNumber
            , ChannelInitializer<SocketChannel> webSocketServerChannelInitializer
    ) {
        WebSocketServerBootstrap webSocketServerBootstrap = new WebSocketServerBootstrap();
        webSocketServerBootstrap.setPort(port);
        webSocketServerBootstrap.setWorkerGroupThreadNumber(workerGroupThreadNumber);
        webSocketServerBootstrap.setWebSocketChannelInitializer(webSocketServerChannelInitializer);

        return webSocketServerBootstrap;
    }

}
