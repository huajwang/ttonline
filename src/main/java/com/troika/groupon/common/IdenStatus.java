package com.troika.groupon.common;

public enum IdenStatus {
	PASS("0","验证成功"),
	NOPASS("1","验证失败");
	private String code;
	private String text;
	private IdenStatus(String code,String text){
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
