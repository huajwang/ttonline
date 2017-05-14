package com.troika.emall.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.troika.emall.common.OrderStatus;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallOrderDetail;
import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.AddressService;
import com.troika.emall.service.CartService;
import com.troika.emall.service.EvaluateService;
import com.troika.emall.service.FavouriteService;
import com.troika.emall.service.OrderProcess;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;

@Controller
@RequestMapping("/detail")
public class DetailController extends BaseController {

	@Autowired
	private CartService cartService;
	@Autowired
	private OrderProcess orderProcess;

	@Autowired
	private AddressService addressService;// 地址

	@Autowired
	private FavouriteService favouriteService;// 收藏

	@Autowired
	private EvaluateService evaluateService;// 评价

	/**
	 * 收藏
	 * 
	 * @param type
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/favourite")
	@ResponseBody
	public String favourite(int type, HttpServletRequest request, long gId) {
		TMallUser user = CommonUtil.ValidateUser(request);
		String resultJson = null;
		if (user == null) {
			resultJson = genFailNotLogin();
		} else {
			long userId = user.getId();
			if (type == 1)
				favouriteService.addFavourite(userId, gId);// 收藏商品
			else
				favouriteService.reduceFavourite(userId, gId);// 取消收藏商品
			resultJson = genSuccessResult();
		}
		return resultJson;
	}

	/**
	 * 判断是否收藏
	 * 
	 * @param type
	 * @param request
	 * @param gId
	 * @return
	 */
	@RequestMapping(value = "/ajaxIsFavorite")
	@ResponseBody
	public String ajaxIsFavorite(HttpServletRequest request, long gId) {
		TMallUser user = CommonUtil.ValidateUser(request);
		String resultJson = null;
		if (user == null) {
			resultJson = genFailNotLogin();
		} else {
			Map<String, String> map = favouriteService.findGidIsFavourite(
					user.getId(), gId);
			resultJson = genSuccessResult(map);
		}
		return resultJson;
	}

	/**
	 * 加入购物车
	 * 
	 * @param request
	 * @param cartDetail
	 * @param total
	 * @param isOut
	 * @return
	 */
	@RequestMapping(value = "/addCart")
	@ResponseBody
	public String addCart(HttpServletRequest request, String cartDetail,
			BigDecimal total, String isOut) {
		TMallUser user = CommonUtil.ValidateUser(request);
		String resultJson = null;
		if (user == null) {
			resultJson = genFailNotLogin();
		} else {
			Map<String, Object> result = cartService.buildCart(user.getId(),
					cartDetail, total, isOut);
			resultJson = genSuccessResult(result);
		}
		return resultJson;
	}

