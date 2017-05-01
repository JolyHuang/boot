package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;

import com.sharingif.cube.spring.boot.core.autoconfigure.components.CoreComponentsAutoconfigure;
import com.sharingif.cube.web.springmvc.http.converter.json.ExtendedMappingJackson2HttpMessageConverter;

/**
 * SpringMVCComponentsAutoconfigure
 * 2017年5月1日 下午6:40:39
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class SpringMVCComponentsAutoconfigure {
	
	private static final SpringMVCComponentsAutoconfigure springMVCComponentsAutoconfigure = new SpringMVCComponentsAutoconfigure();
	
	public static SpringMVCComponentsAutoconfigure getInstance() {
		return springMVCComponentsAutoconfigure;
	}
	
	@Bean
	public WebBindingInitializer getWebBindingInitializer() {
		ConfigurableWebBindingInitializer webBindingInitializer = new ConfigurableWebBindingInitializer();
		webBindingInitializer.setConversionService(CoreComponentsAutoconfigure.getInstance().getConversionService());
		webBindingInitializer.setValidator(CoreComponentsAutoconfigure.getInstance().getValidator());
		
		return webBindingInitializer;
	}
	
	@Bean
	public ByteArrayHttpMessageConverter getByteArrayHttpMessageConverter() {
		return new ByteArrayHttpMessageConverter();
	}
	
	@Bean
	public StringHttpMessageConverter getStringHttpMessageConverter() {
		return new StringHttpMessageConverter();
	}
	
	@Bean
	@SuppressWarnings("rawtypes")
	public SourceHttpMessageConverter getSourceHttpMessageConverter() {
		return new SourceHttpMessageConverter();
	}
	
	@Bean
	public AllEncompassingFormHttpMessageConverter getAllEncompassingFormHttpMessageConverter() {
		return new AllEncompassingFormHttpMessageConverter();
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
		return new ExtendedMappingJackson2HttpMessageConverter();
	}
	
	@Bean
	public List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>(4);
		messageConverters.add(getByteArrayHttpMessageConverter());
		messageConverters.add(getStringHttpMessageConverter());
		messageConverters.add(getSourceHttpMessageConverter());
		messageConverters.add(getMappingJackson2HttpMessageConverter());
		
		return messageConverters;
	}
	

}
