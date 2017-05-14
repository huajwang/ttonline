package com.troika.groupon.common;


/*
 * 拼团个人订单状态
 */
public enum GrouponOrderStatus {
	ACTIVE(0,"待支付"),
	PAID(1,"待发货"),
	SEND(2, "待收货"),
	RETURN(3, "退货中"),
	RETURNED(4,"退货完成"),
	FINISH(5,"订单完成 ");
	
	private int code;
	private String text;
	private GrouponOrderStatus(int code,String text){
		this.code = code;
		this.text = text;
	}
	public int getCode(){
		return code;
	}
	public String getText(){
		return text;
	}
}

