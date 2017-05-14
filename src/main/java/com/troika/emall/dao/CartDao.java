package com.troika.emall.dao;

import java.util.List;
import java.util.Map;

import com.troika.emall.model.TMallCart;
import com.troika.emall.model.TMallCartDetail;

public interface CartDao {
	public TMallCart getCartByUserId(long userId);
	public List<TMallCartDetail> getCartDetailByCartId(long cartId);
	public void saveOrUpdateDetail(List<TMallCartDetail> list);
	public TMallCart saveOrUpdateCart(TMallCart cart);
	public void deleteCartDetail(List<TMallCartDetail> list);
	public List<Map<String,Object>> getCartListByUserId(long userId);
	public TMallCartDetail queryByProperties(long cartId,long gId,String propertyTableName,long propertyId);
	public TMallCartDetail saveOrUpdateOneDetail(TMallCartDetail detail);
	public void delDetail(List<Long> list);
	public Map<String,Long> getGoodsNum(long userId);
	//获取购物车主表数据
	public TMallCart getCartByCartId(long cartId);
}
