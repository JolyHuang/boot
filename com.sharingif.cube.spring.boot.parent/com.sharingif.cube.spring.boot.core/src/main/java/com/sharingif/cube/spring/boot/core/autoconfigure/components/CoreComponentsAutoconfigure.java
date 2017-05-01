package com.sharingif.cube.spring.boot.core.autoconfigure.components;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.sharingif.cube.beans.factory.config.ExtendedPropertyPlaceholderConfigurer;
import com.sharingif.cube.core.util.Charset;

/**
 * CoreComponentsAutoconfigure 2017年5月1日 下午6:15:05
 * 
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class CoreComponentsAutoconfigure {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	private String defaultEncoding;
	
	public CoreComponentsAutoconfigure() {
		ResourceBundle resourceBundle = null;
		try {
			resourceBundle = ResourceBundle.getBundle("config.app.CubeConfigure");
		} catch (Exception e) {
			logger.error("config.app.CubeConfigure file not found");
		}
		if(resourceBundle == null) {
			defaultEncoding = Charset.UTF8.toString();
		} else {
			defaultEncoding = resourceBundle.getString("app.properties.default.encoding");
		}
		
	}

	@Bean(name="conversionService")
	public ConversionService getConversionService() {
		return new FormattingConversionServiceFactoryBean().getObject();
	}
	
	@Bean(name="validator")
	public Validator getValidator() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean(name="commonAnnotationBeanPostProcessor")
	public CommonAnnotationBeanPostProcessor getCommonAnnotationBeanPostProcessor() {
		CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor = new CommonAnnotationBeanPostProcessor();
		commonAnnotationBeanPostProcessor.setFallbackToDefaultTypeMatch(false);
		
		return commonAnnotationBeanPostProcessor;
	}
	
	@Bean(name="messageSource")
	public ResourceBundleMessageSource getMessageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setDefaultEncoding(defaultEncoding);
		messageSource.setBasenames(
				"config.i18n.exception.DefaultValidationMessages"
				,"config.i18n.exception.Cube"
				,"config.i18n.exception.Business"
				,"config.i18n.exception.Runtime"
				,"config.i18n.exception.Validation"
				,"config.i18n.exception.Security"
				,"config.i18n.exception.ValidationForm"
				,"config.i18n.constants.Constants"
				,"config.i18n.dictionary.Dictionary"
				);
		
		return messageSource;
	}
	
	@Bean(name="commonProperties")
	public List<Resource> getCommonProperties() {
		List<Resource> commonProperties = new ArrayList<Resource>(2);
		commonProperties.add(new ClassPathResource("config/app/CubeConfigure.properties"));
		
		return commonProperties;
	}
	
	@Bean(name="devPropertyPlaceholderConfigurer")
	@ConfigurationProperties(prefix="DEV")
	public PropertyPlaceholderConfigurer getDevPropertyPlaceholderConfigurer() {
		ExtendedPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ExtendedPropertyPlaceholderConfigurer(getCommonProperties());
		propertyPlaceholderConfigurer.setFileEncoding(defaultEncoding);
		propertyPlaceholderConfigurer.setLocations(new ClassPathResource("config/app/AppConfigure_DEV.properties"));
		
		return propertyPlaceholderConfigurer;
	}
	
	@Bean(name="testPropertyPlaceholderConfigurer")
	@ConfigurationProperties(prefix="TEST")
	public PropertyPlaceholderConfigurer getTestPropertyPlaceholderConfigurer() {
		ExtendedPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ExtendedPropertyPlaceholderConfigurer(getCommonProperties());
		propertyPlaceholderConfigurer.setFileEncoding(defaultEncoding);
		propertyPlaceholderConfigurer.setLocations(new ClassPathResource("config/app/AppConfigure_TEST.properties"));
		
		return propertyPlaceholderConfigurer;
	}
	
	@Bean(name="prodPropertyPlaceholderConfigurer")
	@ConfigurationProperties(prefix="PROD")
	public PropertyPlaceholderConfigurer getProdPropertyPlaceholderConfigurer() {
		ExtendedPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ExtendedPropertyPlaceholderConfigurer(getCommonProperties());
		propertyPlaceholderConfigurer.setFileEncoding(defaultEncoding);
		propertyPlaceholderConfigurer.setLocations(new ClassPathResource("config/app/AppConfigure_PROD.properties"));
		
		return propertyPlaceholderConfigurer;
	}

}
