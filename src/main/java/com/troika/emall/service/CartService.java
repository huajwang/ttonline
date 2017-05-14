package com.troika.emall.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.troika.emall.model.TMallCart;

public interface CartService {
	/**
	 * 购物车合并
	 * @param userId
	 * @param cartDetailJson
	 * @param total
	 * @return
	 */
	public Map<String,Object> buildCartList(long userId,String cartDetailJson,BigDecimal total);
	/**
	 * 添加购物车
	 * @param userId
	 * @param cartDetailJson
	 * @param total
	 * @param isOut
	 * @return
	 */
	public Map<String,Object> buildCart(long userId,String cartDetailJson,BigDecimal total,String isOut);
	/**
	 * 根据用户ID获取购物车列表
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> getCartListByUserId(long userId);
	/**
	 * 删除购物车
	 * @param cartDetail
	 */
	public void delDetail(String cartDetail);
	public void delDetail(List<Long> list);
	/**
	 * 获取购物车中商品数
	 * @param userId
	 * @return
	 */
	public Map<String,Long> getGoodsNum(long userId);
	//获取购物车主表数据
	public TMallCart getCartByCartId(long cartId);
}
