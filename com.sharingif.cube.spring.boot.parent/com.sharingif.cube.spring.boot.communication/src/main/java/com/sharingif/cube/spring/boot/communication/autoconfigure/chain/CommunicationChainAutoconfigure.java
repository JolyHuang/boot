package com.sharingif.cube.spring.boot.communication.autoconfigure.chain;

import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * CommunicationChainAutoconfigure
 * 2017年5月14日 下午4:02:21
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class CommunicationChainAutoconfigure {
	
	@Bean(name="transportChains")
	@ConditionalOnMissingBean(name = "transportChains")
	public MultiHandlerMethodChain<HandlerMethodContent> createTransportChains(
			@Qualifier("transportMonitorPerformanceChain")MonitorPerformanceChain transportMonitorPerformanceChain
			) {
		
		List<HandlerMethodChain<? super HandlerMethodContent>> chains = new ArrayList<HandlerMethodChain<? super HandlerMethodContent>>(1);
		chains.add(transportMonitorPerformanceChain);
		
		MultiHandlerMethodChain<HandlerMethodContent> transportChains = new MultiHandlerMethodChain<HandlerMethodContent>();
		transportChains.setChains(chains);
		
		return transportChains;
	}

}
