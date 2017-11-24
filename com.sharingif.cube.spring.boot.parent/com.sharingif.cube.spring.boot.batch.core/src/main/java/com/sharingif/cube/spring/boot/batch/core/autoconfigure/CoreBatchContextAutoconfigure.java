package com.sharingif.cube.spring.boot.batch.core.autoconfigure;

import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequestContextResolver;
import com.sharingif.cube.communication.view.MultiViewResolver;
import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @SuppressWarnings("rawtypes")
    @Bean("simpleDispatcherHandler")
    public SimpleDispatcherHandler createSimpleDispatcherHandler(
            MultiHandlerMethodChain transactionMonitorPerformanceChain
            ,JobRequestContextResolver jobRequestContextResolver
            ,MultiHandlerMapping multiHandlerMapping
            ,MultiHandlerMethodAdapter multiHandlerMethodAdapter
            ,MultiCubeExceptionHandler multiCubeExceptionHandler
            ,MultiViewResolver multiViewResolver
    ) {
        SimpleDispatcherHandler simpleDispatcherHandler = new SimpleDispatcherHandler();
        simpleDispatcherHandler.setHandlerMethodChain(transactionMonitorPerformanceChain);
        simpleDispatcherHandler.setRequestContextResolver(jobRequestContextResolver);
        simpleDispatcherHandler.setMultiHandlerMapping(multiHandlerMapping);
        simpleDispatcherHandler.setMultiHandlerMethodAdapter(multiHandlerMethodAdapter);
        simpleDispatcherHandler.setMultiCubeExceptionHandler(multiCubeExceptionHandler);
        simpleDispatcherHandler.setMultiViewResolver(multiViewResolver);

        return simpleDispatcherHandler;
    }

}
