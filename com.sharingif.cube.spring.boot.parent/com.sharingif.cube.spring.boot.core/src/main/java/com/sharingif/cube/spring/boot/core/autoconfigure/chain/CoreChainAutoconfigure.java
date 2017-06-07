package com.sharingif.cube.spring.boot.core.autoconfigure.chain;

import com.sharingif.cube.core.handler.HandlerMethodContent;
import com.sharingif.cube.core.handler.chain.AnnotationHandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MDCChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	public AnnotationHandlerMethodChain<HandlerMethodContent> createAnnotationHandlerMethodChain() {
		return new AnnotationHandlerMethodChain<HandlerMethodContent>();
	}
	
	@Bean(name="mdcChain")
	public MDCChain getMDCChain() {
		return new MDCChain();
	}
	
	@Bean(name="transactionMonitorPerformanceChain")
	public MonitorPerformanceChain createTransactionMonitorPerformanceChain() {
		MonitorPerformanceChain transportMonitorPerformanceChain = new MonitorPerformanceChain();
		transportMonitorPerformanceChain.setName("transaction");
		return transportMonitorPerformanceChain;
	}
	
	@Bean(name="controllerMonitorPerformanceChain")
	public MonitorPerformanceChain createControllerMonitorPerformanceChain() {
		return new MonitorPerformanceChain();
	}
	
	@Bean(name="transportMonitorPerformanceChain")
	public MonitorPerformanceChain createTransportMonitorPerformanceChain() {
		MonitorPerformanceChain transportMonitorPerformanceChain = new MonitorPerformanceChain();
		transportMonitorPerformanceChain.setName("Transport");
		return transportMonitorPerformanceChain;
	}
	
}
