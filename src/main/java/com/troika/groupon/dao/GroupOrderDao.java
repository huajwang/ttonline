package com.troika.groupon.dao;

import java.util.List;
import java.util.Map;

import com.troika.groupon.model.TGrouponOrder;
import com.troika.groupon.model.TGrouponOrderDetails;

public interface GroupOrderDao {
	public TGrouponOrder saveOrUpdate(TGrouponOrder order);

	public TGrouponOrder findTGrouponOrderById(Integer id);

	public void saveOrUpdateDetails(List<TGrouponOrderDetails> details);

	public void updOrderStatus(Integer Id, Integer status);

	public List<Map<String, Object>> getOrderListByProjectId(Integer projectId);

	public List<Map<String, Object>> getOrderListByUser(long userId, int status);

	// 获取产品剩余数量
	public Map<String, Object> getProductSurplus(Integer ProductId);
	/**
	 * 通过订单主键获取项目的发起人
	 * @param id
	 * @return
	 */
	public Map<String,Object> findCreatedUserByOrder(Integer id);
	/**
	 * 通过订单编号获取订单数据
	 * @param id
	 * @return
	 */
	public TGrouponOrder findOrderByOrderNo(String orderNo);

}
