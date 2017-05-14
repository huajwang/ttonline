package com.troika.emall.restapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.model.TMallArea;
import com.troika.emall.model.TMallCity;
import com.troika.emall.model.TMallProvince;
import com.troika.emall.service.SystemCodeService;
import com.troika.emall.sms.HttpSender;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;

@Controller
@RequestMapping("/sys/code/")
public class SystemCodeController extends BaseController{
	@Autowired
	private SystemCodeService systemCodeService;
	/**
	 *获取省
	 * @return
	 */
	@RequestMapping("/getAllProvince.api")
	@ResponseBody
	public String getAllProvince(){
		List<TMallProvince> list = systemCodeService.findAllProvince();
		String resultJson = genSuccessResult(list);
		return resultJson;
	}
	/**
	 * 获取市
	 * @param provinceId
	 * @return
	 */
	@RequestMapping("/getCityByProvinceId.api")
	@ResponseBody
	public String getCityByProvinceId(String provinceId){
		List<TMallCity> list = systemCodeService.findCityByProvinceId(provinceId);
		String resultJson = genSuccessResult(list);
		return resultJson;
	}
	/**
	 * 获取区县
	 * @param cityId
	 * @return
	 */
	@RequestMapping("/getAreaByCityId.api")
	@ResponseBody
	public String getAreaByCityId(String cityId){
		List<TMallArea> list = systemCodeService.findAreaByCityId(cityId);
		String resultJson = genSuccessResult(list);
		return resultJson;
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/smsCode.api")
	@ResponseBody
	public String smsCode(HttpServletRequest request,String phone){
		int sixNum = CommonUtil.randomSixNum();
		request.getSession().setAttribute(Constant.SMS_CODE, sixNum);
		HttpSender.sendContent(phone, sixNum+"");
		return genSuccessResult();
	}
	
	@RequestMapping("/checkSmsCode.api")
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
}
