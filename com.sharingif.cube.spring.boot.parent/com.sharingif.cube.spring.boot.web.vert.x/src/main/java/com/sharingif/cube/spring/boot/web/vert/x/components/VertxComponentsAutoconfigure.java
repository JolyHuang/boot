package com.sharingif.cube.spring.boot.web.vert.x.components;

import com.sharingif.cube.core.view.MultiViewResolver;
import com.sharingif.cube.core.view.ViewResolver;
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
import com.sharingif.cube.core.util.StringUtils;
import com.sharingif.cube.security.web.exception.handler.validation.access.AccessDecisionCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.WebCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.BindValidationCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.TokenValidationCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.ValidationCubeExceptionHandler;
import com.sharingif.cube.web.handler.adapter.PathVariableMethodArgumentResolver;
import com.sharingif.cube.web.vert.x.handler.adapter.CORSHandlerAdapter;
import com.sharingif.cube.web.vert.x.handler.adapter.JsonHandlerMethodArgumentResolver;
import com.sharingif.cube.web.vert.x.handler.adapter.StaticHandlerAdapter;
import com.sharingif.cube.web.vert.x.handler.adapter.container.DataContainerMethodArgumentResolver;
import com.sharingif.cube.web.vert.x.handler.mapping.CORSHandlerMapping;
import com.sharingif.cube.web.vert.x.handler.mapping.StaticHandlerMapping;
import com.sharingif.cube.web.vert.x.request.VertXRequestContextResolver;
import com.sharingif.cube.web.vert.x.view.CORSViewResolver;
import com.sharingif.cube.web.vert.x.view.StaticHandlerImpl;
import com.sharingif.cube.web.vert.x.view.VertXJsonViewResolver;
import com.sharingif.cube.web.vert.x.view.VertXStaticViewResolver;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.handler.StaticHandler;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean("dataContainerMethodArgumentResolver")
    public DataContainerMethodArgumentResolver createDataContainerMethodArgumentResolver() {
        return new DataContainerMethodArgumentResolver();
    }

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

    @Bean("vertXArgumentResolvers")
    public List<HandlerMethodArgumentResolver> createVertXArgumentResolvers(
            DataContainerMethodArgumentResolver dataContainerMethodArgumentResolver
            ,PathVariableMethodArgumentResolver pathVariableMethodArgumentResolver
            ,JsonHandlerMethodArgumentResolver jsonHandlerMethodArgumentResolver
            ) {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();
        argumentResolvers.add(dataContainerMethodArgumentResolver);
        argumentResolvers.add(pathVariableMethodArgumentResolver);
        argumentResolvers.add(jsonHandlerMethodArgumentResolver);

        return argumentResolvers;
    }

    @Bean("vertXRequestContextResolver")
    public VertXRequestContextResolver createVertXRequestContextResolver() {
        return new VertXRequestContextResolver();
    }

    @Bean("vertXStaticHandlerMapping")
    @ConditionalOnMissingBean(name="staticHandlerMapping")
    public StaticHandlerMapping createStaticHandlerMapping(@Value("${verx.web.root:}") String webRoot) {

        StaticHandler staticHandler = new StaticHandlerImpl(webRoot,this.getClass().getClassLoader());

        Map<String,StaticHandler> urlMap = new HashMap<String,StaticHandler>();
        urlMap.put("/static/**", staticHandler);
        urlMap.put("**/favicon.ico", staticHandler);

        StaticHandlerMapping staticHandlerMapping = new StaticHandlerMapping();
        staticHandlerMapping.setUrlMap(urlMap);


        return staticHandlerMapping;
    }

    @Bean("vertXCorsHandlerMapping")
    @ConditionalOnMissingBean(name="corsHandlerMapping")
    public CORSHandlerMapping createCORSHandlerMapping(@Value("${corst.allowed.origin.pattern:}") String allowedOriginPattern
    ,@Value("${corst.max.age.seconds:}") Integer maxAgeSeconds) {
        if(StringUtils.isTrimEmpty(allowedOriginPattern)) {
            allowedOriginPattern = "*";
        }
        if(maxAgeSeconds == null) {
            maxAgeSeconds = 1*60*60;
        }
        CorsHandler corsHandler = CorsHandler.create(allowedOriginPattern)
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.HEAD)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.PUT)
                .allowedMethod(HttpMethod.DELETE)
                .allowedHeader("Content-Type")
                .maxAgeSeconds(maxAgeSeconds);

        if(!"*".equals(allowedOriginPattern)) {
            corsHandler.allowCredentials(true);
        }

        CORSHandlerMapping corsHandlerMapping = new CORSHandlerMapping();
        corsHandlerMapping.setCorsHandler(corsHandler);

        return corsHandlerMapping;
    }

	@Bean("vertXHandlerMappings")
	@SuppressWarnings("rawtypes")
    public List<HandlerMapping> createVertXHandlerMappings(
            RequestMappingHandlerMapping requestMappingHandlerMapping
            ,StaticHandlerMapping vertXStaticHandlerMapping
            ,CORSHandlerMapping vertXCorsHandlerMapping
            ) {
        List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
        handlerMappings.add(requestMappingHandlerMapping);
        handlerMappings.add(vertXStaticHandlerMapping);
        handlerMappings.add(vertXCorsHandlerMapping);

        return handlerMappings;
    }

    @Bean("vertXMultiHandlerMapping")
    @SuppressWarnings("rawtypes")
    public MultiHandlerMapping createVertXMultiHandlerMapping(@Qualifier("vertXHandlerMappings") List<HandlerMapping> handlerMappings) {
        MultiHandlerMapping multiHandlerMapping = new MultiHandlerMapping();
        multiHandlerMapping.setHandlerMappings(handlerMappings);

        return multiHandlerMapping;
    }

    @Bean("vertXStaticHandlerAdapter")
    public StaticHandlerAdapter createVertXStaticHandlerAdapter() {
        return new StaticHandlerAdapter();
    }

    @Bean("vertXCorsHandlerAdapter")
    public CORSHandlerAdapter createVertXCorsHandlerAdapter() {
        return new CORSHandlerAdapter();
    }

    @Bean("vertXHandlerMethodHandlerAdapter")
    public HandlerMethodHandlerAdapter createVertXHandlerMethodHandlerAdapter(
            MultiHandlerMethodChain vertxControllerChains
            ,List<HandlerMethodArgumentResolver> vertXArgumentResolvers
            ,BindingInitializer bindingInitializer
            ) {
        HandlerMethodHandlerAdapter handlerMethodHandlerAdapter = new HandlerMethodHandlerAdapter();
        handlerMethodHandlerAdapter.setHandlerMethodChain(vertxControllerChains);
        handlerMethodHandlerAdapter.setArgumentResolvers(vertXArgumentResolvers);
        handlerMethodHandlerAdapter.setBindingInitializer(bindingInitializer);

        return handlerMethodHandlerAdapter;
    }

	@Bean("vertXHandlerAdapters")
	@SuppressWarnings("rawtypes")
    public List<HandlerAdapter> createVertXHandlerAdapters(
            HandlerMethodHandlerAdapter vertXHandlerMethodHandlerAdapter
            ,StaticHandlerAdapter vertXStaticHandlerAdapter
            ,CORSHandlerAdapter vertXCorsHandlerAdapter
            ) {
        List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();
        handlerAdapters.add(vertXHandlerMethodHandlerAdapter);
        handlerAdapters.add(vertXStaticHandlerAdapter);
        handlerAdapters.add(vertXCorsHandlerAdapter);

        return handlerAdapters;
    }

    @Bean("vertXMultiHandlerMethodAdapter")
    @SuppressWarnings("rawtypes")
    public MultiHandlerMethodAdapter createVertXMultiHandlerMethodAdapter(@Qualifier("vertXHandlerAdapters") List<HandlerAdapter> vertXHandlerAdapters) {
        MultiHandlerMethodAdapter multiHandlerMethodAdapter = new MultiHandlerMethodAdapter();
        multiHandlerMethodAdapter.setHandlerAdapters(vertXHandlerAdapters);

        return multiHandlerMethodAdapter;
    }

    @Bean("webCubeExceptionHandlers")
    public List<WebCubeExceptionHandler> createWebCubeExceptionHandlers(
            AccessDecisionCubeExceptionHandler accessDecisionCubeExceptionHandler
            ,TokenValidationCubeExceptionHandler tokenValidationCubeExceptionHandler
            ,BindValidationCubeExceptionHandler bindValidationCubeExceptionHandler
            ,ValidationCubeExceptionHandler validationCubeExceptionHandler
            ,WebCubeExceptionHandler webCubeExceptionHandler
            ) {
        List<WebCubeExceptionHandler> webCubeExceptionHandlers = new ArrayList<WebCubeExceptionHandler>();
        webCubeExceptionHandlers.add(accessDecisionCubeExceptionHandler);
        webCubeExceptionHandlers.add(tokenValidationCubeExceptionHandler);
        webCubeExceptionHandlers.add(bindValidationCubeExceptionHandler);
        webCubeExceptionHandlers.add(validationCubeExceptionHandler);
        webCubeExceptionHandlers.add(webCubeExceptionHandler);

        return webCubeExceptionHandlers;
    }

    @Bean("vertxCubeExceptionHandler")
    public MultiCubeExceptionHandler createVertxCubeExceptionHandler(
            @Qualifier("webCubeExceptionHandlers") List<WebCubeExceptionHandler> webCubeExceptionHandlers
            ) {

        MultiCubeExceptionHandler vertxCubeExceptionHandler = new MultiCubeExceptionHandler();
        vertxCubeExceptionHandler.setCubeExceptionHandlers(webCubeExceptionHandlers);

        return vertxCubeExceptionHandler;
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

    @Bean("corsViewResolver")
    public CORSViewResolver createCORSViewResolver() {
        return new CORSViewResolver();
    }

	@Bean("vertXViewResolvers")
    public List<ViewResolver> createViewResolvers(
            VertXStaticViewResolver vertXStaticViewResolver
            ,VertXJsonViewResolver vertXJsonViewResolver
            ,CORSViewResolver corsViewResolver
            ) {
        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(vertXStaticViewResolver);
        viewResolvers.add(vertXJsonViewResolver);
        viewResolvers.add(corsViewResolver);

        return viewResolvers;
    }

    @Bean("vertMultiViewResolver")
    public MultiViewResolver createVertMultiViewResolver(@Qualifier("vertXViewResolvers") List<ViewResolver> viewResolvers) {
        MultiViewResolver multiViewResolver = new MultiViewResolver();
        multiViewResolver.setViewResolvers(viewResolvers);

        return multiViewResolver;
    }

}
