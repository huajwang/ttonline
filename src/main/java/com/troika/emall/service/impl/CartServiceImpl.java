package com.troika.emall.service.impl;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.troika.emall.common.CartStatus;
import com.troika.emall.dao.CartDao;
import com.troika.emall.dao.GoodsDao;
import com.troika.emall.model.TMallCart;
import com.troika.emall.model.TMallCartDetail;
import com.troika.emall.model.TMallGood;
import com.troika.emall.service.CartService;

@Service
@Transactional
public class CartServiceImpl extends BaseServiceImpl implements CartService {

	@Autowired
	private CartDao cartDao;
	@Autowired
	private GoodsDao goodsDao;

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> buildCartList(long userId,
			String cartDetailJson, BigDecimal total) {
		Map<String, Object> result = new HashMap<String, Object>();
		logger.info("合并购物车列表");
		List<Map<String, Object>> cartList = new Gson().fromJson(
				cartDetailJson, List.class);
		TMallCart cart = cartDao.getCartByUserId(userId);
		logger.info("查询物车列表是否已经存在");
		if (cart != null) {
			List<TMallCartDetail> detailList = cartDao
					.getCartDetailByCartId(cart.getCartId());
			logger.info("物车详情列表有" + detailList.size() + "条数据");
			List<TMallCartDetail> existList = new ArrayList<TMallCartDetail>();
			List<TMallCartDetail> delList = new ArrayList<TMallCartDetail>();
			for (int k = 0; k < detailList.size(); k++) {
				TMallCartDetail detail = detailList.get(k);
				boolean isExists = false;
				for (int i = 0; i < cartList.size(); i++) {
					Map<String, Object> map = cartList.get(i);
					long gId = (long) Double.parseDouble(map.get("gId")
							.toString());
					if (detail.getGId() == gId) {
						int gQuantity = (int) Double.parseDouble(map.get(
								"gQuantity").toString());
						Object propertyTableNameObj = map
								.get("propertyTableName");
						if (propertyTableNameObj != null
								&& !"".equals(propertyTableNameObj.toString())) {
							long propertyId = (long) Double.parseDouble(map
									.get("propertyId").toString());
							if (detail.getPropertyId() == propertyId) {// 同个商品相同规格且数量一样则更新
								if (gQuantity != detail.getGQuantity()) {
									detail.setGQuantity(gQuantity);
									existList.add(detail);
								}
								isExists = true;
								cartList.remove(i);// 删除已经存在的
							}
						} else {
							if (gQuantity != detail.getGQuantity()) {
								detail.setGQuantity(gQuantity);
								existList.add(detail);
							}
							isExists = true;
							cartList.remove(i);// 删除已经存在的
						}
					}
				}
				if (!isExists) {// 不存在删除
					delList.add(detail);
				}
			}
			logger.info("删除购物车详情数据" + delList.size() + "条");
			cartDao.deleteCartDetail(delList);// 删除
			logger.info("更新购物车详情数据" + existList.size() + "条");
			cartDao.saveOrUpdateDetail(existList);// 更新
			List<TMallCartDetail> insertList = buildDetailInsertList(
					cart.getCartId(), cartList);
			logger.info("新增购物车详情数据" + insertList.size() + "条");
			cartDao.saveOrUpdateDetail(insertList);// 插入
			// 合并计算总价
			cart.setTotal(total);
			logger.info("更新购物车");
			cartDao.saveOrUpdateCart(cart);
		} else {
			// 新增
			cart = new TMallCart();
			cart.setUserId(userId);
			cart.setTotal(total);
			cart.setCartStatus(CartStatus.ACTIVE.getCode());
			cart.setCartCreateTime(new Date());
			logger.info("新增购物车");
			TMallCart resultCart = cartDao.saveOrUpdateCart(cart);
			List<TMallCartDetail> insertList = buildDetailInsertList(
					resultCart.getCartId(), cartList);
			logger.info("新增购物车详情");
			cartDao.saveOrUpdateDetail(insertList);// 插入
		}
		result.put("status", "0");
		return result;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> buildCart(long userId, String cartDetailJson,
			BigDecimal total, String isOut) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> cartMap = new Gson().fromJson(cartDetailJson,
				Map.class);
		logger.info("查找用户【" + userId + "】购物车是否存在");
		TMallCart cart = cartDao.getCartByUserId(userId);
		TMallCartDetail resultDetail = null;
		// 推荐人数据
		Object inducerObj = cartMap.get("introducer");
		Integer introducer = null;
		if (inducerObj != null) {
			introducer = Integer.valueOf(cartMap.get("introducer").toString());
		}
		if (cart != null) {
			long gId = (long) Double.parseDouble(cartMap.get("gId").toString());
			int gQuantity = (int) Double.parseDouble(cartMap.get("gQuantity")
					.toString());
			Object propertyTableNameObj = cartMap.get("propertyTableName");
			TMallCartDetail td = null;
			logger.info("查找商品【" + gId + "】是否在购物车中");
			if (propertyTableNameObj != null
					&& !"".equals(propertyTableNameObj.toString())) {
				long propertyId = (long) Double.parseDouble(cartMap.get(
						"propertyId").toString());
				td = cartDao.queryByProperties(cart.getCartId(), gId,
						propertyTableNameObj.toString(), propertyId);
			} else {
				td = cartDao.queryByProperties(cart.getCartId(), gId, null, 0);
			}
			if (td != null) {
				// 更新商品价格
				if (propertyTableNameObj != null
						&& !"".equals(propertyTableNameObj.toString())) {
					long propertyId = (long) Double.parseDouble(cartMap.get(
							"propertyId").toString());
					Map<String, Object> existMap = goodsDao
							.getPropertyByPropertyNameAndId(
									propertyTableNameObj.toString(), propertyId);
					if (existMap != null) {
						td.setPrice((BigDecimal) existMap.get("price"));
					}
				} else {
					TMallGood existGood = goodsDao.findByGid(gId);
					td.setPrice(existGood.getPrice());// 写入最新的商品价
				}
				logger.info("更新商品【" + gId + "】在购物车中的数量");
				gQuantity += StringUtils.isNotBlank(isOut) ? td.getGQuantity()
						: 0;
				td.setGQuantity(gQuantity);
				resultDetail = cartDao.saveOrUpdateOneDetail(td);
			} else {
				logger.info("添加商品【" + gId + "】到购物车");
				List<TMallCartDetail> insertList = buildDetailInsertMap(
						cart.getCartId(), cartMap);
				resultDetail = cartDao.saveOrUpdateOneDetail(insertList.get(0));// 插入
			}
			logger.info("更新购物车");
			// 合并计算总价
			cart.setTotal(total);
			if (introducer != null && introducer > 0)
				cart.setIntroducer(introducer);// 推荐人
			cartDao.saveOrUpdateCart(cart);
		} else {
			// 新增
			cart = new TMallCart();
			cart.setUserId(userId);
			cart.setTotal(total);
			cart.setIntroducer(introducer);// 推荐人
			cart.setCartStatus(CartStatus.ACTIVE.getCode());
			cart.setCartCreateTime(new Date());
			logger.info("添加用户【" + userId + "】的购物车");
			TMallCart resultCart = cartDao.saveOrUpdateCart(cart);
			List<TMallCartDetail> insertList = buildDetailInsertMap(
					resultCart.getCartId(), cartMap);
			resultDetail = cartDao.saveOrUpdateOneDetail(insertList.get(0));// 插入
			logger.info("添加用户【" + userId + "】的购物车明细");
		}
		result.put("cartDetailId", resultDetail.getId());
		return result;
	}

