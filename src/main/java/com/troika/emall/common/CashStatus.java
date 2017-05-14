package com.troika.emall.common;



public enum CashStatus {
	APPLY("0","申请中"),//购物车正常状态
	APPROVAL("1","批准"),//删除状态
	REFUSE("2","拒绝");
	private String code;
	private String text;
	private CashStatus(String code,String text){
		this.code = code;
		this.text = text;
	}
	public String getCode(){
		return code;
	}
	public String getText(){
		return text;
	}
	//根据code获取text
	public String getTextbyCode(String code){
		for(CashStatus item:CashStatus.values()){
			if(item.getCode().equals(code))
				return item.getText();
		}
		return "";
	}
}
