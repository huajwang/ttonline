package com.troika.groupon.dao;

import java.util.List;
import java.util.Map;

import com.troika.groupon.model.TGrouponProject;

public interface GroupProjectDao {
	/**
	 * 添加项目
	 * 
	 * @param project
	 * @return
	 */
	public TGrouponProject saveOrUpdateProject(TGrouponProject project);

	/**
	 * 删除项目
	 * 
	 * @param projectId
	 */
	public void deleteProject(Integer projectId);

	/**
	 * 根据id获取项目
	 * 
	 * @param id
	 * @return
	 */
	public TGrouponProject findProjectById(Integer id);

	/**
	 * 根据id获取项目-map
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> findProjectMapById(Integer id);

	/**
	 * 所有项目信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findAllProject();

	/**
	 * 获取各个状态的项目列表数据
	 * 
	 * @param user
	 * @param status
	 * @return
	 */
	public List<Map<String, Object>> findProjectByStatus(Integer userId,
			Integer status);

	/**
	 * 获取最新的项目
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findNewProjectByList();

	/**
	 * 获取精选项目列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> findSelectedProjectByList();
	
	/**
	 * 通过发起人ID获取项目列表
	 * @param createUser
	 * @return
	 */
	public List<Map<String, Object>> findProjectByCreateUser(Integer createUser);
}
