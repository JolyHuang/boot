package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileItemFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.security.web.exception.handler.validation.access.AccessDecisionCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.WebCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.WebExceptionContent;
import com.sharingif.cube.web.exception.handler.WebRequestInfo;
import com.sharingif.cube.web.exception.handler.validation.BindValidationCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.TokenValidationCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.ValidationCubeExceptionHandler;
import com.sharingif.cube.web.springmvc.exception.handler.validation.MethodArgumentNotValidExceptionHandler;
import com.sharingif.cube.web.springmvc.http.converter.json.ExtendedMappingJackson2HttpMessageConverter;
import com.sharingif.cube.web.springmvc.servlet.view.ExtendedInternalResourceViewResolver;
import com.sharingif.cube.web.springmvc.servlet.view.ExtendedJstlView;
import com.sharingif.cube.web.springmvc.servlet.view.json.ExtendedMappingJackson2JsonView;
import com.sharingif.cube.web.springmvc.servlet.view.referer.RefererViewResolver;
import com.sharingif.cube.web.springmvc.servlet.view.stream.StreamView;
import com.sharingif.cube.web.springmvc.servlet.view.stream.StreamViewResolver;

/**
 * SpringMVCComponentsAutoconfigure
 * 2017年5月1日 下午6:40:39
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class SpringMVCComponentsAutoconfigure {
	
	@Resource
	private ConversionService conversionService;
	@Resource
	private Validator validator;
	@Resource
	private AccessDecisionCubeExceptionHandler accessDecisionCubeExceptionHandler;
	@Resource
	private TokenValidationCubeExceptionHandler tokenValidationCubeExceptionHandler;
	@Resource
	private BindValidationCubeExceptionHandler bindValidationCubeExceptionHandler;
	@Resource
	private ValidationCubeExceptionHandler validationCubeExceptionHandler;
	@Resource
	private WebCubeExceptionHandler webCubeExceptionHandler;
	
	
	@Bean(name="webBindingInitializer")
	public WebBindingInitializer getWebBindingInitializer() {
		ConfigurableWebBindingInitializer webBindingInitializer = new ConfigurableWebBindingInitializer();
		webBindingInitializer.setConversionService(conversionService);
		webBindingInitializer.setValidator(validator);
		
		return webBindingInitializer;
	}
	
	@Bean(name="byteArrayHttpMessageConverter")
	public ByteArrayHttpMessageConverter getByteArrayHttpMessageConverter() {
		return new ByteArrayHttpMessageConverter();
	}
	
	@Bean(name="stringHttpMessageConverter")
	public StringHttpMessageConverter getStringHttpMessageConverter() {
		return new StringHttpMessageConverter();
	}
	
	@Bean("sourceHttpMessageConverter")
	@SuppressWarnings("rawtypes")
	public SourceHttpMessageConverter getSourceHttpMessageConverter() {
		return new SourceHttpMessageConverter();
	}
	
	@Bean(name="allEncompassingFormHttpMessageConverter")
	public AllEncompassingFormHttpMessageConverter getAllEncompassingFormHttpMessageConverter() {
		return new AllEncompassingFormHttpMessageConverter();
	}
	
	@Bean(name="mappingJackson2HttpMessageConverter")
	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
		return new ExtendedMappingJackson2HttpMessageConverter();
	}
	
	@Bean(name="customMessageConverters")
	public List<HttpMessageConverter<?>> getCustomMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>(4);
		messageConverters.add(getByteArrayHttpMessageConverter());
		messageConverters.add(getStringHttpMessageConverter());
		messageConverters.add(getSourceHttpMessageConverter());
		messageConverters.add(getAllEncompassingFormHttpMessageConverter());
		messageConverters.add(getMappingJackson2HttpMessageConverter());
		
		return messageConverters;
	}
	
	@Bean(name="methodArgumentNotValidExceptionHandler")
	public MethodArgumentNotValidExceptionHandler getMethodArgumentNotValidExceptionHandler() {
		MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler = new MethodArgumentNotValidExceptionHandler();
		
		return methodArgumentNotValidExceptionHandler;
	}
	
	@Bean(name="springMVCCubeExceptionHandlers")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MultiCubeExceptionHandler<WebRequestInfo,WebExceptionContent,HandlerMethod> getSpringMVCCubeExceptionHandlers() {
		List<WebCubeExceptionHandler> webCubeExceptionHandlers = new ArrayList<WebCubeExceptionHandler>(6);
		webCubeExceptionHandlers.add(accessDecisionCubeExceptionHandler);
		webCubeExceptionHandlers.add(tokenValidationCubeExceptionHandler);
		webCubeExceptionHandlers.add(getMethodArgumentNotValidExceptionHandler());
		webCubeExceptionHandlers.add(bindValidationCubeExceptionHandler);
		webCubeExceptionHandlers.add(validationCubeExceptionHandler);
		webCubeExceptionHandlers.add(webCubeExceptionHandler);
		
		
		MultiCubeExceptionHandler springMVCCubeExceptionHandlers = new MultiCubeExceptionHandler();
		springMVCCubeExceptionHandlers.setCubeExceptionHandlers(webCubeExceptionHandlers);
		
		return springMVCCubeExceptionHandlers;
	}
	
	@Bean(name="contentNegotiationManager")
	public ContentNegotiationManagerFactoryBean getContentNegotiationManager() {
		
		Properties mediaTypes = new Properties();
		mediaTypes.put("image", "image/*");
		mediaTypes.put("json", MediaType.APPLICATION_JSON_VALUE);
		mediaTypes.put("xml", MediaType.TEXT_XML_VALUE);
		mediaTypes.put("html", MediaType.TEXT_HTML_VALUE);
		
		ContentNegotiationManagerFactoryBean contentNegotiationManagerFactoryBean = new ContentNegotiationManagerFactoryBean();
		contentNegotiationManagerFactoryBean.setFavorPathExtension(false);
		contentNegotiationManagerFactoryBean.setFavorParameter(true);
		contentNegotiationManagerFactoryBean.setDefaultContentType(MediaType.TEXT_HTML);
		contentNegotiationManagerFactoryBean.setMediaTypes(mediaTypes);
		
		return contentNegotiationManagerFactoryBean;
	}
	
	@Bean(name="streamView")
	public StreamView getStreamView() {
		StreamView streamView = new StreamView();
		streamView.setContentType(MediaType.IMAGE_PNG_VALUE);
		
		return streamView;
	}
	
	@Bean(name="streamViewResolver")
	public StreamViewResolver getStreamViewResolver() {
		Map<String,StreamView> streamViews = new HashMap<String,StreamView>(1);
		streamViews.put("imagePng", getStreamView());
		
		StreamViewResolver streamViewResolver = new StreamViewResolver();
		streamViewResolver.setStreamViews(streamViews);
		
		return streamViewResolver;
	}
	
	@Bean(name="refererViewResolver")
	public RefererViewResolver getRefererViewResolver() {
		RefererViewResolver refererViewResolver = new RefererViewResolver();
		
		return refererViewResolver;
	}
	
	@Bean(name="internalResourceViewResolver")
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		ExtendedInternalResourceViewResolver internalResourceViewResolver = new ExtendedInternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setViewClass(ExtendedJstlView.class);
		
		return internalResourceViewResolver;
	}
	
	@Bean(name="multipartResolver")
	@ConditionalOnBean(FileItemFactory.class)
	public CommonsMultipartResolver getMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1024*1024*5);
		
		return multipartResolver;
	}
	
	@Bean(name="mappingJackson2JsonView")
	public MappingJackson2JsonView getMappingJackson2JsonView() {
		ExtendedMappingJackson2JsonView mappingJackson2JsonView = new ExtendedMappingJackson2JsonView();
		
		return mappingJackson2JsonView;
	}
	
	@Bean(name="viewResolvers")
	public List<ViewResolver> getViewResolvers() {
		List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>(3);
		viewResolvers.add(getStreamViewResolver());
		viewResolvers.add(getRefererViewResolver());
		viewResolvers.add(getInternalResourceViewResolver());
		
		return viewResolvers;
	}
	
	@Bean(name="defaultViews")
	public List<View> getDefaultViews() {
		List<View> defaultViews = new ArrayList<View>(1);
		defaultViews.add(getMappingJackson2JsonView());
		
		return defaultViews;
	}
	
}
