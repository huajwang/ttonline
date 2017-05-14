package com.troika.groupon.controller;

import java.math.BigDecimal;
import java.util.Date;
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
import com.troika.emall.service.AddressService;
import com.troika.emall.util.Constant;
import com.troika.groupon.model.TGroupFollowRecord;
import com.troika.groupon.model.TGrouponComment;
import com.troika.groupon.service.GroupService;

/***** 项目管理 ***/

@Controller
@RequestMapping("/groupon/project")
public class ProjectController extends BaseController {

	@Autowired
	private GroupService groupService;

	@Autowired
	private AddressService addressService;

	/**
	 * 帮助_发起项目相关问题
	 * 
	 * */

	@RequestMapping
	public String index(HttpServletRequest request, Model model) {
		return "groupon/project/help_initiate/help_initiate_index";
	}

	/**
	 * 帮助_发起项目相关问题(发起项目有何规范)
	 * 
	 * */
	@RequestMapping(value = "/standardization")
	public String specification(HttpServletRequest request, Model model) {
		return "groupon/project/help_initiate/help_initiate_specification";
	}

	/**
	 * 帮助_发起项目相关问题(拼团成功后钱存在那里)
	 * 
	 * */
	@RequestMapping(value = "/standardization_prep")
	public String prep(HttpServletRequest request, Model model) {
		return "groupon/project/help_initiate/help_initiate_save";
	}

	/**
	 * 帮助_发起项目相关问题(什么叫项目成功和项目失败)
	 * 
	 * */
	@RequestMapping(value = "/standardization_account")
	public String account(HttpServletRequest request, Model model) {
		return "groupon/project/help_initiate/help_initiate_account";
	}

	/**
	 * 帮助_发起项目相关问题(项目成功后收取费用吗)
	 * 
	 * */
	@RequestMapping(value = "/standardization_feecharging")
	public String fee_charging(HttpServletRequest request, Model model) {
		return "groupon/project/help_initiate/help_initiate_charge";
	}

	/**
	 * 帮助_发起项目相关问题(项目发布后，是否还能修改编辑)
	 * 
	 * */
	@RequestMapping(value = "/standardization_edit")
	public String edit_modify(HttpServletRequest request, Model model) {
		return "groupon/project/help_initiate/help_initiate_editor";
	}

	/**
	 * 帮助_发起项目相关问题(申请提现后多久可以到账)
	 * 
	 * */
	@RequestMapping(value = "/standardization_success_failure")
	public String success_and_failure(HttpServletRequest request, Model model) {
		return "groupon/project/help_initiate/help_initiate_Successfailure";
	}

	/**
	 * 帮助_支持项目相关问题的页面
	 * 
	 * */
	@RequestMapping(value = "/standardization_support")
	public String support_project(HttpServletRequest request, Model model) {
		return "groupon/project/support_initiate/support_initiate";
	}

	/**
	 * 帮助_尝鲜预售相关问题
	 * 
	 * */
	@RequestMapping(value = "/standardization_presell")
	public String forward_sale(HttpServletRequest request, Model model) {
		return "groupon/project/presell_initiate/presell_initiate";
	}

	/**
	 * 帮助_项目管理的相关问题
	 * 
	 * */
	@RequestMapping(value = "/standardization_management")
	public String management(HttpServletRequest request, Model model) {
		return "groupon/project/management_initiate/management_initiate";
	}

	/**
	 * 全部订单 zjw
	 */
	@RequestMapping(value = "/allorderlis")
	public String allorderlis(HttpServletRequest request, Model model, int id) {
		// 通过项目获取所有订单数据
		List<Map<String, Object>> orderList = groupService
				.getOrderListByProjectId(id);
		model.addAttribute("orderList", orderList);
		model.addAttribute("count", orderList.size());// 支持次数
		// 获取当前已拼总金额
		BigDecimal target = new BigDecimal(0);
		for (Map<String, Object> map : orderList) {
			String status = map.get("status").toString();
			map.put("status", status);
			target = target.add((BigDecimal) map.get("amount"));
			Map<String, Object> addrMap = null;
			if (map.get("addrId") != null) {
				addrMap = addressService.findAddressById(Long.valueOf(map.get(
						"addrId").toString()));
			}
			map.put("address", addrMap);// 获取收货地址
		}
		model.addAttribute("target", target);

		int count = groupService.getRecordCount(id);
		model.addAttribute("RecordCount", count);
		return "groupon/project/projectmanage_allorder";
	}

