package com.troika.emall.restapi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.troika.emall.model.TMallHomeMenu;
import com.troika.emall.model.TMallHomeTopPic;
import com.troika.emall.service.GoodsService;
import com.troika.emall.service.HomePageService;
import com.troika.emall.util.Constant;

/**
 * 首页API接口
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/homePage")
public class HomePageController extends BaseController {

	@Autowired
	private HomePageService homePageService;
	@Autowired
	private GoodsService goodsService;

	/**
	 * 获取菜单数据
	 * 
	 * @return
	 */
	@RequestMapping("/getMainPage.api")
	@ResponseBody
	public String getMenu() {
		List<TMallHomeMenu> list = homePageService.findAllMenu();
		String resultJson = new Gson().toJson(list);
		return resultJson;
	}

	/**
	 * 获取热卖、优惠和特色商品
	 * 
	 * @return
	 */
	@RequestMapping("/getTopGoods.api")
	@ResponseBody
	public String getTopGoods() {
		List<Map<String, Object>> list = homePageService.getTopGoods();
		String resultJson = new Gson().toJson(list);
		return resultJson;
	}
	/**
	 * 返回给android的首页集合
	 * @return
	 */
	@RequestMapping("/getHomeInfo.api")
	@ResponseBody
	public String getHomeInfo(){
		List<Map<String, Object>> topPic = homePageService.findAllPic();
		List<Map<String, Object>> frontCategory = homePageService.getFrontCategory();
		List<Map<String, Object>> bigHit = homePageService.findBigHitAll();
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("topPic", topPic);
		result.put("frontCategory", frontCategory);
		result.put("bigHit", bigHit);
		return genSuccessResult(result);
	}
	
	
	/**
	 * 根据首页子类的id获取所属首页子类商品列表
	 * @param frontSubcategoryId - 首页子类Id
	 * @return
	 */
	@RequestMapping("/frontSubcategoryGoods.api")
	@ResponseBody
	public String frontSubcategoryGoods(int frontSubcategoryId) {
		List<Map<String, Object>> list = goodsService.findFrontGoods(frontSubcategoryId);
		String resultJson = genSuccessResult(list);
		return resultJson;
	}
}
