package com.sharingif.cube.spring.boot.batch.core.autoconfigure.components;

import com.sharingif.cube.batch.core.exception.JobExceptionHandler;
import com.sharingif.cube.batch.core.handler.adapter.JobRequestHandlerMethodArgumentResolver;
import com.sharingif.cube.batch.core.request.JobRequestContextResolver;
import com.sharingif.cube.batch.core.view.JobViewResolver;
import com.sharingif.cube.communication.view.MultiViewResolver;
import com.sharingif.cube.communication.view.ViewResolver;
import com.sharingif.cube.core.exception.handler.IExceptionHandler;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * CoreComponentsAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/21 下午4:15
 */
@Configuration
public class CoreBatchComponentsAutoconfigure {

    @Bean("jobRequestHandlerMethodArgumentResolver")
    public JobRequestHandlerMethodArgumentResolver createJobRequestHandlerMethodArgumentResolver() {
        return new JobRequestHandlerMethodArgumentResolver();
    }

    @Bean("argumentResolvers")
    public List<HandlerMethodArgumentResolver> createArgumentResolvers(
            JobRequestHandlerMethodArgumentResolver jobRequestHandlerMethodArgumentResolver
    ) {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<HandlerMethodArgumentResolver>();
        argumentResolvers.add(jobRequestHandlerMethodArgumentResolver);

        return argumentResolvers;
    }

    @Bean("jobRequestContextResolver")
    public JobRequestContextResolver createJobRequestContextResolver() {
        return new JobRequestContextResolver();
    }

    @Bean("requestMappingHandlerMapping")
    public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
        handlerMapping.setUseSuffixPatternMatch(false);

        return handlerMapping;
    }

    @Bean("handlerMappings")
    @SuppressWarnings("rawtypes")
    public List<HandlerMapping> createHandlerMappings(
            RequestMappingHandlerMapping requestMappingHandlerMapping
    ) {
        List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
        handlerMappings.add(requestMappingHandlerMapping);

        return handlerMappings;
    }

    @Bean("multiHandlerMapping")
    @SuppressWarnings("rawtypes")
    public MultiHandlerMapping createMultiHandlerMapping(List<HandlerMapping> handlerMappings) {
        MultiHandlerMapping multiHandlerMapping = new MultiHandlerMapping();
        multiHandlerMapping.setHandlerMappings(handlerMappings);

        return multiHandlerMapping;
    }

    @Bean("handlerMethodHandlerAdapter")
    public HandlerMethodHandlerAdapter createHandlerMethodHandlerAdapter(
            MultiHandlerMethodChain batchControllerChains
            ,List<HandlerMethodArgumentResolver> argumentResolvers
            ,BindingInitializer bindingInitializer
    ) {
        HandlerMethodHandlerAdapter handlerMethodHandlerAdapter = new HandlerMethodHandlerAdapter();
        handlerMethodHandlerAdapter.setHandlerMethodChain(batchControllerChains);
        handlerMethodHandlerAdapter.setArgumentResolvers(argumentResolvers);
        handlerMethodHandlerAdapter.setBindingInitializer(bindingInitializer);

        return handlerMethodHandlerAdapter;
    }

    @Bean("handlerAdapters")
    @SuppressWarnings("rawtypes")
    public List<HandlerAdapter> createHandlerAdapters(
            HandlerMethodHandlerAdapter handlerMethodHandlerAdapter
    ) {
        List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();
        handlerAdapters.add(handlerMethodHandlerAdapter);

        return handlerAdapters;
    }

    @Bean("multiHandlerMethodAdapter")
    @SuppressWarnings("rawtypes")
    public MultiHandlerMethodAdapter createHandlerAdapter(List<HandlerAdapter> handlerAdapters) {
        MultiHandlerMethodAdapter multiHandlerMethodAdapter = new MultiHandlerMethodAdapter();
        multiHandlerMethodAdapter.setHandlerAdapters(handlerAdapters);

        return multiHandlerMethodAdapter;
    }

    @Bean("exceptionHandlers")
    @ConditionalOnMissingBean(name = "exceptionHandlers")
    public List<IExceptionHandler> createWebCubeExceptionHandlers(
    ) {
        List<IExceptionHandler> jobExceptionHandlers = new ArrayList<IExceptionHandler>();
        jobExceptionHandlers.add(new JobExceptionHandler());

        return jobExceptionHandlers;
    }

    @Bean("multiCubeExceptionHandler")
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MultiCubeExceptionHandler createVertxCubeExceptionHandler(List<IExceptionHandler> exceptionHandlers) {

        MultiCubeExceptionHandler multiCubeExceptionHandler = new MultiCubeExceptionHandler();
        multiCubeExceptionHandler.setCubeExceptionHandlers(exceptionHandlers);

        return multiCubeExceptionHandler;
    }

    @Bean("viewResolvers")
    @ConditionalOnMissingBean(name = "viewResolvers")
    public List<ViewResolver> createViewResolvers() {
        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(new JobViewResolver());

        return viewResolvers;
    }

    @Bean("multiViewResolver")
    public MultiViewResolver createmultiViewResolver(List<ViewResolver> viewResolvers) {
        MultiViewResolver multiViewResolver = new MultiViewResolver();
        multiViewResolver.setViewResolvers(viewResolvers);

        return multiViewResolver;
    }

}
