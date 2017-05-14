package com.troika.emall.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troika.emall.util.Constant;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;

public class BaseServiceImpl {
	Logger logger = LogManager.getLogger(this.getClass());

//	@SuppressWarnings("unchecked")
//	/**
//	 * 从缓存里或从数据库里获取数据。 如果缓存里没有相关数据， 则从数据库里读取，然后放入缓存中。
//	 * @param key 所要的内容在ocs缓存里对应的健值
//	 * @param dao 数据层抽象接口。 客户传入对应的具体实现，以执行数据库查询。
//	 * @return
//	 */
//	protected List<Map<String, Object>> getData(String key, CacheableDao dao) {
////		MemcachedClient cache = null;
//		Object value = null;
////		try {
////			cache = new MemcachedClient(new BinaryConnectionFactory(), 
////					AddrUtil.getAddresses(Constant.OCS_HOST + ":" + Constant.OCS_PORT));
////
////			//执行get操作，从缓存中读数据
////			value = cache.get(key);
////			if (value == null) {
////				// 缓存里没有找到， 从数据库读取
//				value = dao.readFromDB();
////				int expireTime = 1000; // 过期时间，单位s; 从写入时刻开始计时，超过expireTime s后，该数据过期失效，无法再读出；
////				// 向OCS中存入数据
////				cache.set(key, expireTime, value);
////			} else {
////				System.out.println(key + " target hit!");
////			}
////
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////		if (cache != null) {
////			cache.shutdown();
////		}
//
//		return (List<Map<String, Object>>) value;
//	}
//
//	/**
//	 * 
//	 * @param <T> generic type
//	 * @param key 在OCS缓存里的健值
//	 * @param dao 实现了CacheableDao2<T> 的concrete class
//	 * @return list of type T object
//	 */
//	@SuppressWarnings("unchecked")
//	protected <T> List<T> readData(String key, CacheableDao2<T> dao) {
////		MemcachedClient cache = null;
//		Object value = null;
////		try {
////			cache = new MemcachedClient(new BinaryConnectionFactory(), 
////					AddrUtil.getAddresses(Constant.OCS_HOST + ":" + Constant.OCS_PORT));
////
////			//执行get操作，从缓存中读数据
////			value = cache.get(key);
////			if (value == null) {
////				// 缓存里没有找到， 从数据库读取
//				value = dao.readFromDB();
////				int expireTime = 1000; // 过期时间，单位s; 从写入时刻开始计时，超过expireTime s后，该数据过期失效，无法再读出；
////				// 向OCS中存入数据
////				cache.set(key, expireTime, value);
////			} else {
////				System.out.println(key + " target hit!");
////			}
////
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////		if (cache != null) {
////			cache.shutdown();
////		}
//
//		return (List<T>) value;
//	}

}
