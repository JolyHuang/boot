package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.components;

import com.sharingif.cube.core.exception.handler.MultiCubeExceptionHandler;
import com.sharingif.cube.core.handler.HandlerMethod;
import com.sharingif.cube.security.web.exception.handler.validation.access.AccessDecisionCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.WebCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.BindValidationCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.TokenValidationCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.ValidationCubeExceptionHandler;
import com.sharingif.cube.web.springmvc.exception.NoHandlerFoundExceptionHandler;
import com.sharingif.cube.web.springmvc.exception.handler.validation.MethodArgumentNotValidExceptionHandler;
import com.sharingif.cube.web.springmvc.exception.handler.validation.SingleMethodArgumentNotValidExceptionHandler;
import com.sharingif.cube.web.springmvc.http.converter.json.ExtendedMappingJackson2HttpMessageConverter;
import com.sharingif.cube.web.springmvc.request.SpringMVCHttpRequestContext;
import com.sharingif.cube.web.springmvc.servlet.view.ExtendedInternalResourceViewResolver;
import com.sharingif.cube.web.springmvc.servlet.view.ExtendedJstlView;
import com.sharingif.cube.web.springmvc.servlet.view.NoHandlerFoundViewResolver;
import com.sharingif.cube.web.springmvc.servlet.view.json.ExtendedMappingJackson2JsonView;
import com.sharingif.cube.web.springmvc.servlet.view.referer.RefererViewResolver;
import com.sharingif.cube.web.springmvc.servlet.view.stream.StreamView;
import com.sharingif.cube.web.springmvc.servlet.view.stream.StreamViewResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.*;

