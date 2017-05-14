package com.troika.groupon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.troika.groupon.dao.GroupOrderDao;
import com.troika.groupon.model.TGrouponOrder;
import com.troika.groupon.model.TGrouponOrderDetails;
import com.troika.groupon.service.GroupOrderService;
import com.troika.groupon.util.CommonUtil;

@Component
@Transactional
public class GroupOrderServiceImpl implements GroupOrderService {
	public Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private GroupOrderDao groupOrderDao;

	public synchronized TGrouponOrder addOrder(Integer projectId,
			BigDecimal amount, Integer userId, String remark, Integer status,
			List<Map<String, Object>> Details, int addrId) {
		logger.info("添加订单，用户ID【" + userId + "】");
		TGrouponOrder order = new TGrouponOrder();
		try {
			String orderNO = CommonUtil.buildOrderId(userId);// 订单编号
			order.setOrderno(orderNO);
			order.setProjectid(projectId);
			order.setAmount(amount);
			order.setUserid(userId);
			order.setRemark(remark);
			order.setStatus(status.toString());
			order.setCreatetime(new Date());
			order.setAddrId(addrId);// 收货地址
			order = groupOrderDao.saveOrUpdate(order);
			Integer orderId = order.getId();
			List<TGrouponOrderDetails> details = new ArrayList<TGrouponOrderDetails>();
			for (Map<String, Object> map : Details) {
				int productId = Integer
						.valueOf(map.get("productId").toString());
				int num = Integer.valueOf(map.get("num").toString());
				BigDecimal price = new BigDecimal(map.get("price").toString());
				String unit = map.get("unit").toString();
				TGrouponOrderDetails detail = new TGrouponOrderDetails();
				detail.setOrderid(orderId);
				detail.setProductid(productId);
				detail.setPrice(price);
				detail.setNum(num);
				detail.setUnit(unit);
				detail.setCreatetime(new Date());
				details.add(detail);
			}
			groupOrderDao.saveOrUpdateDetails(details);
		} catch (Exception e) {
			logger.error("用户【" + userId + "】添加项目【" + projectId + "】定单失败："
					+ e.getMessage());
			return null;
		}
		return order;
	}

	public boolean updOrderStatus(Integer Id, Integer status) {
		try {
			groupOrderDao.updOrderStatus(Id, status);
		} catch (Exception e) {
			logger.error("更新订单【" + Id + "】状态失败：" + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> getOrderListByUser(long userId, int status) {
		return groupOrderDao.getOrderListByUser(userId, status);
	}

	@Override
	public boolean addOrderReason(Integer id, String reason) {
		TGrouponOrder order = groupOrderDao.findTGrouponOrderById(id);
		order.setReason(reason);
		groupOrderDao.saveOrUpdate(order);
		return true;
	}

	/**
	 * 获取订单数据
	 */
	@Override
	public TGrouponOrder findOrderById(Integer id) {
		// TODO Auto-generated method stub
		return groupOrderDao.findTGrouponOrderById(id);
	}

	/**
	 * 修改订单数据
	 */
	@Override
	public TGrouponOrder saveOrUpdate(TGrouponOrder order) {
		// TODO Auto-generated method stub
		return groupOrderDao.saveOrUpdate(order);
	}

	/**
	 * 获取产品剩余数量
	 */
	@Override
	public Map<String, Object> getProductSurplus(Integer ProductId) {
		// TODO Auto-generated method stub
		return groupOrderDao.getProductSurplus(ProductId);
	}

	/**
	 * 通过订单编号获取订单数据
	 */
	@Override
	public TGrouponOrder findOrderByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		return groupOrderDao.findOrderByOrderNo(orderNo);
	}
}
