package com.troika.emall.common;

/**
 * 这些状态由商城项目,商家管理后台ttvendor项目和联众智慧的管理后台共同维护
 *
 */
public enum GoodsReturnStatus {
	// 0 退货申请中， 1 退货中， 2 退款中， 3 退款完成 4 退货失败
	REQUEST("0","退货申请中"),
	SENDING_BACK("1","退货中"),
	CASHING_BACK("2", "退款中"),
	CASHED_BACK("3", "退款完成"),
	FAIL("4", "退货失败");
	
	private String code;
	private String text;
	private GoodsReturnStatus(String code,String text){
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

