package com.troika.groupon.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import com.troika.groupon.service.SendMsgWXService;

/**
 * 异步推送微信通知，发送给发起人
 * 
 * @author rd
 * 
 */
@Component
public class GrouponAsyncSearch {
	Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private SendMsgWXService sendMsgWXService;

	/**
	 * 向项目发起者推送下单通知
	 */
	@Async
	public ListenableFuture<String> asyncNotifyVendors(Integer orderId) {
		try {
			logger.debug("notifying vendors...");
			sendMsgWXService.notifyVendors(orderId);
		} catch (Exception e) {
			return new AsyncResult<String>("error");
		}
		return new AsyncResult<String>("success");
	}
}
