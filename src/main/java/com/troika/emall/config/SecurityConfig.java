package com.troika.emall.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebMvcSecurity
/**
 * Spring Security must be configured in a bean that implements WebSecurityConfigurer or 
 * (for convenience) extends WebSecurityConfigurerAdapter. 
 * 
 * Among other things, the @EnableWebMvcSecurity annotation configures a Spring MVC argument
 * resolver so that handler methods can receive the authenticated user’s principal (or username)
 * via @AuthenticationPrincipal-annotated parameters. It also configures a bean that automatically 
 * adds a hidden cross-site request forgery (CSRF) token field on forms using Spring’s form-binding
 * tag library.
 * 
 * You can configure web security by overriding WebSecurity-ConfigurerAdapter’s three configure() 
 * methods and setting behavior on the parameter passed in.
 */
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationSuccessHandler successHandler;


	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery("select username, password, true " +  
				 "from t_mall_user where username = ?")
		.authoritiesByUsernameQuery("select username, 'ROLE_USER' from t_mall_user where username=?")
		.passwordEncoder(passwordEncoder);

	}

//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().antMatchers("/webjars/**", "/images/**", "/oauth/uncache_approvals", "/oauth/cache_approvals");
//	}
//
//
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//        // @formatter:off
		//                 http
		//            .authorizeRequests()
		//                .antMatchers("/login.jsp").permitAll()
		//                .anyRequest().hasRole("USER")
		//                .and()
		//            .exceptionHandling()
		//                .accessDeniedPage("/login.jsp?authorization_error=true")
		//                .and()
		//            // TODO: put CSRF protection back into this endpoint
		//            .csrf()
		//                .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
		//                .disable()
		//            .logout()
		//            	.logoutUrl("/logout")
		//                .logoutSuccessUrl("/login.jsp")
		//                .and()
		//            .formLogin()
		//            	.loginProcessingUrl("/login")
		//                .failureUrl("/login.jsp?authentication_error=true")
		//                .loginPage("/login.jsp");
		//        // @formatter:on
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/member/**", "/orders/**", "/cart/**").authenticated()
		.antMatchers(HttpMethod.POST, "/spittles").authenticated()
		.anyRequest().permitAll()
		.and()
		.formLogin()
			.loginProcessingUrl("/login")
			.loginPage("/login.jsp")
			.successHandler(successHandler)
			.permitAll()
		.and()
		.httpBasic();

	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//		.jdbcAuthentication()
//		.dataSource(dataSource)
//		.usersByUsernameQuery("select username, password, true " +  
//				 "from t_mall_user where username = ?")
//		.authoritiesByUsernameQuery("select username, 'ROLE_USER' from t_mall_user where username=?")
//		.passwordEncoder(passwordEncoder);
//	}
}
