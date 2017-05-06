package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.core.handler.chain.MultiHandlerMethodChain;
import com.sharingif.cube.web.exception.handler.WebExceptionContent;
import com.sharingif.cube.web.exception.handler.WebRequestInfo;
import com.sharingif.cube.web.springmvc.filter.ExtendedHiddenHttpMethodFilter;
import com.sharingif.cube.web.springmvc.handler.SpringMVCHandlerMethodContent;
import com.sharingif.cube.web.springmvc.handler.annotation.ExtendedRequestMappingHandlerAdapter;
import com.sharingif.cube.web.springmvc.servlet.ExtendedDispatcherServlet;
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
public class WebCubeContextAutoconfigure {
	
	@Bean(name="handlerMapping")
	public RequestMappingHandlerMapping createRequestMappingHandlerMapping() {
		RequestMappingHandlerMapping handlerMapping = new RequestMappingHandlerMapping();
		handlerMapping.setUseSuffixPatternMatch(false);
		return handlerMapping;
	}

	@Bean(name="handlerAdapter")
	public RequestMappingHandlerAdapter createRequestMappingHandlerAdapter(
			MultiHandlerMethodChain<SpringMVCHandlerMethodContent> springMCVChains
			,WebBindingInitializer webBindingInitializer
			,List<HttpMessageConverter<?>> customMessageConverters
			) {
		ExtendedRequestMappingHandlerAdapter handlerAdapter = new ExtendedRequestMappingHandlerAdapter();
		handlerAdapter.setHandlerMethodChain(springMCVChains);
		handlerAdapter.setWebBindingInitializer(webBindingInitializer);
		handlerAdapter.setMessageConverters(customMessageConverters);
		
		return handlerAdapter;
	}
	
	@Bean("handlerExceptionResolver")
	public HandlerExceptionResolver createHandlerExceptionResolver(@Qualifier("springMVCCubeExceptionHandlers") MultiCubeExceptionHandler<WebRequestInfo, WebExceptionContent, HandlerMethod> springMVCCubeExceptionHandlers) {
		SimpleHandlerExceptionResolver handlerExceptionResolver = new SimpleHandlerExceptionResolver();
		handlerExceptionResolver.setCubeExceptionHandler(springMVCCubeExceptionHandlers);
		
		return handlerExceptionResolver;
	}
	
	@Bean(name="viewResolver")
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
	public CharacterEncodingFilter createCharacterEncodingFilter(@Value("${app.properties.default.encoding}") String encoding) {
		CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setForceEncoding(true);
		filter.setEncoding(encoding);
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
		dispatcherServlet.setThrowExceptionIfNoHandlerFound(false);
		
		dispatcherServlet.setDetectAllHandlerMappings(false);
		dispatcherServlet.setDetectAllHandlerAdapters(false);
		dispatcherServlet.setDetectAllHandlerExceptionResolvers(false);
		
		return dispatcherServlet;
	}
	
}
