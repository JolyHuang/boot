package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.chain.SpringMVCChainAutoconfigure;
import com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.components.SpringMVCComponentsAutoconfigure;
import com.sharingif.cube.web.springmvc.handler.annotation.ExtendedRequestMappingHandlerAdapter;

/**
 * ExtendedWebMvcConfigurationSupport
 * 2017年4月30日 下午9:44:44
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class WebCubeContextAutoconfigure extends WebMvcConfigurationSupport {

	@Bean(name="handlerMapping")
	@Override
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = super.requestMappingHandlerMapping();
		handlerMapping.setUseSuffixPatternMatch(false);
		return handlerMapping;
	}

	@Bean(name="handlerAdapter")
	@Override
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		ExtendedRequestMappingHandlerAdapter handlerAdapter = new ExtendedRequestMappingHandlerAdapter();
		handlerAdapter.setHandlerMethodChain(SpringMVCChainAutoconfigure.getInstance().getSpringMCVChains());
		handlerAdapter.setWebBindingInitializer(SpringMVCComponentsAutoconfigure.getInstance().getWebBindingInitializer());
		handlerAdapter.setMessageConverters(SpringMVCComponentsAutoconfigure.getInstance().getMessageConverters());
		
		return handlerAdapter;
	}
	
}
