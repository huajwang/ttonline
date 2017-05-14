package com.troika.emall.common;

public enum CartStatus {
	ACTIVE(0,"购物车使用中"),//购物车正常状态
	DELETE(-1,"购物车删除");//删除状态
	private Integer code;
	private String text;
	private CartStatus(Integer code,String text){
		this.code = code;
		this.text = text;
	}
	public Integer getCode(){
		return code;
	}
	public String getText(){
		return text;
	}
}
