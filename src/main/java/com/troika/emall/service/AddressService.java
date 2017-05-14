package com.troika.emall.service;

import java.util.List;
import java.util.Map;

import com.troika.emall.model.TMallAddress;

public interface AddressService {
	/**
	 * 修改保存地址
	 * @param addr
	 */
	public void saveOrUpdate(TMallAddress addr);
	/**
	 * 获取用户的地址列表
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> findAllAddress(long userId);
	/**
	 * 根据ID获取地址
	 * @param id
	 * @return
	 */
	public Map<String,Object> findAddressById(long id);
	/**
	 * 默认地址
	 * @param userId
	 * @return
	 */
	public Map<String,Object> findActiveAddress(long userId);
	/**
	 * 删除地址
	 * @param id
	 * @return
	 */
	public int delAddressById(long id);
	/**
	 * 设置默认地址
	 * @param userId
	 * @param addressId
	 * @return
	 */
	public int setDefault(long id,long userId);
	
}
