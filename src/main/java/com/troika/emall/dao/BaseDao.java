package com.troika.emall.dao;

import java.util.Map;

import com.troika.emall.util.Page;

public interface BaseDao<T> {
	public Page<T> getPageList(Page<T> page,String sql,Map<String,Object> paramsMap);
}
