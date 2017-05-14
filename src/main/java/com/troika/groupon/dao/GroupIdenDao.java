package com.troika.groupon.dao;

import java.util.List;

import com.troika.groupon.model.TGrouponIdentification;

public interface GroupIdenDao {
	public TGrouponIdentification saveOrUpdate(TGrouponIdentification iden);
	public TGrouponIdentification findById(Integer userId);
	/**
	 * 根据审核状态获取数据
	 * @param status
	 * @return
	 */
	public List<TGrouponIdentification> findByStatus(String status);
}
