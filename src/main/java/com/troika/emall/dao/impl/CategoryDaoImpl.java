package com.troika.emall.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.CategoryDao;
import com.troika.emall.model.TMallCategory;
import com.troika.emall.model.TMallSubcategory;

@Repository
public class CategoryDaoImpl implements CategoryDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TMallCategory> getAllCategory() {
		return entityManager.createNamedQuery("findAllCategory").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<TMallSubcategory> getSubcategoryByCategoryId(int categoryId) {
		return entityManager.createQuery("select t from TMallSubcategory t where t.parentId=? order by t.showOrder")
		.setParameter(1, categoryId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public TMallSubcategory getSubcategoryById(int id) {
		List<TMallSubcategory> list = entityManager.createQuery("select t from TMallSubcategory t where t.subcategoryId=?")
		.setParameter(1, id).getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

}
