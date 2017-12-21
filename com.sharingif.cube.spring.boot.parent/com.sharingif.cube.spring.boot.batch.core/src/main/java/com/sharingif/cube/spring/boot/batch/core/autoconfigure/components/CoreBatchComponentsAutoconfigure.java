package com.sharingif.cube.spring.boot.batch.core.autoconfigure.components;

import com.sharingif.cube.batch.core.JobService;
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
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean("batchArgumentResolvers")
    public List<HandlerMethodArgumentResolver> createBatchArgumentResolvers(
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

    @Bean("batchRequestMappingHandlerMapping")
    public RequestMappingHandlerMapping createBatchRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
        handlerMapping.setUseSuffixPatternMatch(false);

        return handlerMapping;
    }

    @Bean("batchHandlerMappings")
    @SuppressWarnings("rawtypes")
    public List<HandlerMapping> createBatchHandlerMappings(
            RequestMappingHandlerMapping batchRequestMappingHandlerMapping
    ) {
        List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
        handlerMappings.add(batchRequestMappingHandlerMapping);

        return handlerMappings;
    }

    @Bean("batchMultiHandlerMapping")
    @SuppressWarnings("rawtypes")
    public MultiHandlerMapping createBatchMultiHandlerMapping(@Qualifier("batchHandlerMappings") List<HandlerMapping> batchHandlerMappings) {
        MultiHandlerMapping multiHandlerMapping = new MultiHandlerMapping();
        multiHandlerMapping.setHandlerMappings(batchHandlerMappings);

        return multiHandlerMapping;
    }

    @Bean("batchHandlerMethodHandlerAdapter")
    public HandlerMethodHandlerAdapter createBatchHandlerMethodHandlerAdapter(
            MultiHandlerMethodChain batchControllerChains
            ,List<HandlerMethodArgumentResolver> batchArgumentResolvers
            ,BindingInitializer bindingInitializer
    ) {
        HandlerMethodHandlerAdapter handlerMethodHandlerAdapter = new HandlerMethodHandlerAdapter();
        handlerMethodHandlerAdapter.setHandlerMethodChain(batchControllerChains);
        handlerMethodHandlerAdapter.setArgumentResolvers(batchArgumentResolvers);
        handlerMethodHandlerAdapter.setBindingInitializer(bindingInitializer);

        return handlerMethodHandlerAdapter;
    }

    @Bean("batchHandlerAdapters")
    @SuppressWarnings("rawtypes")
    public List<HandlerAdapter> createBatchHandlerAdapters(
            HandlerMethodHandlerAdapter batchHandlerMethodHandlerAdapter
    ) {
        List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();
        handlerAdapters.add(batchHandlerMethodHandlerAdapter);

        return handlerAdapters;
    }

    @Bean("batchMultiHandlerMethodAdapter")
    @SuppressWarnings("rawtypes")
    public MultiHandlerMethodAdapter createBatchMultiHandlerMethodAdapter(@Qualifier("batchHandlerAdapters") List<HandlerAdapter> handlerAdapters) {
        MultiHandlerMethodAdapter multiHandlerMethodAdapter = new MultiHandlerMethodAdapter();
        multiHandlerMethodAdapter.setHandlerAdapters(handlerAdapters);

        return multiHandlerMethodAdapter;
    }

    @Bean("batchExceptionHandlers")
    @ConditionalOnMissingBean(name = "batchExceptionHandlers")
    public List<IExceptionHandler> createBatchExceptionHandlers(
    ) {
        List<IExceptionHandler> jobExceptionHandlers = new ArrayList<IExceptionHandler>();
        jobExceptionHandlers.add(new JobExceptionHandler());

        return jobExceptionHandlers;
    }

    @Bean("batchCubeExceptionHandler")
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MultiCubeExceptionHandler createBatchCubeExceptionHandler(@Qualifier("batchExceptionHandlers") List<IExceptionHandler> batchExceptionHandlers) {

        MultiCubeExceptionHandler multiCubeExceptionHandler = new MultiCubeExceptionHandler();
        multiCubeExceptionHandler.setCubeExceptionHandlers(batchExceptionHandlers);

        return multiCubeExceptionHandler;
    }

    @Bean("batchViewResolvers")
    @ConditionalOnMissingBean(name = "batchViewResolvers")
    public List<ViewResolver> createBatchViewResolvers(JobService jobService) {
        JobViewResolver jobViewResolver = new JobViewResolver(jobService);

        List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
        viewResolvers.add(jobViewResolver);

        return viewResolvers;
    }

    @Bean("batchMultiViewResolver")
    public MultiViewResolver createBatchMultiViewResolver(@Qualifier("batchViewResolvers") List<ViewResolver> batchViewResolvers) {
        MultiViewResolver multiViewResolver = new MultiViewResolver();
        multiViewResolver.setViewResolvers(batchViewResolvers);

        return multiViewResolver;
    }

}
