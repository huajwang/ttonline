package com.troika.groupon.api;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.troika.emall.model.TMallUser;
import com.troika.emall.util.Constant;
import com.troika.groupon.controller.BaseController;
import com.troika.groupon.model.TGroupFollowRecord;
import com.troika.groupon.model.TGrouponIdentification;
import com.troika.groupon.model.TGrouponOrder;
import com.troika.groupon.model.TGrouponProject;
import com.troika.groupon.model.TGrouponSettlement;
import com.troika.groupon.service.GroupOrderService;
import com.troika.groupon.service.GroupService;

@Controller
@RequestMapping("/groupon/api/project")
public class GroupController extends BaseController {
	@Autowired
	private GroupService groupService;
	@Autowired
	private GroupOrderService groupOrderService;

	/**
	 * 发起项目
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
	@RequestMapping("/addProject")
	@ResponseBody
	public String addProject(String name, Integer createUser,
			BigDecimal amount, Date endTime, String sendTime, String EMS,
			Integer status, String remark, List<Map<String, Object>> Icon,
			List<Map<String, Object>> product) {
		boolean isSuc = groupService.addProject(name, createUser, amount,
				endTime, sendTime, EMS, status, remark, Icon, product);
		if (isSuc) {
			return genSuccessResult();
		}
		return genFailResult();
	}

	/**
	 * 删除项目信息
	 * 
	 * @return
	 */
	@RequestMapping("/delProject")
	@ResponseBody
	public String delProject(Integer projectId) {
		boolean isSuc = groupService.delProject(projectId);
		if (isSuc) {
			return genSuccessResult();
		}
		return genFailResult();
	}

	/**
	 * 删除项目图片信息
	 * 
	 * @param projectId
	 * @param Icon
	 * @return
	 */
	@RequestMapping("/delProjectImg")
	@ResponseBody
	public String delProjectImg(Integer projectId, Integer Icon) {
		boolean isSuc = groupService.delProjectImg(projectId, Icon);
		if (isSuc) {
			return genSuccessResult();
		}
		return genFailResult();
	}

	/**
	 * 删除产品信息
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/delProduct")
	@ResponseBody
	public String delProduct(Integer productId) {
		boolean isSuc = groupService.delProduct(productId);
		if (isSuc) {
			return genSuccessResult();
		}
		return genFailResult();
	}

	/**
	 * 添加定单
	 * 
	 * @param orderNO
	 * @param projectId
	 * @param amount
	 * @param userId
	 * @param remark
	 * @param status
	 * @param Details
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addOrder", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String addOrder(HttpServletRequest request, Integer projectId,
			BigDecimal amount, String remark, String Details, int addrId) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		int userId = (int) user.getId();// 购买用户Id
		int status = 0;// 订单状态
		// 结果集合
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> productList = new Gson().fromJson(Details,
				List.class);
		// 判断产品数量是否足够
		for (Map<String, Object> map : productList) {
			// 产品主键
			int productId = Integer.valueOf(map.get("productId").toString());
			Map<String, Object> numMap = groupOrderService
					.getProductSurplus(productId);
			if (numMap == null)// 尚未有订单数据
				continue;
			else {
				int num = Integer.valueOf(map.get("num").toString());// 购买数量
				int Surplus = Integer.valueOf(numMap.get("Surplus").toString());// 剩余数量
				String ProductName = numMap.get("ProductName").toString();// 产品名称
				// 购买数量大于剩余数量
				if (Surplus < num) {
					result.put("msg", ProductName + "剩余数量：" + Surplus + "份");
					break;
				}
			}
		}
		// 数量不足
		if (result.size() > 0)
			return genFailResult(result);
		TGrouponOrder order = groupOrderService.addOrder(projectId, amount,
				userId, remark, status, productList, addrId);
		if (order != null) {
			return genSuccessResult(order);
		} else {
			result.put("msg", "下单失败！请与管理员联系");
			return genFailResult(result);
		}
	}

	/**
	 * 修改订单状态
	 * 
	 * @param Id
	 * @param status
	 * @return
	 */
	@RequestMapping("/updOrderStatus")
	@ResponseBody
	public String updOrderStatus(Integer Id, Integer status) {
		boolean isSuc = groupOrderService.updOrderStatus(Id, status);
		if (isSuc) {
			return genSuccessResult();
		}
		return genFailResult();
	}

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
	@RequestMapping("/addComment")
	@ResponseBody
	public String addComment(
			Integer userId,
			Integer projectId,
			Integer scheduleId,
			String content,
			@RequestParam(value = "parentId", required = false) Integer parentId,
			Integer orderId) {
		boolean isSuc = groupService.addComment(userId, projectId, scheduleId,
				content, parentId, orderId);
		if (isSuc) {
			return genSuccessResult();
		}
		return genFailResult();
	}