	private List<TMallCartDetail> buildDetailInsertMap(long cartId,
			Map<String, Object> cartMap) {
		List<Map<String, Object>> cartList = new ArrayList<Map<String, Object>>();
		cartList.add(cartMap);
		return buildDetailInsertList(cartId, cartList);
	}

	private List<TMallCartDetail> buildDetailInsertList(long cartId,
			List<Map<String, Object>> cartList) {
		logger.info("构建购物车明细开始");
		List<TMallCartDetail> insertList = new ArrayList<TMallCartDetail>();
		for (Map<String, Object> map : cartList) {
			TMallCartDetail detail = new TMallCartDetail();
			long gId = (long) Double.parseDouble(map.get("gId").toString());
			logger.info("查找商品【" + gId + "】");
			int gQuantity = (int) Double.parseDouble(map.get("gQuantity")
					.toString());
			Object propertyTableNameObj = map.get("propertyTableName");
			detail.setCartId(cartId);
			detail.setGId(gId);
			detail.setGQuantity(gQuantity);
			// detail.setPrice(new BigDecimal(map.get("price").toString()));//
			// 写入最新的商品价
			if (propertyTableNameObj != null
					&& !"".equals(propertyTableNameObj)) {
				long propertyId = (long) Double.parseDouble(map.get(
						"propertyId").toString());
				detail.setPropertyId(propertyId);
				detail.setPropertyTableName(propertyTableNameObj.toString());
				Map<String, Object> existMap = goodsDao
						.getPropertyByPropertyNameAndId(
								propertyTableNameObj.toString(), propertyId);
				if (existMap != null) {
					if (existMap.size() > 0)// 判断是否存在数据
						detail.setPrice((BigDecimal) existMap.get("price"));
					else {
						detail.setPrice(new BigDecimal(map.get("price")
								.toString()));
					}
				}
			} else {
				TMallGood existGood = goodsDao.findByGid(gId);
				detail.setPrice(existGood.getPrice());
			}
			insertList.add(detail);
		}
		logger.info("构建购物车明细完成");
		return insertList;
	}

	public List<Map<String, Object>> getCartListByUserId(long userId) {
		logger.info("获取用户【" + userId + "】的购物车列表");
		List<Map<String, Object>> list = cartDao.getCartListByUserId(userId);
		return list;
	}

	public void delDetail(String cartDetail) {
		logger.info("删除购物车中的商品:" + cartDetail);
		Type listType = new TypeToken<ArrayList<Long>>() {
		}.getType();
		List<Long> cartList = new Gson().fromJson(cartDetail, listType);
		cartDao.delDetail(cartList);
	}

	public Map<String, Long> getGoodsNum(long userId) {
		return cartDao.getGoodsNum(userId);
	}

	@Override
	public void delDetail(List<Long> list) {
		// TODO Auto-generated method stub
		cartDao.delDetail(list);
	}

	/**
	 * 获取购物主表数据
	 */
	@Override
	public TMallCart getCartByCartId(long cartId) {
		TMallCart record = cartDao.getCartByCartId(cartId);
		return record;
	}
}
