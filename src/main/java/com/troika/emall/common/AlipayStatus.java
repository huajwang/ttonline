package com.troika.emall.common;

public enum AlipayStatus {
	TRADE_FINISHED("TRADE_FINISHED","交易成功"),
	TRADE_SUCCESS("TRADE_SUCCESS","支付成功"),
	WAIT_BUYER_PAY("WAIT_BUYER_PAY","交易创建"),
	TRADE_CLOSED("TRADE_CLOSED","交易关闭");
	private String code;
	private String text;
	private AlipayStatus(String code,String text){
		this.code = code;
		this.text = text;
	}
	public String getCode(){
		return code;
	}
	public String getText(){
		return text;
	}
}
