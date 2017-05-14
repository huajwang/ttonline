package com.troika.groupon.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {
	public static String buildOrderId(Integer userId){
		String id = "00" + userId;
		String order = "g" + getSecTime() + id.substring(id.length() - 3);
		return order;
	}
	
	public static String getSecTime(){
		SimpleDateFormat sdf = new SimpleDateFormat("MMddhhmmss");
		return sdf.format(new Date());
	}
}
