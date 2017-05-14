package com.troika.groupon.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.util.Constant;
import com.troika.groupon.common.ComplainStatus;
import com.troika.groupon.common.IdenStatus;
import com.troika.groupon.common.PatrolStatus;
import com.troika.groupon.common.ReadStatus;
import com.troika.groupon.dao.GroupCommentDao;
import com.troika.groupon.dao.GroupComplainDao;
import com.troika.groupon.dao.GroupIdenDao;
import com.troika.groupon.dao.GroupImgPatrolDao;
import com.troika.groupon.dao.GroupNewsDao;
import com.troika.groupon.dao.GroupOrderDao;
import com.troika.groupon.dao.GroupProductDao;
import com.troika.groupon.dao.GroupProjectDao;
import com.troika.groupon.dao.GroupProjectImgDao;
import com.troika.groupon.dao.GroupScheduleDao;
import com.troika.groupon.dao.GroupScheduleImgDao;
import com.troika.groupon.dao.GrouponFlowRecordDao;
import com.troika.groupon.dao.GrouponSettlementDao;
import com.troika.groupon.dao.impl.GroupScheduleDaoImpl;
import com.troika.groupon.model.TGroupFollowRecord;
import com.troika.groupon.model.TGrouponComment;
import com.troika.groupon.model.TGrouponComplain;
import com.troika.groupon.model.TGrouponComplainImg;
import com.troika.groupon.model.TGrouponIdentification;
import com.troika.groupon.model.TGrouponNews;
import com.troika.groupon.model.TGrouponNewsRead;
import com.troika.groupon.model.TGrouponProduct;
import com.troika.groupon.model.TGrouponProject;
import com.troika.groupon.model.TGrouponProjectImg;
import com.troika.groupon.model.TGrouponSchedule;
import com.troika.groupon.model.TGrouponScheduleImg;
import com.troika.groupon.model.TGrouponSettlement;
import com.troika.groupon.service.GroupService;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {
	public Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private GroupProductDao groupProductDao;
	@Autowired
	private GroupProjectImgDao groupProjectImgDao;
	@Autowired
	private GroupProjectDao groupProjectDao;
	@Autowired
	private GroupCommentDao groupCommentDao;
	@Autowired
	private GroupScheduleDao groupScheduleDao;
	@Autowired
	private GroupOrderDao groupOrderDao;
	@Autowired
	private GroupIdenDao groupIdenDao;
	@Autowired
	private GroupNewsDao groupNewsDao;
	@Autowired
	private GroupComplainDao groupComplainDao;
	@Autowired
	private GroupImgPatrolDao groupImgPatrolDao;
	@Autowired
	private GrouponFlowRecordDao grouponFlowRecordDao;// 关注
	@Autowired
	private GrouponSettlementDao grouponSettlementDao;// 结算

	@Autowired
	private GroupScheduleImgDao groupScheduleImgDao;// 项目更新进度图片

	@Override
	public boolean addProject(String name, Integer createUser,
			BigDecimal amount, Date endTime, String sendTime, String EMS,
			Integer status, String remark, List<Map<String, Object>> Icon,
			List<Map<String, Object>> product) {
		logger.info("构建拼团项目，发起人ID【" + createUser + "】，发起项目名【" + name + "】");
		TGrouponProject gproject = new TGrouponProject();
		gproject.setName(name);
		gproject.setCreateUser(createUser);
		gproject.setAmount(amount);
		gproject.setEndTime(endTime);
		gproject.setSendTime(sendTime);
		gproject.setEms(EMS);
		gproject.setStatus(status.toString());
		gproject.setRemark(remark);
		logger.info("保存项目【" + name + "】,发起人ID【" + createUser + "】");
		gproject = groupProjectDao.saveOrUpdateProject(gproject);
		Integer projectId = gproject.getId();
		List<TGrouponProjectImg> imgs = new ArrayList<TGrouponProjectImg>();
		for (Map<String, Object> map : Icon) {
			String icon = (String) map.get("Icon");
			String isdisplay = (String) map.get("IsDisplay");
			TGrouponProjectImg img = new TGrouponProjectImg();
			img.setProjectid(projectId);
			img.setIcon(icon);
			img.setIsdisplay(isdisplay);
			img.setCreatetime(new Date());
			imgs.add(img);
		}
		logger.info("保存项目【" + name + "】图片,发起人ID【" + createUser + "】");
		groupProjectImgDao.saveOrUpdateProjectImg(imgs);
		List<TGrouponProduct> productList = new ArrayList<TGrouponProduct>();
		for (Map<String, Object> map : product) {
			String productName = (String) map.get("ProductName");
			String icon = (String) map.get("Icon");
			String content = (String) map.get("content");
			Integer num = Integer.valueOf(map.get("num").toString());
			BigDecimal price = new BigDecimal(map.get("price").toString());
			String unit = (String) map.get("unit");
			TGrouponProduct gproduct = new TGrouponProduct();
			gproduct.setProjectid(projectId);
			gproduct.setProductname(productName);
			gproduct.setIcon(icon);
			gproduct.setContent(content);
			gproduct.setNum(num);
			gproduct.setPrice(price);
			gproduct.setUnit(unit);
			gproduct.setCreatetime(new Date());
			productList.add(gproduct);
		}
		logger.info("保存项目【" + name + "】产品信息,发起人ID【" + createUser + "】");
		groupProductDao.saveOrUpdateProduct(productList);
		logger.info("保存项目【" + name + "】成功,发起人ID【" + createUser + "】");
		return true;
	}

	/**
	 * 删除项目
	 */
	public boolean delProject(Integer projectId) {
		try {
			groupProjectDao.deleteProject(projectId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean delProjectImg(Integer projectId, Integer Icon) {
		try {
			groupProjectImgDao.delProjectImg(projectId, Icon);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean delProduct(Integer productId) {
		try {
			groupProductDao.delProduct(productId);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean addComment(Integer userId, Integer projectId,
			Integer scheduleId, String content, Integer parentId,
			Integer orderId) {
		try {
			TGrouponComment comment = new TGrouponComment();
			comment.setUserid(userId);
			comment.setProjectid(projectId);
			comment.setScheduleid(scheduleId);
			comment.setContent(content);
			comment.setParentid(parentId);
			comment.setOrderid(orderId);
			comment.setCreatetime(new Date());
			groupCommentDao.saveOrUpdate(comment);
		} catch (Exception e) {
			logger.error("用户【" + userId + "】添加评论失败：" + e.getMessage());
			return false;
		}
		return true;
	}

	public boolean delComment(Integer id) {
		try {
			groupCommentDao.delComment(id);
		} catch (Exception e) {
			logger.error("删除评论【" + id + "】失败：" + e.getMessage());
			return false;
		}
		return true;
	}

	public List<Map<String, Object>> getComment(Integer projectId) {
		// List<Map<String, Object>> result = new ArrayList<Map<String,
		// Object>>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = groupCommentDao.getComment(projectId);

			// result = buildComment(list);
		} catch (Exception e) {
			logger.info("获取项目【" + projectId + "】评论异常：" + e.getMessage());
			return null;
		}
		// return result;
		return getParentName(list);
	}

	@Override
	public List<Map<String, Object>> getCommentByOrderId(Integer orderId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = groupCommentDao.getCommentByOrderId(orderId);
		} catch (Exception e) {
			logger.info("获取订单【" + orderId + "】评论异常：" + e.getMessage());
			return null;
		}
		return getParentName(list);
	}

	@Override
	public List<Map<String, Object>> getCommentByScheduleId(Integer scheduleId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = groupCommentDao.getCommentByScheduleId(scheduleId);
		} catch (Exception e) {
			logger.info("获取进度【" + scheduleId + "】评论异常：" + e.getMessage());
			return null;
		}
		return getParentName(list);
	}

	public List<Map<String, Object>> getParentName(
			List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			for (Map<String, Object> map2 : list) {
				int id = (int) map2.get("id");
				int parentId = (int) map.get("parentId");
				if (id == parentId) {
					map.put("parent", map2.get("userName"));
				}
			}
		}
		return list;
	}

	public List<Map<String, Object>> buildComment(List<Map<String, Object>> list) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Integer parentId = (Integer) map.get("parentId");
			if (parentId == null) {
				result.add(map);// 第一级评论
			}
		}
		Map<Integer, List<Map<String, Object>>> childMap = new HashMap<Integer, List<Map<String, Object>>>();
		for (Map<String, Object> map : result) {
			Integer id = (Integer) map.get("id");
			List<Map<String, Object>> child = buildChildComment(list, id,
					childMap);
			if (child != null) {
				map.put("child", child);
			}
		}
		return result;
	}

	public List<Map<String, Object>> buildChildComment(
			List<Map<String, Object>> list, Integer parentId,
			Map<Integer, List<Map<String, Object>>> childMap) {
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Integer tempPrarentId = (Integer) map.get("parentId");
			if (tempPrarentId == parentId) {
				Map<String, Object> child = list.remove(i);
				Integer id = (Integer) child.get("id");
				if (childMap.containsKey(parentId)) {
					List<Map<String, Object>> tempList = childMap.get(parentId);
					tempList.add(child);
				} else {
					List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
					tempList.add(child);
					childMap.put(parentId, tempList);
				}
				List<Map<String, Object>> result = buildChildComment(list, id,
						childMap);
				if (result != null) {
					child.put("child", result);
				}
				i--;
			}
		}
		return childMap.get(parentId);
	}

	public List<Map<String, Object>> getProjectImg(Integer projectId) {
		try {
			List<Map<String, Object>> list = groupProjectImgDao
					.findImgsByProjectId(projectId);
			return list;
		} catch (Exception e) {
			logger.error("获取项目【" + projectId + "】图片信息失败：" + e.getMessage());
			return null;
		}
	}

	// 添加项目更新进度数据
	public boolean addSchedule(Integer userId, Integer projectId,
			String content, List<Map<String, Object>> IconList) {
		try {
			TGrouponSchedule schedule = new TGrouponSchedule();
			schedule.setUserid(userId);
			schedule.setProjectid(projectId);
			schedule.setContent(content);
			schedule.setCreatetime(new Date());
			schedule = groupScheduleDao.saveOrUpdateProject(schedule);
			int id = schedule.getId();// 获取主键
			List<TGrouponScheduleImg> record = new ArrayList<>();
			// 保存项目更新进度图片
			for (Map<String, Object> map : IconList) {
				String Icon = map.get("Icon").toString();
				TGrouponScheduleImg img = new TGrouponScheduleImg();
				img.setIcon(Icon);
				img.setScheduleId(id);
				img.setCreatetime(new Date());
				record.add(img);
			}
			groupScheduleImgDao.saveOrUpdateProject(record);
		} catch (Exception e) {
			logger.error("添加项目【" + projectId + "】进度失败：" + e.getMessage());
			return false;
		}
		return true;
	}

	public List<Map<String, Object>> getSchedule(Integer projectId) {
		try {
			List<Map<String, Object>> scheduleList = groupScheduleDao
					.findAllByProjectId(projectId);
			List<Map<String, Object>> commentList = getComment(projectId);
			for (Map<String, Object> schedule : scheduleList) {
				if (!schedule.get("iconUrl").toString().contains("http://wx")) {
					// 存入更新进度人的图片
					schedule.put("iconUrl",
							Constant.PHOTO_URL + schedule.get("iconUrl"));
				}
				// 过去评论
				Integer id = (Integer) schedule.get("id");
				List<Map<String, Object>> scheComList = new ArrayList<Map<String, Object>>();

				for (int i = 0; i < commentList.size(); i++) {
					Map<String, Object> temp = commentList.get(i);
					Integer scheduleId = (Integer) temp.get("scheduleId");
					if (id == scheduleId) {
						scheComList.add(temp);
						commentList.remove(i);
						i--;
					}
				}
				schedule.put("comment", scheComList);
			}
			return scheduleList;
		} catch (Exception e) {
			logger.error("获取项目【" + projectId + "】进度失败：" + e.getMessage());
			return null;
		}
	}

	public List<Map<String, Object>> getScheduleAndComments(Integer projectId) {
		try {
			List<Map<String, Object>> scheduleList = groupScheduleDao
					.findAllByProjectId(projectId);
			List<Map<String, Object>> commentList = getCommentByScheduleId(projectId);
			for (Map<String, Object> schedule : scheduleList) {
				if (!schedule.get("iconUrl").toString().contains("http://wx")) {
					// 存入更新进度人的图片
					schedule.put("iconUrl",
							Constant.PHOTO_URL + schedule.get("iconUrl"));
				}
				// 过去评论
				Integer id = (Integer) schedule.get("id");// 获取进度Id
				List<Map<String, Object>> scheComList = getCommentByScheduleId(id);
				schedule.put("comment", scheComList);
				List<TGrouponScheduleImg> imgList = groupScheduleImgDao
						.getScheduleImg(id);
				schedule.put("imgList", imgList);// 获取更新进度的所有图片
			}
			return scheduleList;
		} catch (Exception e) {
			logger.error("获取项目【" + projectId + "】进度失败：" + e.getMessage());
			return null;
		}
	}

	public Map<String, Object> getProject(Integer id) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> project = groupProjectDao.findProjectMapById(id);
		if (!project.get("iconUrl").toString().contains("http://wx"))
			project.put("iconUrl", Constant.PHOTO_URL + project.get("iconUrl"));
		if (project != null) {
			List<Map<String, Object>> product = groupProductDao
					.findProductsByProjectId(id);
			result.put("product", product);// 相关产品信息
			List<Map<String, Object>> imgList = groupProjectImgDao
					.findImgsByProjectId(id);
			result.put("ImgList", imgList);// 项目图片
			List<Map<String, Object>> orderList = groupOrderDao
					.getOrderListByProjectId(id);
			result.put("OrderList", orderList);// 订单数据
			for (Map<String, Object> map : orderList) {
				List<Map<String, Object>> commentList = getCommentByOrderId((int) map
						.get("id"));
				map.put("comlist", commentList);
			}
			// 获取当前已拼总金额
			BigDecimal target = new BigDecimal(0);
			for (Map<String, Object> map : orderList)
				target = target.add((BigDecimal) map.get("amount"));
			project.put("target", target);

			result.putAll(project);
		}

		return result;
	}

	public List<Map<String, Object>> getProjectByList() {
		List<Map<String, Object>> list = groupProjectDao.findAllProject();
		for (Map<String, Object> project : list) {
			Integer id = (Integer) project.get("id");
			List<Map<String, Object>> product = groupProductDao
					.findProductsByProjectId(id);
			project.put("product", product);// 相关产品信息
			List<Map<String, Object>> imgList = groupProjectImgDao
					.findImgsByProjectId(id);
			project.put("ImgList", imgList);// 项目图片
			List<Map<String, Object>> orderList = groupOrderDao
					.getOrderListByProjectId(id);
			project.put("OrderList", orderList);// 订单数据
		}
		return list;
	}

	public boolean addIdentification(Integer userId, String name,
			String IDCard, String phone, Integer type, String IDCard_Icon,
			String license_Icon) {
		TGrouponIdentification iden = new TGrouponIdentification();
		iden.setUserid(userId);
		iden.setName(name);
		iden.setIdcard(IDCard);
		iden.setPhone(phone);
		iden.setType(type.toString());
		iden.setIdcard_Icon(IDCard_Icon);
		iden.setLicense_Icon(license_Icon);
		iden.setCreatetime(new Date());
		groupIdenDao.saveOrUpdate(iden);
		return true;
	}

	public TGrouponIdentification getIdentification(Integer userId) {
		TGrouponIdentification iden = groupIdenDao.findById(userId);
		return iden;
	}

	@Override
	public Map<String, Object> findNewsByUserId(Integer userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<TGrouponNews> readList = findReadNewsByUserId(userId);
		List<TGrouponNews> unReadList = findUnReadNewsByUserId(userId);
		result.put("unRead", unReadList);
		result.put("read", readList);
		return result;
	}

	@Override
	public List<TGrouponNews> findReadNewsByUserId(Integer userId) {
		return groupNewsDao.findNewsByUserIdAndStatus(userId,
				ReadStatus.READ.getCode());
	}

	@Override
	public List<TGrouponNews> findUnReadNewsByUserId(Integer userId) {
		return groupNewsDao.findNewsByUserIdAndStatus(userId,
				ReadStatus.UNREAD.getCode());
	}

	public boolean updNewsStatus(Integer id, Integer userId, String status) {
		return groupNewsDao.updNewsStatus(id, userId, status);
	}

	@Override
	public boolean addNews(Integer type, String title, String content,
			Integer userId) {
		type = type == null ? 0 : type;
		TGrouponNews news = new TGrouponNews();
		news.setContent(content);
		news.setTitle(title);
		news.setType(type.toString());
		news.setCreatetime(new Date());
		// 保存新闻
		news = groupNewsDao.saveOrUpdate(news);
		List<TGrouponNewsRead> readList = new ArrayList<TGrouponNewsRead>();
		Integer newsId = news.getId();
		if (type == 1) {
			TGrouponNewsRead read = new TGrouponNewsRead();
			read.setIsread(ReadStatus.UNREAD.getCode());
			read.setNewsid(newsId);
			read.setUserid(userId);
			readList.add(read);
		} else {
			List<TGrouponIdentification> list = groupIdenDao
					.findByStatus(IdenStatus.PASS.getCode());
			for (TGrouponIdentification iden : list) {
				TGrouponNewsRead read = new TGrouponNewsRead();
				read.setIsread(ReadStatus.UNREAD.getCode());
				read.setNewsid(newsId);
				read.setUserid(iden.getUserid());
				readList.add(read);
			}
		}
		// 标记读取
		groupNewsDao.saveOrUpdateNewsRead(readList);
		return true;
	}

	/**
	 * 获取项目的状态数据
	 */
	@Override
	public List<Map<String, Object>> getProjectByStatus(Integer userId,
			Integer status) {
		// TODO Auto-generated method stub
		return groupProjectDao.findProjectByStatus(userId, status);
	}

	public boolean addComplain(Integer projectId, Integer userId, String phone,
			String realName, String IDCare, String content, List<String> Icon) {
		// 添加投诉数据
		TGrouponComplain complain = new TGrouponComplain();
		complain.setProjectId(projectId);
		complain.setUserId(userId);
		complain.setPhone(phone);
		complain.setRealName(realName);
		complain.setIDCare(IDCare);
		complain.setContent(content);
		complain.setStatus(ComplainStatus.CPREQUEST.getCode());
		complain.setCreateTime(new Date());
		complain = groupComplainDao.saveOrUpdate(complain);
		Integer complainId = complain.getId();
		List<TGrouponComplainImg> imgs = new ArrayList<TGrouponComplainImg>();
		for (String icon : Icon) {
			TGrouponComplainImg img = new TGrouponComplainImg();
			img.setComplainId(complainId);
			img.setIcon(icon);
			imgs.add(img);
		}
		// 插入投诉图片
		groupComplainDao.saveOrUpdateImgs(imgs);
		return true;
	}

	public Map<Integer, List<Map<String, Object>>> getComplainList(
			Integer userId) {
		return groupComplainDao.getComplainList(userId);
	}

	public List<Map<String, Object>> getImg() {
		return groupImgPatrolDao.getImgsByStatus(PatrolStatus.UP);
	}

	/**
	 * 获取最新的项目列表
	 * 
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getNewProjectByList() {
		// TODO Auto-generated method stub
		return groupProjectDao.findNewProjectByList();
	}

	/**
	 * 获取精选项目列表
	 */
	@Override
	public List<Map<String, Object>> getSelectProjectByList() {
		// TODO Auto-generated method stub
		return groupProjectDao.findSelectedProjectByList();
	}

	/**
	 * 获取项目列表
	 */
	@Override
	public TGrouponProject findProjectById(Integer id) {
		// TODO Auto-generated method stub
		return groupProjectDao.findProjectById(id);
	}

	/**
	 * 保存项目数据
	 */
	@Override
	public TGrouponProject saveOrUpdateProject(TGrouponProject project) {
		// TODO Auto-generated method stub
		return groupProjectDao.saveOrUpdateProject(project);
	}

	/**
	 * 通过项目获取订单数据
	 */
	@Override
	public List<Map<String, Object>> getOrderListByProjectId(Integer projectId) {
		// TODO Auto-generated method stub
		return groupOrderDao.getOrderListByProjectId(projectId);
	}

	/**
	 * 添加关注
	 */
	@Override
	public TGroupFollowRecord saveOrUpdateRecord(TGroupFollowRecord record) {
		// TODO Auto-generated method stub
		return grouponFlowRecordDao.saveOrUpdateRecord(record);
	}

	/**
	 * 获取某个项目的关注数
	 */
	@Override
	public int getRecordCount(Integer projectId) {
		// TODO Auto-generated method stub
		return grouponFlowRecordDao.getRecordCount(projectId);
	}

	/**
	 * 判断用户是否已经关注过
	 */
	@Override
	public TGroupFollowRecord getRecord(Integer userId, Integer projectId) {
		// TODO Auto-generated method stub
		return grouponFlowRecordDao.getRecord(userId, projectId);
	}

	/**
	 * 通过发起人获取项目列表
	 */
	@Override
	public List<Map<String, Object>> findProjectByCreateUser(Integer createUser) {
		return groupProjectDao.findProjectByCreateUser(createUser);
	}

	/**
	 * 保存结算数据
	 */
	@Override
	public TGrouponSettlement saveOrUpdateRecord(TGrouponSettlement record) {
		// TODO Auto-generated method stub
		return grouponSettlementDao.saveOrUpdateRecord(record);
	}

	/**
	 * 获取结算数据
	 */
	@Override
	public TGrouponSettlement getSettlement(Integer projectId, Integer status) {
		// TODO Auto-generated method stub
		return grouponSettlementDao.getSettlement(projectId, status);
	}

	/**
	 * 计算结算数据
	 */
	@Override
	public Map<String, Object> setSettlement(Integer projectId) {
		// TODO Auto-generated method stub
		return grouponSettlementDao.setSettlement(projectId);
	}

	/**
	 * 通过项目主键获取结算数据
	 */
	@Override
	public List<TGrouponSettlement> getSettlement(Integer projectId) {
		// TODO Auto-generated method stub
		return grouponSettlementDao.getSettlement(projectId);
	}

	/**
	 * 获取用户所有的结算数据
	 */
	@Override
	public List<Map<String, Object>> getSettlementByUser(Integer userId) {
		// TODO Auto-generated method stub
		return grouponSettlementDao.getSettlementByUser(userId);
	}

	/**
	 * 判断项目是否在结算中或已经结算
	 * 
	 * @param projectId
	 * @return
	 */
	@Override
	public Boolean whetherSett(Integer projectId) {
		// TODO Auto-generated method stub
		return grouponSettlementDao.whetherSett(projectId);
	}

}
