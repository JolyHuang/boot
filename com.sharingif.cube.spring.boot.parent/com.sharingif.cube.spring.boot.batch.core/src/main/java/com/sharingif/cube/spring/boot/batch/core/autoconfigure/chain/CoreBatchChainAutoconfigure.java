package com.sharingif.cube.spring.boot.batch.core.autoconfigure.chain;

import com.sharingif.cube.core.handler.chain.AnnotationHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
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

    @Bean(name="batchControllerChains")
    @ConditionalOnMissingBean(name="batchControllerChains")
    public MultiHandlerMethodChain createSpringMCVChains(
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
