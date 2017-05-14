package com.troika.emall.restapi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.service.GoodsService;

@Controller
@RequestMapping("/goods")
public class SearchController extends BaseController {

	@Autowired
	private GoodsService goodsService;
	/**
	 * 查找商品
	 * 
	 * @return
	 */
	@RequestMapping(value = "/search.api")
	@ResponseBody
	public String search(String keyword) {
		try {
			List<Map<String, Object>> goodList = goodsService.findGoodsByName(keyword);
			return genSuccessResult(goodList);
		} catch (Exception e) {
			logger.error("查找商品出错: " + e.getMessage());
		}
		return genFailResult();
	}
}
