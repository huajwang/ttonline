package com.troika.emall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.troika.emall.dao.CategoryDao;
import com.troika.emall.model.TMallCategory;
import com.troika.emall.model.TMallSubcategory;
import com.troika.emall.service.CategoryService;
import com.troika.emall.util.Constant;

/**
 * 类别处理服务
 *
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl implements CategoryService{

	@Autowired
	private CategoryDao categoryDao;

	@Override
	/**
	 * 获取所有大类
	 */
	public List<TMallCategory> getAllCategory() {
		List<TMallCategory> listCategory = categoryDao.getAllCategory();
		return listCategory;
	}

	@Override
	/**
	 * 获取大类下的所有子类
	 * @param categoryId int 大类ID 
	 */
	public List<TMallSubcategory> getSubcategoryByCategoryId(int categoryId) {
		List<TMallSubcategory> subcategories=  categoryDao.getSubcategoryByCategoryId(categoryId);
		for (TMallSubcategory subcategory : subcategories) {
			subcategory.setIconUrl(Constant.PHOTO_URL + subcategory.getIconUrl());
		}
		return subcategories;
	}

}
