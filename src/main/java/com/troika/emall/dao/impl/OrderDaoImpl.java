package com.troika.emall.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.common.GoodsReturnStatus;
import com.troika.emall.common.OrderDetailStatus;
import com.troika.emall.common.OrderStatus;
import com.troika.emall.dao.GoodsDao;
import com.troika.emall.dao.OrderDao;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallOrderCancel;
import com.troika.emall.model.TMallOrderDetail;
import com.troika.emall.util.Constant;

@Repository
public class OrderDaoImpl implements OrderDao {
	public Logger logger = LogManager.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private GoodsDao goodsDao;
	private Set<String> noSet = null;// 一些不用的字段

	public OrderDaoImpl() {
		noSet = new HashSet<String>();
		noSet.add("id");
		noSet.add("gId");
		noSet.add("stock");
		noSet.add("sold");
	}

	@Override
	@Transactional
	public TMallOrder saveOrUpdateOrder(TMallOrder order) {
		return entityManager.merge(order);
	}

	@Override
	@Transactional
	public TMallOrderCancel saveOrUpdateOrderCancel(TMallOrderCancel order) {
		return entityManager.merge(order);
	}

	@Override
	@Transactional
	public void saveOrUpdateOrderDetail(List<TMallOrderDetail> orderDetailList) {
		for (int i = 0; i < orderDetailList.size(); i++) {
			TMallOrderDetail orderDetail = orderDetailList.get(i);
			entityManager.persist(orderDetail);
			// if (i % 10 == 0) {
			// entityManager.flush();
			// // entityManager.clear();
			// }
		}
	}

