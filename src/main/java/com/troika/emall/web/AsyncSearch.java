package com.troika.emall.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import com.troika.emall.service.PayService;

/**
 * 异步推送微信通知
 * @author rd
 *
 */
@Component
public class AsyncSearch {
	Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	private PayService payService;
	
	/**
	 * 向厂商推送下单通知
	 */
	@Async
	public ListenableFuture<String> asyncNotifyVendors(String orderId){
		try {
			logger.debug("notifying vendors...");
			payService.notifyVendors(orderId);
		} catch (Exception e) {
			return new AsyncResult<String>("error");
		}
		return new AsyncResult<String>("success");
	}
	
	/**
	 * 向用户推送佣金获取消息
	 * 
	 */
	@Async
	public ListenableFuture<String> asyncNotifyIntroducer(String orderId){
		try{
			logger.debug("notifying introducer ...");
			payService.notifyIntroducer(orderId);
		}catch (Exception e) {
			return new AsyncResult<String>("error");
		}
		return new AsyncResult<String>("success");
		
		
	}
	
	public void addCallback(ListenableFuture listenableFuture,String successful,String failure){
		
		final String successInfo = successful;
		final String failureInfo = failure;
		
		/**
		 * 创建异步成功的回调函数
		 */
		SuccessCallback<String> successCallback = new SuccessCallback<String>() {
			@Override
			public void onSuccess(String str){
				logger.info(successInfo+"，return:"+str);
			}
		};
		
		/**
		 * 创建异步失败的回调函数
		 */
		FailureCallback failureCallback = new FailureCallback() {
			@Override
			public void onFailure(Throwable ex) {
				logger.info(failureInfo+"，exception message:"+ex.getMessage());
			}
		};
		
		listenableFuture.addCallback(successCallback,failureCallback);
	}
}
