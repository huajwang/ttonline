package com.troika.emall.dao;

import java.util.List;
import java.util.Map;

import com.troika.emall.model.TMallAddress;

public interface AddressDao {
	public void saveOrUpdate(TMallAddress addr);
	public List<Map<String,Object>> findAllAddress(long userId);
	public Map<String,Object> findAddressById(long id);
	public Map<String,Object> findAddressDetailByOrderId(long orderId);
	public Map<String,Object> findAddressByUserIdAndActive(long userId,int active);
	public int setDefaultAddressByUserIdAndId(long userId,long id);
	public int delAddressById(long id);
}