	@Transactional
	public void saveOrUpdateOneOrderDetail(TMallOrderDetail orderDetail) {
		entityManager.merge(orderDetail);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public TMallOrder findByIdAndStatus(long id, String status) {
		Query query = entityManager
				.createQuery("select t from TMallOrder t where t.id=? and t.orderStatus=?");
		query.setParameter(1, id);
		query.setParameter(2, status);
		List<TMallOrder> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Transactional(readOnly = true)
	@Override
	public TMallOrder findById(long id) {
		Query query = entityManager
				.createQuery("select t from TMallOrder t where t.id=?");
		query.setParameter(1, id);
		List<TMallOrder> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TMallOrderDetail> findOrderDetailByOrderId(long orderId) {
		Query query = entityManager
				.createQuery("select t from TMallOrderDetail t where t.orderId=?");
		query.setParameter(1, orderId);
		List<TMallOrderDetail> list = query.getResultList();
		return list;
	}

	@Transactional(readOnly = true)
	public List<TMallOrderDetail> findOrderDetailByOrderId(String orderId) {
		Query query = entityManager
				.createQuery("select t from TMallOrderDetail t,TMallOrder o where t.orderId=o.id and o.orderId=?");
		query.setParameter(1, orderId);
		List<TMallOrderDetail> list = query.getResultList();
		return list;
	}

	/**
	 * 在规定时间内没有完成支付,根据订单id删除订单详情表里相应的子订单
	 */
	@Transactional
	public void deleteOrderDetail(List<TMallOrderDetail> list) {
		for (TMallOrderDetail detail : list) {
			entityManager
					.createQuery("delete from TMallOrderDetail t where t.id=?")
					.setParameter(1, detail.getId()).executeUpdate();
		}
	}

	/**
	 * 在规定时间内没有完成支付,物理删除订单
	 * 
	 * @param id
	 *            - 取消的订单id
	 */
	@Transactional
	public void deleteOrder(long id) {
		// 首先删除订单详情表里的子订单
		deleteOrderDetail(findOrderDetailByOrderId(id));
		// 再删除订单表里的总订单
		String sql = "delete from t_mall_order where id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, id);
		query.executeUpdate();
		// String sql =
		// "update t_mall_order t set t.orderStatus=?,t.orderProcessTime=? where t.id=?";
		// Query query = entityManager.createNativeQuery(sql);
		// query.setParameter(1, OrderStatus.DELETE.getCode());
		// query.setParameter(2, new Date());
		// query.setParameter(3, id);
		// query.executeUpdate();

	}

	/**
	 * 根据订单id更新订单状态为status。同时更新订单的处理时间orderProcessTime
	 */
	@Transactional
	public void updateOrderStatus(long id, String status) {
		String sql = "update t_mall_order t set t.orderStatus=?,t.orderProcessTime=? where t.id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, status);
		query.setParameter(2, new Date());
		query.setParameter(3, id);
		logger.debug("即将执行 update t_mall_order t set t.orderStatus = " + status
				+ ", t.orderProcessTime = ?" + " where t.id = " + id);
		query.executeUpdate();
		logger.debug("执行数据库更新完成");
	}

	/**
	 * 获取所有订单-去除已评价商品
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<Long, List<Map<String, Object>>> getOrderListByUserId(long userId) {// price都改成订单里的
		Query query = entityManager
				.createNativeQuery("select concat(c.id,'') as oId,c.orderId,c.amount,g.id,g.gName,g.iconUrl,cd.price,g.inPrice,g.upForSale,cd.quantity,"
						+ "cd.propertyTableName,cd.propertyId from t_mall_order c "
						+ " left join t_mall_order_detail cd on c.id=cd.orderId "
						+ " left join t_mall_goods g on cd.gId=g.id where cd.detailStatus !='7' and c.orderStatus !='-1' and c.userId=?");
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		Map<Long, List<Map<String, Object>>> result = new HashMap<Long, List<Map<String, Object>>>();
		for (Map<String, Object> map : list) {
			Object iconUrl = map.get("iconUrl");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
			}
			Object propertyName = map.get("propertyTableName");
			if (propertyName != null && !"".equals(propertyName.toString())) {
				Object propertyId = map.get("propertyId");
				long id = Long.parseLong(propertyId.toString());
				Map<String, Object> gProMap = goodsDao
						.getPropertyByPropertyNameAndId(
								propertyName.toString(), id);
				Iterator<String> it = noSet.iterator();
				while (it.hasNext()) {
					gProMap.remove(it.next());
				}
				map.put("goodsProperty", gProMap);
			}
			String oId = (String) map.get("oId");
			Long oidL = Long.parseLong(oId);
			if (result.containsKey(oidL)) {
				List<Map<String, Object>> tempList = result.get(oidL);
				tempList.add(map);
			} else {
				List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
				tempList.add(map);
				result.put(oidL, tempList);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getOrderDetailById(long orderId) {
		Query query = entityManager
				.createNativeQuery("select concat(c.id,'') as oId,c.orderId,g.id,g.gName,g.iconUrl,cd.price,g.inPrice,g.upForSale,cd.quantity,"
						+ "cd.propertyTableName,cd.propertyId,cd.detailStatus from t_mall_order c "
						+ " left join t_mall_order_detail cd on c.id=cd.orderId "
						+ " left join t_mall_goods g on cd.gId=g.id where c.id=?");// 去掉c.orderStatus='0'
																					// and
		query.setParameter(1, orderId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		for (Map<String, Object> map : list) {
			Object propertyName = map.get("propertyTableName");
			if (propertyName != null && !"".equals(propertyName.toString())) {
				Object propertyId = map.get("propertyId");
				long id = Long.parseLong(propertyId.toString());
				Map<String, Object> gProMap = goodsDao
						.getPropertyByPropertyNameAndId(
								propertyName.toString(), id);
				Iterator<String> it = noSet.iterator();
				while (it.hasNext()) {
					gProMap.remove(it.next());
				}
				map.put("goodsProperty", gProMap);
			}
			Object iconUrl = map.get("iconUrl");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
			}
		}
		return list;
	}

	@Transactional
	public void updateStatusByOrderId(String orderId, String status) {
		String sql = "update t_mall_order set orderStatus=?,orderProcessTime=? where orderId=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, status);
		query.setParameter(2, new Date());
		query.setParameter(3, orderId);
		query.executeUpdate();
	}

	@Transactional(readOnly = true)
	public Map<Long, List<Map<String, Object>>> getOrderListByStatus(
			long userId, OrderStatus orderStatus,
			OrderDetailStatus orderDetailStatus) {
		Query query = entityManager
				.createNativeQuery("select concat(c.id,'') as oId,cd.id as detailId,c.orderId,c.amount,g.id,g.gName,g.iconUrl,cd.price,g.inPrice,g.upForSale,cd.quantity,"
						+ "cd.propertyTableName,cd.propertyId,cd.logisticsNo from t_mall_order c "
						+ " left join t_mall_order_detail cd on (c.id=cd.orderId and cd.detailStatus=?) "
						+ " left join t_mall_goods g on cd.gId=g.id where cd.detailStatus is not null and c.orderStatus=? and c.userId=?");
		query.setParameter(1, orderDetailStatus.getCode());
		query.setParameter(2, orderStatus.getCode());
		query.setParameter(3, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		Map<Long, List<Map<String, Object>>> result = new HashMap<Long, List<Map<String, Object>>>();
		for (Map<String, Object> map : list) {
			Object iconUrl = map.get("iconUrl");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
			}
			Object propertyName = map.get("propertyTableName");
			if (propertyName != null && !"".equals(propertyName.toString())) {
				Object propertyId = map.get("propertyId");
				long id = Long.parseLong(propertyId.toString());
				Map<String, Object> gProMap = goodsDao
						.getPropertyByPropertyNameAndId(
								propertyName.toString(), id);
				Iterator<String> it = noSet.iterator();
				while (it.hasNext()) {
					gProMap.remove(it.next());
				}
				map.put("goodsProperty", gProMap);
			}
			String oId = (String) map.get("oId");
			Long oidL = Long.parseLong(oId);
			if (result.containsKey(oidL)) {
				List<Map<String, Object>> tempList = result.get(oidL);
				tempList.add(map);
			} else {
				List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
				tempList.add(map);
				result.put(oidL, tempList);
			}
		}
		return result;
	}

	/**
	 * 待评介
	 */
	@Transactional(readOnly = true)
	public Map<Long, List<Map<String, Object>>> getWaitEvalList(long userId) {
		Query query = entityManager
				.createNativeQuery("select concat(c.id,'') as oId,c.orderId,c.amount,g.id,g.gName,g.iconUrl,cd.price,g.inPrice,g.upForSale,cd.quantity,"
						+ "cd.propertyTableName,cd.propertyId,cd.logisticsNo from t_mall_order c "
						+ " left join t_mall_order_detail cd on (c.id=cd.orderId and cd.detailStatus=?) "
						+ " left join t_mall_goods g on cd.gId=g.id "
						+ " where c.orderStatus in (?,?) and c.userId=?");
		query.setParameter(1, OrderDetailStatus.EVALUATE.getCode());
		query.setParameter(2, OrderStatus.FINISH.getCode());
		query.setParameter(3, OrderStatus.PAID.getCode());
		query.setParameter(4, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		Map<Long, List<Map<String, Object>>> result = new HashMap<Long, List<Map<String, Object>>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Object iconUrl = map.get("iconUrl");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
			}
			Object propertyName = map.get("propertyTableName");
			if (propertyName != null && !"".equals(propertyName.toString())) {
				Object propertyId = map.get("propertyId");
				long id = Long.parseLong(propertyId.toString());
				Map<String, Object> gProMap = goodsDao
						.getPropertyByPropertyNameAndId(
								propertyName.toString(), id);
				Iterator<String> it = noSet.iterator();
				while (it.hasNext()) {
					gProMap.remove(it.next());
				}
				map.put("goodsProperty", gProMap);
			}
			String oId = (String) map.get("oId");
			Long oidL = Long.parseLong(oId);
			if (result.containsKey(oidL)) {
				List<Map<String, Object>> tempList = result.get(oidL);
				tempList.add(map);
			} else {
				List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
				tempList.add(map);
				result.put(oidL, tempList);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<TMallOrder> findAllWaitPay() {
		Query query = entityManager
				.createQuery("select t from TMallOrder t where t.orderStatus=?");
		query.setParameter(1, OrderStatus.ACTIVE.getCode());
		List<TMallOrder> list = query.getResultList();
		return list;
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getSupplierByOrderId(String orderId) {
		Query query = entityManager
				.createNativeQuery("select s.* from t_mall_order c "
						+ " left join t_mall_order_detail cd on c.id=cd.orderId "
						+ " left join t_mall_goods g on cd.gId=g.id "
						+ " left join t_mall_supplier_detail s on s.id=g.supplierId "
						+ " where c.orderId=? " + " group by s.id");
		query.setParameter(1, orderId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> OrderListByStatus(long userId,
			OrderStatus orderStatus, OrderDetailStatus orderDetailStatus) {
		return OrderListByStatus(userId, orderStatus, orderDetailStatus, null);
	}

	/**
	 * 订单各个状态详细记录
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public List<Map<String, Object>> OrderListByStatus(long userId,
			OrderStatus orderStatus, OrderDetailStatus orderDetailStatus,
			Integer type) {
		String sql = "select t from TMallOrder t where t.userId=? and t.orderStatus=? order by t.createTime desc";
		if (type != null && type == 1) {
			sql = "select t from TMallOrder t where t.userId=? and t.orderStatus in (?,?) order by t.createTime desc";
		}
		Query query = entityManager.createQuery(sql);
		query.setParameter(1, userId);
		query.setParameter(2, orderStatus.getCode());
		if (type != null && type == 1) {
			query.setParameter(3, OrderStatus.PAID.getCode());
		}
		List<TMallOrder> list = query.getResultList();
		// 获取订单数据
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (TMallOrder order : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			long id = order.getId();
			map.put("id", id);
			map.put("orderId", order.getOrderId());
			map.put("amount", order.getAmount());
			map.put("createTime", order.getCreateTime());
			Query query1 = entityManager
					.createNativeQuery("select a.streetAddr,a.contactName,a.phone,g.id,g.gName,g.iconUrl,cd.price,g.upForSale,cd.quantity,cd.propertyTableName,cd.logisticsNo,"
							+ "cd.propertyId,cd.id as detailId from t_mall_order_detail cd "
							+ " left join t_mall_goods g on cd.gId=g.id"
							+ " left join t_mall_order o on cd.orderId = o.id"
							+ " left join t_mall_address a on a.id = o.addrId"
							+ " where cd.detailStatus=? and cd.orderId=?");
			query1.setParameter(1, orderDetailStatus.getCode());
			query1.setParameter(2, id);
			query1.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			// 获取订单详情数据
			List<Map<String, Object>> details = query1.getResultList();
			for (Map<String, Object> m : details) {
				Object iconUrl = m.get("iconUrl");
				if (iconUrl != null && !"".equals(iconUrl)) {
					m.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
				}
				Object propertyName = m.get("propertyTableName");// 属性表
				// 判断属性表 获取属性表数据
				if (propertyName != null && !"".equals(propertyName.toString())) {
					Object propertyId = m.get("propertyId");
					long _propertyId = Long.parseLong(propertyId.toString());
					Map<String, Object> gProMap = goodsDao
							.getPropertyByPropertyNameAndId(
									propertyName.toString(), _propertyId);
					Iterator<String> it = noSet.iterator();
					while (it.hasNext()) {
						gProMap.remove(it.next());
					}
					m.put("goodsProperty", gProMap);
				}
			}
			// 判断订单详情是否有数据,如果有数据返回
			if (details.size() > 0) {
				map.put("details", details);
				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 订单各个状态详细记录
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> OrderListByStatus(long userId) {
		String sql = "select t from TMallOrder t where t.userId=?  order by t.createTime desc";
		Query query = entityManager.createQuery(sql);
		query.setParameter(1, userId);
		List<TMallOrder> list = query.getResultList();
		// 获取订单数据
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (TMallOrder order : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			long id = order.getId();
			map.put("id", id);
			map.put("orderId", order.getOrderId());
			map.put("amount", order.getAmount());
			map.put("createTime", order.getCreateTime());
			Query query1 = entityManager
					.createNativeQuery("select g.id,g.gName,g.iconUrl,cd.price,g.upForSale,cd.quantity,cd.detailStatus,cd.propertyTableName,cd.logisticsNo,"
							+ "cd.propertyId,cd.id as detailId from t_mall_order_detail cd left join t_mall_goods g on cd.gId=g.id"
							+ " where cd.orderId=?");
			query1.setParameter(1, id);
			query1.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			// 获取订单详情数据
			List<Map<String, Object>> details = query1.getResultList();
			for (Map<String, Object> m : details) {
				Object iconUrl = m.get("iconUrl");
				if (iconUrl != null && !"".equals(iconUrl)) {
					m.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
				}
				Object propertyName = m.get("propertyTableName");// 属性表
				if (propertyName != null && !"".equals(propertyName.toString())) {
					Object propertyId = m.get("propertyId");
					long _propertyId = Long.parseLong(propertyId.toString());
					Map<String, Object> gProMap = goodsDao
							.getPropertyByPropertyNameAndId(
									propertyName.toString(), _propertyId);
					Iterator<String> it = noSet.iterator();
					while (it.hasNext()) {
						gProMap.remove(it.next());
					}
					m.put("goodsProperty", gProMap);
				}
			}
			// 判断订单详情数据是否存在
			if (details.size() > 0) {
				map.put("details", details);
				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 通过用户ID获取用户的退货列表
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getReturnListByUserId(long userId) {
		Query query = entityManager
				.createNativeQuery("select e.id,e.requestTime,e.processTime,e.cancelReason,e.status,e.resultExplain,g.id as gId,g.gName,g.iconUrl,d.propertyTableName,d.propertyId,o.amount from t_mall_return_record e"
						+ " left join t_mall_order_detail d on e.orderDetailId = d.id "
						+ " left join t_mall_goods g on g.id = d.gId "
						+ " left join t_mall_order o on d.orderId = o.id "
						+ " left join t_mall_user u on u.id = o.userId "
						+ " where u.id=?");
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		for (Map<String, Object> map : list) {
			String status = (String) map.get("status");
			for (GoodsReturnStatus item : GoodsReturnStatus.values()) {
				if (item.getCode().equals(status))
					map.put("status", item.getText());
			}
			Object iconUrl = map.get("iconUrl");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
			}
			Object propertyName = map.remove("propertyTableName");
			Object propertyId = map.remove("propertyId");
			if (propertyName != null && !"".equals(propertyName.toString())) {
				long id = Long.parseLong(propertyId.toString());
				Map<String, Object> gProMap = goodsDao
						.getPropertyByPropertyNameAndId(
								propertyName.toString(), id);
				Iterator<String> it = noSet.iterator();
				while (it.hasNext()) {
					gProMap.remove(it.next());
				}
				map.put("goodsProperty", gProMap);
			}
		}
		return list;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> getReturnDetailId(long rId) {
		Query query = entityManager
				.createNativeQuery("select e.id,e.requestTime,e.processTime,e.cancelReason,e.status,e.resultExplain,g.id as gId,g.gName,g.iconUrl,d.propertyTableName,d.propertyId,o.amount from t_mall_return_record e"
						+ " left join t_mall_order_detail d on e.orderDetailId = d.id "
						+ " left join t_mall_goods g on g.id = d.gId "
						+ " left join t_mall_order o on d.orderId = o.id "
						+ " where e.id=?");
		query.setParameter(1, rId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		Map<String, Object> map = null;
		if (list != null && list.size() > 0) {
			map = list.get(0);
		}
		if (map != null) {
			String status = (String) map.get("status");
			for (GoodsReturnStatus item : GoodsReturnStatus.values()) {
				if (item.getCode().equals(status))
					map.put("status", item.getText());
			}
			Object iconUrl = map.get("iconUrl");
			if (iconUrl != null && !"".equals(iconUrl)) {
				map.put("iconUrl", Constant.PHOTO_URL + iconUrl.toString());
			}
			Object propertyName = map.remove("propertyTableName");
			Object propertyId = map.remove("propertyId");
			if (propertyName != null && !"".equals(propertyName.toString())) {
				long id = Long.parseLong(propertyId.toString());
				Map<String, Object> gProMap = goodsDao
						.getPropertyByPropertyNameAndId(
								propertyName.toString(), id);
				Iterator<String> it = noSet.iterator();
				while (it.hasNext()) {
					gProMap.remove(it.next());
				}
				map.put("goodsProperty", gProMap);
			}
		} else {
			map = new HashMap<String, Object>();
		}
		return map;
	}

	/**
	 * 获取订单详情数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public TMallOrderDetail findOrderDetailById(long id) {
		Query query = entityManager
				.createQuery("select t from TMallOrderDetail t where t.id=?");
		query.setParameter(1, id);
		List<TMallOrderDetail> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Transactional
	public boolean ajaxConfirmGoodDetail(long detailId) {
		TMallOrderDetail detail = findOrderDetailById(detailId);
		if (detail != null) {
			detail.setDetailStatus(OrderDetailStatus.EVALUATE.getCode());// 确认收货
			saveOrUpdateOneOrderDetail(detail);
			return true;
		}
		return false;
	}

	@Transactional(readOnly = true)
	public TMallOrderDetail findOrderDetailByoIdAndGid(long oId, long gId) {
		Query query = entityManager
				.createQuery("select t from TMallOrderDetail t where t.orderId=? and t.gId=?");
		query.setParameter(1, oId);
		query.setParameter(2, gId);
		List<TMallOrderDetail> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 获取所有订单详情为orderDetailStatus的订单详情
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TMallOrderDetail> findOrderDetailsByStatus(
			OrderDetailStatus orderDetailStatus) {
		Query query = entityManager
				.createQuery("SELECT t FROM TMallOrderDetail t WHERE t.detailStatus = ?");
		query.setParameter(1, orderDetailStatus.getCode());
		List<TMallOrderDetail> list = query.getResultList();
		return list;
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
	@Transactional
	public void updateOrderDetailStatus(long id, OrderDetailStatus status) {
		String sql = "update t_mall_order_detail set detailStatus=? where id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, status.getCode());
		query.setParameter(2, id);
		query.executeUpdate();
	}

	/**
	 * 获取所有状态为status订单
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TMallOrder> getOrdersByStatus(OrderStatus status) {
		Query query = entityManager
				.createQuery("SELECT t FROM TMallOrder t WHERE t.orderStatus = ?");
		query.setParameter(1, status.getCode());
		List<TMallOrder> list = query.getResultList();
		return list;
	}

	@Override
	public Map<String, Object> findOrderDetailByOrderIdAndOpenId(String orderId, String openId) {
		Query query = entityManager
				.createNativeQuery("select d.*,g.gName,g.unit,u.phone,u.userName from t_mall_order_detail d "
						+" left join t_mall_order o on o.id=d.orderId "
						+" left join t_mall_user u on u.id=o.userId "
						+" left join t_mall_goods g on d.gId=g.id "
						+" left JOIN t_mall_supplier_detail s on g.supplierId = s.id "
						+" where o.orderId=? and s.openId = ?");
		query.setParameter(1, orderId);
		query.setParameter(2, openId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Transactional(readOnly = true)
	@Override
	public TMallOrder findOrderByOrderId(String orderId) {
			Query query = entityManager
					.createQuery("select t from TMallOrder t where t.orderId=?");
			query.setParameter(1, orderId);
			List<TMallOrder> list = query.getResultList();
			return list.size() > 0 ? list.get(0) : null;
	}
}
