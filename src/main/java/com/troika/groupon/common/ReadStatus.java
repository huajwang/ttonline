package com.troika.groupon.common;

public enum ReadStatus {
	UNREAD("0","未读"),
	READ("1","已读");
	private String code;
	private String text;
	private ReadStatus(String code,String text){
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
