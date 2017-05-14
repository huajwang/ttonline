package com.troika.emall.dao.impl;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.dao.BaseDao;
import com.troika.emall.util.Page;
@Repository
@Transactional
public class BaseDaoImpl<T> implements BaseDao<T>{
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	public Page<T> getPageList(Page<T> page,String sql,Map<String,Object> paramsMap){
		Map<String,Object> sqlMap = parseSql(sql, paramsMap);
		String querySql = (String) sqlMap.get("sql");
		Object[] objs = (Object[]) sqlMap.get("obj");
		Query query =  entityManager.createNativeQuery(querySql);
		for(int i=0;i<objs.length;i++){
			query.setParameter(i+1, objs[i]);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		long total = query.getResultList().size();
		int start = (page.getPageNo() - 1)*page.getPageSize();
		if(start < total){
			query.setFirstResult(start);
			query.setMaxResults(page.getPageSize());
			page.setPageNo(page.getPageNo() + 1);
		}
		page.setTotalRecords(total);
		page.setPageResult(query.getResultList());
		return page;
	}
	
	private long pageCount(String countSql,Map<String,Object> paramsMap){
		Map<String,Object> sqlMap = parseSql(countSql, paramsMap);
		String querySql = (String) sqlMap.get("sql");
		Object[] objs = (Object[]) sqlMap.get("obj");
		Query query =  entityManager.createNativeQuery(querySql);
		for(int i=0;i<objs.length;i++){
			query.setParameter(i+1, objs[i]);
		}
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		return 0;
	}
	
	private Map<String,Object> parseSql(String sql,Map<String,Object> paramsMap){
		String reg = "\\#\\{([^\\#\\{\\}]+)\\}";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(sql);
		Object[] objs = new Object[paramsMap.size()];
		int count=0;
		while(m.find()){
			objs[count] = paramsMap.get(m.group(1));
			count++;
		}
		sql = sql.replaceAll(reg, "?");
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("sql", sql);
		result.put("obj", objs);
		return result;
	}

}