	/**
	 * 删除评论
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delComment")
	@ResponseBody
	public String delComment(Integer id) {
		boolean isSuc = groupService.delComment(id);
		if (isSuc) {
			return genSuccessResult();
		}
		return genFailResult();
	}

	/**
	 * 获取评论信息
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/getComment")
	@ResponseBody
	public String getComment(Integer projectId) {
		List<Map<String, Object>> result = groupService.getComment(projectId);
		if (result != null) {
			return genSuccessResult(result);
		}
		return genFailResult();
	}

	/**
	 * 获取订单评论信息
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/getCommentByOrderId")
	@ResponseBody
	public String getCommentByOrderId(Integer orderId) {
		List<Map<String, Object>> result = groupService.getComment(orderId);
		if (result != null) {
			return genSuccessResult(result);
		}
		return genFailResult();
	}

	/**
	 * 获取进度评论信息
	 * 
	 * @param orderId
	 * @return
	 */
	@RequestMapping("/getCommentByScheduleId")
	@ResponseBody
	public String getCommentByScheduleId(Integer scheduleId) {
		List<Map<String, Object>> result = groupService
				.getCommentByScheduleId(scheduleId);
		if (result != null) {
			return genSuccessResult(result);
		}
		return genFailResult();
	}

	/**
	 * 获取图片信息
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/getProjectImg")
	@ResponseBody
	public String getProjectImg(Integer projectId) {
		List<Map<String, Object>> result = groupService
				.getProjectImg(projectId);
		if (result != null) {
			return genSuccessResult(result);
		}
		return genFailResult();
	}

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
	@SuppressWarnings("unchecked")
	@RequestMapping("/addSchedule")
	@ResponseBody
	public String addSchedule(HttpServletRequest request, Integer projectId,
			String content, String Icon) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		Integer userId = (int) user.getId();
		List<Map<String, Object>> IconList = new Gson().fromJson(Icon,
				List.class);
		boolean isSuc = groupService.addSchedule(userId, projectId, content,
				IconList);
		if (isSuc) {
			return genSuccessResult();
		}
		return genFailResult();
	}

	/**
	 * 获取项目进度信息
	 * 
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/getSchedule")
	@ResponseBody
	public String getSchedule(Integer projectId) {
		List<Map<String, Object>> list = groupService.getSchedule(projectId);
		if (list != null) {
			return genSuccessResult(list);
		}
		return genFailResult();
	}

	/**
	 * 获取项目信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/getProject")
	@ResponseBody
	public String getProject(Integer id) {
		Map<String, Object> result = groupService.getProject(id);
		return genSuccessResult(result);
	}

	/**
	 * 获取所有项目
	 * 
	 * @return
	 */
	@RequestMapping("/getProjectByList")
	@ResponseBody
	public String getProjectByList() {
		List<Map<String, Object>> result = groupService.getProjectByList();
		return genSuccessResult(result);
	}

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
	@RequestMapping("/addIdentification")
	@ResponseBody
	public String addIdentification(Integer userId, String name, String IDCard,
			String phone, Integer type, String IDCard_Icon, String license_Icon) {
		groupService.addIdentification(userId, name, IDCard, phone, type,
				IDCard_Icon, license_Icon);
		return genSuccessResult();
	}

	/**
	 * 获取认证信息
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getIdentification")
	@ResponseBody
	public String getIdentification(Integer userId) {
		TGrouponIdentification iden = groupService.getIdentification(userId);
		return genSuccessResult(iden);
	}

	/**
	 * 获取各个状态的项目列表数据
	 * 
	 * @param userId
	 * @param status
	 * @return
	 */
	@RequestMapping("/getProjectByStatus")
	@ResponseBody
	public String getProjectByStatus(Integer userId, Integer status) {
		List<Map<String, Object>> result = groupService.getProjectByStatus(
				userId, status);
		return genSuccessResult(result);
	}

	/**
	 * 获取首页轮播表（为发布的数据）
	 * 
	 * @return
	 */
	@RequestMapping("/getImg")
	@ResponseBody
	public String getImg() {
		List<Map<String, Object>> list = groupService.getImg();
		return genSuccessResult(list);
	}

	/**
	 * 获取最新的项目列表
	 * 
	 * @return
	 */
	@RequestMapping("/getNewProjectByList")
	@ResponseBody
	public String getNewProjectByList() {
		List<Map<String, Object>> list = groupService.getNewProjectByList();
		return genSuccessResult(list);
	}

