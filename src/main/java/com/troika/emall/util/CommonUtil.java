package com.troika.emall.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.troika.emall.model.TMallUser;

public class CommonUtil {
	public static Logger logger = LogManager.getLogger(CommonUtil.class);
	
	public static String buildToken(){
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		return token;
	}
	/**
	 * session里的时间加上超时时间判断是否超时
	 * @param sessionDate
	 * @return
	 */
	public static boolean isLogin(Date sessionDate){
		Date date = DateUtil.addMinute(sessionDate, Constant.TOKEN_OUT_MINUTE);//加上超时时间
		Date now = new Date();
		return date.compareTo(now) >= 0 ? true : false;
	}
	/**
	 * 订单编号
	 * @return
	 */
	public static String buildOrderId(Long userId){
		String id = "000" + userId;
		String order = DateUtil.getSecTime() + id.substring(id.length() - 4);
		return order;
	}
	
	public static String buildRegCode(){
		String regCode = UUID.randomUUID().toString().replaceAll("-", "");
		return regCode;
	}
	
	public static synchronized String buildUserId(){
		//根据一定的规则，目前先用时间
		return DateUtil.getMsecTime();
	}
	
	/**
	 * 六位随机数
	 * @return
	 */
	public static int randomSixNum(){
		int max = 999999;
		int min = 100000;
		int temp = randomNum(max,min);
        return temp;
	}
	
	public static int randomNum(int max,int min){
		Random rand= new Random();
        int tmp = Math.abs(rand.nextInt());
        int temp = tmp % (max - min + 1) + min;
        return temp;
	}
	
	/**
	 * 验证用户是否登录
	 * @param request
	 */
	public static TMallUser ValidateUser(HttpServletRequest request)
	{
		HttpSession httpSession = request.getSession();
		logger.debug("session id = " + httpSession.getId() + ", created at " + httpSession.getCreationTime()
		+ "last accessed at = " + httpSession.getLastAccessedTime() + ", max inactive interval time = " + 
				httpSession.getMaxInactiveInterval());
		TMallUser user = (TMallUser) httpSession.getAttribute(Constant.LOGIN_USER);
		if (user == null) {
			logger.info("no user in session");
		}
		return user;		
	}
}
