package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.chain;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.AnnotationHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
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
	
	@Resource
	private MonitorPerformanceChain monitorPerformanceChain;
	@Resource
	private AnnotationHandlerMethodChain<HandlerMethodContent> annotationHandlerMethodChain;
	
	@Bean(name="springMCVChains")
	public MultiHandlerMethodChain<SpringMVCHandlerMethodContent> getSpringMCVChains() {
		
		List<HandlerMethodChain<? super SpringMVCHandlerMethodContent>> chains = new ArrayList<HandlerMethodChain<? super SpringMVCHandlerMethodContent>>(3);
		chains.add(getViewRefererChain());
		chains.add(monitorPerformanceChain);
		chains.add(annotationHandlerMethodChain);
		
		MultiHandlerMethodChain<SpringMVCHandlerMethodContent> springMVCHandlerMethodContent = new MultiHandlerMethodChain<SpringMVCHandlerMethodContent>();
		springMVCHandlerMethodContent.setChains(chains);
		
		return  springMVCHandlerMethodContent;
	}
	
	@Bean(name="viewRefererChain")
	public ViewRefererChain getViewRefererChain() {
		return new ViewRefererChain();
	}

}
