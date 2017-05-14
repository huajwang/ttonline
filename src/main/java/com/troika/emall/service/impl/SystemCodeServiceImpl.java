package com.troika.emall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.troika.emall.dao.SystemCodeDao;
import com.troika.emall.model.TMallArea;
import com.troika.emall.model.TMallCity;
import com.troika.emall.model.TMallProvince;
import com.troika.emall.service.SystemCodeService;
/**
 * 系统编码
 * @author boa_cool
 *
 */
@Service
public class SystemCodeServiceImpl implements SystemCodeService {
	
	@Autowired
	private SystemCodeDao systemCodeDao;

	@Override
	public List<TMallProvince> findAllProvince() {
		return systemCodeDao.findAllProvince();
	}

	@Override
	public List<TMallCity> findCityByProvinceId(String provinceId) {
		return systemCodeDao.findCityByProvinceId(provinceId);
	}

	@Override
	public List<TMallArea> findAreaByCityId(String cityId) {
		return systemCodeDao.findAreaByCityId(cityId);
	}

}
