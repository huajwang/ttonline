package com.troika.emall.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class Audience {
	
	@Pointcut("execution(** com.troika.emall.web.HomeController.home(..))")
	public void performance() {}
	
	@Before("performance()")
	public void silentPhone() {
		System.out.println("silent the phone before peformance.");
	}
	
	@After("performance()")
	public void applaud() {
		System.out.println("clap clap clap ...");
	}

}
