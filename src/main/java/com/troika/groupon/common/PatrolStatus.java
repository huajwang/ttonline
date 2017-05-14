package com.troika.groupon.common;

public enum PatrolStatus {
	//状态:1 未发布 2 已发布 3 下架
	UNUP("1","未发布"),
	UP("2","已发布"),
	DOWN("3","下架");
	private String code;
	private String text;
	private PatrolStatus(String code,String text){
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
