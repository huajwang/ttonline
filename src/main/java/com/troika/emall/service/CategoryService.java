package com.troika.emall.service;

import java.util.List;

import com.troika.emall.model.TMallCategory;
import com.troika.emall.model.TMallSubcategory;


public interface CategoryService {
	/**
	 * 获取大分类
	 * @return
	 */
	public List<TMallCategory> getAllCategory();
	/**
	 * 获取子分类
	 * @param categoryId
	 * @return
	 */
	public List<TMallSubcategory> getSubcategoryByCategoryId(int categoryId);
}