	/**
	 * 更新项目进展 zjw
	 */
	@RequestMapping(value = "/projectup_schedule")
	public String projectup_schedule(HttpServletRequest request, Model model,
			int id) {
		model.addAttribute("projectId", id);// 项目主键Id
		return "groupon/project/projectmanage_update";
	}

	/**
	 * 修改时间 wt
	 */
	@RequestMapping(value = "/projectup_modificationtime")
	public String modification_time(HttpServletRequest request, Model model,
			int id, String endTime, int dayNum, String createTime) {
		model.addAttribute("id", id);// 项目主键
		model.addAttribute("endTime", endTime);// 截止日期
		model.addAttribute("dayNum", dayNum);// 天数
		model.addAttribute("createTime", createTime);// 创建时间
		return "groupon/project/projectmanage_amend_time";
	}

	/**
	 * 修改金额 wt
	 */
	@RequestMapping(value = "/projectup_modificationamount")
	public String modification_amount(HttpServletRequest request, Model model,
			double amount, int id) {
		model.addAttribute("amount", amount);// 目标金额
		model.addAttribute("id", id);// 项目主键
		return "groupon/project/projectmanage_amend_money";
	}

	/**
	 * 项目结算 zjw
	 */
	@RequestMapping(value = "/projectsettlement")
	public String projectsettlement(HttpServletRequest request, Model model,
			int id) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		// 计算结算数据
		Map<String, Object> map = groupService.setSettlement(id);
		model.addAttribute("data", map);
		model.addAttribute("projectId", id);
		model.addAttribute("user", user);
		return "groupon/project/projectmanage_settlement";
	}

	/**
	 * 编辑 zjw
	 */
	@RequestMapping(value = "/projectedit")
	public String projectedit(HttpServletRequest request, Model model, int id) {
		// 获取项目的基本信息
		Map<String, Object> map = groupService.getProject(id);
		model.addAttribute("project", map);

		// 获取项目进度信息
		List<Map<String, Object>> list = groupService.getSchedule(id);
		model.addAttribute("schedule", list);
		//判断项目是否已经结算了
		Boolean sett=groupService.whetherSett(id);
		if(sett)
			model.addAttribute("SettType", "1");//已经结算或者结算中
		else
			model.addAttribute("SettType", "2");//审核失败或者为提交结算申请
		return "groupon/project/projectmanage_edit";
	}

	/**
	 * 增加提现账户 wt
	 */
	@RequestMapping(value = "/project_account")
	public String projectaccount(HttpServletRequest request, Model model) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		if (user != null) {
			model.addAttribute("user", user);
		}
		return "/groupon/project/project_add_account";
	}

	/**
	 * 关注
	 */
	@RequestMapping("/addFollowRecord")
	@ResponseBody
	public String addFollowRecord(int projectId, HttpServletRequest request) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		TGroupFollowRecord record = new TGroupFollowRecord();
		record.setPrejectid(projectId);
		try {
			record.setUserid((int) user.getId());
			record.setCreatetime(new Date());
			record = groupService.saveOrUpdateRecord(record);
		} catch (Exception e) {
			return genFailResult();
		}
		if (record.getId() != 0)
			return genSuccessResult();
		return genFailResult();
	}

	/**
	 * 添加评论
	 */
	@RequestMapping("/addComment")
	@ResponseBody
	public String addComment(TGrouponComment comment, HttpServletRequest request) {
		try {
			TMallUser user = (TMallUser) request.getSession().getAttribute(
					Constant.LOGIN_USER);
			boolean result = groupService.addComment((int) user.getId(),
					comment.getProjectid(), comment.getScheduleid(),
					comment.getContent(), comment.getParentid(),
					comment.getOrderid());
			if (result)
				return genSuccessResult();
			else
				return genFailResult();
		} catch (Exception e) {
			return genFailResult();
		}

	}

}
