package com.sharingif.cube.spring.boot.batch.core.autoconfigure;

import com.sharingif.cube.batch.core.handler.MultithreadDispatcherHandler;
import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequestContextResolver;
import com.sharingif.cube.core.view.MultiViewResolver;
import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * CoreBatchContextAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/21 下午4:44
 */
@Configuration
public class CoreBatchContextAutoconfigure {

    @Bean("batchDispatcherHandler")
    @ConditionalOnMissingBean(name = "batchDispatcherHandler")
    public SimpleDispatcherHandler createatchDispatcherHandler(
            MultiHandlerMethodChain batchTransactionChains
            ,JobRequestContextResolver jobRequestContextResolver
            ,MultiHandlerMapping batchMultiHandlerMapping
            ,MultiHandlerMethodAdapter batchMultiHandlerMethodAdapter
            ,MultiCubeExceptionHandler batchCubeExceptionHandler
            ,MultiViewResolver batchMultiViewResolver
    ) {
        SimpleDispatcherHandler simpleDispatcherHandler = new SimpleDispatcherHandler();
        simpleDispatcherHandler.setHandlerMethodChain(batchTransactionChains);
        simpleDispatcherHandler.setRequestContextResolver(jobRequestContextResolver);
        simpleDispatcherHandler.setMultiHandlerMapping(batchMultiHandlerMapping);
        simpleDispatcherHandler.setMultiHandlerMethodAdapter(batchMultiHandlerMethodAdapter);
        simpleDispatcherHandler.setMultiCubeExceptionHandler(batchCubeExceptionHandler);
        simpleDispatcherHandler.setMultiViewResolver(batchMultiViewResolver);

        return simpleDispatcherHandler;
    }

    @Bean("batchMultithreadDispatcherHandler")
    public MultithreadDispatcherHandler createBatchMultithreadDispatcherHandler(
            SimpleDispatcherHandler batchDispatcherHandler
            , ThreadPoolTaskExecutor jobThreadPoolTaskExecutor
    ) {
        MultithreadDispatcherHandler multithreadDispatcherHandler = new MultithreadDispatcherHandler();

        multithreadDispatcherHandler.setMultithreadDispatcherHandlerThreadPoolTaskExecutor(jobThreadPoolTaskExecutor);
        multithreadDispatcherHandler.setSimpleDispatcherHandler(batchDispatcherHandler);

        return multithreadDispatcherHandler;
    }

}
