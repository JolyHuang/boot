package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure;

import com.sharingif.cube.core.config.CubeConfigure;
import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.web.springmvc.filter.ExtendedHiddenHttpMethodFilter;
import com.sharingif.cube.web.springmvc.handler.annotation.ExtendedRequestMappingHandlerAdapter;
import com.sharingif.cube.web.springmvc.request.SpringMVCHttpRequestContext;
import com.sharingif.cube.web.springmvc.servlet.ExtendedDispatcherServlet;
import com.sharingif.cube.web.springmvc.servlet.handler.SimpleHandlerExceptionResolver;
import com.sharingif.cube.web.springmvc.servlet.view.ExtendedContentNegotiatingViewResolver;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.filter.OrderedCharacterEncodingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.util.List;
import java.util.Map;

/**
 * ExtendedWebMvcConfigurationSupport
 * 2017年4月30日 下午9:44:44
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class WebCubeContextAutoconfigure {
	
	@Bean(name="simpleUrlHandlerMapping")
	@ConditionalOnMissingBean(name="simpleUrlHandlerMapping")
	public SimpleUrlHandlerMapping createSimpleUrlHandlerMapping(ResourceHttpRequestHandler resourceHttpRequestHandler) {
		
		Map<String, ResourceHttpRequestHandler> urlMap = new ManagedMap<String, ResourceHttpRequestHandler>();
		urlMap.put("/static/**", resourceHttpRequestHandler);
		urlMap.put("**/favicon.ico", resourceHttpRequestHandler);
		
		SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
		simpleUrlHandlerMapping.setUrlMap(urlMap);
		
		return simpleUrlHandlerMapping;
	}
	@Bean(name="handlerMapping")
	@ConditionalOnMissingBean(name="handlerMapping")
	public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
		handlerMapping.setUseSuffixPatternMatch(false);
		
		return handlerMapping;
	}

	@Bean(name="handlerAdapter")
	@ConditionalOnMissingBean(name="handlerAdapter")
	public RequestMappingHandlerAdapter createRequestMappingHandlerAdapter(
			MultiHandlerMethodChain springMCVChains
			,WebBindingInitializer webBindingInitializer
			,List<HttpMessageConverter<?>> messageConverters
			) {
		ExtendedRequestMappingHandlerAdapter handlerAdapter = new ExtendedRequestMappingHandlerAdapter();
		handlerAdapter.setHandlerMethodChain(springMCVChains);
		handlerAdapter.setWebBindingInitializer(webBindingInitializer);
		handlerAdapter.setMessageConverters(messageConverters);
		
		return handlerAdapter;
	}
	@Bean(name="httpRequestHandlerAdapter")
	@ConditionalOnMissingBean(name="httpRequestHandlerAdapter")
	public HttpRequestHandlerAdapter createHttpRequestHandlerAdapter() {
		HttpRequestHandlerAdapter httpRequestHandlerAdapter = new HttpRequestHandlerAdapter();
		
		return httpRequestHandlerAdapter;
	}
	
	@Bean("handlerExceptionResolver")
	public HandlerExceptionResolver createHandlerExceptionResolver(MultiCubeExceptionHandler<SpringMVCHttpRequestContext, HandlerMethod> springMVCCubeExceptionHandler) {
		SimpleHandlerExceptionResolver handlerExceptionResolver = new SimpleHandlerExceptionResolver();
		handlerExceptionResolver.setCubeExceptionHandler(springMVCCubeExceptionHandler);
		
		return handlerExceptionResolver;
	}
	
	@Bean(name="viewResolver")
	@ConditionalOnMissingBean(name = "viewResolver")
	public ViewResolver createViewResolver(
			ContentNegotiationManager contentNegotiationManager
			,List<ViewResolver> viewResolvers
			,List<View> defaultViews
			) {
		ExtendedContentNegotiatingViewResolver viewResolver = new ExtendedContentNegotiatingViewResolver();
		viewResolver.setContentNegotiationManager(contentNegotiationManager);
		viewResolver.setViewResolvers(viewResolvers);
		viewResolver.setDefaultViews(defaultViews);
		
		return viewResolver;
	}
	
	@Bean(name="characterEncodingFilter")
	public CharacterEncodingFilter createCharacterEncodingFilter() {
		CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setForceEncoding(true);
		filter.setEncoding(CubeConfigure.DEFAULT_ENCODING);
		return filter;
	}
	
	@Bean(name="hiddenHttpMethodFilter")
	public HiddenHttpMethodFilter createHiddenHttpMethodFilter() {
		return new ExtendedHiddenHttpMethodFilter();
	}
	
	@Bean(name="dispatcherServlet")
	public DispatcherServlet createDispatcherServlet() {
		ExtendedDispatcherServlet dispatcherServlet = new ExtendedDispatcherServlet();
		dispatcherServlet.setDispatchOptionsRequest(true);
		dispatcherServlet.setDispatchTraceRequest(false);
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

		dispatcherServlet.setDetectAllHandlerMappings(false);
		dispatcherServlet.setDetectAllHandlerAdapters(false);
		dispatcherServlet.setDetectAllViewResolvers(false);
		dispatcherServlet.setDetectAllHandlerExceptionResolvers(false);
		
		return dispatcherServlet;
	}
	
}
