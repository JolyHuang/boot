package com.sharingif.cube.spring.boot.netty.autoconfigure.components;

import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.adapter.HandlerAdapter;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.handler.adapter.HandlerMethodHandlerAdapter;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.core.handler.mapping.HandlerMapping;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import com.sharingif.cube.core.handler.mapping.RequestMappingHandlerMapping;
import com.sharingif.cube.core.view.MultiViewResolver;
import com.sharingif.cube.core.view.ViewResolver;
import com.sharingif.cube.netty.websocket.WebSocketServerBootstrap;
import com.sharingif.cube.netty.websocket.handler.HttpRequestHandler;
import com.sharingif.cube.netty.websocket.handler.TextWebSocketFrameHandler;
import com.sharingif.cube.netty.websocket.handler.WebSocketServerChannelInitializer;
import com.sharingif.cube.netty.websocket.handler.adapter.JsonHandlerMethodArgumentResolver;
import com.sharingif.cube.netty.websocket.request.WebSocketRequestContextResolver;
import com.sharingif.cube.netty.websocket.view.ChannelAllView;
import com.sharingif.cube.netty.websocket.view.ChannelView;
import com.sharingif.cube.netty.websocket.view.ModelAndViewResolver;
import com.sharingif.cube.security.web.exception.handler.validation.access.AccessDecisionCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.WebCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.BindValidationCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.TokenValidationCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.ValidationCubeExceptionHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class NettyWebSocketComponentsAutoconfigure {

    @Bean(name="jsonHandlerMethodArgumentResolver")
    public JsonHandlerMethodArgumentResolver createJsonHandlerMethodArgumentResolver() {
        JsonHandlerMethodArgumentResolver jsonHandlerMethodArgumentResolver = new JsonHandlerMethodArgumentResolver();

        return jsonHandlerMethodArgumentResolver;
    }

    @Bean("nettyArgumentResolvers")
    public List<HandlerMethodArgumentResolver> createNettyArgumentResolvers(
            JsonHandlerMethodArgumentResolver jsonHandlerMethodArgumentResolver
    ) {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();
        argumentResolvers.add(jsonHandlerMethodArgumentResolver);

        return argumentResolvers;
    }

    @Bean("webSocketRequestContextResolver")
    public WebSocketRequestContextResolver createWebSocketRequestContextResolver() {
        WebSocketRequestContextResolver webSocketRequestContextResolver = new WebSocketRequestContextResolver();

        return webSocketRequestContextResolver;
    }

    @Bean("nettyHandlerMappings")
    public List<HandlerMapping> createNettyHandlerMappings(
            RequestMappingHandlerMapping requestMappingHandlerMapping
    ) {
        List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
        handlerMappings.add(requestMappingHandlerMapping);

        return handlerMappings;
    }

    @Bean("nettyMultiHandlerMapping")
    public MultiHandlerMapping createNettyMultiHandlerMapping(@Qualifier("nettyHandlerMappings") List<HandlerMapping> handlerMappings) {
        MultiHandlerMapping multiHandlerMapping = new MultiHandlerMapping();
        multiHandlerMapping.setHandlerMappings(handlerMappings);

        return multiHandlerMapping;
    }

    @Bean("nettyHandlerMethodHandlerAdapter")
    public HandlerMethodHandlerAdapter createNettyHandlerMethodHandlerAdapter(
            MultiHandlerMethodChain nettyControllerChains
            , List<HandlerMethodArgumentResolver> nettyArgumentResolvers
            , BindingInitializer bindingInitializer
    ) {
        HandlerMethodHandlerAdapter handlerMethodHandlerAdapter = new HandlerMethodHandlerAdapter();
        handlerMethodHandlerAdapter.setHandlerMethodChain(nettyControllerChains);
        handlerMethodHandlerAdapter.setArgumentResolvers(nettyArgumentResolvers);
        handlerMethodHandlerAdapter.setBindingInitializer(bindingInitializer);

        return handlerMethodHandlerAdapter;
    }

    @Bean("nettyHandlerAdapters")
    public List<HandlerAdapter> createNettyHandlerAdapters(
            HandlerMethodHandlerAdapter nettyHandlerMethodHandlerAdapter
    ) {
        List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();
        handlerAdapters.add(nettyHandlerMethodHandlerAdapter);

        return handlerAdapters;
    }

    @Bean("nettyMultiHandlerMethodAdapter")
    public MultiHandlerMethodAdapter createNettyMultiHandlerMethodAdapter(@Qualifier("nettyHandlerAdapters") List<HandlerAdapter> nettyHandlerAdapters) {
        MultiHandlerMethodAdapter multiHandlerMethodAdapter = new MultiHandlerMethodAdapter();
        multiHandlerMethodAdapter.setHandlerAdapters(nettyHandlerAdapters);

        return multiHandlerMethodAdapter;
    }

    @Bean("nettyCubeExceptionHandlers")
    public List<WebCubeExceptionHandler> createNettyCubeExceptionHandlers(
            AccessDecisionCubeExceptionHandler accessDecisionCubeExceptionHandler
            , TokenValidationCubeExceptionHandler tokenValidationCubeExceptionHandler
            , BindValidationCubeExceptionHandler bindValidationCubeExceptionHandler
            , ValidationCubeExceptionHandler validationCubeExceptionHandler
            , WebCubeExceptionHandler webCubeExceptionHandler
    ) {
        List<WebCubeExceptionHandler> webCubeExceptionHandlers = new ArrayList<WebCubeExceptionHandler>();
        webCubeExceptionHandlers.add(accessDecisionCubeExceptionHandler);
        webCubeExceptionHandlers.add(tokenValidationCubeExceptionHandler);
        webCubeExceptionHandlers.add(bindValidationCubeExceptionHandler);
        webCubeExceptionHandlers.add(validationCubeExceptionHandler);
        webCubeExceptionHandlers.add(webCubeExceptionHandler);

        return webCubeExceptionHandlers;
    }

    @Bean("nettyMultiCubeExceptionHandler")
    public MultiCubeExceptionHandler createNettyMultiCubeExceptionHandler(
            @Qualifier("nettyCubeExceptionHandlers") List<WebCubeExceptionHandler> nettyCubeExceptionHandlers
    ) {

        MultiCubeExceptionHandler nettyCubeExceptionHandler = new MultiCubeExceptionHandler();
        nettyCubeExceptionHandler.setCubeExceptionHandlers(nettyCubeExceptionHandlers);

        return nettyCubeExceptionHandler;
    }

    @Bean("channelView")
    public ChannelView createChannelView() {
        return new ChannelView();
    }

    @Bean("channelAllView")
    public ChannelAllView createChannelAllView() {
        return new ChannelAllView();
    }

    @Bean("modelAndViewResolver")
    public ModelAndViewResolver createModelAndViewResolver(
            ChannelView channelView
            , ChannelAllView channelAllView
            ) {
        ModelAndViewResolver modelAndViewResolver = new ModelAndViewResolver();
        modelAndViewResolver.setChannelView(channelView);
        modelAndViewResolver.setChannelAllView(channelAllView);

        return modelAndViewResolver;
    }

    @Bean("nettyViewResolvers")
    public List<ViewResolver> createNettyViewResolvers(
            ModelAndViewResolver modelAndViewResolver
    ) {
        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(modelAndViewResolver);

        return viewResolvers;
    }

    @Bean("nettyMultiViewResolver")
    public MultiViewResolver createNettyMultiViewResolver(@Qualifier("nettyViewResolvers") List<ViewResolver> nettyViewResolvers) {
        MultiViewResolver multiViewResolver = new MultiViewResolver();
        multiViewResolver.setViewResolvers(nettyViewResolvers);

        return multiViewResolver;
    }

}
