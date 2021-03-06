package com.troika.groupon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.troika.emall.util.Constant;

public class BaseController {
	public Logger logger = LogManager.getLogger(this.getClass());

	public String genResult(Object obj,String status){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("data", obj);
		result.put("status", status);
		return new Gson().toJson(result);
	}
	public String genResult(String status){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", status);
		return new Gson().toJson(result);
	}
	public String genSuccessOrderList(Map<Long,List<Map<String,Object>>> map){
		Iterator<Entry<Long,List<Map<String,Object>>>> it = map.entrySet().iterator();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		while(it.hasNext()){
			Entry<Long,List<Map<String,Object>>> entry = it.next();
			Map<String,Object> temp = new HashMap<String,Object>();
			temp.put("id",entry.getKey());
			temp.put("list",entry.getValue());
			list.add(temp);
		}
		return genResult(list, Constant.STATUS_SUC);
	}
	/**
	 * 成功返回
	 * @param obj
	 * @return
	 */
	public String genSuccessResult(Object obj){
		return genResult(obj, Constant.STATUS_SUC);
	}
	/**
	 * 失败
	 * @param obj
	 * @return
	 */
	public String genFailResult(Object obj){
		return genResult(obj, Constant.STATUS_FAIL);
	}
	
	public String genSuccessResult(){
		return genResult(Constant.STATUS_SUC);
	}
	public String genFailResult(){
		return genResult(Constant.STATUS_FAIL);
	}
	/**
	 * 未登录
	 * @return
	 */
	public String genFailNotLogin(){
		return genResult(Constant.NOT_LOGGED_IN);
	}
}
