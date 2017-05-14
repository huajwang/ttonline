package com.troika.emall.common;

public enum OrderDetailStatus {
	NORMAL("0","常态"),//订单中物品正常状态
	SEND("1","待发货"), // 买家已经完成支付
	TAKEIN("2","待收货"), // 厂家已经发货
	EVALUATE("3","待评价"), // 买家收到商品，可以进行评论。 
	RETURN_REQUEST("4","退货申请中"), // 买家提交退货请求， 等待厂家审核
	RETURN_IN_PROGRESS("5", "退货中"), // 买家退还物品给厂家
	CASHBACK_APPROVED("6","同意退款"), // 厂商收到退货并且满意退货的状况，同意联众智慧（公司）退款给买家。
	FINISH("7","已完成"), // 订单顺利完成
	CASHBACK_TO_BUYER_DONE("8", "退款完成"), // 公司已经垫付退款给买家
	CHARGE_BACK_VENDOR_DONE("9", "扣款成功"), // 公司垫付给买家的钱，已从厂商处扣款完成
	CANCEL("-1","已取消");
	private String code;
	private String text;
	private OrderDetailStatus(String code,String text){
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
