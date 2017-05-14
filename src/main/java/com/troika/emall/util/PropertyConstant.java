package com.troika.emall.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyConstant {

	public static String SMS_URL=null;
	public static String SMS_ACCOUNT=null;
	public static String SMS_PWD=null;
	public static Integer ORDER_LIMIT = 24 * 3;
	public static final Integer ORDER_DONE_LIMIT = 24 * 11; // 从厂商发货开始计算时间
	
	static  {
        Properties prop =  new  Properties();    
        InputStream in = PropertyConstant.class.getResourceAsStream( "/common.properties" );    
         try  {    
            prop.load(in);
            SMS_URL = prop.getProperty( "smsUrl","http://222.73.117.158/msg/").trim();
            SMS_ACCOUNT = prop.getProperty( "smsAccount","jiekou-clcs-09").trim();
            SMS_PWD = prop.getProperty( "smsPwd","Tch123456CL").trim();
            ORDER_LIMIT = Integer.parseInt(prop.getProperty( "orderLimit","24").trim());
        }  catch  (IOException e) {    
            e.printStackTrace();    
        }    
    }
}
