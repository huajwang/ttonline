package com.troika.groupon.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.AddressService;
import com.troika.emall.util.Constant;
import com.troika.groupon.model.TGrouponIdentification;
import com.troika.groupon.model.TGrouponSettlement;
import com.troika.groupon.service.GroupService;

/***** 发起 ***/
@Controller
@RequestMapping("/groupon/launch")
public class SponsorController extends BaseController {
	@Autowired
	private GroupService groupService;

	@Autowired
	private AddressService addressService;

	/**
	 * 发起首页
	 * 
	 * */

	@RequestMapping
	public String index(HttpServletRequest request, Model model) {
		return "groupon/sponsor/sponsorindex";
	}

	/**
	 * 发起页面
	 */
	@RequestMapping(value = "/projectfq")
	public String projectfq(HttpServletRequest request, Model model) {
		return "groupon/sponsor/sponsor_fq";
	}

	/**
	 * 发布产品跳转页面
	 */
	@RequestMapping(value = "/product")
	public String myshopping(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		if (user != null) {
			// 判断用户是否已经验证过
			TGrouponIdentification iden = groupService
					.getIdentification((int) user.getId());
			if (iden != null) {
				model.addAttribute("Identification", "true");
			}
		}
		return "groupon/sponsor/fbproduct";
	}

	/**
	 * 我的拼团购物
	 */
	@RequestMapping(value = "/start")
	public String already(HttpServletRequest request, Model model) {
		return "groupon/sponsor/launched";
	}

	/**
	 * 身份认证
	 */
	@RequestMapping(value = "/authentication")
	public String position(HttpServletRequest request, Model model) {
		return "groupon/sponsor/identity";
	}

	/**
	 * 个人验证
	 */
	@RequestMapping(value = "/identifiable ")
	public String personally(HttpServletRequest request, Model model) {
		return "groupon/sponsor/personal_verification";
	}

	/**
	 * 企业验证
	 */
	@RequestMapping(value = "/legalentity ")
	public String entity(HttpServletRequest request, Model model) {
		return "groupon/sponsor/legal";
	}

	/**
	 * 我的发起
	 */
	@RequestMapping(value = "/initiate")
	public String myinitiate(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		if (user != null) {
			int userId = (int) user.getId();
			// 获取审核中的项目列表
			List<Map<String, Object>> list1 = groupService.getProjectByStatus(
					userId, 1);
			// 获取审核成功的项目列表
			List<Map<String, Object>> list2 = groupService.getProjectByStatus(
					userId, 2);
			// 获取审核失败的项目列表
			List<Map<String, Object>> list3 = groupService.getProjectByStatus(
					userId, 3);
			// 获取项目结束的项目列表
			List<Map<String, Object>> list4 = groupService.getProjectByStatus(
					userId, 4);
			model.addAttribute("list1", list1);
			model.addAttribute("list2", list2);
			model.addAttribute("list3", list3);
			for (Map<String, Object> map : list4) {
				int id = Integer.valueOf(map.get("id").toString());
				List<TGrouponSettlement> SettlList = groupService
						.getSettlement(id);
				if (SettlList.size() > 0) {// 判断结算数据是否存在
					map.put("Settlement", SettlList.get(0).getStatus());
				} else {
					map.put("Settlement", "");
				}
			}
			model.addAttribute("list4", list4);
		}
		return "groupon/sponsor/sponsor_my";
	}

	/**
	 * 关于欢乐拼
	 */
	@RequestMapping(value = "/regard")
	public String asregards(HttpServletRequest request, Model model) {
		return "groupon/sponsor/sponsor_about";
	}

	/**
	 * 帮助中心
	 */
	@RequestMapping(value = "/helpcenter")
	public String supportcenter(HttpServletRequest request, Model model) {
		return "groupon/sponsor/sponsor_help";
	}

