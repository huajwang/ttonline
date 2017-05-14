package com.troika.groupon.service;

import java.io.IOException;

/**
 * 发送微信消息接口
 * 
 * @author Administrator
 * 
 */
public interface SendMsgWXService {
	/**
	 * 通过微信推送消息给项目发起者
	 */
	public void notifyVendors(Integer orderId) throws IOException;
}
