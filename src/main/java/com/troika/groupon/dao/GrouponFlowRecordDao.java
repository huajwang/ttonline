package com.troika.groupon.dao;

import com.troika.groupon.model.TGroupFollowRecord;

public interface GrouponFlowRecordDao {
	/**
	 * 添加关注
	 * 
	 * @param project
	 * @return
	 */
	public TGroupFollowRecord saveOrUpdateRecord(TGroupFollowRecord record);

	/**
	 * 获取某个项目的关注数
	 * 
	 * @param projectId
	 * @return
	 */
	public int getRecordCount(Integer projectId);

	/**
	 * 判断用户是否已经关注过
	 * 
	 * @param userId
	 * @param projectId
	 * @return
	 */
	public TGroupFollowRecord getRecord(Integer userId, Integer projectId);
}
