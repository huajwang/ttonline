package com.troika.emall.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.troika.emall.oauth2.AccessConfirmationController;
import com.troika.emall.oauth2.AdminController;
import com.troika.emall.oauth2.PhotoController;
import com.troika.emall.oauth2.PhotoInfo;
import com.troika.emall.oauth2.PhotoService;
import com.troika.emall.oauth2.PhotoServiceImpl;
import com.troika.emall.oauth2.PhotoServiceUserController;
import com.troika.emall.oauth2.SparklrUserApprovalHandler;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.troika.emall", "com.troika.groupon" })
@EnableScheduling
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Override
	/**
	 * configure static content handling
	 */
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("home");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations(
				"classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public PhotoServiceUserController photoServiceUserController(PhotoService photoService) {
		PhotoServiceUserController photoServiceUserController = new PhotoServiceUserController();
		return photoServiceUserController;
	}

	@Bean
	public PhotoController photoController(PhotoService photoService) {
		PhotoController photoController = new PhotoController();
		photoController.setPhotoService(photoService);
		return photoController;
	}

	@Bean
	public AccessConfirmationController accessConfirmationController(ClientDetailsService clientDetailsService,
			ApprovalStore approvalStore) {
		AccessConfirmationController accessConfirmationController = new AccessConfirmationController();
		accessConfirmationController.setClientDetailsService(clientDetailsService);
		accessConfirmationController.setApprovalStore(approvalStore);
		return accessConfirmationController;
	}

	@Bean
	public PhotoServiceImpl photoServices() {
		List<PhotoInfo> photos = new ArrayList<PhotoInfo>();
		photos.add(createPhoto("1", "marissa"));
		photos.add(createPhoto("2", "paul"));
		photos.add(createPhoto("3", "marissa"));
		photos.add(createPhoto("4", "paul"));
		photos.add(createPhoto("5", "marissa"));
		photos.add(createPhoto("6", "paul"));

		PhotoServiceImpl photoServices = new PhotoServiceImpl();
		photoServices.setPhotos(photos);
		return photoServices;
	}
	
	// N.B. the @Qualifier here should not be necessary (gh-298) but lots of users report needing it.
	@Bean
	public AdminController adminController(TokenStore tokenStore,
			@Qualifier("consumerTokenServices") ConsumerTokenServices tokenServices,
			SparklrUserApprovalHandler userApprovalHandler) {
		AdminController adminController = new AdminController();
		adminController.setTokenStore(tokenStore);
		adminController.setTokenServices(tokenServices);
		adminController.setUserApprovalHandler(userApprovalHandler);
		return adminController;
	}

	
	private PhotoInfo createPhoto(String id, String userId) {
		PhotoInfo photo = new PhotoInfo();
		photo.setId(id);
		photo.setName("photo" + id + ".jpg");
		photo.setUserId(userId);
		photo.setResourceURL("/org/springframework/security/oauth/examples/sparklr/impl/resources/" + photo.getName());
		return photo;
	}



	// THYMELEAF CONFIG
	// @Bean
	// public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
	// ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	// viewResolver.setTemplateEngine(templateEngine);
	// return viewResolver;
	// }
	//
	// @Bean
	// public SpringTemplateEngine templateEngine(TemplateResolver
	// templateResolver) {
	// SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	// templateEngine.setTemplateResolver(templateResolver);
	// return templateEngine;
	// }
	//
	// @Bean
	// public TemplateResolver templateResolver() {
	// TemplateResolver templateResolver = new ServletContextTemplateResolver();
	// templateResolver.setPrefix("/WEB-INF/views/");
	// templateResolver.setSuffix(".html");
	// templateResolver.setTemplateMode("HTML5");
	// return templateResolver;
	// }
}
