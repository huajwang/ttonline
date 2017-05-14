package com.troika.emall.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.troika.emall.model.TMallActivityGoods;
import com.troika.emall.model.TMallActivityGoodsImg;
import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.ActivityService;
import com.troika.emall.service.CartService;
import com.troika.emall.service.EvaluateService;
import com.troika.emall.service.GoodsService;
import com.troika.emall.util.Constant;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

	@Autowired
	private GoodsService goodsService;// 商品

	@Autowired
	private CartService cartService;

	@Autowired
	private EvaluateService evaluateService;// 评价
	@Autowired
	private ActivityService activityService;// 活动

	/**
	 * 商品详情
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/productDetail")
	public String productDetail(HttpServletRequest request, Model model) {
		// 获取活动产品的数据
		TMallActivityGoods agoods = activityService.findActivityGoods();
		int productId = 0;
		if (agoods != null) {
			productId = (int) agoods.getGoodId();
			model.addAttribute("activity", agoods);
		}
		TMallUser userinfo = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);
		Map<String, Object> good = null;
		if (userinfo == null) {
			good = goodsService.findGoodsById(productId);
		} else {
			good = goodsService.findGoodsByIdUser(productId, userinfo.getId());
			model.addAttribute("userId", userinfo.getId());// 获取当前用户Id
		}
		// 商品详情图片列表
		List<TMallActivityGoodsImg> ImgList = activityService
				.findActivityGoodsImg((int) agoods.getId(), 1);
		for (TMallActivityGoodsImg gimg : ImgList) {
			String ImgSrc = gimg.getIcon();
			gimg.setIcon(Constant.PHOTO_URL + ImgSrc);
		}
		good.put("DetialImg", ImgList);

		// 获取商品图片路径
		List<TMallActivityGoodsImg> list = activityService
				.findActivityGoodsImg((int) agoods.getId(), 0);
		for (TMallActivityGoodsImg gimg : list) {
			String ImgSrc = gimg.getIcon();
			gimg.setIcon(Constant.PHOTO_URL + ImgSrc);
		}
		// 获取商品属性数据
		String subcategoryId = good.get("subcategoryId").toString();// 子类数据
		// 获取商品的属性
		List<Map<String, Object>> property = goodsService.findAllProperties(
				Long.valueOf(productId), Integer.valueOf(subcategoryId));

		model.addAttribute("good", good);
		model.addAttribute("photos", list);
		int type = 0;
		double min = -1;// 最低价格
		double max = -1;// 最高价格
		if ((Integer.parseInt(good.get("showPopup").toString().trim())) == 1
				&& property != null) {
			List<Map<String, Object>> colorList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> sizeList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> styleList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : property) {
				Map<String, Object> m = new HashMap<String, Object>();
				Map<String, Object> m2 = new HashMap<String, Object>();
				Map<String, Object> m3 = new HashMap<String, Object>();
				String color = (String) map.get("color");
				String size = (String) map.get("size");
				String style = (String) map.get("style");
				m.put("color", color);
				m2.put("size", size);
				m3.put("style", style);
				if (!colorList.contains(m))// 判断元素是否存在
				{
					colorList.add(m);
				}
				if (!sizeList.contains(m2))// 判断元素是否存在
				{
					sizeList.add(m2);
				}
				if (!styleList.contains(m3))// 判断元素是否存在
				{
					styleList.add(m3);
				}
				if (min == -1) {
					min = Double.valueOf(map.get("price").toString());
					max = Double.valueOf(map.get("price").toString());
				} else {
					double value = Double.valueOf(map.get("price").toString());
					if (max < value)
						max = value;
					if (min > value)
						min = value;
				}
			}
			model.addAttribute("colorList", colorList);
			model.addAttribute("sizeList", sizeList);
			model.addAttribute("styleList", styleList);
			String JSON = new Gson().toJson(property);
			if (JSON.indexOf("color") >= 0)// 颜色
				type++;
			if (JSON.indexOf("size") >= 0)// 尺寸
				type++;
			if (JSON.indexOf("style") >= 0)// 风格
				type++;
			model.addAttribute("propertyJSON", JSON);
		}
		model.addAttribute("type", type);
		model.addAttribute("min", min);
		model.addAttribute("max", max);

		return "goods/activityProductDetail";
	}
}
