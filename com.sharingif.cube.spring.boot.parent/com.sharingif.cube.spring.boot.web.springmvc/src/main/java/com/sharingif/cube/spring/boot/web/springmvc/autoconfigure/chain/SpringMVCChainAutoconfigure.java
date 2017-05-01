package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.chain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.spring.boot.core.autoconfigure.chain.CoreChainAutoconfigure;
import com.sharingif.cube.web.springmvc.handler.SpringMVCHandlerMethodContent;
import com.sharingif.cube.web.springmvc.handler.chain.ViewRefererChain;

/**
 * SpringMVCChainAutoconfigure
 * 2017年5月1日 下午3:19:52
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class SpringMVCChainAutoconfigure {
	
	private static final SpringMVCChainAutoconfigure springMVCChainAutoconfigure = new SpringMVCChainAutoconfigure();
	
	public static SpringMVCChainAutoconfigure getInstance() {
		return springMVCChainAutoconfigure;
	}
	
	@Bean
	public MultiHandlerMethodChain<SpringMVCHandlerMethodContent> getSpringMCVChains() {
		
		List<HandlerMethodChain<? super SpringMVCHandlerMethodContent>> chains = new ArrayList<HandlerMethodChain<? super SpringMVCHandlerMethodContent>>(3);
		chains.add(getViewRefererChain());
		chains.add(CoreChainAutoconfigure.getInstance().getMonitorPerformanceChain());
		chains.add(CoreChainAutoconfigure.getInstance().getAnnotationHandlerMethodChain());
		
		MultiHandlerMethodChain<SpringMVCHandlerMethodContent> springMVCHandlerMethodContent = new MultiHandlerMethodChain<SpringMVCHandlerMethodContent>();
		springMVCHandlerMethodContent.setChains(chains);
		
		return  springMVCHandlerMethodContent;
	}
	
	@Bean
	public ViewRefererChain getViewRefererChain() {
		return new ViewRefererChain();
	}

}