	/**
	 * 获取精选项目列表
	 * 
	 * @return
	 */
	@RequestMapping("/getSelectProjectByList")
	@ResponseBody
	public String getSelectProjectByList() {
		List<Map<String, Object>> list = groupService.getSelectProjectByList();
		return genSuccessResult(list);
	}

	/**
	 * 修改项目的目金额
	 * 
	 * @return
	 */
	@RequestMapping("/updateProjiectByAmount")
	@ResponseBody
	public String updateProjiectByAmount(int id, double amount) {
		TGrouponProject project = groupService.findProjectById(id);
		java.math.BigDecimal bd1 = new java.math.BigDecimal(amount);
		project.setAmount(bd1);// 修改目标金额
		groupService.saveOrUpdateProject(project);
		return genSuccessResult();
	}

	/**
	 * 修改项目的截止时间
	 * 
	 * @return
	 */
	@RequestMapping("/updateProjiectByEndTime")
	@ResponseBody
	public String updateProjiectByEndTime(int id, String endTime) {
		try {
			TGrouponProject project = groupService.findProjectById(id);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date = sdf.parse(endTime);
			project.setEndTime(date);// 截止时间
			groupService.saveOrUpdateProject(project);
			return genSuccessResult();
		} catch (Exception ex) {
			return genFailResult();
		}
	}

	/**
	 * 通过项目获取所有订单数据
	 * 
	 * @param projectId
	 * @param endTime
	 * @return
	 */
	@RequestMapping("/ getOrderListByProjectId")
	@ResponseBody
	public String getOrderListByProjectId(int projectId, String endTime) {
		try {
			List<Map<String, Object>> list = groupService
					.getOrderListByProjectId(projectId);
			return genSuccessResult(list);
		} catch (Exception ex) {
			return genFailResult();
		}
	}

	/**
	 * 添加关注
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/addFollowRecord")
	@ResponseBody
	public String addFollowRecord(HttpServletRequest request, Integer projectId) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		if (user == null)
			return genFailResult();
		TGroupFollowRecord record = new TGroupFollowRecord();
		record.setCreatetime(new Date());// 创建事件
		record.setPrejectid(projectId);// 项目主键
		record.setUserid((int) user.getId());// 当前用户
		record = groupService.saveOrUpdateRecord(record);
		return genSuccessResult(record);
	}

	/**
	 * 获取关注数量
	 */
	@RequestMapping("/getRecordCount")
	@ResponseBody
	public String getRecordCount(HttpServletRequest request, Integer projectId) {
		int count = groupService.getRecordCount(projectId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("num", count);// 关注数量
		return genSuccessResult(map);
	}

	/**
	 * 判断用户是否关注
	 */
	@RequestMapping("/getRecord")
	@ResponseBody
	public String getRecord(HttpServletRequest request, Integer projectId) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 获取当前用户
		if (user == null)
			return genFailResult();
		TGroupFollowRecord record = groupService.getRecord((int) user.getId(),
				projectId);
		return genSuccessResult(record);
	}

	/**
	 * 保存或修改结算数据
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping("/saveOrUpdateSettlement")
	@ResponseBody
	public String saveOrUpdateSettlement(HttpServletRequest request,
			Integer projectId, double Actual, double Total, double Service,
			Integer type, double Refund) {
		TGrouponSettlement record = new TGrouponSettlement();
		record.setProjectid(projectId);// 项目主键
		// 实际结算金额
		java.math.BigDecimal actual = new java.math.BigDecimal(Actual);
		record.setActual(actual);
		// 总金额
		java.math.BigDecimal total = new java.math.BigDecimal(Total);
		record.setTotal(total);
		// 平台服务费用
		java.math.BigDecimal service = new java.math.BigDecimal(Service);
		record.setService(service);
		record.setType(type.toString());// 付款方式
		record.setCompensate(new java.math.BigDecimal(0));// 赔偿
		// 退款金额
		java.math.BigDecimal refund = new java.math.BigDecimal(Refund);
		record.setRefund(refund);
		record.setStatus("0");
		record.setCreatetime(new Date());
		record = groupService.saveOrUpdateRecord(record);// 保存
		return genSuccessResult(record);
	}

	/**
	 * 获取产品剩余数量
	 * 
	 * @param ProductId
	 * @return
	 */
	@RequestMapping("/getProductSurplus")
	@ResponseBody
	public String getProductSurplus(Integer ProductId) {
		Map<String, Object> map = groupOrderService
				.getProductSurplus(ProductId);
		if (map == null)
			return null;
		else
			return map.get("Surplus").toString();
	}
}
