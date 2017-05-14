package com.troika.emall.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class SysParams {
	public static Map<String,String> sysParam = new HashMap<String,String>();
	
	
	/**
	 * 获取系统参数�?
	 * @param paramName 参数�?
	 * @return
	 */
	public static String get(String paramName){
		return sysParam.get(paramName);
	}


	/**
	 * 获取网站根URL
	 * @return
	 */
	public static String getSiteRootUrl(){
		return sysParam.get("site_root_url");
	}
	
	/**
	 * 项目上下文路�?
	 * @return
	 */
	public static String getContextPath(){
		return sysParam.get("context_path");
	}
	
	/**
	 * 获取首页URL
	 * @return
	 */
	public static String getHomeUrl(){
		return sysParam.get("home_url");
	}
	
	/**
	 * 创建微信菜单URL
	 * @return
	 */
	public static String getWxMenuCreateUrl(){
		return sysParam.get("wx_menu_create_url");
	}
	
	/**
	 * 阿里云资源URL
	 * @return
	 */
	public static String getAliYunBaseUrl(){
		return sysParam.get("ali_yun_base_url");
	}
	
	
	/**
	 *公众号服务器地址商家ID参数�?
	 * @return
	 */
	public static String getWxUrlParamName(){
		return sysParam.get("wx_url_param_name");
	}
	
	/**
	 *获取 ActiveMQ URL
	 * @return
	 */
	public static String getActiveMqUrl(){
		return sysParam.get("active_mq_url");
	}
	
	/**
	 * ��ȡ��ҳURL������·����
	 * @return
	 */
	public static String getHomeActionUrl(Object params) {
		StringBuilder sb = new StringBuilder();
		sb.append(getSiteRootUrl());
		sb.append(getContextPath());
		sb.append(getHomeUrl());
		sb.append(params);
		return sb.toString();
	}
	
	/**
	 * ��ȡҳ��¼��С
	 * @return
	 */
	public static int getPageSize(){
		int pageSize = IntUtil.convertToInt(sysParam.get("page_size"));
		return pageSize > 0 ? pageSize : 20;
	}
	
	
}




