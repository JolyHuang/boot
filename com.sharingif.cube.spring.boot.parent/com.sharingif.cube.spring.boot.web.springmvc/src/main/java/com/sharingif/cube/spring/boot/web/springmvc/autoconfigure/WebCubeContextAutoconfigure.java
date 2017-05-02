package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.sharingif.cube.core.exception.handler.AbstractCubeExceptionHandler;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.web.exception.handler.WebExceptionContent;
import com.sharingif.cube.web.exception.handler.WebRequestInfo;
import com.sharingif.cube.web.springmvc.handler.SpringMVCHandlerMethodContent;
import com.sharingif.cube.web.springmvc.handler.annotation.ExtendedRequestMappingHandlerAdapter;
import com.sharingif.cube.web.springmvc.servlet.handler.SimpleHandlerExceptionResolver;
import com.sharingif.cube.web.springmvc.servlet.view.ExtendedContentNegotiatingViewResolver;

/**
 * ExtendedWebMvcConfigurationSupport
 * 2017年4月30日 下午9:44:44
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class WebCubeContextAutoconfigure extends WebMvcConfigurationSupport {
	
	@Resource
	private MultiHandlerMethodChain<SpringMVCHandlerMethodContent> springMCVChains;
	@Resource
	private WebBindingInitializer webBindingInitializer;
	@Resource
	private List<HttpMessageConverter<?>> customMessageConverters;
	@Resource
	private AbstractCubeExceptionHandler<WebRequestInfo, WebExceptionContent, HandlerMethod> springMVCCubeExceptionHandlers;
	@Resource
	private ContentNegotiationManager contentNegotiationManager;
	@Resource
	private List<ViewResolver> viewResolvers;
	@Resource
	private List<View> defaultViews;

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
		handlerAdapter.setHandlerMethodChain(springMCVChains);
		handlerAdapter.setWebBindingInitializer(webBindingInitializer);
		handlerAdapter.setMessageConverters(customMessageConverters);
		
		return handlerAdapter;
	}
	
	@Bean("handlerExceptionResolver")
	@Override
	public HandlerExceptionResolver handlerExceptionResolver() {
		SimpleHandlerExceptionResolver handlerExceptionResolver = new SimpleHandlerExceptionResolver();
		handlerExceptionResolver.setCubeExceptionHandler(springMVCCubeExceptionHandlers);
		
		return handlerExceptionResolver;
	}
	
	@Bean(name="viewResolver")
	@Override
	public ViewResolver mvcViewResolver() {
		ExtendedContentNegotiatingViewResolver viewResolver = new ExtendedContentNegotiatingViewResolver();
		viewResolver.setContentNegotiationManager(contentNegotiationManager);
		viewResolver.setViewResolvers(viewResolvers);
		viewResolver.setDefaultViews(defaultViews);
		
		return viewResolver;
	}
	
}
