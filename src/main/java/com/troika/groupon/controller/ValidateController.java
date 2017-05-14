package com.troika.groupon.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.util.Constant;
import com.troika.groupon.service.GroupService;

/**
 * 身份验证
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/groupon/validate")
public class ValidateController extends BaseController {

	@Autowired
	private GroupService groupService;

	/**
	 * 添加身份验证
	 * 
	 * @param request
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping("/addIdentification")
	@ResponseBody
	public String addIdentification(HttpServletRequest request, String jsonStr)
			throws Exception {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		if (user == null)
			return genFailResult();
		int userId = (int) user.getId();// 当前用户
		JSONObject json = JSONObject.fromObject(jsonStr);
		String name = json.getString("name");// 真实姓名
		String IDCard = json.getString("IDCard");// 身份号码
		String phone = json.getString("phone");// 联系电话
		String IDCard_Icon = "";
		if (json.get("IDCard_Icon") != null)
			IDCard_Icon = json.getString("IDCard_Icon");// 身份证图片
		int type = json.getInt("type");// 验证类型 0 个人验证 1 企业认证
		String license_Icon = "";// 企业营业执照
		if (json.get("license_Icon") != null)
			license_Icon = json.getString("license_Icon");
		groupService.addIdentification(userId, name, IDCard, phone, type,
				IDCard_Icon, license_Icon);
		return genSuccessResult();
	}
}
