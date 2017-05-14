package com.troika.emall.restapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.emall.model.TMallCategory;
import com.troika.emall.model.TMallSubcategory;
import com.troika.emall.service.CategoryService;

@Controller
@RequestMapping("/sys/category/")
public class CategoryController extends BaseController{
	@Autowired
	private CategoryService categoryService;
	
	/**
	 * 获取所有大类
	 * @return
	 */
	@RequestMapping("/getAllCategory.api")
	@ResponseBody
	public String getAllCategory(){
		List<TMallCategory> list = categoryService.getAllCategory();
		return genSuccessResult(list);
	}
	/**
	 * 根据Id获取所有小类
	 * @param categoryId
	 * @return
	 */
	@RequestMapping("/getAllSubCategory.api")
	@ResponseBody
	public String getAllSubCategory(int categoryId){
		List<TMallSubcategory> list = categoryService.getSubcategoryByCategoryId(categoryId);
		return genSuccessResult(list);
	}
	
}