	/**
	 * 立即购买
	 * 
	 * @param request
	 * @param cartDetail
	 * @param total
	 * @param isOut
	 * @param introducer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveOrder")
	@ResponseBody
	public String SaveOrder(HttpServletRequest request, String cartDetail,
			BigDecimal total, String introducer) {
		TMallUser user = CommonUtil.ValidateUser(request);
		String resultJson = null;
		Map<String, Object> result = new HashMap<String, Object>();
		if (user == null) {
			resultJson = genFailNotLogin();
		} else {
			Map<String, Object> map = new Gson()
					.fromJson(cartDetail, Map.class);

			long userId = user.getId();
			// 获取用户的默认地址
			Map<String, Object> address = addressService
					.findActiveAddress(userId);
			// if (address.size() == 0) {
			// result.put("msg", "请先设置收货地址");// 订单Id
			// resultJson = genFailResult(result);
			// return resultJson;
			// }
			if (StringUtils.isBlank(introducer))
				introducer = "0";
			TMallOrder order = new TMallOrder();
			order.setUserId(userId);
			// 判断用户是否设置默认地址
			if (address.size() > 0)
				order.setAddrId(Long.parseLong(address.get("id").toString()));
			order.setAmount(total);
			order.setIntroducer(Long.parseLong(introducer)); // 在总订单里设置订单推荐人
			order.setOrderId(CommonUtil.buildOrderId(userId));
			order.setOrderStatus(OrderStatus.ACTIVE.getCode());
			order.setCreateTime(new Date());
			order = orderProcess.saveOrUpdateOrder(order);

			List<TMallOrderDetail> insertList = new ArrayList<TMallOrderDetail>();
			// 保存订单详情数据
			TMallOrderDetail orderD = new TMallOrderDetail();
			long gid = Long.parseLong(map.get("gId").toString());
			orderD.setgId(gid);
			orderD.setOrderId(order.getId());
			orderD.setQuantity(Integer
					.parseInt(map.get("gQuantity").toString()));
			orderD.setPrice(new BigDecimal(map.get("price").toString()));
			orderD.setPropertyId(Long.parseLong(map.get("propertyId")
					.toString()));
			orderD.setPropertyTableName(map.get("propertyTableName").toString());
			orderD.setDetailStatus("0");
			insertList.add(orderD);

			orderProcess.saveOrUpdateOrderDetail(insertList);
			result.put("orderId", order.getOrderId());// 订单编号
			result.put("id", order.getId());// 订单Id
			resultJson = genSuccessResult(result);
		}
		return resultJson;
	}

	/**
	 * 显示商品评价列表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("evalist")
	public String myFavorite(HttpServletRequest request, Model model, long gId) {
		List<Map<String, Object>> list = evaluateService.evalListByGid(gId);
		// 评论名字的处理
		for (Map<String, Object> map2 : list) {
			if (map2.get("userName") != null && map2.get("userName") != ""
					&& (map2.get("userName") + "").length() > 3
					&& !"null".equals(map2.get("userName") + "")) {
				String lastOne = map2
						.get("userName")
						.toString()
						.substring(map2.get("userName").toString().length() - 1);
				map2.put("userName",
						map2.get("userName").toString().substring(0, 2) + "xxx"
								+ lastOne);
			}
		}
		model.addAttribute("list", list);
		return "goods/evalist";
	}

	/**
	 * 判断用户是否登录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ajaxLogin")
	@ResponseBody
	public String ajaxLogin(HttpServletRequest request) {
		TMallUser user = CommonUtil.ValidateUser(request);
		String resultJson = null;
		if (user == null) {
			resultJson = genFailNotLogin();
		} else {
			resultJson = genSuccessResult(user);
		}
		return resultJson;
	}

	/**
	 * 通过微信登录鉴权跳转该页面 保存订单数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveOrderByPage")
	public void SaveOrderByPage(HttpServletRequest request,
			HttpServletResponse response, Model model, String Detail,
			String introducer) throws Exception {
		logger.debug("获取参数信息：" + Detail);
		String cartDetail = Detail;
		TMallUser user = CommonUtil.ValidateUser(request);// 获取当前用户
		Map<String, Object> result = new HashMap<String, Object>();
		// JSON格式的字符串转Map
		Map<String, Object> map = new Gson().fromJson(cartDetail, Map.class);

		long gid = Long.parseLong(map.get("gId").toString());// 商品Id值
		BigDecimal total = new BigDecimal(map.get("total").toString());// 总金额
		logger.debug("获取总金额：" + total);
		// 添加购物车功能
		if (map.get("isOut") != null) {
			String isOut = map.get("isOut").toString();
			// 插入购物车
			result = cartService.buildCart(user.getId(), cartDetail, total,
					isOut);
			logger.debug("加入购物车：" + cartDetail);
			// 跳转到项目详情页面
			response.sendRedirect("../productDetail?productId=" + gid);
		} else {// 添加订单功能
			long userId = user.getId();
			logger.debug("获取当前用户Id：" + userId);
			// 获取用户的默认地址
			Map<String, Object> address = addressService
					.findActiveAddress(userId);
			if (StringUtils.isBlank(introducer))
				introducer = "0";
			TMallOrder order = new TMallOrder();
			order.setUserId(userId);
			// 判断用户是否设置默认地址
			if (address.size() > 0)
				order.setAddrId(Long.parseLong(address.get("id").toString()));
			order.setAmount(total);
			order.setIntroducer(Long.parseLong(introducer)); // 在总订单里设置订单推荐人
			order.setOrderId(CommonUtil.buildOrderId(userId));
			order.setOrderStatus(OrderStatus.ACTIVE.getCode());
			order.setCreateTime(new Date());
			order = orderProcess.saveOrUpdateOrder(order);// 订单主表保存

			List<TMallOrderDetail> insertList = new ArrayList<TMallOrderDetail>();
			// 保存订单详情数据
			TMallOrderDetail orderD = new TMallOrderDetail();
			orderD.setgId(gid);
			orderD.setOrderId(order.getId());
			orderD.setQuantity(Integer
					.parseInt(map.get("gQuantity").toString()));
			orderD.setPrice(new BigDecimal(map.get("price").toString()));
			orderD.setPropertyId(Long.parseLong(map.get("propertyId")
					.toString()));
			orderD.setPropertyTableName(map.get("propertyTableName").toString());
			orderD.setDetailStatus("0");
			insertList.add(orderD);

			orderProcess.saveOrUpdateOrderDetail(insertList);
			result.put("orderId", order.getOrderId());// 订单编号
			result.put("id", order.getId());// 订单Id
			// 跳转到提交订单页面
			response.sendRedirect("../orders/subOrder?id=" + order.getId());
		}
	}
}
