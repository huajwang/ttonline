package com.troika.emall.dao;

import java.util.List;
import java.util.Map;

import com.troika.emall.common.OrderDetailStatus;
import com.troika.emall.common.OrderStatus;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallOrderCancel;
import com.troika.emall.model.TMallOrderDetail;

public interface OrderDao {
	public TMallOrder saveOrUpdateOrder(TMallOrder order);
	public void saveOrUpdateOrderDetail(List<TMallOrderDetail> orderDetailList);
	public TMallOrder findByIdAndStatus(long id,String status);
	public TMallOrder findById(long id);
	public List<TMallOrderDetail> findOrderDetailByOrderId(long orderId);
	public void deleteOrderDetail(List<TMallOrderDetail> list);
	public void deleteOrder(long id);
	public Map<Long,List<Map<String,Object>>> getOrderListByUserId(long userId);
	public List<Map<String,Object>> getOrderDetailById(long orderId);
	public void updateStatusByOrderId(String orderId,String status);
	public Map<Long,List<Map<String,Object>>> getOrderListByStatus(long userId,OrderStatus orderStatus,OrderDetailStatus orderDetailStatus);
	public Map<Long,List<Map<String,Object>>> getWaitEvalList(long userId);
	public List<TMallOrder> findAllWaitPay();
	public List<Map<String,Object>> getSupplierByOrderId(String orderId);
	public List<Map<String,Object>> OrderListByStatus(long userId,OrderStatus orderStatus,OrderDetailStatus orderDetailStatus);
	public List<Map<String, Object>> OrderListByStatus(long userId,OrderStatus orderStatus,OrderDetailStatus orderDetailStatus,Integer type);
	public List<Map<String, Object>> OrderListByStatus(long userId);
	public void saveOrUpdateOneOrderDetail(TMallOrderDetail orderDetail);
	public void updateOrderStatus(long id,String status);
	public List<TMallOrderDetail> findOrderDetailByOrderId(String orderId);//根据订单编号
	public TMallOrderCancel saveOrUpdateOrderCancel(TMallOrderCancel order);
	public List<Map<String, Object>> getReturnListByUserId(long userId);
	public Map<String, Object> getReturnDetailId(long rId);
	public boolean ajaxConfirmGoodDetail(long detailId);
	public TMallOrderDetail findOrderDetailByoIdAndGid(long oId,long gId);
	public TMallOrder findOrderByOrderId(String orderId);
	/**
	 * 获取订单详情数据
	 * @param id 订单详情主键
	 * @return
	 */
	public TMallOrderDetail findOrderDetailById(long id);
	/**
	 * 获取所有用户的处于orderDetailStatus的订单详情
	 * @return
	 */
	List<TMallOrderDetail> findOrderDetailsByStatus(OrderDetailStatus orderDetailStatus);
	public void updateOrderDetailStatus(long id, OrderDetailStatus status);
	public List<TMallOrder> getOrdersByStatus(OrderStatus status);
	
	public Map<String, Object> findOrderDetailByOrderIdAndOpenId(String orderId,String openId);
}
