package com.troika.emall.dao;

import java.util.List;

import com.troika.emall.model.TMallCategory;
import com.troika.emall.model.TMallSubcategory;

public interface CategoryDao {
	public List<TMallCategory> getAllCategory();
	public List<TMallSubcategory> getSubcategoryByCategoryId(int categoryId);
	public TMallSubcategory getSubcategoryById(int id);
}
