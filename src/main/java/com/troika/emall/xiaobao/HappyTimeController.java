package com.troika.emall.xiaobao;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/happytime")
public class HappyTimeController {
	
	@RequestMapping
	public String index() {
		return "happytime/index";
	}
}
