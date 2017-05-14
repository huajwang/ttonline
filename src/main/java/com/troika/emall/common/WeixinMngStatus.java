package com.troika.emall.common;

/**
 * 这些状态由商城项目,商家管理后台ttvendor项目和联众智慧的管理后台共同维护
 *
 */
public enum WeixinMngStatus {
	// 0 退货申请中， 1 退货中， 2 退款中， 3 退款完成 4 退货失败
	GETMENU("getMenu","获取菜单列表"),
	CUSTOMMENU("custommenu","自定义菜单"),
	SENDALL("sendAll", "群发消息"),
	CREATETEMPORARY("createTemporary", "创建带参临时二维码"),
	GETSHORTURL("getShortUrl","长链接转短链接");
	
	private String code;
	private String text;
	private WeixinMngStatus(String code,String text){
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

