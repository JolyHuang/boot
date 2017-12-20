package com.sharingif.cube.spring.boot.batch.core.autoconfigure;

import com.sharingif.cube.batch.core.handler.SimpleDispatcherHandler;
import com.sharingif.cube.batch.core.request.JobRequestContextResolver;
import com.sharingif.cube.communication.view.MultiViewResolver;
import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.adapter.MultiHandlerMethodAdapter;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.core.handler.mapping.MultiHandlerMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

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
    @ConditionalOnMissingBean(name = "simpleDispatcherHandler")
    public SimpleDispatcherHandler createSimpleDispatcherHandler(
            MultiHandlerMethodChain transactionMonitorPerformanceChain
            ,JobRequestContextResolver jobRequestContextResolver
            ,MultiHandlerMapping batchMultiHandlerMapping
            ,MultiHandlerMethodAdapter batchMultiHandlerMethodAdapter
            ,MultiCubeExceptionHandler batchCubeExceptionHandler
            ,MultiViewResolver batchMultiViewResolver
            ,DataSourceTransactionManager dataSourceTransactionManager
    ) {
        SimpleDispatcherHandler simpleDispatcherHandler = new SimpleDispatcherHandler();
        simpleDispatcherHandler.setHandlerMethodChain(transactionMonitorPerformanceChain);
        simpleDispatcherHandler.setRequestContextResolver(jobRequestContextResolver);
        simpleDispatcherHandler.setMultiHandlerMapping(batchMultiHandlerMapping);
        simpleDispatcherHandler.setMultiHandlerMethodAdapter(batchMultiHandlerMethodAdapter);
        simpleDispatcherHandler.setMultiCubeExceptionHandler(batchCubeExceptionHandler);
        simpleDispatcherHandler.setMultiViewResolver(batchMultiViewResolver);
        simpleDispatcherHandler.setDataSourceTransactionManager(dataSourceTransactionManager);

        return simpleDispatcherHandler;
    }

}
