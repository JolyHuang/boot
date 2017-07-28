package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.chain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sharingif.cube.core.handler.chain.AnnotationHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.security.web.handler.chain.CoreUserContextHolderChain;
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
	
	@Bean(name="viewRefererChain")
	public ViewRefererChain createViewRefererChain() {
		return new ViewRefererChain();
	}
	
	@Bean(name="springMCVChains")
	@ConditionalOnMissingBean(name="springMCVChains")
	public MultiHandlerMethodChain createSpringMCVChains(
			CoreUserContextHolderChain coreUserContextHolderChain
			,MonitorPerformanceChain controllerMonitorPerformanceChain
			,ViewRefererChain viewRefererChain
			,AnnotationHandlerMethodChain annotationHandlerMethodChain
			) {
		
		List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>(3);
		chains.add(coreUserContextHolderChain);
		chains.add(controllerMonitorPerformanceChain);
		chains.add(viewRefererChain);
		chains.add(annotationHandlerMethodChain);
		
		MultiHandlerMethodChain springMVCHandlerMethodContent = new MultiHandlerMethodChain();
		springMVCHandlerMethodContent.setChains(chains);
		
		return  springMVCHandlerMethodContent;
	}

}
