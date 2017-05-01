package com.sharingif.cube.spring.boot.web.springmvc.autoconfigure.components;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;

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
	
	@Resource
	private ConversionService conversionService;
	@Resource
	private Validator validator;
	
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
	

}
