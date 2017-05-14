package com.troika.emall.oauth2;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrincipalRestController {
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.GET}, value = "/hi", 
			consumes = {"application/json"}, produces = {"application/json"})
	public Principal principal(Principal p) {
		System.out.println(p.getName());
		return p;
	}
}
