package com.troika.emall.util;

import java.util.Date;

public class Constant {

	/**
	 * 阿里云 OSS
	 */
	public static final String ACCESS_ID = "RGuLZmurTxJecZLy";
	public static final String ACCESS_KEY = "m0PvU8zJeulqPYssrWygJGsuegy2Lz";
	public static final String OSS_BUCKET_NAME = "toyke";
	
	//登录成功状态
	public static final String LOGIN_STATUS_SUC = "0";
	//登录token标识
	public static final String LOGIN_TOKEN = "login_token";
	public static final String LOGIN_USER = "login_user";
	public static final String REGISTER_CODE = "register_code";
	public static int TOKEN_OUT_MINUTE = 30;//登录超时为30分钟
	public static final String SMS_CODE = "sms_code";//短信码
	
	//vtools
	public static final String VTOOLS_USER_VIP = "vtools_user_vip";//用户session中的vip信息
	public static final String VTOOLS_TODAY_CLUSTER_COUNT = "today_cluster_count";//用户session中今日注入广告的文章篇数
	public static final String VTOOLS_TODAY_CLUSTER_ISBAN = "today_cluster_isban";//用户今日文章注入广告的篇数是否达到上限
	public static final String VTOOLS_AD_SAVE_ISFULL = "ad_save_isfull";//用户存储广告条数是否达到上限
	public static final String VTOOLS_AD_COUNT_MY = "ad_save_isfull";//用户存储广告条数是否达到上限
	
	//未登录或者登录超时
	public static String NOT_LOGGED_IN = "99";
	public static String STATUS_SUC = "0";//成功
	public static String STATUS_FAIL = "1";//失败
	public static String STATUS_EXCEP = "-1";//异常
	//RSA密钥
	public static final String RSA_CODE = "emall";
	//图片地址
	public static final String PHOTO_URL = "http://toyke.oss-cn-hangzhou.aliyuncs.com/";
	public static final String UP_PHOTO_URL = "http://oss-cn-hangzhou.aliyuncs.com/";
	
	public static final char SHARE_COMMISSION_DONE = '1';
	public static final Object MAKER_COMMISSION_DONE = '1';
	
	public static final int WALLET_TYPE_ALIPAY = 0;
	public static final int WALLET_TYPE_WX = 1;
	
	// OCS 9b3bc7fbd02544d1.m.cnhzaliqshpub001.ocs.aliyuncs.com
    public final static String OCS_HOST = "9b3bc7fbd02544d1.m.cnhzaliqshpub001.ocs.aliyuncs.com"; //控制台上的“内网地址”
    public final static String OCS_PORT = "11211"; //默认端口 11211，不用改
    
    //存放页面信息缓存数据
    public final static String USER_BALANCE = "user_balance";//用户账户余额
    
    public final static int HIT_PER_PAGE_COUNT = 8;//首页推荐商品一次推送的个数
    public final static int DEFAULT_FIRST_COUNT = 8;//首页推荐商品一次推送的个数
    
    
    //微信二维码相关参数
    public final static String WX_BUSID = "100013";//当前操作的微信公众号
    public final static int WX_LIFETIME = 2592000;//生成的二维码的有效期，2592000=30天
    public final static String WX_SERVICE_URL_MNG = "http://121.40.69.138/wx/mng";//接收指令的服务器接口地址
    public final static String WX_API_CODE = "ChenRongDong";//微信接口验证密码
    
    //佣金比例
    public final static float COMMISSION_RATIO = 0.1f;
    
	public static final String TTMALL_HTTP_URL = "http://www.xmtroika.com/ttmall/";
	// public static final String TTMALL_HTTP_URL = "http://1ywg.6655.la/ttmall/";
	
	public static final Integer WEIXIN_NON_SUBSCRIBED = 0;
    
}
