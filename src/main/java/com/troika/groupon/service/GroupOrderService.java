package com.troika.groupon.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.troika.groupon.model.TGrouponOrder;

public interface GroupOrderService {
	/**
	 * 添加订单
	 * 
	 * @param orderNO
	 * @param projectId
	 * @param amount
	 * @param userId
	 * @param remark
	 * @param status
	 * @param Details
	 * @return
	 */
	public TGrouponOrder addOrder(Integer projectId, BigDecimal amount,
			Integer userId, String remark, Integer status,
			List<Map<String, Object>> Details, int addrId);

	/**
	 * 修改订单状态
	 * 
	 * @param Id
	 * @param status
	 * @return
	 */
	public boolean updOrderStatus(Integer Id, Integer status);

	/**
	 * 根据订单状态获取用户的订单
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	public List<Map<String, Object>> getOrderListByUser(long userId, int status);

	/**
	 * 根据订单id添加退货原因
	 * 
	 * @param id
	 * @param reason
	 * @return
	 */
	public boolean addOrderReason(Integer id, String reason);

	/**
	 * 获取订单数据
	 * 
	 * @param id
	 * @return
	 */
	public TGrouponOrder findOrderById(Integer id);

	/**
	 * 修改订单数据
	 * 
	 * @param order
	 * @return
	 */
	public TGrouponOrder saveOrUpdate(TGrouponOrder order);

	/**
	 * 获取产品剩余数量
	 * 
	 * @param ProductId
	 * @return
	 */
	public Map<String, Object> getProductSurplus(Integer ProductId);

	/**
	 * 通过订单编号获取订单数据
	 */
	public TGrouponOrder findOrderByOrderNo(String orderNo);

}
