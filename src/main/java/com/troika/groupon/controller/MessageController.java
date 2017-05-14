package com.troika.groupon.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.troika.emall.restapi.BaseController;

/*****消息***/

@Controller
@RequestMapping("/groupon/news")
public class MessageController extends BaseController {
	
	/**
	 * 消息首页
	 * 
	 * */
	
	@RequestMapping
	public String index(HttpServletRequest request, Model model){
		return "groupon/information/information";
	}
}
