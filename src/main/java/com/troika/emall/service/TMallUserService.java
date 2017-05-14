package com.troika.emall.service;

import java.util.List;
import java.util.Map;

import com.troika.emall.model.TMallFeedback;
import com.troika.emall.model.TMallUser;

public interface TMallUserService {
	/**
	 * 用户登录
	 * @param property
	 * @param password
	 * @return
	 */
	public Map<String,Object> login(String property,String password);
	/**
	 * 用户注册
	 * @param phone
	 * @param password
	 * @return
	 */
	public Map<String,Object> register(String phone,String password);
	/**
	 * 重设密码
	 * @param phone
	 * @param password
	 * @return
	 */
	public Map<String,Object> reSetPassword(String phone,String password);
	/**
	 * 保存用户的反馈意见
	 * @param record
	 */
	public void saveOrUpdateFeedback(TMallFeedback record);
	/**
	 * 修改用户信息
	 * @param user
	 */
	public TMallUser saveOrUpdateUser(TMallUser user);
	/**
	 * 获取分享交易记录
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> getShareTrades(long userId);
	/**
	 * 获取创客交易记录
	 * @param makerId  即用户的Phone电话号码数据
	 * @return
	 */
	public List<Map<String,Object>> getMakerTrades(String makerId);
	/**
	 * 获取用户的钱包
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getUserWallets(long userId);
	/**
	 * 修改用户钱包账户
	 * @param alipay
	 * @param wx
	 */
	public void updateUserWallets(long userId,String alipay,String wx);
	/**
	 * 获取用户的分享创客的收益记录
	 * 
	 * @param userId
	 * @param makerId
	 * @return
	 */
	public Map<String, Object> getProfitRecord(long userId, String makerId);
	/**
	 * 统计佣金数据
	 * 
	 * @param userId
	 * @param makerId
	 * @param type
	 * @return
	 */
	public Map<String, Object> getProfitSum(long userId, String makerId,int type);
	
	/**
	 * 
	 * @param id
	 * @param payMethod
	 * @param account
	 */
	public void updateWallet(long id, int accType, String account);
	
	/**
	 * 修改用户头像
	 * @param user
	 */
	public void updateIconUrl(long id,String iconUrl);
	/**
	 * 微信注册
	 * @param openId
	 * @return
	 */
	public Map<String,Object> register(String openId,String phone,String type);

	public TMallUser findUserByOpenId(String openId);
	/** 
	 * 通过手机号码获取用户信息
	 * @param phone
	 * @return
	 */
	public TMallUser findUserByPhone(String phone);
	/**
	 * 通过登录用户获取用户信息
	 * @param userName
	 * @return
	 */
	public TMallUser findUserByUserName(String userName);
	/**
	 * 通过邮件获取用户信息
	 * @param userName
	 * @return
	 */
	public TMallUser findUserByEmail(String email);
	/**
	 * 通过id获取用户信息
	 * @param id
	 * @return TMallUser
	 */
	public TMallUser findUserById(long id);
}
