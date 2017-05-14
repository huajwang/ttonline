package com.troika.emall.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.model.TMallAddress;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallProvince;
import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.AddressService;
import com.troika.emall.service.OrderProcess;
import com.troika.emall.service.SystemCodeService;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;

@Controller
@RequestMapping("/address")
public class AddressController extends BaseController {

	@Autowired
	private AddressService addressService;

	@Autowired
	private SystemCodeService systemCodeService;

	@Autowired
	private OrderProcess orderProcess;

	/**
	 * 收货人信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping
	public String index(HttpServletRequest request, Model model) {
		TMallUser user = CommonUtil.ValidateUser(request);
		try {
			long id = user.getId();
			String orderId = request.getParameter("orderId");
			List<Map<String, Object>> list = addressService.findAllAddress(id);
			if (orderId != null) {
				TMallOrder order = orderProcess.getOrderInfoById(Long
						.valueOf(orderId));
				long AddrId = order.getAddrId();
				for (Map<String, Object> map : list) {
					if (Long.valueOf(map.get("id").toString()) == AddrId) {
						map.put("active", "1");
					} else {
						map.put("active", "0");
					}
				}
			}
			model.addAttribute("address", list);
		} catch (Exception e) {
			if (user == null) {
				// TODO no TMallUser exists in session
				logger.error("no user in session: " + e.getMessage());
			}
		} 
		return "address/index";
	}

	/**
	 * 添加或编辑地址信息
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/Edit")
	public String Edit(HttpServletRequest request, Model model) {
		String id = request.getParameter("id");
		if (id != null) {
			Map<String, Object> address = addressService.findAddressById(Long
					.parseLong(id));
			model.addAttribute("address", address);
		}
		List<TMallProvince> Provinces = systemCodeService.findAllProvince();// 获取省数据
		model.addAttribute("Provinces", Provinces);
		return "address/EditAddr";
	}

	/**
	 * 保存新地址
	 * 
	 * @param request
	 * @param address
	 * @return
	 */
	@RequestMapping("/ajaxSaveOrUpdateAddress")
	@ResponseBody
	public String ajaxSaveOrUpdateAddress(HttpServletRequest request,
			TMallAddress address) {
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		address.setUserId(user.getId());
		if(addressService.findAllAddress(user.getId()).isEmpty())
			address.setActive(1);
		addressService.saveOrUpdate(address);
		return genSuccessResult();
	}
	
	/**
	 * 设置新的默认地址
	 */
	@RequestMapping("/ajaxSetDefaultAddress")
	@ResponseBody
	public int ajaxSetDefaultAddress(HttpServletRequest request,long id){
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		int result = 0;
		result = addressService.setDefault(id, user.getId());
		return result;
	}

	/**
	 * 删除地址
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delAddress")
	@ResponseBody
	public int delAddress(long id) {
		return addressService.delAddressById(id);
	}

	/**
	 * 修改订单的收货地址
	 * 
	 * @param request
	 * @param orderId
	 * @param addressId
	 * @return
	 */
	@RequestMapping("/updOrderAddress")
	@ResponseBody
	public String updOrderAddress(HttpServletRequest request, long orderId,
			long addressId) {
		TMallOrder order = orderProcess.getOrderInfoById(orderId);
		order.setAddrId(addressId);
		orderProcess.saveOrUpdateOrder(order);
		return genSuccessResult();
	}
}
