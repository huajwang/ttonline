package com.troika.groupon.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.util.Constant;
import com.troika.groupon.dao.GroupComplainDao;
import com.troika.groupon.model.TGrouponComplain;
import com.troika.groupon.model.TGrouponComplainImg;
@Repository
@Transactional
public class GroupComplainDaoImpl implements GroupComplainDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public TGrouponComplain saveOrUpdate(TGrouponComplain complain) {
		return entityManager.merge(complain);
	}

	@Override
	@Transactional
	public void saveOrUpdateImgs(List<TGrouponComplainImg> imgs) {
		for (int i = 0; i < imgs.size(); i++) {
			TGrouponComplainImg img = imgs.get(i);
			entityManager.persist(img);
			if (i % 10 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}
	@Transactional(readOnly = true)
	public Map<Integer,List<Map<String,Object>>> getComplainList(Integer userId){
		Query query = entityManager
				.createNativeQuery("SELECT p.id as projectId,p.name as projectName,u.realName as createUserName, "
				 + " ci.complainId as complainId,c.content,c.status,c.result,ci.icon "
				 + " FROM t_groupon_project p "
				 + " left join t_mall_user u on u.id=p.createUser "
				 + " left join t_groupon_complain c on c.projectId=p.id "
				 + " left join t_groupon_complain_Img ci on c.id=ci.complainId "
				 + " where u.id=?");
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		Map<Integer, List<Map<String, Object>>> result = new HashMap<Integer, List<Map<String, Object>>>();
		Map<Integer,List<String>> imgMap = new HashMap<Integer,List<String>>();
		for (Map<String, Object> map : list) {
			boolean iconExists = false;
			Object iconUrl = map.get("icon");
			if (iconUrl != null && !"".equals(iconUrl)) {
				iconExists = true;
				map.put("icon", Constant.PHOTO_URL + iconUrl.toString());
			}
			Integer projectId = (Integer) map.get("projectId");
			Integer complainId = (Integer) map.get("complainId");
			if (result.containsKey(projectId)) {
				List<Map<String, Object>> tempList = result.get(projectId);
				tempList.add(map);
				if(imgMap.containsKey(complainId)){
					if(iconExists){
						List<String> iconList = imgMap.get(complainId);
						iconList.add((String) map.remove("icon"));
						imgMap.put(complainId, iconList);
					}
				}else{
					if(iconExists){
						List<String> iconList = new ArrayList<String>();
						iconList.add((String) map.remove("icon"));
						imgMap.put(complainId, iconList);
					}
				}
			} else {
				List<Map<String, Object>> tempList = new ArrayList<Map<String, Object>>();
				tempList.add(map);
				if(iconExists){
					List<String> iconList = new ArrayList<String>();
					iconList.add((String) map.remove("icon"));
					imgMap.put(complainId, iconList);
				}
				result.put(projectId, tempList);
			}
		}
		Set<Integer> set = new HashSet<Integer>();
		Iterator<Entry<Integer, List<Map<String, Object>>>> it = result.entrySet().iterator();
		while(it.hasNext()){
			Entry<Integer, List<Map<String, Object>>> entry = it.next();
			List<Map<String, Object>> complains = entry.getValue();
			for(int i= 0;i<complains.size();i++){
				Map<String, Object> map = complains.get(i);
				Integer complainId = (Integer) map.remove("complainId");
				if(set.contains(complainId)){
					complains.remove(i);
					i--;
					continue;
				}
				set.add(complainId);
				List<String> Icon = imgMap.containsKey(complainId) ? imgMap.get(complainId): new ArrayList<String>();
				map.put("Icon", Icon);
			}
		}
		return result;
	}
}
