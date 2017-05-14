package com.troika.emall.web;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.troika.emall.model.TMallCart;
import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.AddressService;
import com.troika.emall.service.CartService;
import com.troika.emall.service.OrderProcess;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.util.Constant;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {
	@Autowired
	private OrderProcess orderProcess;

	@Autowired
	private CartService cartService;

	@Autowired
	private TMallUserService tMallUserService;
	@Autowired
	private AddressService addressService;
	
	@RequestMapping
	public String index(HttpServletRequest request, Model model, Principal principal) {
		TMallUser user = tMallUserService.findUserByUserName(principal.getName());
		if (user == null) {
			model.addAttribute("path", "cart");
			return "login";
		} else {
			Long userId = user.getId();
			Map<String, Object> address = addressService
					.findActiveAddress(userId);
			List<Map<String, Object>> list = cartService
					.getCartListByUserId(user.getId());
			model.addAttribute("goods", list);
			model.addAttribute("json", new Gson().toJson(list));
			model.addAttribute("address", address);
			return "goods/cart";
		}
	}

	/**
	 * 购物车转订单数据
	 * 
	 * @param request
	 * @param orderDetail
	 * @param orderSource
	 * @param amount
	 * @param introducer
	 * @param addrId
	 * @return
	 */
	@RequestMapping("/addOrder")
	@ResponseBody
	public String addOrder(HttpServletRequest request, String orderDetail,
			String orderSource, BigDecimal amount, long addrId, long cartId) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		// 获取购物主表数据
		Long introducer = null;
		TMallCart record = cartService.getCartByCartId(cartId);
		if(record != null){
			// 获取推荐人数据
			introducer = record.getIntroducer().longValue();
		}
		Map<String, Object> result = orderProcess.buildOrder(user.getId(),
				addrId, orderDetail, orderSource, amount, "",
				introducer, cartId == 0 ? null : cartId);
		if (result.containsKey("orderId")) {
			return genSuccessResult(result);
		}
		return genResult(result, "1");
	}

	/**
	 * 删除购物车数据
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteCart")
	@ResponseBody
	public String deleteCart(HttpServletRequest request, long id) {
		List<Long> list = new ArrayList<>();
		list.add(id);
		cartService.delDetail(list);
		return genResult("0");
	}
}
