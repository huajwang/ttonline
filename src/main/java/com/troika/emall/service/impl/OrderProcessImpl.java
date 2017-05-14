package com.troika.emall.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;

import com.google.gson.Gson;
import com.troika.emall.common.GoodsReturnStatus;
import com.troika.emall.common.OrderDetailStatus;
import com.troika.emall.common.OrderStatus;
import com.troika.emall.dao.AddressDao;
import com.troika.emall.dao.CartDao;
import com.troika.emall.dao.ExchangeDao;
import com.troika.emall.dao.GoodsDao;
import com.troika.emall.dao.OrderDao;
import com.troika.emall.model.TMallCartDetail;
import com.troika.emall.model.TMallGood;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallOrderCancel;
import com.troika.emall.model.TMallOrderDetail;
import com.troika.emall.model.TMallReturnImg;
import com.troika.emall.model.TMallReturnRecord;
import com.troika.emall.service.OrderProcess;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.web.AsyncSearch;

@Component
@Transactional
public class OrderProcessImpl implements OrderProcess {

	@Autowired
	private AddressDao addressDao;
	@Autowired
	private GoodsDao goodsDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private CartDao cartDao;
	@Autowired
	private ExchangeDao exchangeDao;

	@Autowired
	private AsyncSearch asyncSearch;

	Logger logger = LogManager.getLogger(this.getClass());

