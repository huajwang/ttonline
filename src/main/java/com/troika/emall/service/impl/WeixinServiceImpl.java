package com.troika.emall.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.troika.emall.common.WeixinMngStatus;
import com.troika.emall.dao.QrcodeDao;
import com.troika.emall.model.WxTBusQrcode;
import com.troika.emall.service.WeixinService;
import com.troika.emall.util.Constant;
import com.troika.emall.util.MyClient;

@Service
public class WeixinServiceImpl implements WeixinService {
	
	@Autowired
	private QrcodeDao qrcodeDao;

	@Override
	public String getQrcodeUrl(int gId,int userId) throws Exception {
		WxTBusQrcode qrcode = new WxTBusQrcode();
		qrcode.setCreateTime(new Date());
		qrcode.setGId(gId);
		qrcode.setUserId(userId);
		qrcode.setLifeTime(Constant.WX_LIFETIME);
		qrcode = qrcodeDao.saveOrUpQrcode(qrcode);
		String result = null;
		if(qrcode.getId() != 0){
			Map<String,String> map = new HashMap<String, String>();
			map.put("busId", Constant.WX_BUSID);
			map.put("code", Constant.WX_API_CODE);
			map.put("action", WeixinMngStatus.CREATETEMPORARY.getCode());
			map.put("expireSeconds", qrcode.getLifeTime()+"");
			map.put("sceneId", String.valueOf(qrcode.getId()));
			result = MyClient.post(Constant.WX_SERVICE_URL_MNG, map);
		}
		return result;
	}

	@Override
	public String getQrcodeIMGUrl(int gId,int userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
