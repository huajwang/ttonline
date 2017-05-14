package com.troika.emall.restapi;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.service.GoodsService;

@Controller
@RequestMapping("/sys/goods/")
public class GoodsController extends BaseController {
	@Autowired
	private GoodsService goodsService;

	/**
	 * 排序
	 * @param subCategoryId
	 * @param type
	 * @param sort
	 * @return
	 */
	@RequestMapping("/getSortGoodsByType.api")
	@ResponseBody
	public String getSortGoodsByType(int subCategoryId,String type,String sort) {
		List<Map<String, Object>> list = goodsService
				.findGoodsByCategoryId(subCategoryId,type,sort);
		String resultJson = genSuccessResult(list);
		return resultJson;
	}
	
	/**
	 * 通过子类别ID获取商品概要列表
	 * 
	 * @param subCategoryId
	 * @return
	 */
	@RequestMapping("/getGoodsList.api")
	@ResponseBody
	public String getGoodsListByCategoryId(int subCategoryId) {
		List<Map<String, Object>> list = goodsService
				.findGoodsByCategoryId(subCategoryId);
		String resultJson = genSuccessResult(list);
		return resultJson;
	}

	/**
	 * 获取单个商品详情
	 * 
	 * @param gId
	 * @return
	 */
	@RequestMapping("/getGoodsById.api")
	@ResponseBody
	public String getGoodsById(long gId) {
		Map<String, Object> goodsMap = goodsService.findGoodsById(gId);
		List<Map<String, Object>> ImgList = goodsService.findGoodsImg((new Long(gId).intValue()));
		goodsMap.put("DetialImg", ImgList);
		String resutJson = genSuccessResult(goodsMap);
		return resutJson;
	}
	
	/**
	 * 展现相关规格属性
	 * 
	 * @param subcategoryId
	 * @param gId
	 * @return
	 */
	@RequestMapping("/showPropertiesByPId.api")
	@ResponseBody
	public String showPropertiesByPId(int subcategoryId, long gId) {
		List<Map<String, Object>> list = goodsService.findAllProperties(gId,
				subcategoryId);
		String resultJson = genSuccessResult(list);
		return resultJson;
	}

	/**
	 * 展现相关规格属性
	 * 
	 * @param propertyTableName
	 * @param gId
	 * @return
	 */
	@RequestMapping("/showPropertiesByPName.api")
	@ResponseBody
	public String showPropertiesByPName(String propertyTableName, long gId) {
		List<Map<String, Object>> list = goodsService.findAllProperties(gId,
				propertyTableName);
		String resultJson = genSuccessResult(list);
		return resultJson;
	}

}
