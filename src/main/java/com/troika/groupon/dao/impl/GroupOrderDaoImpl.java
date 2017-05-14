package com.troika.groupon.dao.impl;

import java.util.ArrayList;
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
import com.troika.groupon.dao.GroupOrderDao;
import com.troika.groupon.model.TGrouponOrder;
import com.troika.groupon.model.TGrouponOrderDetails;

@Repository
public class GroupOrderDaoImpl implements GroupOrderDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public TGrouponOrder saveOrUpdate(TGrouponOrder order) {
		return entityManager.merge(order);
	}

	@Transactional
	public void saveOrUpdateDetails(List<TGrouponOrderDetails> details) {
		for (int i = 0; i < details.size(); i++) {
			TGrouponOrderDetails detail = details.get(i);
			entityManager.persist(detail);
			if (i % 10 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
	}

	@Transactional(readOnly = true)
	public TGrouponOrder findTGrouponOrderById(Integer id) {
		List<TGrouponOrder> list = new ArrayList<TGrouponOrder>();
		Query query = entityManager
				.createNamedQuery("TGrouponOrder.findOrderById");
		query.setParameter("id", id);
		list = query.getResultList();
		return list.get(0);
	}

	@Transactional(readOnly = true)
	public void updOrderStatus(Integer Id, Integer status) {
		String sql = "update t_groupon_order set status=? where id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, status.toString());
		query.setParameter(2, Id);
		query.executeUpdate();
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getOrderListByProjectId(Integer projectId) {
		// ①根据用户id获取他所拥有的订单，存入list<map>，一个订单对应一个map对象
		Query query = entityManager
				.createNativeQuery("select o.id,o.amount,o.createTime,u.realName,u.userName,u.iconUrl,p.name,o.reason,o.status,u.phone,o.addrId "
						+ " from t_groupon_order o "
						+ " left join t_groupon_project p on o.projectId=p.id "
						+ " left join t_mall_user u on u.id=o.userId "
						+ " where o.projectId=? and o.status<>0 order by o.createTime desc");
		query.setParameter(1, projectId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		// ②按订单的id依次获取每个订单包含的多个产品信息存入list2<map>，每个产品信息对应一个map,
		Query queryDetail = entityManager
				.createNativeQuery("select d.*,p.ProductName,p.Icon from t_groupon_order_details d "
						+ " left join t_groupon_product p on d.productId = p.id "
						+ " where d.orderId = ?");
		for (Map<String, Object> map : list) {
			if(!map.get("iconUrl").toString().contains("http://wx"))
				map.put("iconUrl", Constant.PHOTO_URL + map.get("iconUrl"));
			queryDetail.setParameter(1, map.get("id"));
			queryDetail.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> productList = queryDetail.getResultList();
			for (Map<String, Object> item : productList)
				item.put("Icon", Constant.PHOTO_URL + item.get("Icon"));
			map.put("productList", productList);
		}
		// ③将每个订单id对应的存放产品信息的list2<map>放入订单对象map中
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getOrderListByUser(long userId, int status) {
		// ①根据用户id获取他所拥有的订单，存入list<map>，一个订单对应一个map对象
		Query query = entityManager
				.createNativeQuery("select o.id,o.amount,o.createTime,u.realName,p.name "
						+ " from t_groupon_order o "
						+ " left join t_groupon_project p on o.projectId=p.id "
						+ " left join t_mall_user u on u.id=o.userId "
						+ " where o.userId=? and o.status=? order by o.createTime desc");
		query.setParameter(1, userId);
		query.setParameter(2, status);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		// ②按订单的id依次获取每个订单包含的多个产品信息存入list2<map>，每个产品信息对应一个map,
		Query queryDetail = entityManager
				.createNativeQuery("select d.num,d.price,d.unit,p.Icon,p.id from t_groupon_order_details d "
						+ " left join t_groupon_product p on d.productId = p.id "
						+ " where d.orderId = ?");
		for (Map<String, Object> map : list) {
			queryDetail.setParameter(1, map.get("id"));
			queryDetail.unwrap(SQLQuery.class).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> productList = queryDetail.getResultList();
			for (Map<String, Object> item : productList)
				item.put("Icon", Constant.PHOTO_URL + item.get("Icon"));
			map.put("productList", productList);
		}
		// ③将每个订单id对应的存放产品信息的list2<map>放入订单对象map中
		return list;

	}

	/**
	 * 获取产品剩余数量
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getProductSurplus(Integer ProductId) {
		String sql = "select c.id,c.num,c.ProductName,(c.num-IFNULL(sum(b.num),0)) as Surplus,IFNULL(sum(b.num),0) as OrderNum  from t_groupon_product c "
				+ "LEFT JOIN t_groupon_order_details b on c.id=b.productId "
				+ "LEFT join t_groupon_order a on a.id=b.orderId "
				+ "where c.id=? and a.`status`<>0 group by c.id,c.num,c.ProductName";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, ProductId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list.size() == 0 ? null : list.get(0);
	}

	/**
	 * 通过订单主键获取项目发起人信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> findCreatedUserByOrder(Integer id) {
		String sql = "select a.orderNO,b.`name`,c.* from t_groupon_order a join t_groupon_project b on a.projectId=b.id "
				+ "join t_mall_user c on b.createUser=c.id where a.id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, id);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list.size() == 0 ? null : list.get(0);
	}

	/**
	 * 通过订单编号获取订单数据
	 */
	@SuppressWarnings("unchecked")
	@Override
	public TGrouponOrder findOrderByOrderNo(String orderNo) {
		// TODO Auto-generated method stub
		List<TGrouponOrder> list = new ArrayList<TGrouponOrder>();
		Query query = entityManager
				.createQuery("SELECT t FROM TGrouponOrder t WHERE t.orderno=?");
		query.setParameter(1, orderNo);
		list = query.getResultList();
		return list.get(0);
	}
}
