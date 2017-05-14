package com.troika.groupon.common;

public enum ComplainStatus {
	//状态：0 投诉中 1 处理中 2 处理完成
	CPREQUEST("0","投诉中"),
	CPDEAL("1","处理中"),
	CPCOMPLETE("2","处理完成");
	private String code;
	private String text;
	private ComplainStatus(String code,String text){
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
