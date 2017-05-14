package com.troika.groupon.dao;

import java.util.List;
import java.util.Map;

import com.troika.groupon.model.TGrouponProduct;

public interface GroupProductDao {
	public void saveOrUpdateProduct(List<TGrouponProduct> products);
	public void delProduct(Integer productId);
	public List<Map<String,Object>> findProductsByProjectId(Integer projectId);
}
