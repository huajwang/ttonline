package com.troika.groupon.dao;

import java.util.List;
import java.util.Map;

import com.troika.groupon.model.TGrouponSettlement;

public interface GrouponSettlementDao {
	/**
	 * 保存或修改数据
	 * 
	 * @param record
	 * @return
	 */
	public TGrouponSettlement saveOrUpdateRecord(TGrouponSettlement record);

	/**
	 * 获取结算数据
	 * 
	 * @param projectId
	 * @return
	 */
	public TGrouponSettlement getSettlement(Integer projectId, Integer status);

	/**
	 * 计算结算数据
	 * 
	 * @param projectId
	 * @return
	 */
	public Map<String, Object> setSettlement(Integer projectId);

	/**
	 * 获取结算数据
	 * 
	 * @param projectId
	 * @return
	 */
	public List<TGrouponSettlement> getSettlement(Integer projectId);

	/**
	 * 获取用户所有结算数据
	 * 
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getSettlementByUser(Integer userId);
	/**
	 * 判断项目是否在结算中或已经结算
	 * @param projectId
	 * @return
	 */
	public Boolean whetherSett(Integer projectId);
}
