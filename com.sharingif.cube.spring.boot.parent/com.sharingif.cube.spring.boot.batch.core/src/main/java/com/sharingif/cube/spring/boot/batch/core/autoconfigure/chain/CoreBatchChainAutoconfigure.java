package com.sharingif.cube.spring.boot.batch.core.autoconfigure.chain;

import com.sharingif.cube.components.handler.chain.RequestLocalContextHolderChain;
import com.sharingif.cube.core.handler.chain.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * CoreChainAutoconfigure
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 * 2017/11/21 下午4:35
 */
@Configuration
public class CoreBatchChainAutoconfigure {

    @Bean(name="batchTransactionChains")
    @ConditionalOnMissingBean(name="batchTransactionChains")
    public MultiHandlerMethodChain createBatchTransactionChains(
            RequestLocalContextHolderChain requestLocalContextHolderChain
            ,MDCChain mdcChain
            ,MonitorPerformanceChain transactionMonitorPerformanceChain
    ) {

        List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>();
        chains.add(requestLocalContextHolderChain);
        chains.add(mdcChain);
        chains.add(transactionMonitorPerformanceChain);

        MultiHandlerMethodChain multiHandlerMethodChain = new MultiHandlerMethodChain();
        multiHandlerMethodChain.setChains(chains);

        return  multiHandlerMethodChain;
    }

    @Bean(name="batchControllerChains")
    @ConditionalOnMissingBean(name="batchControllerChains")
    public MultiHandlerMethodChain createBatchControllerChains(
            MonitorPerformanceChain controllerMonitorPerformanceChain
            ,AnnotationHandlerMethodChain annotationHandlerMethodChain
    ) {

        List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>();
        chains.add(controllerMonitorPerformanceChain);
        chains.add(annotationHandlerMethodChain);

        MultiHandlerMethodChain multiHandlerMethodChain = new MultiHandlerMethodChain();
        multiHandlerMethodChain.setChains(chains);

        return  multiHandlerMethodChain;
    }

}