	/**
	 * 提现记录
	 */
	@RequestMapping(value = "/withdrawal")
	public String withdrawalplan(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		if (user != null) {
			int userId = (int) user.getId();
			List<Map<String, Object>> list = groupService
					.getSettlementByUser(userId);
			model.addAttribute("list", list);//获取所有结算数据
		}
		return "groupon/sponsor/sponsor_recommend_list";
	}

	/**
	 * 退货详情
	 */
	@RequestMapping(value = "/salesreturn")
	public String particularsdetails(HttpServletRequest request, Model model) {
		try {
			TMallUser user = (TMallUser) request.getSession().getAttribute(
					Constant.LOGIN_USER);
			List<Map<String, Object>> list = groupService
					.findProjectByCreateUser(user.getId());
			for (Map<String, Object> map : list) {
				List<Map<String, Object>> orders = groupService
						.getOrderListByProjectId((int) map.get("id"));
				for (int i = orders.size() - 1; i >= 0; i--) {
					String status = String.valueOf(orders.get(i).get("status"));
					if (!status.equals("3") && !status.equals("4")) {
						orders.remove(i); // 剔除非退换货订单
						continue;
					}
					Map<String, Object> address = addressService
							.findAddressById((int) orders.get(i).get("addrId"));
					orders.get(i).put("address", address);
				}
				map.put("order", orders);

			}
			model.addAttribute("list", list);
		} catch (Exception e) {

		}
		return "groupon/sponsor/sponnsor_back_detail";
	}

	/**
	 * 添加拼团产品
	 */
	@RequestMapping(value = "/internetproduct")
	public String productdisplay(HttpServletRequest request, Model model) {
		return "groupon/sponsor/promulgate";
	}

	// ===========================================================================

	/**
	 * 添加项目
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/addProject", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addProject(HttpServletRequest request, String jsonStr)
			throws Exception {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		Map<String, Object> ErrMap = new HashMap<>();
		if (user == null) {
			ErrMap.put("err_msg", "用户尚未登录！");
			return genFailResult(ErrMap);
		}
		JSONObject json = JSONObject.fromObject(jsonStr);
		String name = json.getString("name");// 项目名称
		String amountStr = json.getString("amount");// 拼款金额
		BigDecimal amount = new BigDecimal(amountStr);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date endTime = sdf.parse(json.getString("endTime"));// 截止日期
		String sendTime = json.getString("sendTime");// 发货说明
		String EMS = json.getString("EMS");// 运费说明
		int status = 1;// 项目状态
		String remark = json.getString("remark");// 项目说明
		JSONArray jaIcon = json.getJSONArray("Icon");
		List<Map<String, Object>> Icon = new ArrayList<>();// 项目图片集合
		for (int i = 0; i < jaIcon.size(); i++) {
			JSONObject data = jaIcon.getJSONObject(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Icon", data.getString("Icon"));// 项目图片
			map.put("IsDisplay", data.getString("IsDisplay"));// 是否显示
			Icon.add(map);
		}

		JSONArray jaProduct = json.getJSONArray("product");
		List<Map<String, Object>> product = new ArrayList<>();// 产品集合
		for (int i = 0; i < jaProduct.size(); i++) {
			JSONObject data = jaProduct.getJSONObject(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ProductName", data.getString("ProductName"));// 产品名称
			map.put("price", data.getString("price"));// 产品价格
			map.put("content", data.getString("content"));// 产品介绍
			map.put("num", data.getString("num"));// 产品数量
			map.put("Icon", data.getString("Icon"));// 产品图片
			map.put("unit", data.getString("unit"));// 单位
			product.add(map);
		}
		int createUser = (int) user.getId();// 发布人
		boolean isSuc = groupService.addProject(name, createUser, amount,
				endTime, sendTime, EMS, status, remark, Icon, product);
		if (isSuc) {
			return genSuccessResult();
		} else {
			ErrMap.put("err_msg", "保存出现异常，请与管理员联系！");
			return genFailResult(ErrMap);
		}

	}

}
