package com.troika.emall.dao;

import java.util.List;

import com.troika.emall.model.TMallReturnImg;
import com.troika.emall.model.TMallReturnRecord;

public interface ExchangeDao {
	public void saveExchangeImgs(List<TMallReturnImg> list);
	public void saveReturnRecord(TMallReturnRecord exchange);
}
