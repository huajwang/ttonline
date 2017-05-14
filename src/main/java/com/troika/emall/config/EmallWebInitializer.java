package com.troika.emall.config;

import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class EmallWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	// The SecurityConfig.class were added to the root context because they define beans 
	// that can be used by other servlets and services in the same application. 
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class, SecurityConfig.class};
	}

	@Override
	// The getServletConfigClasses() is used only to instantiate the servlet-related beans.
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class, WebSocketStompConfig.class }; // WebSocketStompConfig.class, BatchConfig.class
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setAsyncSupported(true);
	}
}