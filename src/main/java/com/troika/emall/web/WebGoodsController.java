package com.troika.emall.web;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.CartService;
import com.troika.emall.service.GoodsService;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.util.CommonUtil;

@Controller
@RequestMapping("/goods")
public class WebGoodsController extends BaseController {

	@Autowired
	private GoodsService goodsService;// 商品
	
	@Autowired
	private CartService cartService;
	
	/**
	 * 查找商品
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/search")
	public String search(HttpServletRequest request, Model model) {
		try {
			String name=URLDecoder.decode(request.getParameter("name"),"UTF-8");
			List<Map<String, Object>> list=goodsService.findGoodsByName(name);
			model.addAttribute("goods", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 显示购物车商品数量
				TMallUser user = CommonUtil.ValidateUser(request);
				//判断用户是否登录
				if (user != null) {
					List<Map<String, Object>> list1 = cartService
							.getCartListByUserId(user.getId());
						model.addAttribute("cartsize", list1.size());
						model.addAttribute("carts", list1);
					}
		return "goods/searchGoods";
	}
}
