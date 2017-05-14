package com.troika.emall.common;

public enum OrderStatus {
	ACTIVE("0","待支付"),
	PAID("1","已支付"),
	FINISH("2","订单完成 "),
	CANCEL("3", "订单取消"),
	DELETE("-1","订单删除");//删除状态
	
	private String code;
	private String text;
	private OrderStatus(String code,String text){
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

