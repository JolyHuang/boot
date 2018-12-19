package com.sharingif.cube.spring.boot.web.autoconfigure.chain;

import com.sharingif.cube.components.handler.chain.RequestLocalContextHolderChain;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MDCChain;
import com.sharingif.cube.core.handler.chain.MonitorPerformanceChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.security.web.handler.chain.CoreUserContextHolderChain;
import com.sharingif.cube.web.user.CoreUserHttpSessionManage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

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
	@ConditionalOnMissingBean(name = "webHandlerMethodChain")
	public MultiHandlerMethodChain createWebHandlerMethodChain(
			CoreUserContextHolderChain coreUserContextHolderChain
			,RequestLocalContextHolderChain requestLocalContextHolderChain
			,MDCChain mdcChain
			,MonitorPerformanceChain transactionMonitorPerformanceChain
			) {
		
		List<HandlerMethodChain> chains = new ArrayList<HandlerMethodChain>();
		chains.add(coreUserContextHolderChain);
		chains.add(requestLocalContextHolderChain);
		chains.add(mdcChain);
		chains.add(transactionMonitorPerformanceChain);
		
		MultiHandlerMethodChain webHandlerMethodChain = new MultiHandlerMethodChain();
		webHandlerMethodChain.setChains(chains);

		return  webHandlerMethodChain;
	}

	@Bean("coreUserContextHolderChain")
	public CoreUserContextHolderChain createCoreUserContextHolderChain(CoreUserHttpSessionManage coreUserHttpSessionManage) {
		CoreUserContextHolderChain coreUserContextHolderChain = new CoreUserContextHolderChain();
		coreUserContextHolderChain.setWebUserManage(coreUserHttpSessionManage);

		return coreUserContextHolderChain;
	}

}
