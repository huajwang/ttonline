package com.troika.emall.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.util.MD5;
import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.SystemCodeService;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.sms.HttpSender;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;

/**
 * 注册类
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {

	@Autowired
	private SystemCodeService systemCodeService;

	@Autowired
	private TMallUserService tMallUserService;

	/**
	 * 发送注册码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/smsCode")
	@ResponseBody
	public String smsCode(HttpServletRequest request, String phone) {
		TMallUser user = tMallUserService.findUserByPhone(phone);
		if (user == null) {
			int sixNum = CommonUtil.randomSixNum();
			request.getSession().setAttribute(Constant.SMS_CODE, sixNum);
			HttpSender.sendContent(phone, sixNum + "");
			return genSuccessResult();
		} else {
			Map<String, Object> map = new HashMap<>();
			map.put("msg", "该手机已经注册了，不能在注册");
			return genFailResult(map);
		}
	}

	/**
	 * 用户注册
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping("userRegister")
	@ResponseBody
	public String userRegister(HttpServletRequest request, String registerCode,
			String phone, String password) {
		Object regObj = request.getSession().getAttribute(
				Constant.REGISTER_CODE);
		Map<String, Object> result = null;
		if (regObj != null && regObj.toString().equals(registerCode)) {
			result = tMallUserService.register(phone,
					MD5.GetMD5Code(password, true));
			// 自动登录
			Map<String, Object> map = tMallUserService.login(phone,
					MD5.GetMD5Code(password, true));
			String status = map.remove("status").toString();
			if (status.equals(Constant.LOGIN_STATUS_SUC)) {// 登录成功的
				String token = map.get("token").toString();
				request.getSession().setAttribute(Constant.LOGIN_TOKEN, token);
				request.getSession().setAttribute(Constant.LOGIN_USER,
						map.get("result"));
				request.getSession().setAttribute(token, new Date());// token放入的时间
			}
		} else {
			result = new HashMap<String, Object>();
			result.put("msg", "没有注册码不能注册");
		}
		if (result.isEmpty()) {
			return genSuccessResult();
		} else {
			return genFailResult(result);
		}
	}

	/**
	 * 校验注册码
	 * 
	 * @param request
	 * @param smsCode
	 * @return
	 */
	@RequestMapping("/checkSmsCode")
	@ResponseBody
	public String checkSmsCode(HttpServletRequest request, String smsCode) {
		Integer sixNum = (Integer) request.getSession().getAttribute(
				Constant.SMS_CODE);
		Map<String, Object> result = new HashMap<String, Object>();
		if (sixNum != null) {
			if (sixNum.toString().equals(smsCode)) {
				request.getSession().setAttribute(Constant.REGISTER_CODE,
						smsCode);
				result.put("smsCode", smsCode);
			}
		}
		if (result.isEmpty()) {
			return genFailResult();
		} else {
			return genSuccessResult(result);
		}
	}
}