	@SuppressWarnings("unchecked")
	@Override
	public synchronized Map<String, Object> buildOrder(long userId,
			long addrId, String orderDetail, String orderSource,
			BigDecimal amount, String remark, Long introducer, Long cartId) {
		logger.info("userId = " + userId + ", addrId = " + addrId
				+ ", orderDetail = " + orderDetail + ", orderSource = "
				+ orderSource + ", amount = " + amount + ", remark = " + remark
				+ ", introducer = " + introducer + ", cartId = " + cartId);
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> cartList = new Gson().fromJson(orderDetail,
				List.class);
		List<Map<String, Object>> noStockList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : cartList) {
			long gId = (long) Double.parseDouble(map.get("gId").toString());
			int gQuantity = (int) Double.parseDouble(map.get("gQuantity")
					.toString());
			Object propertyTableNameObj = map.get("propertyTableName");
			if (propertyTableNameObj == null || "".equals(propertyTableNameObj)) {// 没有属性
				logger.info("该商品没有附加属性。 gid: " + gId);
				TMallGood goods = goodsDao.findByGid(gId);
				if (goods != null) {
					if (goods.getStock() < gQuantity) {// 当库存小于购买数量
						logger.info("库存小于购买数量: 商品库存量 = " + goods.getStock()
								+ ", 购买数量 = " + gQuantity);
						Map<String, Object> tempGoodsMap = new HashMap<String, Object>();
						tempGoodsMap.put("gId", gId);
						tempGoodsMap.put("gName", goods.getGName());
						tempGoodsMap.put("msg", "库存不够");
						noStockList.add(tempGoodsMap);
					}
				} else {
					logger.info("不存在此商品: gId = " + gId);
					Map<String, Object> tempGoodsMap = new HashMap<String, Object>();
					tempGoodsMap.put("gId", gId);
					tempGoodsMap.put("gName", "");
					tempGoodsMap.put("msg", "不存在此商品");
					noStockList.add(tempGoodsMap);
				}
			} else {
				logger.info("商品有附加属性。 gid: " + gId);
				long propertyId = (long) Double.parseDouble(map.get(
						"propertyId").toString());
				Map<String, Object> proMap = goodsDao.getPGByPropertyNameAndId(
						propertyTableNameObj.toString(), propertyId);
				if (proMap != null) {
					int stock = (int) proMap.get("stock");
					if (stock < gQuantity) {
						logger.info("有附加属性的该商品库存 < 购买数量。 gid = " + gId
								+ ", stock = " + stock + ", gQuantity = "
								+ gQuantity);
						Map<String, Object> tempGoodsMap = new HashMap<String, Object>();
						tempGoodsMap.put("gId", gId);
						tempGoodsMap.put("gName", proMap.get("gName"));
						tempGoodsMap.put("msg", "库存不够");
						noStockList.add(tempGoodsMap);
					}
				} else {
					logger.info("不存在此有附加属性的商品: gId = " + gId);
					Map<String, Object> tempGoodsMap = new HashMap<String, Object>();
					tempGoodsMap.put("gId", gId);
					tempGoodsMap.put("gName", "");
					tempGoodsMap.put("msg", "不存在此商品");
					noStockList.add(tempGoodsMap);
				}
			}
		}
		if (noStockList.size() > 0) {// 表示有些商品库存不够
			logger.info("有些商品库存不够。");
			result.put("status", "1");
			result.put("result", noStockList);
		} else {// 更新库存
			// 保存订单
			TMallOrder order = new TMallOrder();
			order.setUserId(userId);
			order.setAddrId(addrId);
			order.setAmount(amount);
			order.setIntroducer(introducer); // 在总订单里设置订单推荐人
			order.setOrderId(CommonUtil.buildOrderId(userId));
			order.setOrderStatus(OrderStatus.ACTIVE.getCode());
			order.setCreateTime(new Date());
			logger.info("更新库存; 保存订单");
			order = orderDao.saveOrUpdateOrder(order);
			List<TMallOrderDetail> insertList = new ArrayList<TMallOrderDetail>();
			// 即使有属性也把总的购买更新到总商品
			Map<Long, Integer> gMap = new HashMap<Long, Integer>();
			for (Map<String, Object> map : cartList) {
				long gId = (long) Double.parseDouble(map.get("gId").toString());
				int gQuantity = (int) Double.parseDouble(map.get("gQuantity")
						.toString());
				Object propertyTableNameObj = map.get("propertyTableName");
				if (propertyTableNameObj == null
						|| "".equals(propertyTableNameObj)) {// 没有属性
					logger.info("更新没有附加属性的商品的库存。");
					goodsDao.updateGoodsStock(gId, gQuantity, true);
					TMallOrderDetail orderD = new TMallOrderDetail();
					orderD.setgId(gId);
					orderD.setOrderId(order.getId());
					orderD.setQuantity(gQuantity);
					orderD.setDetailStatus(OrderDetailStatus.NORMAL.getCode());// 物品状态
					TMallGood goods = goodsDao.findByGid(gId);
					orderD.setPrice(goods.getPrice());// 写入商品价
					insertList.add(orderD);
				} else {
					if (gMap.containsKey(gId)) {
						int tempAmount = gMap.get(gId);
						tempAmount += gQuantity;
						gMap.put(gId, tempAmount);
					} else {
						gMap.put(gId, gQuantity);
					}
					long propertyId = (long) Double.parseDouble(map.get(
							"propertyId").toString());
					logger.info("更新有附加属性的商品的库存。propertyTableName,  propertyId, gQuantity = ("
							+ propertyTableNameObj.toString()
							+ ","
							+ propertyId + "," + gQuantity + ")");
					goodsDao.updateGoodsPStock(propertyTableNameObj.toString(),
							propertyId, gQuantity, true);
					TMallOrderDetail orderD = new TMallOrderDetail();
					orderD.setgId(gId);
					orderD.setOrderId(order.getId());
					orderD.setQuantity(gQuantity);
					orderD.setPropertyId(propertyId);
					orderD.setPropertyTableName(propertyTableNameObj.toString());
					orderD.setDetailStatus(OrderDetailStatus.NORMAL.getCode());// 物品状态
					Map<String, Object> existMap = goodsDao
							.getPropertyByPropertyNameAndId(
									propertyTableNameObj.toString(), propertyId);
					if (existMap != null) {
						orderD.setPrice((BigDecimal) existMap.get("price"));// 写入商品价
					}
					insertList.add(orderD);
				}
			}
			// 有属性的鞋子也更新总量
			Iterator<Entry<Long, Integer>> gIt = gMap.entrySet().iterator();
			while (gIt.hasNext()) {
				Entry<Long, Integer> entry = gIt.next();
				goodsDao.updateGoodsStock(entry.getKey(), entry.getValue(),
						true);
			}
			orderDao.saveOrUpdateOrderDetail(insertList);
			// 如果cartId不为空的话就更新购物车
			if (cartId != null) {
				logger.info("cartId不为空的话就更新购物车. cartId = " + cartId);
				List<TMallCartDetail> cartDetailList = cartDao
						.getCartDetailByCartId(cartId);
				List<TMallCartDetail> delCartDetailList = new ArrayList<TMallCartDetail>();
				for (TMallCartDetail detail : cartDetailList) {
					for (int i = 0; i < insertList.size(); i++) {
						TMallOrderDetail orderTemp = insertList.get(i);
						if (detail.getGId() == orderTemp.getgId()) {
							if (StringUtils.isBlank(detail
									.getPropertyTableName())) {// 表示此商品没属性值
								delCartDetailList.add(detail);
								break;
							} else {
								if (detail.getPropertyId() == orderTemp
										.getPropertyId()) {
									delCartDetailList.add(detail);
									break;
								}
							}
						}
					}
				}
				// 删除购物车对应的
				logger.info("删除购物车对应的");
				cartDao.deleteCartDetail(delCartDetailList);
			}
			result.put("orderId", order.getOrderId());// 订单编号
			result.put("id", order.getId());// 订单Id
		}
		return result;
	}

	/**
	 * 删除订单
	 */
	public Map<String, Object> deleteOrderById(long id) {
		Map<String, Object> result = new HashMap<String, Object>();
		// 获取订单号为id的待支付订单
		TMallOrder order = orderDao.findByIdAndStatus(id,
				OrderStatus.ACTIVE.getCode());
		if (order != null) {
			Map<Long, Integer> gMap = new HashMap<Long, Integer>();
			List<TMallOrderDetail> listDetail = orderDao
					.findOrderDetailByOrderId(id);
			for (TMallOrderDetail detail : listDetail) {
				if (StringUtils.isBlank(detail.getPropertyTableName())) {
					goodsDao.updateGoodsStock(detail.getgId(),
							detail.getQuantity(), false);
				} else {
					if (gMap.containsKey(detail.getgId())) {
						int tempAmount = gMap.get(detail.getgId());
						tempAmount += detail.getQuantity();
						gMap.put(detail.getgId(), tempAmount);
					} else {
						gMap.put(detail.getgId(), detail.getQuantity());
					}
					goodsDao.updateGoodsPStock(detail.getPropertyTableName(),
							detail.getPropertyId(), detail.getQuantity(), false);
				}
			}
			// 有属性的鞋子也更新总量
			Iterator<Entry<Long, Integer>> gIt = gMap.entrySet().iterator();
			while (gIt.hasNext()) {
				Entry<Long, Integer> entry = gIt.next();
				goodsDao.updateGoodsStock(entry.getKey(), entry.getValue(),
						false);
			}
			// 删除order状态变为已删除，
			orderDao.deleteOrder(order.getId());
			result.put("status", "0");
			result.put("msg", "删除成功");
		} else {
			result.put("status", "1");
			result.put("msg", "该订单不可删除");
		}
		return result;
	}

	/**
	 * 订单列表
	 */
	public Map<Long, List<Map<String, Object>>> getOrderListByUserId(long userId) {
		Map<Long, List<Map<String, Object>>> result = orderDao
				.getOrderListByUserId(userId);
		return result;
	}

	public Map<String, Object> getOrderDetailById(long orderId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = orderDao.getOrderDetailById(orderId);
		// 订单详情
		TMallOrder order = orderDao.findById(orderId);
		// 地址
		Map<String, Object> address = addressDao
				.findAddressDetailByOrderId(orderId);
		result.put("orderDetail", list);
		result.put("order", order);
		result.put("address", address);
		return result;
	}

	public List<Map<String, Object>> getOrderSingleDetailById(long orderId) {
		List<Map<String, Object>> list = orderDao.getOrderDetailById(orderId);
		return list;
	}

	@Override
	public TMallOrder getOrderInfoById(long orderId) {
		TMallOrder order = orderDao.findById(orderId);
		return order;
	}

	@Override
	public Map<String, Object> getAddressInfoByOrderId(long orderId) {
		Map<String, Object> address = addressDao
				.findAddressDetailByOrderId(orderId);
		return address;
	}

	/**
	 * 待支付订单列表
	 */
	public Map<Long, List<Map<String, Object>>> getWaitPayOrderList(long userId) {
		return orderDao.getOrderListByStatus(userId, OrderStatus.ACTIVE,
				OrderDetailStatus.NORMAL);
	}

	/**
	 * 已支付，待发货订单列表
	 */
	public Map<Long, List<Map<String, Object>>> getWaitSendOrderList(long userId) {
		return orderDao.getOrderListByStatus(userId, OrderStatus.PAID,
				OrderDetailStatus.SEND);
	}

	/**
	 * 已发货，待收货订单列表
	 */
	public Map<Long, List<Map<String, Object>>> getWaitGetOrderList(long userId) {
		return orderDao.getOrderListByStatus(userId, OrderStatus.PAID,
				OrderDetailStatus.TAKEIN);
	}

	/**
	 * 已完成订单列表---这个暂定全部完成
	 */
	public Map<Long, List<Map<String, Object>>> getDoneOrderList(long userId) {
		return orderDao.getOrderListByStatus(userId, OrderStatus.FINISH,
				OrderDetailStatus.FINISH);
	}

	/**
	 * 待评价
	 */
	public Map<Long, List<Map<String, Object>>> getWaitEvalList(long userId) {
		return orderDao.getWaitEvalList(userId);
	}

	// 待支付
	public List<Map<String, Object>> WaitPayOrderList(long userId) {
		return orderDao.OrderListByStatus(userId, OrderStatus.ACTIVE,
				OrderDetailStatus.NORMAL);
	}

	// 待发货
	@Override
	public List<Map<String, Object>> WaitSendOrderList(long userId) {
		return orderDao.OrderListByStatus(userId, OrderStatus.PAID,
				OrderDetailStatus.SEND);
	}

	// 待收货
	@Override
	public List<Map<String, Object>> WaitGetOrderList(long userId) {
		return orderDao.OrderListByStatus(userId, OrderStatus.PAID,
				OrderDetailStatus.TAKEIN);
	}

	@Override
	public List<Map<String, Object>> DoneOrderList(long userId) {
		return orderDao.OrderListByStatus(userId, OrderStatus.FINISH,
				OrderDetailStatus.FINISH);
	}

	// 待评价
	public List<Map<String, Object>> WaitEvalList(long userId) {
		return orderDao.OrderListByStatus(userId, OrderStatus.FINISH,
				OrderDetailStatus.EVALUATE, 1);
	}

	/**
	 * 申请退货 按订单里具体的某个商品进行退货 。商家管理后台有个处理退货的功能模块。 具体退货 + 厂家结算 流程： 1.
	 * 买家提交某个商品的退货请求；订单详情状态改为 RETURN_REQUEST("4","退货申请中"), // 买家提交退货请求， 等待厂家审核
	 * 2. 厂家在商家管理后台查看该请求。 如果同意买家退货， 该订单详情状态改为RETURN_IN_PROGRESS("5", "退货中") //
	 * 买家退还物品给厂家 如果不同意， 则线下处理； 3. 买家在个人中心里知道该笔交易可以退款，寄还商品到指定地址。
	 * 厂家收到商品后满意商品的状况，确认联众智慧可以退款给买家。 CASHBACK_APPROVED("6","同意退款") 4.
	 * 联众智慧垫付退款给买家 CASHBACK_TO_BUYER_DONE("8", "退款完成"), // 公司已经垫付退款给买家 5.
	 * 厂家在进行结算时，把t_mall_order_detail表中settleStatus为未结算的金额计算出来，
	 * 然后减去detailStatus为（退款完成 & 还未扣款成功）的金额；
	 * 
	 * @param orderId
	 *            订单ID
	 * @param gid
	 *            商品ID
	 * @param propertyId
	 *            对应商品属性表里的id。通过propertyTableName和propertyId这两个值，
	 *            就可以在该商品属性表里找到具有某些属性的商品
	 * @param imgUrls
	 *            买家申请退货时附上的若干张图片的地址
	 * @param reason
	 *            买家申请退货时的退货理由
	 * 
	 *            提交退货请求 买家提交包括原因和图片在内的退货请求。插入一条退款请求到t_mall_return_record表中，
	 *            并把订单详情 的状态改为退货申请中。
	 */
	public Map<String, String> requestReturnOrder(Long orderId, Long gId,
			Long propertyId, String imgUrls, String reason) {
		Map<String, String> result = new HashMap<String, String>();
		// 通过orderId获取订单详情列表。 一个订单详情记录包含订单里一个商品的状态。
		List<TMallOrderDetail> listDetail = orderDao
				.findOrderDetailByOrderId(orderId);
		TMallOrderDetail removeDetail = null;
		for (int i = 0; i < listDetail.size(); i++) {
			TMallOrderDetail detail = listDetail.get(i);
			// 找出订单详情列表里对应商品的订单详情
			if (gId == detail.getgId()) {
				removeDetail = detail;
				// 更改订单中对应商品的订单详情状态为退货中状态
				detail.setDetailStatus(OrderDetailStatus.RETURN_REQUEST
						.getCode());
				orderDao.saveOrUpdateOneOrderDetail(detail);// 更新状态
				break;
			}
		}

		if (removeDetail != null) { // 在订单详情表里找到该笔订单详情
			// 把退货原因存入t_mall_return_record表里;
			TMallReturnRecord returnRecord = new TMallReturnRecord();
			// 设置订单详情ID
			returnRecord.setOrderDetailId(removeDetail.getId());
			returnRecord.setCancelReason(reason);
			returnRecord.setRequestType("0");// 只做退货
			returnRecord.setRequestTime(new Date());
			returnRecord.setStatus(GoodsReturnStatus.REQUEST.getCode());
			exchangeDao.saveReturnRecord(returnRecord);
			// 图片存入t_mall_return_img表中
			if (StringUtils.isNotBlank(imgUrls)) {
				String[] imgArr = imgUrls.split(",");// 规定以逗号隔开
				List<TMallReturnImg> imgList = new ArrayList<TMallReturnImg>();
				for (String img : imgArr) {
					TMallReturnImg exchangeImg = new TMallReturnImg();
					// TODO - 刚插入数据库表的记录returnRecord能获得Id吗？
					exchangeImg.setReturnId(returnRecord.getId());
					exchangeImg.setImge(img);
					imgList.add(exchangeImg);
				}
				exchangeDao.saveExchangeImgs(imgList);// 插入上传的退货图片
			}
			result.put("status", "0");
			result.put("msg", "退换货申请成功");
		} else { // 在订单详情表里不存在该笔订单详情
			result.put("status", "1");
			result.put("msg", "退换货申请失败");
		}
		return result;
	}

	public Map<String, String> requestReturnOrderObject(String imgUrls,
			TMallReturnRecord record) {
		Map<String, String> result = new HashMap<String, String>();
		TMallOrderDetail detail = findOrderDetailById(record.getOrderDetailId());
		detail.setDetailStatus(OrderDetailStatus.RETURN_REQUEST.getCode());
		orderDao.saveOrUpdateOneOrderDetail(detail);// 更新状态
		record.setRequestTime(new Date());
		record.setStatus(GoodsReturnStatus.REQUEST.getCode());
		exchangeDao.saveReturnRecord(record);
		// 图片存入t_mall_return_img表中
		if (StringUtils.isNotBlank(imgUrls)) {
			String[] imgArr = imgUrls.split(",");// 规定以逗号隔开
			List<TMallReturnImg> imgList = new ArrayList<TMallReturnImg>();
			for (String img : imgArr) {
				TMallReturnImg exchangeImg = new TMallReturnImg();
				// TODO - 刚插入数据库表的记录returnRecord能获得Id吗？
				exchangeImg.setReturnId(record.getId());
				exchangeImg.setImge(img);
				imgList.add(exchangeImg);
			}
			exchangeDao.saveExchangeImgs(imgList);// 插入上传的退货图片
		}
		result.put("status", "0");
		result.put("msg", "退换货申请成功");
		// result.put("status", "1");
		// result.put("msg", "退换货申请失败");
		return result;
	}

	/**
	 * 保存订单
	 * 
	 * @param record
	 * @return
	 */
	@Override
	public TMallOrder saveOrUpdateOrder(TMallOrder record) {
		return orderDao.saveOrUpdateOrder(record);
	}

	/**
	 * 保存订单详情
	 * 
	 * @param orderDetailList
	 */
	@Override
	public void saveOrUpdateOrderDetail(List<TMallOrderDetail> orderDetailList) {
		orderDao.saveOrUpdateOrderDetail(orderDetailList);
	}

	/**
	 * 根据订单id更新订单状态为status。同时更新订单的处理时间orderProcessTime
	 */
	@Override
	public void updateOrderStatus(long id, String status) {
		orderDao.updateOrderStatus(id, status);
	}

	/**
	 * 更新订单状态为已支付
	 */
	public void updateOrderWithPaid(String orderId) {
		orderDao.updateStatusByOrderId(orderId, OrderStatus.PAID.getCode());// 完成支付
		logger.debug("更新订单状态为已支付。并微信通知厂家和订单介绍人");
		ListenableFuture<String> listenableFuture = asyncSearch
				.asyncNotifyVendors(orderId);
		asyncSearch.addCallback(listenableFuture, "向厂商推送下单通知成功", "向厂商推送下单通知失败");
		ListenableFuture<String> introduceListenableFuture = asyncSearch
				.asyncNotifyIntroducer(orderId);
		asyncSearch.addCallback(introduceListenableFuture, "推送佣金通知成功",
				"推送佣金通知失败");
		List<TMallOrderDetail> details = orderDao
				.findOrderDetailByOrderId(orderId);// 根据订单编号获取所有详情
		for (TMallOrderDetail detail : details) {
			detail.setDetailStatus(OrderDetailStatus.SEND.getCode());// 已完成支付待发货
			orderDao.saveOrUpdateOneOrderDetail(detail);
		}

	}

	public Map<String, String> candelOrder(Long orderId, String reason) {
		TMallOrder order = orderDao.findById(orderId);
		Map<String, String> result = new HashMap<String, String>();
		if (order != null) {
			// 订单只有在已支付状态下且没有部分删除，且不是完成的订单才能进行取消
			if (order.getOrderStatus() == OrderStatus.PAID.getCode()) {
				order.setOrderStatus(OrderStatus.CANCEL.getCode());
				order.setOrderProcessTime(new Date());
				orderDao.saveOrUpdateOrder(order);// 更新订单状态
				// 添加取消记录
				TMallOrderCancel orderCancel = new TMallOrderCancel();
				orderCancel.setCancelReason(reason);
				orderCancel.setOrderId(orderId);
				orderCancel.setRequestTime(new Date());
				// orderCancel.setStatus(status)
				orderDao.saveOrUpdateOrderCancel(orderCancel);
				// 更新订单里商品的的数量--到时如果有申请流程再进行处理
				List<TMallOrderDetail> listDetail = orderDao
						.findOrderDetailByOrderId(orderId);
				for (int i = 0; i < listDetail.size(); i++) {
					TMallOrderDetail detail = listDetail.get(i);
					// 找出订单详情列表里对应商品的订单详情
					if (detail.getPropertyId() != 0) {// 有属性的
						goodsDao.updateGoodsPStock(
								detail.getPropertyTableName(),
								detail.getPropertyId(), detail.getQuantity(),
								false);
					} else { // 对于没有附加属性的商品，直接在商品表里更改库存
						goodsDao.updateGoodsStock(detail.getgId(),
								detail.getQuantity(), false);
					}
					// 更改订单中对应商品的订单详情状态为取消状态
					detail.setDetailStatus(OrderDetailStatus.CANCEL.getCode());
					orderDao.saveOrUpdateOneOrderDetail(detail);// 更新状态
				}
				// 返回值
				result.put("status", "0");
				result.put("msg", "订单取消成功");
				return result;
			}
		}
		result.put("status", "1");
		result.put("msg", "取消定单失败");
		return result;
	}

	public List<Map<String, Object>> getReturnListByUserId(long userId) {
		return orderDao.getReturnListByUserId(userId);
	}

	public Map<String, Object> getReturnDetailId(long rId) {
		return orderDao.getReturnDetailId(rId);
	}

	/**
	 * 获取订单详情数据
	 */
	@Override
	public TMallOrderDetail findOrderDetailById(long id) {
		// TODO Auto-generated method stub
		return orderDao.findOrderDetailById(id);
	}

	/**
	 * 修改订单详情
	 */
	@Override
	public void saveOrUpdateOneOrderDetail(TMallOrderDetail orderDetail) {
		// TODO Auto-generated method stub
		orderDao.saveOrUpdateOneOrderDetail(orderDetail);
	}

	/**
	 * 获取用户所有订单数据
	 */
	@Override
	public List<Map<String, Object>> getAllOrderList(long userId) {
		return orderDao.OrderListByStatus(userId);
	}

	public boolean ajaxConfirmGoodDetail(long detailId) {
		return orderDao.ajaxConfirmGoodDetail(detailId);
	}

	/**
	 * 获取所有待收货订单
	 */
	@Override
	public List<TMallOrderDetail> findOrderDetailsByStatus(
			OrderDetailStatus status) {
		return orderDao.findOrderDetailsByStatus(status);
	}

	/**
	 * 把订单详情的状态更新为status状态
	 * 
	 * @param id
	 *            - 订单详情的id
	 * @param status
	 *            - 更新后的状态
	 */
	@Override
	public void updateOrderDetailStatus(long id, OrderDetailStatus status) {
		orderDao.updateOrderDetailStatus(id, status);
	}

	/**
	 * 获取所有状态处于status的订单
	 * 
	 * @param status
	 * @return
	 */
	@Override
	public List<TMallOrder> getOrdersByStatus(OrderStatus status) {
		return orderDao.getOrdersByStatus(status);
	}

	/**
	 * 根据订单号获取所属的订单详情列表
	 */
	@Override
	public List<TMallOrderDetail> findOrderDetailsByOrderId(long id) {
		return orderDao.findOrderDetailByOrderId(id);
	}

	/**
	 * 通过订单编号获取订单数据
	 */
	@Override
	public TMallOrder findOrderByOrderId(String orderId) {
		// TODO Auto-generated method stub
		return orderDao.findOrderByOrderId(orderId);
	}
}
