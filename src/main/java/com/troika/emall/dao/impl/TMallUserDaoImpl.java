package com.troika.emall.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.druid.sql.ast.expr.SQLCaseExpr.Item;
import com.troika.emall.dao.TMallUserDao;
import com.troika.emall.model.TMallFeedback;
import com.troika.emall.model.TMallUser;
import com.troika.emall.util.Constant;

@Repository
public class TMallUserDaoImpl implements TMallUserDao {
	
	public Logger logger = LogManager.getLogger(this.getClass());

	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public TMallUser findUserByUserPropeties(String keyword) {
		List<TMallUser> list = entityManager
				.createQuery(
						"select t from TMallUser t where t.phone=? or email=?")
				.setParameter(1, keyword).setParameter(2, keyword)
				.getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public TMallUser findUserByPhone(String phone) {
		List<TMallUser> list = entityManager
				.createQuery("select t from TMallUser t where t.phone=?")
				.setParameter(1, phone).getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Transactional
	public TMallUser saveOrUpdateUser(TMallUser user) {
		logger.debug("存储用户数据到数据库里: " + user);
		return entityManager.merge(user);
	}

	@Transactional
	public void updatePassword(long userId, String password) {
		String sql = "update t_mall_user set password=? where id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, password);
		query.setParameter(2, userId);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void saveOrUpdateFeedback(TMallFeedback record) {
		entityManager.merge(record);
	}

	/**
	 * 获取分享交易记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getShareTrades(long userId) {
		// Query query = entityManager
		// .createNativeQuery("select d.phone,a.orderId,a.amount,a.createTime from t_mall_order a "
		// + "join t_mall_user d on a.userId=d.id where a.introducer=?");
		Query query = entityManager
				.createNativeQuery("select m_user.phone,m_user.userName,m_order.orderId,c_record.amount,c_record.createTime,"
						+ "(case when c_record.state='1' then '已完成' when c_record.state='0' "
						+ "then '未完成' ELSE '取消' end) as state from t_mall_order m_order "
						+ "join t_mall_commission_record c_record on m_order.id=c_record.orderId "
						+ "join t_mall_order_detail o_detail on m_order.id=o_detail.orderId "
						//+ "join t_mall_goods goods on o_detail.gId=goods.id "
						+ "join t_mall_user m_user on m_order.userId=m_user.id  where m_order.introducer=?");
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}

	/**
	 * 获取创客交易记录
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getMakerTrades(String makerId) {
		Query query = entityManager
				.createNativeQuery("select d.phone,d.userName,c.orderId,a.quantity,a.amount,a.createTime,"
						+ "(case when a.state='1' then '已完成' when a.state='0' then '未完成' "
						+ "ELSE '取消' end) as state from t_mall_maker_commission_record a "
						//+ "join t_mall_goods b on a.gid=b.id "
						+ "join t_mall_order c on a.orderId=c.id "
						+ "join t_mall_user d on c.userId=d.id "
						+ "where a.makerId=?");
		query.setParameter(1, makerId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}

	/**
	 * 获取用户的分享创客的收益记录
	 * 
	 * @param userId
	 * @param makerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Map<String, Object> getProfitRecord(long userId, String makerId) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select (select IFNULL(sum(amount),0) as sum "
				+ "from t_mall_commission_record where introducer=? and state=0) as cr0 "// 分享-未结算金额
				+ ", "
				+ "(select IFNULL(sum(amount),0) as sum "
				+ "from t_mall_commission_record where introducer=? ) as cr1"// 分享金额
				+ ", "
				+ "(select IFNULL(sum(amount),0) as sum "
				+ "from t_mall_maker_commission_record where makerId=? ) as mcr0"// 创客金额
				+ ", "
				+ "(select IFNULL(sum(amount),0) as sum "
				+ "from t_mall_maker_commission_record where makerId=? and state=1 ) as mcr1";// 创客-已结算金额
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, userId);
		query.setParameter(2, userId);
		query.setParameter(3, makerId);
		query.setParameter(4, makerId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list.get(0);
	}

	/**
	 * 获取用户钱包
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getUserWallets(long userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		String sql = "select wxwallet,alipaywallet from t_mall_user where id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, userId);
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}

	/**
	 * 修改用户钱包
	 */
	@Override
	@Transactional
	public void updateUserWallets(long userId, String alipay, String wx) {
		String sql = "update t_mall_user set alipaywallet=? , wxwallet=? where id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, alipay);
		query.setParameter(2, wx);
		query.setParameter(3, userId);
		query.executeUpdate();
	}

	/**
	 * 统计佣金数据
	 * 
	 * @param userId
	 * @param makerId
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public Map<String, Object> getProfitSum(long userId, String makerId,
			int type) {
		String sql = "";
		Query query = null;
		switch (type) {
		case 1:// 分享——昨日
			sql = "select IFNULL(sum(amount),0) as sum,count(amount) as count "
					+ "from t_mall_commission_record "
					+ "where introducer=? and left(createTime,10)= left(DATE_ADD(NOW(),INTERVAL -1 DAY),10)";
			query = entityManager.createNativeQuery(sql);
			query.setParameter(1, userId);
			break;
		case 2:// 分享——近7天
			sql = "select IFNULL(sum(amount),0) as sum,count(amount) as count "
					+ "from t_mall_commission_record "
					+ "where introducer=? and left(createTime,10)>= left(DATE_ADD(NOW(),INTERVAL -1 WEEK),10)";
			query = entityManager.createNativeQuery(sql);
			query.setParameter(1, userId);
			break;
		case 3:// 分享——近一个月
			sql = "select IFNULL(sum(amount),0) as sum,count(amount) as count "
					+ "from t_mall_commission_record "
					+ "where introducer=? and left(createTime,10)>= left(DATE_ADD(NOW(),INTERVAL -1 MONTH),10)";
			query = entityManager.createNativeQuery(sql);
			query.setParameter(1, userId);
			break;
		case 4:// 创客——昨日
			sql = "select IFNULL(sum(amount),0) as sum,count(amount) as count "
					+ "from t_mall_maker_commission_record "
					+ "where makerId=? and left(createTime,10)= left(DATE_ADD(NOW(),INTERVAL -1 DAY),10)";
			query = entityManager.createNativeQuery(sql);
			query.setParameter(1, makerId);
			break;
		case 5:// 创客——近7天
			sql = "select IFNULL(sum(amount),0) as sum,count(amount) as count "
					+ "from t_mall_maker_commission_record "
					+ "where makerId=? and left(createTime,10)>= left(DATE_ADD(NOW(),INTERVAL -1 WEEK),10)";
			query = entityManager.createNativeQuery(sql);
			query.setParameter(1, makerId);
			break;
		case 6:// 创客——近一个月
			sql = "select IFNULL(sum(amount),0) as sum,count(amount) as count "
					+ "from t_mall_maker_commission_record "
					+ "where makerId=? and left(createTime,10)>= left(DATE_ADD(NOW(),INTERVAL -1 MONTH),10)";
			query = entityManager.createNativeQuery(sql);
			query.setParameter(1, makerId);
			break;
		}
		query.unwrap(SQLQuery.class).setResultTransformer(
				Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list.get(0);
	}

	@Override
	@Transactional
	public void updateWallet(long userId, int accType, String account) {
		String sql = "";
		if (accType == Constant.WALLET_TYPE_ALIPAY) {
			sql = "update t_mall_user set alipaywallet=? where id=?";
		} else if (accType == Constant.WALLET_TYPE_WX) {
			sql = "update t_mall_user set wxwallet=? where id=?";
		}
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, account);
		query.setParameter(2, userId);
		query.executeUpdate();
	}

	@Override
	@Transactional
	public void updateIconUrl(long id, String iconUrl) {
		String sql = "update t_mall_user set iconUrl=? where id=?";
		Query query = entityManager.createNativeQuery(sql);
		query.setParameter(1, iconUrl);
		query.setParameter(2, id);
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public TMallUser findUserByOpenId(String openId) {
		logger.debug("通过openId查找用户: openId = " + openId);
		List<TMallUser> list = entityManager
				.createQuery("select t from TMallUser t where t.openId=?")
				.setParameter(1, openId).getResultList();
		logger.debug("返回的用户数size = " + list.size());
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过登录用户获取用户信息
	 * 
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public TMallUser findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		List<TMallUser> list = entityManager
				.createQuery("select t from TMallUser t where t.userName=?")
				.setParameter(1, userName).getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过邮件获取用户信息
	 * 
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public TMallUser findUserByEmail(String email) {
		List<TMallUser> list = entityManager
				.createQuery("select t from TMallUser t where t.email=?")
				.setParameter(1, email).getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public TMallUser findUserById(long id) {
		List<TMallUser> list = entityManager
				.createQuery("select t from TMallUser t where t.id=?")
				.setParameter(1, id).getResultList();
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 *隐藏字符处理 
	 * @param list  交易记录列表
	 * @return
	 */
	@Override
	public List<Map<String, Object>> hide(List<Map<String, Object>> list) {
		for(Map<String,Object> item:list){
			String str = (String)item.get("orderId");
			item.put("orderId", str.substring(str.length()-4, str.length()));
			//分享交易记录买家隐藏处理
			if(item.get("phone") != null && item.get("phone") != ""){
				String str1 = (String)item.get("phone");
				item.put("phone", str1.substring(0,3) + "***" + str1.substring(str1.length()-4));
			}else if (item.get("userName") != null && item.get("userName") != ""
					&& (item.get("userName") + "").length() > 3
					&& !"null".equals(item.get("userName") + "")) {
				String lastOne = item
						.get("userName")
						.toString()
						.substring(item.get("userName").toString().length() - 1);
				item.put("userName",
						item.get("userName").toString().substring(0, 2) + "***"
								+ lastOne);
			}
		}
		return list;
	}
}
