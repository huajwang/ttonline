package com.troika.emall.xiaobao;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/poem")
public class PoemController {
	
	@RequestMapping
	public String index() {
		return "/poem_index";
	}
}
