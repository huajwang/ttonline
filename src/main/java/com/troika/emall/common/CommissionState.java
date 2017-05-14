package com.troika.emall.common;

public enum CommissionState {
	UNCOMPLETE("0","未完成"),//购物车正常状态
	COMPLETE("1","已完成"),//删除状态
	CANCEL("2","取消");
	private String code;
	private String text;
	private CommissionState(String code,String text){
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
