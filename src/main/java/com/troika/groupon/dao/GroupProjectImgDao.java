package com.troika.groupon.dao;

import java.util.List;
import java.util.Map;

import com.troika.groupon.model.TGrouponProjectImg;

public interface GroupProjectImgDao {
	public void saveOrUpdateProjectImg(List<TGrouponProjectImg> imgs);
	public void delProjectImg(Integer projectId,Integer Icon);
	public List<Map<String,Object>> findImgsByProjectId(Integer projectId);
}
