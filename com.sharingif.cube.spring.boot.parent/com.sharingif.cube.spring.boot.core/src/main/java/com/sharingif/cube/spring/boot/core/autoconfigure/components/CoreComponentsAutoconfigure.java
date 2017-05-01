package com.sharingif.cube.spring.boot.core.autoconfigure.components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * CoreComponentsAutoconfigure 2017年5月1日 下午6:15:05
 * 
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class CoreComponentsAutoconfigure {

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

}
