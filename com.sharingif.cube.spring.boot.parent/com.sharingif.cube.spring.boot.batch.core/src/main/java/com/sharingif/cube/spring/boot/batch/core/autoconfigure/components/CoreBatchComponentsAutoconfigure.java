package com.sharingif.cube.spring.boot.batch.core.autoconfigure.components;

import com.sharingif.cube.batch.core.JobConfig;
import com.sharingif.cube.batch.core.JobService;
import com.sharingif.cube.batch.core.exception.JobExceptionHandler;
import com.sharingif.cube.batch.core.handler.adapter.JobRequestHandlerMethodArgumentResolver;
import com.sharingif.cube.batch.core.request.JobRequestContextResolver;
import com.sharingif.cube.batch.core.view.JobView;
import com.sharingif.cube.batch.core.view.JobViewResolver;
import com.sharingif.cube.core.view.MultiViewResolver;
import com.sharingif.cube.core.view.ViewResolver;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Bean("taskScheduler")
    public TaskScheduler createTaskScheduler(@Value("${work.max.pool.size}") int poolSize) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(poolSize);
        threadPoolTaskScheduler.setThreadNamePrefix("taskScheduler-");
        return threadPoolTaskScheduler;
    }

    @Bean("jobThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor createThreadPoolTaskExecutor(@Value("${work.max.pool.size}") int poolSize) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(poolSize);
        threadPoolTaskExecutor.setMaxPoolSize(poolSize);
        threadPoolTaskExecutor.setThreadNamePrefix("jobThreadPoolTaskExecutor-");

        return threadPoolTaskExecutor;
    }

    @Bean("jobRequestHandlerMethodArgumentResolver")
    public JobRequestHandlerMethodArgumentResolver createJobRequestHandlerMethodArgumentResolver() {
        return new JobRequestHandlerMethodArgumentResolver();
    }

    @Bean("batchArgumentResolvers")
    @ConditionalOnMissingBean(name = "batchArgumentResolvers")
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

    @Bean("batchHandlerMappings")
    public List<HandlerMapping> createBatchHandlerMappings(
            RequestMappingHandlerMapping requestMappingHandlerMapping
    ) {
        List<HandlerMapping> handlerMappings = new ArrayList<HandlerMapping>();
        handlerMappings.add(requestMappingHandlerMapping);

        return handlerMappings;
    }

    @Bean("batchMultiHandlerMapping")
    public MultiHandlerMapping createBatchMultiHandlerMapping(@Qualifier("batchHandlerMappings") List<HandlerMapping> batchHandlerMappings) {
        MultiHandlerMapping multiHandlerMapping = new MultiHandlerMapping();
        multiHandlerMapping.setHandlerMappings(batchHandlerMappings);

        return multiHandlerMapping;
    }

    @Bean("batchHandlerMethodHandlerAdapter")
    public HandlerMethodHandlerAdapter createBatchHandlerMethodHandlerAdapter(
            MultiHandlerMethodChain batchControllerChains
            ,@Qualifier("batchArgumentResolvers") List<HandlerMethodArgumentResolver> batchArgumentResolvers
            ,BindingInitializer bindingInitializer
    ) {
        HandlerMethodHandlerAdapter handlerMethodHandlerAdapter = new HandlerMethodHandlerAdapter();
        handlerMethodHandlerAdapter.setHandlerMethodChain(batchControllerChains);
        handlerMethodHandlerAdapter.setArgumentResolvers(batchArgumentResolvers);
        handlerMethodHandlerAdapter.setBindingInitializer(bindingInitializer);

        return handlerMethodHandlerAdapter;
    }

    @Bean("batchHandlerAdapters")
    @ConditionalOnMissingBean(name = "batchHandlerAdapters")
    public List<HandlerAdapter> createBatchHandlerAdapters(
            HandlerMethodHandlerAdapter batchHandlerMethodHandlerAdapter
    ) {
        List<HandlerAdapter> handlerAdapters = new ArrayList<HandlerAdapter>();
        handlerAdapters.add(batchHandlerMethodHandlerAdapter);

        return handlerAdapters;
    }

    @Bean("batchMultiHandlerMethodAdapter")
    public MultiHandlerMethodAdapter createBatchMultiHandlerMethodAdapter(@Qualifier("batchHandlerAdapters") List<HandlerAdapter> batchHandlerAdapters) {
        MultiHandlerMethodAdapter multiHandlerMethodAdapter = new MultiHandlerMethodAdapter();
        multiHandlerMethodAdapter.setHandlerAdapters(batchHandlerAdapters);

        return multiHandlerMethodAdapter;
    }

    @Bean("jobExceptionHandler")
    public JobExceptionHandler createJobExceptionHandler() {
        return new JobExceptionHandler();
    }

    @Bean("batchExceptionHandlers")
    @ConditionalOnMissingBean(name = "batchExceptionHandlers")
    public List<IExceptionHandler> createBatchExceptionHandlers(JobExceptionHandler jobExceptionHandler) {
        List<IExceptionHandler> jobExceptionHandlers = new ArrayList<IExceptionHandler>();
        jobExceptionHandlers.add(jobExceptionHandler);

        return jobExceptionHandlers;
    }

    @Bean("batchCubeExceptionHandler")
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MultiCubeExceptionHandler createBatchCubeExceptionHandler(@Qualifier("batchExceptionHandlers") List<IExceptionHandler> batchExceptionHandlers) {

        MultiCubeExceptionHandler multiCubeExceptionHandler = new MultiCubeExceptionHandler();
        multiCubeExceptionHandler.setCubeExceptionHandlers(batchExceptionHandlers);

        return multiCubeExceptionHandler;
    }

    @Bean("jobView")
    public JobView createJobView(@Autowired(required = false)JobService jobService) {
        JobView jobView = new JobView();
        jobView.setJobService(jobService);

        return jobView;
    }

    @Bean("batchViewResolvers")
    @ConditionalOnMissingBean(name = "batchViewResolvers")
    public List<ViewResolver> createBatchViewResolvers(JobView jobView) {
        JobViewResolver jobViewResolver = new JobViewResolver();
        jobViewResolver.setJobView(jobView);

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
