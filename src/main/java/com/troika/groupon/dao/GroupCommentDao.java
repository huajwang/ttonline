package com.troika.groupon.dao;

import java.util.List;
import java.util.Map;

import com.troika.groupon.model.TGrouponComment;

public interface GroupCommentDao {
	public TGrouponComment saveOrUpdate(TGrouponComment comment);
	public void delComment(Integer id);
	public List<Map<String,Object>> getComment(Integer projectId);
	public List<Map<String,Object>> getCommentByOrderId(Integer orderId);
	public List<Map<String,Object>> getCommentByScheduleId(Integer scheduleId);
}
