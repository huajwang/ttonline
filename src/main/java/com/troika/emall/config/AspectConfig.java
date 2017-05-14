package com.troika.emall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.troika.emall.aspect.Audience;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
	@Bean
	public Audience audience() {
		return new Audience();
	}
}
