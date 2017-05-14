package com.troika.emall.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.troika.emall.common.OrderDetailStatus;
import com.troika.emall.common.OrderStatus;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallOrderDetail;
import com.troika.emall.model.TMallReturnRecord;

public interface OrderProcess {
	public Map<String, Object> buildOrder(long userId, long addrId,
			String orderDetail, String orderSource, BigDecimal amount,
			String remark, Long introducer, Long cartId);

	public Map<String, Object> deleteOrderById(long id);

	public Map<Long, List<Map<String, Object>>> getOrderListByUserId(long userId);

	public Map<String, Object> getOrderDetailById(long orderId);

	public List<Map<String, Object>> getOrderSingleDetailById(long orderId);

	public TMallOrder getOrderInfoById(long orderId);

	public Map<String, Object> getAddressInfoByOrderId(long orderId);

	public Map<Long, List<Map<String, Object>>> getWaitPayOrderList(long userId);

	public Map<Long, List<Map<String, Object>>> getWaitSendOrderList(long userId);

	public Map<Long, List<Map<String, Object>>> getWaitGetOrderList(long userId);

	public Map<Long, List<Map<String, Object>>> getDoneOrderList(long userId);

	public Map<Long, List<Map<String, Object>>> getWaitEvalList(long userId);

	public List<Map<String, Object>> WaitPayOrderList(long userId);

	public List<Map<String, Object>> WaitSendOrderList(long userId);

	public List<Map<String, Object>> WaitGetOrderList(long userId);

	public List<Map<String, Object>> DoneOrderList(long userId);

	public List<Map<String, Object>> WaitEvalList(long userId);

	public Map<String, String> requestReturnOrder(Long orderId, Long gId,
			Long propertyId, String imgUrls, String reason);

	public Map<String, String> requestReturnOrderObject(String imgUrls,
			TMallReturnRecord record);

	public Map<String, String> candelOrder(Long orderId, String reason);

	public List<Map<String, Object>> getReturnListByUserId(long userId);// 退货记录

	public Map<String, Object> getReturnDetailId(long rId);// 退货记录详情

	public boolean ajaxConfirmGoodDetail(long detailId);// 确认收货

	/**
	 * 保存订单
	 * 
	 * @param record
	 * @return
	 */
	public TMallOrder saveOrUpdateOrder(TMallOrder record);

	/**
	 * 保存订单详情
	 * 
	 * @param orderDetailList
	 */
	public void saveOrUpdateOrderDetail(List<TMallOrderDetail> orderDetailList);

	/**
	 * 根据订单id更新订单状态为status。同时更新订单的处理时间orderProcessTime
	 * 
	 * @param id
	 *            订单号
	 * @param status
	 *            订单状态
	 */
	public void updateOrderStatus(long id, String status);

	/**
	 * 订单付款后的操作
	 * 
	 * @param id
	 */
	public void updateOrderWithPaid(String orderId);

	/**
	 * 获取订单详情数据
	 * 
	 * @param id
	 *            订单详情主键
	 * @return
	 */
	public TMallOrderDetail findOrderDetailById(long id);

	/**
	 * 修改订单详情
	 * 
	 * @param orderDetail
	 */
	public void saveOrUpdateOneOrderDetail(TMallOrderDetail orderDetail);

	/**
	 * 获取用户订单数据
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getAllOrderList(long userId);

	/**
	 * 获取所有用户的待收货订单详情列表
	 * 
	 * @return
	 */
	List<TMallOrderDetail> findOrderDetailsByStatus(OrderDetailStatus status);

	/**
	 * 把订单详情的状态更新为status状态
	 * 
	 * @param id
	 *            - 订单详情的id
	 * @param status
	 *            - 更新后的状态
	 */
	public void updateOrderDetailStatus(long id, OrderDetailStatus status);

	/**
	 * 获取所有状态处于status的订单
	 * 
	 * @param status
	 * @return
	 */
	public List<TMallOrder> getOrdersByStatus(OrderStatus status);

	public List<TMallOrderDetail> findOrderDetailsByOrderId(long id);

	/**
	 * 通过订单编号获取订单数据
	 * 
	 * @param orderId
	 * @return
	 */
	public TMallOrder findOrderByOrderId(String orderId);
}
