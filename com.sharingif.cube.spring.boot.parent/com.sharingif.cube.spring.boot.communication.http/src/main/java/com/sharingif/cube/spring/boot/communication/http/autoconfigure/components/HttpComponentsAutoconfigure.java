package com.sharingif.cube.spring.boot.communication.http.autoconfigure.components;

import com.sharingif.cube.communication.JsonModel;
import com.sharingif.cube.communication.http.transport.transform.StringToJsonModelMarshaller;
import com.sharingif.cube.communication.transport.transform.ProxyInterfaceHandlerMethodCommunicationTransform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sharingif.cube.communication.http.transport.HandlerMethodCommunicationTransportRequestInfoResolver;
import com.sharingif.cube.communication.http.transport.transform.ObjectToJsonStringMarshaller;

/**
 * HttpComponentsAutoconfigure
 * 2017年5月12日 下午5:29:46
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
@Configuration
public class HttpComponentsAutoconfigure {
	
	@Bean(name = "handlerMethodCommunicationTransportRequestInfoResolver")
	public HandlerMethodCommunicationTransportRequestInfoResolver createHandlerMethodCommunicationTransportRequestInfoResolver() {
		HandlerMethodCommunicationTransportRequestInfoResolver handlerMethodCommunicationTransportRequestInfoResolver = new HandlerMethodCommunicationTransportRequestInfoResolver();
		
		return handlerMethodCommunicationTransportRequestInfoResolver;
	}
	
	@Bean(name = "objectToJsonStringMarshaller")
	public ObjectToJsonStringMarshaller createObjectToJsonStringMarshaller() {
		ObjectToJsonStringMarshaller objectToJsonStringMarshaller = new ObjectToJsonStringMarshaller();
		
		return objectToJsonStringMarshaller;
	}

	@Bean(name="stringToJsonModelMarshaller")
	public StringToJsonModelMarshaller createBatchStringToJsonModelMarshaller() {
		StringToJsonModelMarshaller stringToJsonModelMarshaller = new StringToJsonModelMarshaller();

		return stringToJsonModelMarshaller;
	}

	@Bean(name="jsonModelProxyInterfaceHandlerMethodCommunicationTransform")
	public ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>> createJsonModelProxyInterfaceHandlerMethodCommunicationTransform(
			ObjectToJsonStringMarshaller objectToJsonStringMarshaller
			,StringToJsonModelMarshaller stringToJsonModelMarshaller
	) {
		ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>> proxyInterfaceHandlerMethodCommunicationTransform = new ProxyInterfaceHandlerMethodCommunicationTransform<String,String,JsonModel<Object>>();
		proxyInterfaceHandlerMethodCommunicationTransform.setMarshaller(objectToJsonStringMarshaller);
		proxyInterfaceHandlerMethodCommunicationTransform.setUnmarshaller(stringToJsonModelMarshaller);

		return proxyInterfaceHandlerMethodCommunicationTransform;
	}

}
