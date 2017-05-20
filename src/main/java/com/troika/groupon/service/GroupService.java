package com.troika.groupon.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.troika.groupon.model.TGroupFollowRecord;
import com.troika.groupon.model.TGrouponIdentification;
import com.troika.groupon.model.TGrouponNews;
import com.troika.groupon.model.TGrouponProject;
import com.troika.groupon.model.TGrouponSettlement;

public interface GroupService {
	/**
	 * 添加项目
	 * 
	 * @param name
	 * @param createUser
	 * @param amount
	 * @param endTime
	 * @param sendTime
	 * @param EMS
	 * @param status
	 * @param remark
	 * @param Icon
	 * @param product
	 * @return
	 */
	public boolean addProject(String name, Integer createUser,
			BigDecimal amount, Date endTime, String sendTime, String EMS,
			Integer status, String remark, List<Map<String, Object>> Icon,
			List<Map<String, Object>> product);

	/**
	 * 项目项目
	 * 
	 * @param projectId
	 * @return
	 */
	public boolean delProject(Integer projectId);

	/**
	 * 删除图片信息
	 * 
	 * @param projectId
	 * @param Icon
	 * @return
	 */
	public boolean delProjectImg(Integer projectId, Integer Icon);

	/**
	 * 删除产品信息
	 * 
	 * @param productId
	 * @return
	 */
	public boolean delProduct(Integer productId);

	/**
	 * 添加评论
	 * 
	 * @param userId
	 * @param projectId
	 * @param scheduleId
	 * @param content
	 * @param parentId
	 * @return
	 */
	public boolean addComment(Integer userId, Integer projectId,
			Integer scheduleId, String content, Integer parentId,Integer orderId);

	/**
	 * 删除评论
	 * 
	 * @param id
	 * @return
	 */
	public boolean delComment(Integer id);
	
	
	/**
	 * 获取评论信息
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getComment(Integer projectId);

	/**
	 * 获取订单评论信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<Map<String,Object>> getCommentByOrderId(Integer orderId);
	
	/**
	 * 获取进度评论信息
	 * 
	 * @param orderId
	 * @return
	 */
	public List<Map<String,Object>> getCommentByScheduleId(Integer scheduleId);

	/**
	 * 获取图片信息
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getProjectImg(Integer projectId);

	/**
	 * 添加项目进度
	 * 
	 * @param userId
	 * @param projectId
	 * @param title
	 * @param content
	 * @param IsRelease
	 * @return
	 */
	public boolean addSchedule(Integer userId, Integer projectId, String content,List<Map<String, Object>> IconList);

	/**
	 * 项目进度
	 * 
	 * @param projectId
	 * @return
	 */
	public List<Map<String, Object>> getSchedule(Integer projectId);
	public List<Map<String, Object>> getScheduleAndComments(Integer projectId);//该方法获取评论通过进度表id
	/**
	 * 获取项目信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getProject(Integer id);

	/**
	 * 获取所有项目信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getProjectByList();

	/**
	 * 添加身份认证
	 * 
	 * @param userId
	 * @param name
	 * @param IDCard
	 * @param phone
	 * @param type
	 * @param IDCard_Icon
	 * @param license_Icon
	 * @return
	 */
	public boolean addIdentification(Integer userId, String name,
			String IDCard, String phone, Integer type, String IDCard_Icon,
			String license_Icon);

	/**
	 * 获取认证信息
	 * 
	 * @param userId
	 * @return
	 */
	public TGrouponIdentification getIdentification(Integer userId);

	/**
	 * 获取用户所有消息
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> findNewsByUserId(Integer userId);

	/**
	 * 获取用户未读消息
	 * 
	 * @param userId
	 * @return
	 */
	public List<TGrouponNews> findUnReadNewsByUserId(Integer userId);

	/**
	 * 获取用户已读消息
	 * 
	 * @param userId
	 * @return
	 */
	public List<TGrouponNews> findReadNewsByUserId(Integer userId);

	/**
	 * 修改消息状态
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	public boolean updNewsStatus(Integer id, Integer userId, String status);

	/**
	 * 添加消息
	 * 
	 * @param type
	 * @param title
	 * @param content
	 * @param userId
	 * @return
	 */
	public boolean addNews(Integer type, String title, String content,
			Integer userId);

	/**
	 * 获取项目各个状态列表数据
	 * 
	 * @param userId
	 *            当前用户
	 * @param status
	 *            状态值
	 * @return
	 */
	public List<Map<String, Object>> getProjectByStatus(Integer userId,
			Integer status);

	/**
	 * 添加投诉数据
	 * 
	 * @param projectId
	 * @param userId
	 * @param phone
	 * @param realName
	 * @param IDCare
	 * @param content
	 * @param Icon
	 * @return
	 */
	public boolean addComplain(Integer projectId, Integer userId, String phone,
			String realName, String IDCare, String content, List<String> Icon);

	/**
	 * 获取投诉数据
	 * 
	 * @param userId
	 * @return
	 */
	public Map<Integer, List<Map<String, Object>>> getComplainList(
			Integer userId);

	/**
	 * 获取首页轮播表（为发布的数据）
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getImg();

	/**
	 * 获取最新的项目列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getNewProjectByList();

	/**
	 * 获取精选项目列表
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getSelectProjectByList();

	/**
	 * 获取项目列表
	 * 
	 * @param id
	 * @return
	 */
	public TGrouponProject findProjectById(Integer id);
	
	/**
	 * 获取项目列表
	 * 	通过发起人的id
	 * 
	 * @param CreateUser
	 * @return 
	 */
	public List<Map<String, Object>> findProjectByCreateUser(Long createUser);

	/**
	 * 保存项目列表
	 * 
	 * @param project
	 * @return
	 */
	public TGrouponProject saveOrUpdateProject(TGrouponProject project);
	/**
	 * 通过项目获取所有订单数据
	 * @param projectId
	 * @return
	 */
	public List<Map<String,Object>> getOrderListByProjectId(Integer projectId);
	
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
	public TGrouponSettlement getSettlement(Integer projectId,Integer status);
	/**
	 * 计算结算数据
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
