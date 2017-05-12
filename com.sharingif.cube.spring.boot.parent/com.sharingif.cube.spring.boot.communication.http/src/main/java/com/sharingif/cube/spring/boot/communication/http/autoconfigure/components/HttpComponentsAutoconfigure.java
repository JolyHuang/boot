package com.sharingif.cube.spring.boot.communication.http.autoconfigure.components;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sharingif.cube.communication.http.transport.HttpJsonRemoteHandlerMethodTransportFactory;
import com.sharingif.cube.communication.http.transport.HttpJsonRequestInfoResolver;
import com.sharingif.cube.communication.http.transport.transform.HttpJsonTransform;
import com.sharingif.cube.communication.http.transport.transform.MethodParameterArgumentToJsonModelMarshaller;
import com.sharingif.cube.communication.http.transport.transform.ObjectToJsonStringMarshaller;
import com.sharingif.cube.communication.transport.Connection;
import com.sharingif.cube.core.request.RequestInfo;

/**
 * HttpComponentsAutoconfigure
 * 2017年5月12日 下午5:29:46
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class HttpComponentsAutoconfigure {
	
	@Bean(name = "httpJsonRequestInfoResolver")
	public HttpJsonRequestInfoResolver createHttpJsonRequestInfoResolver() {
		HttpJsonRequestInfoResolver httpJsonRequestInfoResolver = new HttpJsonRequestInfoResolver();
		
		return httpJsonRequestInfoResolver;
	}
	
	@Bean(name = "objectToJsonStringMarshaller")
	public ObjectToJsonStringMarshaller createObjectToJsonStringMarshaller() {
		ObjectToJsonStringMarshaller objectToJsonStringMarshaller = new ObjectToJsonStringMarshaller();
		
		return objectToJsonStringMarshaller;
	}
	
	@Bean(name = "methodParameterArgumentToJsonModelMarshaller")
	public MethodParameterArgumentToJsonModelMarshaller createMethodParameterArgumentToJsonModelMarshaller() {
		MethodParameterArgumentToJsonModelMarshaller methodParameterArgumentToJsonModelMarshaller = new MethodParameterArgumentToJsonModelMarshaller();
		
		return methodParameterArgumentToJsonModelMarshaller;
	}
	
	@Bean(name = "httpJsonTransform")
	public HttpJsonTransform createHttpJsonTransform(ObjectToJsonStringMarshaller objectToJsonStringMarshaller, MethodParameterArgumentToJsonModelMarshaller methodParameterArgumentToJsonModelMarshaller) {
		HttpJsonTransform httpJsonTransform = new HttpJsonTransform();
		httpJsonTransform.setMarshaller(objectToJsonStringMarshaller);
		httpJsonTransform.setUnmarshaller(methodParameterArgumentToJsonModelMarshaller);
		
		return httpJsonTransform;
	}
	
	@Bean(name= "httpJsonRemoteHandlerMethodTransportFactory")
	public HttpJsonRemoteHandlerMethodTransportFactory createHttpJsonRemoteHandlerMethodTransportFactory(Connection<RequestInfo<String>, String> httpJsonConnection, HttpJsonTransform httpJsonTransform) {
		HttpJsonRemoteHandlerMethodTransportFactory httpJsonRemoteHandlerMethodTransportFactory = new HttpJsonRemoteHandlerMethodTransportFactory();
		httpJsonRemoteHandlerMethodTransportFactory.setConnection(httpJsonConnection);
		httpJsonRemoteHandlerMethodTransportFactory.setTransform(httpJsonTransform);;
		
		return httpJsonRemoteHandlerMethodTransportFactory;
	}

}