/**
 * SpringMVCComponentsAutoconfigure
 * 2017年5月1日 下午6:40:39
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class SpringMVCComponentsAutoconfigure {
	
	@Bean(name="webBindingInitializer")
	public WebBindingInitializer createWebBindingInitializer(ConversionService conversionService, Validator validator) {
		ConfigurableWebBindingInitializer webBindingInitializer = new ConfigurableWebBindingInitializer();
		webBindingInitializer.setConversionService(conversionService);
		webBindingInitializer.setValidator(validator);
		
		return webBindingInitializer;
	}
	
	@Bean(name="byteArrayHttpMessageConverter")
	public ByteArrayHttpMessageConverter createByteArrayHttpMessageConverter() {
		return new ByteArrayHttpMessageConverter();
	}
	
	@Bean(name="stringHttpMessageConverter")
	public StringHttpMessageConverter createStringHttpMessageConverter() {
		return new StringHttpMessageConverter();
	}
	
	@Bean("sourceHttpMessageConverter")
	@SuppressWarnings("rawtypes")
	public SourceHttpMessageConverter createSourceHttpMessageConverter() {
		return new SourceHttpMessageConverter();
	}
	
	@Bean(name="allEncompassingFormHttpMessageConverter")
	public AllEncompassingFormHttpMessageConverter createAllEncompassingFormHttpMessageConverter() {
		return new AllEncompassingFormHttpMessageConverter();
	}
	
	@Bean(name="mappingJackson2HttpMessageConverter")
	@ConditionalOnMissingBean(name = "mappingJackson2HttpMessageConverter")
	public MappingJackson2HttpMessageConverter createMappingJackson2HttpMessageConverter() {
		return new ExtendedMappingJackson2HttpMessageConverter();
	}
	
	@SuppressWarnings("rawtypes")
	@Bean(name="messageConverters")
	public List<HttpMessageConverter<?>> createCustomMessageConverters(
			ByteArrayHttpMessageConverter byteArrayHttpMessageConverter
			,StringHttpMessageConverter stringHttpMessageConverter
			,SourceHttpMessageConverter sourceHttpMessageConverter
			,AllEncompassingFormHttpMessageConverter allEncompassingFormHttpMessageConverter
			,MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter
			) {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(byteArrayHttpMessageConverter);
		messageConverters.add(stringHttpMessageConverter);
		messageConverters.add(sourceHttpMessageConverter);
		messageConverters.add(allEncompassingFormHttpMessageConverter);
		messageConverters.add(mappingJackson2HttpMessageConverter);
		
		return messageConverters;
	}
	
	@Bean(name="methodArgumentNotValidExceptionHandler")
	public MethodArgumentNotValidExceptionHandler createMethodArgumentNotValidExceptionHandler() {
		MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler = new MethodArgumentNotValidExceptionHandler();
		
		return methodArgumentNotValidExceptionHandler;
	}

	@Bean(name="singleMethodArgumentNotValidExceptionHandler")
	public SingleMethodArgumentNotValidExceptionHandler createSingleMethodArgumentNotValidExceptionHandler() {
		SingleMethodArgumentNotValidExceptionHandler singleMethodArgumentNotValidExceptionHandler = new SingleMethodArgumentNotValidExceptionHandler();

		return singleMethodArgumentNotValidExceptionHandler;
	}

	@Bean("noHandlerFoundExceptionHandler")
	public NoHandlerFoundExceptionHandler createNoHandlerFoundExceptionHandler() {
		NoHandlerFoundExceptionHandler noHandlerFoundExceptionHandler = new NoHandlerFoundExceptionHandler();

		return noHandlerFoundExceptionHandler;
	}

	@Bean(name="springMVCCubeExceptionHandlers")
	@ConditionalOnMissingBean(name = "springMVCCubeExceptionHandlers")
	public List<WebCubeExceptionHandler> createSpringMVCCubeExceptionHandlers(
			AccessDecisionCubeExceptionHandler accessDecisionCubeExceptionHandler
			,TokenValidationCubeExceptionHandler tokenValidationCubeExceptionHandler
			,MethodArgumentNotValidExceptionHandler methodArgumentNotValidExceptionHandler
			,BindValidationCubeExceptionHandler bindValidationCubeExceptionHandler
			,NoHandlerFoundExceptionHandler noHandlerFoundExceptionHandler
			,ValidationCubeExceptionHandler validationCubeExceptionHandler
			,WebCubeExceptionHandler webCubeExceptionHandler) {

		List<WebCubeExceptionHandler> springMVCCubeExceptionHandlers = new ArrayList<WebCubeExceptionHandler>();
		springMVCCubeExceptionHandlers.add(accessDecisionCubeExceptionHandler);
		springMVCCubeExceptionHandlers.add(tokenValidationCubeExceptionHandler);
		springMVCCubeExceptionHandlers.add(methodArgumentNotValidExceptionHandler);
		springMVCCubeExceptionHandlers.add(bindValidationCubeExceptionHandler);
		springMVCCubeExceptionHandlers.add(noHandlerFoundExceptionHandler);
		springMVCCubeExceptionHandlers.add(validationCubeExceptionHandler);
		springMVCCubeExceptionHandlers.add(webCubeExceptionHandler);

		return springMVCCubeExceptionHandlers;

	}
	
	@Bean(name="springMVCCubeExceptionHandler")
	@ConditionalOnMissingBean(name = "springMVCCubeExceptionHandler")
	public MultiCubeExceptionHandler<SpringMVCHttpRequestContext,HandlerMethod> createSpringMVCCubeExceptionHandler(
			List<WebCubeExceptionHandler> springMVCCubeExceptionHandlers
			) {

		MultiCubeExceptionHandler springMVCCubeExceptionHandler = new MultiCubeExceptionHandler();
		springMVCCubeExceptionHandler.setCubeExceptionHandlers(springMVCCubeExceptionHandlers);

		return springMVCCubeExceptionHandler;
	}
	
	@Bean(name="contentNegotiationManager")
	public ContentNegotiationManagerFactoryBean createContentNegotiationManager() {
		
		Properties mediaTypes = new Properties();
		mediaTypes.put("image", "image/*");
		mediaTypes.put("textCSV", "text/csv");
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
	
	@Bean(name="imagePngStreamView")
	public StreamView createImagePngStreamView() {
		StreamView streamView = new StreamView();
		streamView.setContentType(MediaType.IMAGE_PNG_VALUE);
		
		return streamView;
	}

	@Bean(name="textCSVStreamView")
	public StreamView createTextCSVStreamView() {
		StreamView streamView = new StreamView();
		streamView.setContentType("text/csv");

		return streamView;
	}
	
	@Bean(name="streamViewResolver")
	public StreamViewResolver createStreamViewResolver(
			StreamView imagePngStreamView
			,StreamView textCSVStreamView
	) {
		Map<String,StreamView> streamViews = new HashMap<String,StreamView>(1);
		streamViews.put("imagePng", imagePngStreamView);
		streamViews.put("textCSV", textCSVStreamView);
		
		StreamViewResolver streamViewResolver = new StreamViewResolver();
		streamViewResolver.setStreamViews(streamViews);
		
		return streamViewResolver;
	}
	
	@Bean(name="refererViewResolver")
	public RefererViewResolver createRefererViewResolver() {
		RefererViewResolver refererViewResolver = new RefererViewResolver();
		
		return refererViewResolver;
	}
	
	@Bean(name="internalResourceViewResolver")
	@ConditionalOnMissingBean(name = "internalResourceViewResolver", value = ExtendedInternalResourceViewResolver.class)
	public InternalResourceViewResolver createInternalResourceViewResolver() {
		ExtendedInternalResourceViewResolver internalResourceViewResolver = new ExtendedInternalResourceViewResolver();
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setViewClass(ExtendedJstlView.class);

		return internalResourceViewResolver;
	}
	
//	@Bean(name="multipartResolver")
//	@ConditionalOnBean(FileItemFactory.class)
//	@ConditionalOnMissingBean(name = "multipartResolver")
//	public CommonsMultipartResolver createMultipartResolver(@Value("${upload.temp.dir:}")String uploadTempDir) throws IOException {
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//		multipartResolver.setMaxUploadSize(1024*1024*100);
//		if(!StringUtils.isTrimEmpty(uploadTempDir)) {
//			multipartResolver.setUploadTempDir(new FileSystemResource(uploadTempDir));
//		}
//
//		return multipartResolver;
//	}
	
	@Bean(name="mappingJackson2JsonView")
	@ConditionalOnMissingBean(name = "mappingJackson2JsonView")
	public MappingJackson2JsonView createMappingJackson2JsonView() {
		ExtendedMappingJackson2JsonView mappingJackson2JsonView = new ExtendedMappingJackson2JsonView();
		
		return mappingJackson2JsonView;
	}

	@Bean("noHandlerFoundViewResolver")
	public NoHandlerFoundViewResolver createNoHandlerFoundViewResolver() {
		NoHandlerFoundViewResolver noHandlerFoundViewResolver = new NoHandlerFoundViewResolver();

		return noHandlerFoundViewResolver;
	}

	@Bean(name="viewResolvers")
	@ConditionalOnMissingBean(name = "viewResolvers")
	public List<ViewResolver> getViewResolvers(
			StreamViewResolver streamViewResolver
			,RefererViewResolver refererViewResolver
			,NoHandlerFoundViewResolver noHandlerFoundViewResolver
			,InternalResourceViewResolver internalResourceViewResolver
			) {
		List<ViewResolver> viewResolvers = new ArrayList<ViewResolver>();
		viewResolvers.add(streamViewResolver);
		viewResolvers.add(refererViewResolver);
		viewResolvers.add(noHandlerFoundViewResolver);
		viewResolvers.add(internalResourceViewResolver);
		
		return viewResolvers;
	}
	
	@Bean(name="defaultViews")
	public List<View> createDefaultViews(
			MappingJackson2JsonView mappingJackson2JsonView
			) {
		List<View> defaultViews = new ArrayList<View>();
		defaultViews.add(mappingJackson2JsonView);
		
		return defaultViews;
	}
	
	@Bean(name="resourceHttpRequestHandler")
	public ResourceHttpRequestHandler createResourceHttpRequestHandler() {

		List<Resource> locations = new ArrayList<Resource>();
		locations.add(new ClassPathResource("/static/"));
		
		ResourceHttpRequestHandler resourceHttpRequestHandler = new ResourceHttpRequestHandler();
		resourceHttpRequestHandler.setLocations(locations);
		
		return resourceHttpRequestHandler;
	}

}
