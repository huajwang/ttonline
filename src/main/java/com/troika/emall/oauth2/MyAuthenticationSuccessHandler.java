package com.troika.emall.oauth2;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import com.troika.emall.model.TMallUser;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.util.Constant;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private TMallUserService tMallUserService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// must call the super class method. Otherwise, redirect to original request page will not work.
		super.onAuthenticationSuccess(request, response, authentication);
		System.out.println("onAuthenticationSuccess is callled....");
		HttpSession session = request.getSession(false);
		if (session != null) {
			User user = (User) authentication.getPrincipal();
			TMallUser tMallUser = tMallUserService.findUserByUserName(user.getUsername());
			session.setAttribute(Constant.LOGIN_USER, tMallUser);
		}
	}
}
