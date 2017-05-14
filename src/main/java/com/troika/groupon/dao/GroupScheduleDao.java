package com.troika.groupon.dao;

import java.util.List;
import java.util.Map;

import com.troika.groupon.model.TGrouponSchedule;

public interface GroupScheduleDao {
	/**
	 * 添加进度
	 * @param schedule
	 * @return
	 */
	public TGrouponSchedule saveOrUpdateProject(TGrouponSchedule schedule);
	/**
	 * 获取进度
	 * @param projectId
	 * @return
	 */
	public List<Map<String,Object>> findAllByProjectId(Integer projectId);
}
