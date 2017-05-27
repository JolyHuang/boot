package com.sharingif.cube.spring.boot.web.autoconfigure.components;

import com.sharingif.cube.security.web.exception.handler.validation.access.AccessDecisionCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.WebCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.BindValidationCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.TokenValidationCubeExceptionHandler;
import com.sharingif.cube.web.exception.handler.validation.ValidationCubeExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WebComponentsAutoconfigure
 * 2017年5月2日 上午10:02:59
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class WebComponentsAutoconfigure {

	@Bean(name="accessDecisionCubeExceptionHandler")
	@ConditionalOnMissingBean(name="accessDecisionCubeExceptionHandler", value=AccessDecisionCubeExceptionHandler.class)
	public AccessDecisionCubeExceptionHandler createAccessDecisionCubeExceptionHandler(@Value("${default.login.view:login}") String defaultLoginView) {
		AccessDecisionCubeExceptionHandler accessDecisionCubeExceptionHandler = new AccessDecisionCubeExceptionHandler();
		accessDecisionCubeExceptionHandler.setDefaultErrorView(defaultLoginView);
		
		return accessDecisionCubeExceptionHandler;
	}
	
	@Bean(name="tokenValidationCubeExceptionHandler")
	public TokenValidationCubeExceptionHandler getTokenValidationCubeExceptionHandler() {
		TokenValidationCubeExceptionHandler tokenValidationCubeExceptionHandler = new TokenValidationCubeExceptionHandler();
		tokenValidationCubeExceptionHandler.setDefaultErrorView("common/ResubmitTokenError");
		
		return tokenValidationCubeExceptionHandler;
	}
	
	@Bean(name="bindValidationCubeExceptionHandler")
	public BindValidationCubeExceptionHandler createBindValidationCubeExceptionHandler() {
		BindValidationCubeExceptionHandler bindValidationCubeExceptionHandler = new BindValidationCubeExceptionHandler();
		
		return bindValidationCubeExceptionHandler;
	}
	
	@Bean(name="validationCubeExceptionHandler")
	public ValidationCubeExceptionHandler createValidationCubeExceptionHandler() {
		ValidationCubeExceptionHandler validationCubeExceptionHandler = new ValidationCubeExceptionHandler();
		
		return validationCubeExceptionHandler;
	}
	
	@Bean(name="webCubeExceptionHandler")
	public WebCubeExceptionHandler createWebCubeExceptionHandler() {
		WebCubeExceptionHandler webCubeExceptionHandler = new WebCubeExceptionHandler();
		webCubeExceptionHandler.setDefaultErrorView("error/defaultError");
		
		return webCubeExceptionHandler;
	}
	
}
