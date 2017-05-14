package com.troika.emall.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.troika.emall.dao.AddressDao;
import com.troika.emall.model.TMallAddress;
import com.troika.emall.service.AddressService;

@Service
public class AddressServiceImpl extends BaseServiceImpl implements
		AddressService {

	@Autowired
	private AddressDao addressDao;
	
	@Override
	public void saveOrUpdate(TMallAddress addr) {
		logger.info("地址添加或者更新开始");
		if (addr.getId() == 0) {
			addr.setCreateTime(new Date());
		} else {
			Map<String, Object> temp = findAddressById(addr.getId());
			addr.setCreateTime((Date) temp.get("createTime"));
			addr.setUpdateTime(new Date());
		}
		addressDao.saveOrUpdate(addr);
		logger.info("地址添加或者更新结束");
	}

	@Override
	public List<Map<String, Object>> findAllAddress(long userId) {
		List<Map<String, Object>> list = addressDao.findAllAddress(userId);
		logger.info("通过用户id【" + userId + "】查找地址列表");
		return list;
	}

	@Override
	public Map<String, Object> findAddressById(long id) {
		Map<String, Object> address = addressDao.findAddressById(id);
		logger.info("通过地址id【" + id + "】查找地址");
		return address;
	}

	public Map<String, Object> findActiveAddress(long userId) {
		logger.info("查找用户【" + userId + "】的默认地址");
		Map<String, Object> addressMap = addressDao
				.findAddressByUserIdAndActive(userId, 1);
		if (addressMap.isEmpty()) {
			logger.info("用户【" + userId + "】没有默认地址");
			addressMap = addressDao.findAddressByUserIdAndActive(userId, 0);
		}
		return addressMap;
	}

	/**
	 * 删除地址
	 */
	@Override
	public int delAddressById(long id) {
		logger.info("删除地址【" + id + "】数据");
		return addressDao.delAddressById(id);
	}

	@Override
	public int setDefault(long id, long userId) {
		int result=0;
		result = addressDao.setDefaultAddressByUserIdAndId(userId, id);
		return result;
	}
}
