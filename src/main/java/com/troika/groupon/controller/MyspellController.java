package com.troika.groupon.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.util.Constant;
import com.troika.groupon.common.GrouponOrderStatus;
import com.troika.groupon.service.GroupOrderService;


/*****我的拼团***/

@Controller
@RequestMapping("/groupon/personal")
public class MyspellController extends BaseController{
	
	@Autowired
	private GroupOrderService groupOrderService;
	
	/**
	 * 拼团首页
	 * 
	 * */
	
	@RequestMapping
	public String index(HttpServletRequest request, Model model){
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		if(!user.getIconUrl().contains("http://wx")){
			String arr[] = user.getIconUrl().split("/");
			user.setIconUrl(Constant.PHOTO_URL+arr[arr.length-1]);
		}
		model.addAttribute("user", user);
		return "groupon/myspell/mysepll";
	}
	
	/**
	 * 我的拼团购物
	 */
	@RequestMapping(value="myshopping")
	public String shopping(HttpServletRequest request, Model model){
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		//获取待发货列表
		List<Map<String, Object>> waitlist = groupOrderService.getOrderListByUser(user.getId(), GrouponOrderStatus.PAID.getCode());
		//获取待收货列表
		List<Map<String, Object>> list = groupOrderService.getOrderListByUser(user.getId(), GrouponOrderStatus.SEND.getCode());
		//获取完成订单列表
		List<Map<String, Object>> finishlist = groupOrderService.getOrderListByUser(user.getId(), GrouponOrderStatus.FINISH.getCode());
		model.addAttribute("list", list);
		model.addAttribute("finishlist", finishlist);
		model.addAttribute("waitlist",waitlist);
		return "groupon/myspell/myshopper";
	}
	
	/**
	 * 确认收货
	 */
	@RequestMapping(value="confirm")
	@ResponseBody
	public String confirmGoods(int id){
		groupOrderService.updOrderStatus(id, GrouponOrderStatus.FINISH.getCode());
		return genSuccessResult();
	}
	
	/**
	 * 提交退货申请
	 */
	@RequestMapping(value="applyReturn")
	@ResponseBody
	public String returnGoods(int id,String reason){
		groupOrderService.updOrderStatus(id, GrouponOrderStatus.RETURN.getCode());
		groupOrderService.addOrderReason(id, reason);
		return genSuccessResult();
	}
	
	/**
	 * 申请退货
	 */
	@RequestMapping(value="apply")
	public String applyrefund(int orderid, Model model){
		model.addAttribute("orderid", orderid);
		return "groupon/myspell/refund";
	}
	
	
	
}
