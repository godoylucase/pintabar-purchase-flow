package com.pintabar.purchaseflow.api.config;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.pintabar.commons.api.rest.mappers.AppExceptionMapper;
import com.pintabar.commons.api.rest.mappers.GenericExceptionMapper;
import com.pintabar.purchaseflow.api.impl.OrderingAPIImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lucas.Godoy on 22/07/17.
 */
@Configuration
public class CXFConfiguration {

	@Autowired
	private Bus bus;

	@Autowired
	private AppExceptionMapper appExceptionMapper;

	@Autowired
	private GenericExceptionMapper genericExceptionMapper;

	@Autowired
	private OrderingAPIImpl orderingAPI;


	@Bean
	public Server rsServer() {
		JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
		endpoint.setBus(bus);
		endpoint.setAddress("/");
		setServiceBeans(endpoint);
		setProviders(endpoint);
		return endpoint.create();
	}

	private void setServiceBeans(JAXRSServerFactoryBean endpoint) {
		endpoint.setServiceBean(orderingAPI);
	}

	private void setProviders(JAXRSServerFactoryBean endpoint) {
		endpoint.setProvider(new JacksonJsonProvider());
		endpoint.setProvider(appExceptionMapper);
		endpoint.setProvider(genericExceptionMapper);
	}
}
