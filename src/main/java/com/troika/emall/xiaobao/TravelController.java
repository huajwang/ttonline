package com.troika.emall.xiaobao;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/travel")
public class TravelController {
	
	@RequestMapping
	public String index() {
		return "/travel/travel_index";
	}
}
