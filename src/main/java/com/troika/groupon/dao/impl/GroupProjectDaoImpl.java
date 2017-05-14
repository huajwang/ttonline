package com.troika.groupon.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.util.Constant;
import com.troika.groupon.dao.GroupProjectDao;
import com.troika.groupon.model.TGrouponProject;

@SuppressWarnings("unchecked")
@Repository
@Transactional
public class GroupProjectDaoImpl implements GroupProjectDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public TGrouponProject saveOrUpdateProject(TGrouponProject project) {
		return entityManager.merge(project);
	}

	@Transactional
	public void deleteProject(Integer projectId) {
		entityManager.createQuery("delete from TGrouponProject t where t.id=?")
				.setParameter(1, projectId).executeUpdate();
	}

	@Transactional(readOnly = true)
	public TGrouponProject findProjectById(Integer id) {
		Query query = entityManager
				.createQuery("select t from TGrouponProject t where t.id=?");
		query.setParameter(1, id);
		List<TGrouponProject> list = query.getResultList();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> findProjectMapById(Integer id) {
		Query query = entityManager
				.createNativeQuery("select t.*,TO_DAYS(t.endTime)-TO_DAYS(t.createTime) as dayNum,u.userName,u.iconUrl,"
						+ "(select count(*) from t_groupon_order where projectId=t.id and status<>0) as orderNum from t_groupon_project t "
						+ "left join t_mall_user u on u.id = t.createUser where t.id=?");
		query.setParameter(1, id);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> findAllProject() {
		Query query = entityManager
				.createNativeQuery("select *from t_groupon_project t ");
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}

	/**
	 * 获取各个状态的项目列表数据
	 */
	@Override
	public List<Map<String, Object>> findProjectByStatus(Integer userId,
			Integer status) {
		// TODO Auto-generated method stub
		Query query = entityManager
				.createNativeQuery("select id,name,amount,createTime,opinion,to_days(endTime) -to_days(now()) as day from t_groupon_project where createUser=? and status=? order by createTime desc ");
		query.setParameter(1, userId);
		query.setParameter(2, status);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		entityManager.clear();//清空内存
		entityManager.close();//关闭
		return list;
	}

	/**
	 * 获取最新项目列表
	 */
	@Override
	public List<Map<String, Object>> findNewProjectByList() {
		// TODO Auto-generated method stub
		String sql = "select a.id,a.name,a.createUser,b.userName as username,b.iconUrl,a.amount,a.endTime,"
				+ "(select count(*) from t_groupon_order where projectId=a.id) as orderNum,"
				+ "IFNULL((select sum(amount) from t_groupon_order where projectId=a.id and status<>0),0) as orderMoney,"
				+ "a.remark,a.createTime,to_days(a.endTime) -to_days(now()) as day "
				+ "from t_groupon_project a LEFT JOIN t_mall_user b on a.createUser=b.id where a.status=2 "
				+ "order by a.createTime DESC";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		// 添加图片列表
		for (Map<String, Object> map : list) {
			if(!map.get("iconUrl").toString().contains("http://wx"))
				map.put("iconUrl", Constant.PHOTO_URL+map.get("iconUrl"));
			String id = map.get("id").toString();// 项目主键
			// 获取项目随机4张图
			sql = "select CONCAT('"
					+ Constant.PHOTO_URL
					+ "',icon) as icon from t_groupon_project_img where projectId=? limit 4";
			Query query1 = entityManager.createNativeQuery(sql);
			query1.setParameter(1, id);
			query1.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> ImgList = query1.getResultList();
			map.put("ImgList", ImgList);
		}
		entityManager.clear();//清空内存
		entityManager.close();//关闭
		return list;
	}

	/**
	 * 获取精选项目列表
	 */
	@Override
	public List<Map<String, Object>> findSelectedProjectByList() {
		// TODO Auto-generated method stub
		String sql = "select a.id,a.name,a.createUser,b.userName as username,b.iconUrl,a.amount,a.endTime,"
				+ "(select count(*) from t_groupon_order where projectId=a.id) as orderNum,"
				+ "IFNULL((select sum(amount) from t_groupon_order where projectId=a.id and status<>0),0) as orderMoney,"
				+ "a.remark,a.createTime,to_days(a.endTime) -to_days(now()) as day "
				+ "from t_groupon_project_selected s join "
				+ "t_groupon_project a on s.projectId=a.id "
				+ "LEFT JOIN t_mall_user b on a.createUser=b.id where a.status=2 and s.IsSelected=1 "
				+ "order by s.sequence";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		// 添加图片列表
		for (Map<String, Object> map : list) {
			if(!map.get("iconUrl").toString().contains("http://wx"))
				map.put("iconUrl",Constant.PHOTO_URL+map.get("iconUrl"));
			String id = map.get("id").toString();// 项目主键
			// 获取项目随机4张图
			sql = "select CONCAT('"
					+ Constant.PHOTO_URL
					+ "',icon) as icon from t_groupon_project_img where projectId=? limit 4";
			Query query1 = entityManager.createNativeQuery(sql);
			query1.setParameter(1, id);
			query1.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> ImgList = query1.getResultList();
			map.put("ImgList", ImgList);
		}
		entityManager.clear();//清空内存
		entityManager.close();//关闭
		return list;
	}

	/**
	 * 通过发起人id获取项目列表
	 */
	@Override
	public List<Map<String, Object>> findProjectByCreateUser(Integer createUser) {
		String sql = "select * from t_groupon_project where createUser = ?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, createUser);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		entityManager.clear();//清空内存
		entityManager.close();//关闭
		return list;
	}
}
