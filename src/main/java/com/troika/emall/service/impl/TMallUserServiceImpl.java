package com.troika.emall.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.troika.emall.dao.TMallUserDao;
import com.troika.emall.model.TMallFeedback;
import com.troika.emall.model.TMallUser;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.util.CommonUtil;

/**
 * 用户相关处理
 */
@Service
public class TMallUserServiceImpl implements TMallUserService {

	@Autowired
	private TMallUserDao tMallUserDao;

	@Override
	public Map<String, Object> login(String property, String password) {
		TMallUser user = tMallUserDao.findUserByUserPropeties(property);
		Map<String, Object> result = new HashMap<String, Object>();
		if (user != null) {
			if (user.getPassword().equals(password)) {// 登录检验
				result.put("status", "0");
				result.put("result", user);
				result.put("token", CommonUtil.buildToken());
			} else {
				result.put("status", "2");
			}
		} else {
			result.put("status", "1");
		}
		return result;
	}

	public Map<String, Object> register(String phone, String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		TMallUser tempUser = tMallUserDao.findUserByPhone(phone);
		if (tempUser != null) {
			result.put("msg", "该手机已注册过");
		} else {
			TMallUser user = new TMallUser();
			user.setUserId(CommonUtil.buildUserId());
			user.setPhone(phone);
			user.setPassword(password);
			user.setCreateTime(new Date());
			tMallUserDao.saveOrUpdateUser(user);
		}
		return result;
	}

	/**
	 * 微信注册
	 */
	public Map<String, Object> register(String openId, String phone, String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		TMallUser tempUser;
		tempUser = tMallUserDao.findUserByPhone(phone);
		if (tempUser == null) {
			tempUser = tMallUserDao.findUserByOpenId(openId);
		}
		if (tempUser != null) {
			tempUser.setPhone(phone);
			tempUser.setOpenId(openId);
			tMallUserDao.saveOrUpdateUser(tempUser);
			result.put("msg", "该微信已注册过");
		} else {
			TMallUser user = new TMallUser();
			user.setUserId(CommonUtil.buildUserId());
			user.setPhone(phone);
			// user.setPassword(password);
			user.setOpenId(openId);
			user.setCreateTime(new Date());
			tMallUserDao.saveOrUpdateUser(user);
		}
		return result;
	}

	public Map<String, Object> reSetPassword(String phone, String password) {
		Map<String, Object> result = new HashMap<String, Object>();
		TMallUser tempUser = tMallUserDao.findUserByPhone(phone);
		if (tempUser != null) {
			tMallUserDao.updatePassword(tempUser.getId(), password);
		} else {
			result.put("msg", "此手机未注册");
		}
		return result;
	}

	/**
	 * 保存用户的反馈建议
	 */
	@Override
	public void saveOrUpdateFeedback(TMallFeedback record) {
		// TODO Auto-generated method stub
		tMallUserDao.saveOrUpdateFeedback(record);
	}

	/**
	 * 修改用户信息
	 */
	@Override
	public TMallUser saveOrUpdateUser(TMallUser user) {
		return tMallUserDao.saveOrUpdateUser(user);
	}

	/**
	 * 获取分享交易记录
	 */
	@Override
	public List<Map<String, Object>> getShareTrades(long userId) {
		List<Map<String, Object>> list = tMallUserDao.getShareTrades(userId);
		List<Map<String, Object>> list1 = tMallUserDao.hide(list);
		return list1;
	}

	/**
	 * 获取创客交易记录
	 */
	@Override
	public List<Map<String, Object>> getMakerTrades(String makerId) {
		List<Map<String, Object>> list = tMallUserDao.getMakerTrades(makerId);
		List<Map<String, Object>> list1 = tMallUserDao.hide(list);
		return list1;
	}

	/**
	 * 获取用户的钱包
	 */
	@Override
	public List<Map<String, Object>> getUserWallets(long userId) {
		return tMallUserDao.getUserWallets(userId);
	}

	/**
	 * 修改用户钱包账户
	 * 
	 * @param alipay
	 * @param wx
	 */
	public void updateUserWallets(long userId, String alipay, String wx) {
		tMallUserDao.updateUserWallets(userId, alipay, wx);
	}

	/**
	 * 获取用户的分享创客的收益记录
	 * 
	 * @param userId
	 * @param makerId
	 * @return
	 */
	@Override
	public Map<String, Object> getProfitRecord(long userId, String makerId) {
		// TODO Auto-generated method stub
		return tMallUserDao.getProfitRecord(userId, makerId);
	}

	@Override
	public Map<String, Object> getProfitSum(long userId, String makerId,
			int type) {
		// TODO Auto-generated method stub
		return tMallUserDao.getProfitSum(userId, makerId, type);
	}

	@Override
	/**
	 * @param userId
	 * @param accType 钱包类型
	 * @param account 钱包账号
	 */
	public void updateWallet(long userId, int accType, String account) {
		tMallUserDao.updateWallet(userId, accType, account);
	}

	@Override
	public void updateIconUrl(long id, String iconUrl) {
		tMallUserDao.updateIconUrl(id, iconUrl);

	}

	@Override
	public TMallUser findUserByOpenId(String openId) {
		TMallUser tempUser;
		tempUser = tMallUserDao.findUserByOpenId(openId);
		return tempUser;
	}

	/**
	 * 通过手机号码获取用户信息
	 */
	@Override
	public TMallUser findUserByPhone(String phone) {
		// TODO Auto-generated method stub
		return tMallUserDao.findUserByPhone(phone);
	}

	/**
	 * 通过登录用户获取用户信息
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public TMallUser findUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return tMallUserDao.findUserByUserName(userName);
	}

	/**
	 * 通过邮件获取用户信息
	 * 
	 * @param userName
	 * @return
	 */
	@Override
	public TMallUser findUserByEmail(String email) {
		// TODO Auto-generated method stub
		return tMallUserDao.findUserByEmail(email);
	}

	@Override
	public TMallUser findUserById(long id) {
		return tMallUserDao.findUserById(id);
	}
}
