package com.sharingif.cube.spring.boot.web.vert.x.components;

import com.sharingif.cube.communication.view.MultiViewResolver;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.adapter.HandlerAdapter;
import com.sharingif.cube.core.handler.adapter.HandlerMethodArgumentResolver;
import com.sharingif.cube.core.handler.adapter.HandlerMethodHandlerAdapter;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.bind.support.BindingInitializer;
import com.sharingif.cube.core.handler.mapping.HandlerMapping;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import com.sharingif.cube.core.handler.mapping.RequestMappingHandlerMapping;
import com.sharingif.cube.web.exception.handler.WebCubeExceptionHandler;
import com.sharingif.cube.web.handler.adapter.PathVariableMethodArgumentResolver;
import com.sharingif.cube.web.vert.x.exception.handler.VertXExceptionResolver;
import com.sharingif.cube.web.vert.x.handler.adapter.JsonHandlerMethodArgumentResolver;
import com.sharingif.cube.web.vert.x.handler.adapter.StaticHandlerAdapter;
import com.sharingif.cube.web.vert.x.handler.mapping.StaticHandlerMapping;
import com.sharingif.cube.web.vert.x.request.VertXRequestInfoResolver;
import com.sharingif.cube.web.vert.x.view.StaticHandlerImpl;
import com.sharingif.cube.web.vert.x.view.VertXJsonViewResolver;
import com.sharingif.cube.web.vert.x.view.VertXStaticViewResolver;
import io.vertx.ext.web.handler.StaticHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * VertxComponentsAutoconfigure
 * 2017/5/20 下午11:47
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class VertxComponentsAutoconfigure {

    @Bean("pathVariableMethodArgumentResolver")
    public PathVariableMethodArgumentResolver createPathVariableMethodArgumentResolver() {
        PathVariableMethodArgumentResolver pathVariableMethodArgumentResolver = new PathVariableMethodArgumentResolver();

        return pathVariableMethodArgumentResolver;
    }

    @Bean(name="jsonHandlerMethodArgumentResolver")
    public JsonHandlerMethodArgumentResolver createJsonHandlerMethodArgumentResolver() {
        JsonHandlerMethodArgumentResolver jsonHandlerMethodArgumentResolver = new JsonHandlerMethodArgumentResolver();

        return jsonHandlerMethodArgumentResolver;
    }

    @Bean("argumentResolvers")
    public List<HandlerMethodArgumentResolver> createArgumentResolvers(
            PathVariableMethodArgumentResolver pathVariableMethodArgumentResolver
            ,JsonHandlerMethodArgumentResolver jsonHandlerMethodArgumentResolver
            ) {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();
        argumentResolvers.add(pathVariableMethodArgumentResolver);
        argumentResolvers.add(jsonHandlerMethodArgumentResolver);

        return argumentResolvers;
    }

    @Bean("vertXRequestInfoResolver")
    public VertXRequestInfoResolver createVertXRequestInfoResolver() {
        VertXRequestInfoResolver vertXRequestInfoResolver = new VertXRequestInfoResolver();

        return vertXRequestInfoResolver;
    }

    @Bean("requestMappingHandlerMapping")
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
        handlerMapping.setUseSuffixPatternMatch(false);

        return handlerMapping;
    }

    @Bean("staticHandlerMapping")
    @ConditionalOnMissingBean(name="staticHandlerMapping")
    public StaticHandlerMapping createStaticHandlerMapping(@Value("${verx.web.root :}") String webRoot) {

        StaticHandler staticHandler = new StaticHandlerImpl(webRoot,this.getClass().getClassLoader());

        Map<String,StaticHandler> urlMap = new HashMap<String,StaticHandler>();
        urlMap.put("/static/**", staticHandler);
        urlMap.put("**/favicon.ico", staticHandler);

        StaticHandlerMapping staticHandlerMapping = new StaticHandlerMapping();
        staticHandlerMapping.setUrlMap(urlMap);


        return staticHandlerMapping;
    }

	@Bean("handlerMappings")
	@SuppressWarnings("rawtypes")
    public List<HandlerMapping> createHandlerMappings(
            RequestMappingHandlerMapping requestMappingHandlerMapping
            ,StaticHandlerMapping staticHandlerMapping
            ) {
        List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
        handlerMappings.add(requestMappingHandlerMapping);
        handlerMappings.add(staticHandlerMapping);

        return handlerMappings;
    }

    @Bean("multiHandlerMapping")
    @SuppressWarnings("rawtypes")
    public MultiHandlerMapping createMultiHandlerMapping(List<HandlerMapping> handlerMappings) {
        MultiHandlerMapping multiHandlerMapping = new MultiHandlerMapping();
        multiHandlerMapping.setHandlerMappings(handlerMappings);

        return multiHandlerMapping;
    }

    @Bean("staticHandlerAdapter")
    public StaticHandlerAdapter createStaticHandlerAdapter() {
        return new StaticHandlerAdapter();
    }

    @Bean("handlerMethodHandlerAdapter")
    public HandlerMethodHandlerAdapter createHandlerMethodHandlerAdapter(
            List<HandlerMethodArgumentResolver> argumentResolvers
            ,BindingInitializer bindingInitializer
            ) {
        HandlerMethodHandlerAdapter handlerMethodHandlerAdapter = new HandlerMethodHandlerAdapter();
        handlerMethodHandlerAdapter.setArgumentResolvers(argumentResolvers);
        handlerMethodHandlerAdapter.setBindingInitializer(bindingInitializer);

        return handlerMethodHandlerAdapter;
    }

	@Bean("handlerAdapters")
	@SuppressWarnings("rawtypes")
    public List<HandlerAdapter> createHandlerAdapters(
            HandlerMethodHandlerAdapter handlerMethodHandlerAdapter
            ,StaticHandlerAdapter staticHandlerAdapter
            ) {
        List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();
        handlerAdapters.add(handlerMethodHandlerAdapter);
        handlerAdapters.add(staticHandlerAdapter);

        return handlerAdapters;
    }

    @Bean("multiHandlerMethodAdapter")
    @SuppressWarnings("rawtypes")
    public MultiHandlerMethodAdapter createHandlerAdapter(List<HandlerAdapter> handlerAdapters) {
        MultiHandlerMethodAdapter multiHandlerMethodAdapter = new MultiHandlerMethodAdapter();
        multiHandlerMethodAdapter.setHandlerAdapters(handlerAdapters);

        return multiHandlerMethodAdapter;
    }

    @Bean("webCubeExceptionHandlers")
    public List<WebCubeExceptionHandler> createWebCubeExceptionHandlers(
            WebCubeExceptionHandler webCubeExceptionHandler
            ) {
        List<WebCubeExceptionHandler> webCubeExceptionHandlers = new ArrayList<WebCubeExceptionHandler>(6);
        webCubeExceptionHandlers.add(webCubeExceptionHandler);

        return webCubeExceptionHandlers;
    }

    @Bean("vertxCubeExceptionHandler")
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MultiCubeExceptionHandler createVertxCubeExceptionHandler(List<WebCubeExceptionHandler> webCubeExceptionHandlers) {

        MultiCubeExceptionHandler vertxCubeExceptionHandler = new MultiCubeExceptionHandler();
        vertxCubeExceptionHandler.setCubeExceptionHandlers(webCubeExceptionHandlers);

        return vertxCubeExceptionHandler;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Bean("exceptionResolver")
    public VertXExceptionResolver createVertXExceptionResolver(MultiCubeExceptionHandler vertxCubeExceptionHandler) {
        VertXExceptionResolver exceptionResolver = new VertXExceptionResolver();
        exceptionResolver.setCubeExceptionHandler(vertxCubeExceptionHandler);

        return exceptionResolver;
    }

    @Bean("vertXJsonViewResolver")
    public VertXJsonViewResolver createVertXJsonViewResolver() {
        VertXJsonViewResolver vertXJsonViewResolver = new VertXJsonViewResolver();

        return vertXJsonViewResolver;
    }

    @Bean("vertXStaticViewResolver")
    public VertXStaticViewResolver createVertXStaticViewResolver() {
        return new VertXStaticViewResolver();
    }

    @SuppressWarnings("rawtypes")
	@Bean("viewResolvers")
    public List<ViewResolver> createViewResolvers(
            VertXStaticViewResolver vertXStaticViewResolver
            ,VertXJsonViewResolver vertXJsonViewResolver
            ) {
        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(vertXStaticViewResolver);
        viewResolvers.add(vertXJsonViewResolver);

        return viewResolvers;
    }

    @Bean("multiViewResolver")
    @SuppressWarnings("rawtypes")
    public MultiViewResolver createmultiViewResolver(List<ViewResolver> viewResolvers) {
        MultiViewResolver multiViewResolver = new MultiViewResolver();
        multiViewResolver.setViewResolvers(viewResolvers);

        return multiViewResolver;
    }

}
