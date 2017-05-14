package com.troika.emall.util;

import java.io.UnsupportedEncodingException;

/**
 * 
 * @author lintg
 * 
 *字符串实体类
 */
public class StringUtil {
	
	/**
	 * 过滤微信昵称或手机端输入 存在的表情等乱码 
	 * @param str
	 * @return
	 */
	public synchronized static String dofilterBiaoQing(String str){
		String str_Result = "";
		try {
			if(str == null || "".equals(str)){
				return "";
			}
			str_Result = "";
			String str_OneStr = "";  
			for (int z = 0; z < str.length(); z++) {  
			   str_OneStr = str.substring(z, z + 1);  
			   if (str_OneStr.matches("[\u4e00-\u9fa5]+") || str_OneStr.matches("[\\x00-\\x7F]+")) {  
			      str_Result = str_Result + str_OneStr;    
			   }  
			}
			
			if("".equals(str_Result)){
				str_Result = " ";
			}
		} catch (Exception e) {
			str_Result = str;
			e.printStackTrace();
		}
		return str_Result;
	}

	 /**
     * 字符串编码转换的实现方法
     * @param str    待转换的字符�?
     * @param newCharset    目标编码
     */
    public static String changeCharset(String str, String newCharset)  {
        try {
			if(str != null) {
			    //用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
			    byte[] bs = str.getBytes();
			    return new String(bs, newCharset);    //用新的字符编码生成字符串
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        return null;
    }
    
    /**
     * 字符串编码转换的实现方法
     * @param str    待转换的字符�?
     * @param oldCharset    源字符集
     * @param newCharset    目标字符�?
     */
    public static String changeCharset(String str, String oldCharset, String newCharset)  {
        try {
			if(str != null) {
			    //用源字符编码解码字符�?
			    byte[] bs = str.getBytes(oldCharset);
			    return new String(bs, newCharset);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
    
	/**
	 * 获取属�?�?�? name
	 * 
	 * @param name 方法�? �? getName
	 * @return
	 * @author:lintg
	 */
	public static String getAttrName(String methodName) {
		if (methodName == null || "".equals(methodName)) {
			return "";
		}

		String attr_name = "";
		if (methodName.startsWith("get")) {
			attr_name = methodName.substring(3);
		} else {
			attr_name = methodName.substring(2);
		}
		// 第一个字母改为小�?
		char f = attr_name.charAt(0);
		f = Character.toLowerCase(f);
		attr_name = f + attr_name.substring(1);
		return attr_name;
	}
	/**
	 * 获取方法�?
	 * @param attrName 属�?�?
	 * @return
	 */
	public static String getMethodName(String attrName) {
		String method_name = "";
		
		// 第一个字母改为大�?
		char f = attrName.charAt(0);
		f = Character.toUpperCase(f);
		method_name = "get" + f + attrName.substring(1);
		return method_name;
	}
	/**
	 *  第一个字母改为小�?
	 * @param name
	 * @return
	 */
	public static String getSmallName(String name){
		String _name = name;
		char f = _name.charAt(0);
		f = Character.toLowerCase(f);
		_name = f + _name.substring(1);
		return _name;
	}
	/**
	 * 转成指定长度、格式的字符�?
	 * @param str 原字符串
	 * @param len 要转成的字符长度
	 * @param placeholder 长度不够时的占位�?
	 * @return
	 */
	public static String convertStr(Object obj,int length,String placeholder){
		String str = StringUtil.toString(obj);
		int strLen = str.length();
		for(int i=0;length-strLen > i; i++){
			str = placeholder + str;
		}
		return str;
	}
	public static String toString(Object obj){
		if (obj == null || "".equals(obj)) {
			return "";
		}
		return obj.toString().trim();
	}
	/**
	 * 数字金额转成 中文大写金额
	 * @param strNum
	 * @return
	 */
}
