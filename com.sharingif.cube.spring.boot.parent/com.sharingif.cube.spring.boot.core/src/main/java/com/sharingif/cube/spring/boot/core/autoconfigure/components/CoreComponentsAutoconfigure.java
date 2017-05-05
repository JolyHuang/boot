package com.sharingif.cube.spring.boot.core.autoconfigure.components;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
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
	public FormattingConversionServiceFactoryBean createConversionService() {
		FormattingConversionServiceFactoryBean conversionService = new FormattingConversionServiceFactoryBean();
		return conversionService;
	}
	
	@Bean(name="validator")
	public Validator createValidator() {
		return new LocalValidatorFactoryBean();
	}
	
	@Bean(name="commonAnnotationBeanPostProcessor")
	public CommonAnnotationBeanPostProcessor createCommonAnnotationBeanPostProcessor() {
		CommonAnnotationBeanPostProcessor commonAnnotationBeanPostProcessor = new CommonAnnotationBeanPostProcessor();
		commonAnnotationBeanPostProcessor.setFallbackToDefaultTypeMatch(false);
		
		return commonAnnotationBeanPostProcessor;
	}
	
	@Bean(name="messageSource")
	public ResourceBundleMessageSource createMessageSource() {
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
	public List<Resource> createCommonProperties() {
		List<Resource> commonProperties = new ArrayList<Resource>(2);
		commonProperties.add(new ClassPathResource("config/app/CubeConfigure.properties"));
		
		return commonProperties;
	}
	
	@Bean(name="devPropertyPlaceholderConfigurer")
	@Profile("DEV")
	public PropertyPlaceholderConfigurer createDevPropertyPlaceholderConfigurer() {
		ExtendedPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ExtendedPropertyPlaceholderConfigurer(createCommonProperties());
		propertyPlaceholderConfigurer.setFileEncoding(defaultEncoding);
		propertyPlaceholderConfigurer.setLocations(new ClassPathResource("config/app/AppConfigure_DEV.properties"));
		
		return propertyPlaceholderConfigurer;
	}
	
	@Bean(name="testPropertyPlaceholderConfigurer")
	@Profile("TEST")
	public PropertyPlaceholderConfigurer createTestPropertyPlaceholderConfigurer() {
		ExtendedPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ExtendedPropertyPlaceholderConfigurer(createCommonProperties());
		propertyPlaceholderConfigurer.setFileEncoding(defaultEncoding);
		propertyPlaceholderConfigurer.setLocations(new ClassPathResource("config/app/AppConfigure_TEST.properties"));
		
		return propertyPlaceholderConfigurer;
	}
	
	@Bean(name="prodPropertyPlaceholderConfigurer")
	@Profile("PROD")
	public PropertyPlaceholderConfigurer createProdPropertyPlaceholderConfigurer() {
		ExtendedPropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new ExtendedPropertyPlaceholderConfigurer(createCommonProperties());
		propertyPlaceholderConfigurer.setFileEncoding(defaultEncoding);
		propertyPlaceholderConfigurer.setLocations(new ClassPathResource("config/app/AppConfigure_PROD.properties"));
		
		return propertyPlaceholderConfigurer;
	}

}
