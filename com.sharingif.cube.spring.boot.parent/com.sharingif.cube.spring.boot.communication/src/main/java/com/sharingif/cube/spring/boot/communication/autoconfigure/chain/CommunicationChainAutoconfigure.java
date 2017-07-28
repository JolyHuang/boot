package com.sharingif.cube.spring.boot.communication.autoconfigure.chain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sharingif.cube.communication.handler.chain.JsonModelTransportChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;

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
	public MultiHandlerMethodChain createTransportChains(
			@Qualifier("transportMonitorPerformanceChain")MonitorPerformanceChain transportMonitorPerformanceChain
			) {
		
		List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>(1);
		chains.add(transportMonitorPerformanceChain);
		
		MultiHandlerMethodChain transportChains = new MultiHandlerMethodChain();
		transportChains.setChains(chains);
		
		return transportChains;
	}

	@Bean(name="jsonModelTransportChain")
	public JsonModelTransportChain createJsonModelTransportChain() {
		return new JsonModelTransportChain();
	}

}
