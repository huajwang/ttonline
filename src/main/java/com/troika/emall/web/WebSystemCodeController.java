package com.troika.emall.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.util.MD5;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.SystemCodeService;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.sms.HttpSender;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;

@Controller
@RequestMapping("/web/code")
public class WebSystemCodeController extends BaseController{
	
	
	@Autowired
	private SystemCodeService systemCodeService;
	
	@Autowired
	private TMallUserService tMallUserService;
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/smsCode")
	@ResponseBody
	public String smsCode(HttpServletRequest request,String phone){
		int sixNum = CommonUtil.randomSixNum();
		request.getSession().setAttribute(Constant.SMS_CODE, sixNum);
		HttpSender.sendContent(phone, sixNum+"");
		logger.info("验证码已发送");
		return genSuccessResult();
	}
	
	@RequestMapping("/checkSmsCode")
	@ResponseBody
	public String checkSmsCode(HttpServletRequest request,String smsCode){
		Integer sixNum = (Integer) request.getSession().getAttribute(Constant.SMS_CODE);
		Map<String,Object> result = new HashMap<String,Object>();
		if(sixNum != null){
			if(sixNum.toString().equals(smsCode)){
				String regCode = CommonUtil.buildRegCode();
				request.getSession().setAttribute(Constant.REGISTER_CODE, regCode);
				result.put("regCode", regCode);
			}
		}
		if(result.isEmpty()){
			return genFailResult();
		}else{
			return genSuccessResult(result);
		}
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @param registerCode
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping("resetPassword")
	@ResponseBody
	public String resetPassword(HttpServletRequest request,String registerCode,String phone,String password){
		Object regObj = request.getSession().getAttribute(Constant.REGISTER_CODE);
		Map<String,Object> result = null;
		if(regObj != null && regObj.toString().equals(registerCode)){
			result = tMallUserService.reSetPassword(phone, MD5.GetMD5Code(password, true));
		}else{
			result = new HashMap<String,Object>();
			result.put("msg", "没有校验码不能使用此功能");
		}
		if(result.isEmpty()){
			return genSuccessResult();
		}else{
			return genFailResult(result);
		}
	}
}
