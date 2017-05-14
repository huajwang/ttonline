package com.troika.emall.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtil {
	public static Date addMinute(int amount){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, amount);
		Date date = c.getTime();
		return date;
	}
	public static Date addMinute(Date cur,int amount){
		Calendar c = Calendar.getInstance();
		c.setTime(cur);
		c.add(Calendar.MINUTE, amount);
		Date date = c.getTime();
		return date;
	}
	public static Date addHour(Date cur,int amount){
		Calendar c = Calendar.getInstance();
		c.setTime(cur);
		c.add(Calendar.HOUR, amount);
		Date date = c.getTime();
		return date;
	}
	/**
	 * 获取毫秒
	 * @return
	 */
	public static String getMsecTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		return sdf.format(new Date());
	}
	
	public static String getSecTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		return sdf.format(new Date());
	}
	
	public static Date parseDatetime(String dateTime){
		if(StringUtils.isBlank(dateTime)){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return sdf.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String format(Date dateTime){
		if(dateTime == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(dateTime);
	}
	
	public static String format2(Date dateTime){
		if(dateTime == null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(dateTime);
	}
}
