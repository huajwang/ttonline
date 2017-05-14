package com.troika.groupon.dao;

import java.util.List;
import java.util.Map;

import com.troika.groupon.common.PatrolStatus;

public interface GroupImgPatrolDao {
	public List<Map<String, Object>> getImgsByStatus(PatrolStatus status);
}
