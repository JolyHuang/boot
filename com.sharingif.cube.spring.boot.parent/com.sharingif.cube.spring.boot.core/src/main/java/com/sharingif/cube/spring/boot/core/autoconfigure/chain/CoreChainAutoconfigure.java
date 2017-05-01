package com.sharingif.cube.spring.boot.core.autoconfigure.chain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.AnnotationHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MDCChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.RequestLocalContextHolderChain;

/**
 * 自动配置core chian组件
 * 2017年5月1日 下午3:02:03
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class CoreChainAutoconfigure {
	
	@Bean(name="annotationHandlerMethodChain")
	public AnnotationHandlerMethodChain<HandlerMethodContent> getAnnotationHandlerMethodChain() {
		return new AnnotationHandlerMethodChain<HandlerMethodContent>();
	}
	
	@Bean(name="mdcChain")
	public MDCChain getMDCChain() {
		return new MDCChain();
	}
	
	@Bean(name="monitorPerformanceChain")
	public MonitorPerformanceChain getMonitorPerformanceChain() {
		return new MonitorPerformanceChain();
	}
	
	@Bean(name="requestLocalContextHolderChain")
	public RequestLocalContextHolderChain getRequestLocalContextHolderChain() {
		return new RequestLocalContextHolderChain();
	}
	
}
