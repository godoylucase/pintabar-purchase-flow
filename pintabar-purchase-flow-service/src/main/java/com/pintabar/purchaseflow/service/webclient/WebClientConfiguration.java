package com.pintabar.purchaseflow.service.webclient;

import com.pintabar.usermanagement.api.UserManagementAPI;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Lucas.Godoy on 23/08/17.
 */
@Configuration
public class WebClientConfiguration {

	// this might be changed when working with eureka
	@Value("${pintabar.microservices.userManagement.host}")
	private String userManagementHost;

	@Bean
	public UserManagementAPI userManagementAPIProxy() {
		return JAXRSClientFactory.create(userManagementHost, UserManagementAPI.class);
	}

}
