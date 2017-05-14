package com.troika.emall.web;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.cache.spi.GeneralDataRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.WeixinService;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;

@Controller
@RequestMapping("/weixin")
public class WeixiController extends BaseController {
	
	@Autowired
	private WeixinService weixinService;
	
	@RequestMapping("/getQrcode")
	@ResponseBody
	public String getQrcode(int gId,HttpServletRequest request) throws Exception{
		TMallUser user = CommonUtil.ValidateUser(request);
		if(user != null){
			String result = weixinService.getQrcodeUrl(gId, (int)user.getId());
			return result;
		}else{
			return null;
		}
	}
}
