package com.troika.emall.dao;

import java.util.List;

import com.troika.emall.model.TMallArea;
import com.troika.emall.model.TMallCity;
import com.troika.emall.model.TMallProvince;

public interface SystemCodeDao {
	public List<TMallProvince> findAllProvince();
	public List<TMallCity> findCityByProvinceId(String provinceId);
	public List<TMallArea> findAreaByCityId(String cityId);
}
