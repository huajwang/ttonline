package com.troika.emall.customerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.restapi.BaseController;

@Controller
@RequestMapping("/customerservice")
public class CustomerService extends BaseController {
	
	@Autowired
	private SimpMessageSendingOperations messaging;
	
	@RequestMapping
	public String customerService() {
		return "customerservice/chat";
	}
	
	@RequestMapping("/ajaxReply")
	@ResponseBody
	public String reply(ChatMessage cm) {
		messaging.convertAndSend("/topic/aaa", cm);
		return genSuccessResult();
	}
}
