package com.sharingif.cube.spring.boot.web.autoconfigure.chain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MDCChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.RequestLocalContextHolderChain;

/**
 * WebChainAutoconfigure
 * 2017年5月1日 下午3:19:52
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class WebChainAutoconfigure {
	
	@Bean(name="webHandlerMethodChain")
	public MultiHandlerMethodChain<HttpHandlerMethodContent> createWebHandlerMethodChain(
			MDCChain mdcChain
			,RequestLocalContextHolderChain requestLocalContextHolderChain
			,@Qualifier("transactionMonitorPerformanceChain")MonitorPerformanceChain transactionMonitorPerformanceChain
			) {
		
		List<HandlerMethodChain<? super HttpHandlerMethodContent>> chains = new ArrayList<HandlerMethodChain<? super HttpHandlerMethodContent>>(2);
		chains.add(requestLocalContextHolderChain);
		chains.add(mdcChain);
		chains.add(transactionMonitorPerformanceChain);
		
		MultiHandlerMethodChain<HttpHandlerMethodContent> webHandlerMethodChain = new MultiHandlerMethodChain<HttpHandlerMethodContent>();
		webHandlerMethodChain.setChains(chains);
		
		return  webHandlerMethodChain;
	}

}
