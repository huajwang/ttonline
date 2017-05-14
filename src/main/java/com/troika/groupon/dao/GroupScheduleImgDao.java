package com.troika.groupon.dao;

import java.util.List;

import com.troika.groupon.model.TGrouponScheduleImg;

public interface GroupScheduleImgDao {
	/**
	 * 添加进度图片
	 * 
	 * @param schedule
	 * @return
	 */
	public void saveOrUpdateProject(List<TGrouponScheduleImg> record);

	/**
	 * 获取进度图片数据
	 * 
	 * @param scheduleId
	 * @return
	 */
	public List<TGrouponScheduleImg> getScheduleImg(Integer scheduleId);

}
