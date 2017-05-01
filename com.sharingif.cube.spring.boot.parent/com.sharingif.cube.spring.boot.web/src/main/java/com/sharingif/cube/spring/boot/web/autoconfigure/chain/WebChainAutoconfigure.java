package com.sharingif.cube.spring.boot.web.autoconfigure.chain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sharingif.cube.communication.http.handler.HttpHandlerMethodContent;
import com.sharingif.cube.core.handler.chain.HandlerMethodChain;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.spring.boot.core.autoconfigure.chain.CoreChainAutoconfigure;

/**
 * WebChainAutoconfigure
 * 2017年5月1日 下午3:19:52
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class WebChainAutoconfigure {
	
	private static final WebChainAutoconfigure webChainAutoconfigure = new WebChainAutoconfigure();
	
	public static WebChainAutoconfigure getInstance() {
		return webChainAutoconfigure;
	}
	
	@Bean
	public MultiHandlerMethodChain<HttpHandlerMethodContent> getWebHandlerMethodChain() {
		
		List<HandlerMethodChain<? super HttpHandlerMethodContent>> chains = new ArrayList<HandlerMethodChain<? super HttpHandlerMethodContent>>(2);
		chains.add(CoreChainAutoconfigure.getInstance().getRequestLocalContextHolderChain());
		chains.add(CoreChainAutoconfigure.getInstance().getMDCChain());
		
		MultiHandlerMethodChain<HttpHandlerMethodContent> webHandlerMethodChain = new MultiHandlerMethodChain<HttpHandlerMethodContent>();
		webHandlerMethodChain.setChains(chains);
		
		return  webHandlerMethodChain;
	}

}
