package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.chain;

import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.AnnotationHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.security.web.handler.chain.CoreUserContextHolderChain;
import com.sharingif.cube.web.springmvc.handler.SpringMVCHandlerMethodContent;
import com.sharingif.cube.web.springmvc.handler.chain.ViewRefererChain;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

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
	public MultiHandlerMethodChain<SpringMVCHandlerMethodContent> createSpringMCVChains(
			CoreUserContextHolderChain coreUserContextHolderChain
			,MonitorPerformanceChain controllerMonitorPerformanceChain
			,ViewRefererChain viewRefererChain
			,AnnotationHandlerMethodChain<HandlerMethodContent> annotationHandlerMethodChain
			) {
		
		List<HandlerMethodChain<? super SpringMVCHandlerMethodContent>> chains = new ArrayList<HandlerMethodChain<? super SpringMVCHandlerMethodContent>>(3);
		chains.add(coreUserContextHolderChain);
		chains.add(controllerMonitorPerformanceChain);
		chains.add(viewRefererChain);
		chains.add(annotationHandlerMethodChain);
		
		MultiHandlerMethodChain<SpringMVCHandlerMethodContent> springMVCHandlerMethodContent = new MultiHandlerMethodChain<SpringMVCHandlerMethodContent>();
		springMVCHandlerMethodContent.setChains(chains);
		
		return  springMVCHandlerMethodContent;
	}

}
