package com.sharingif.cube.spring.boot.core.autoconfigure.components;

import org.springframework.context.annotation.Bean;
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
public class CoreComponentsAutoconfigure {

	private static final CoreComponentsAutoconfigure coreComponentsAutoconfigure = new CoreComponentsAutoconfigure();

	public static CoreComponentsAutoconfigure getInstance() {
		return coreComponentsAutoconfigure;
	}
	
	@Bean
	public ConversionService getConversionService() {
		return new FormattingConversionServiceFactoryBean().getObject();
	}
	
	@Bean
	public Validator getValidator() {
		return new LocalValidatorFactoryBean();
	}

}
