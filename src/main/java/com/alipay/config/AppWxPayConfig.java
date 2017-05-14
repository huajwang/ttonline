package com.alipay.config;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alipay.util.HttpUtils;
import com.alipay.util.MD5;

import net.sf.json.JSONObject;

/**
 * 微信接口
 * 
 * @author Administrator
 * 
 */
public class AppWxPayConfig {

	public static String AppID = "wx4d00f1b732ea6301";// AppID(应用ID)
	public static String AppSecret = "17fc585a6efeeb035eed25b50058486f";// AppSecret(应用密钥)
	public static String MCHID = "1343232201";// 商户号
	public static String ACCESS_TOKEN = "";
	public static String KEY = "lzmh0710xiamenWaterlooWindjammer";// 商户支付密钥，参考开户邮件设置（必须配置）

	private static final Logger logger = LoggerFactory
			.getLogger(AppWxPayConfig.class);

	public AppWxPayConfig() {

	}

	/**
	 * 获取access_token数据
	 */
	public void getAccessToken() {
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/token";
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("grant_type", "client_credential");
			params.put("appid", AppID);
			params.put("secret", AppSecret);
			String result = HttpUtils.sendGet(url, params);

			JSONObject json = JSONObject.fromObject(result);
			ACCESS_TOKEN = json.getString("access_token");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("获取'access_token'出现异常", e);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public String ToXml(Map<String, String> params) {
		String xml = "<xml>";
		Iterator iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			xml += "<" + key + ">" + val + "</" + key + ">";
		}
		xml += "</xml>";
		return xml;
	}

	/**
	 * @Dictionary格式转化成url参数格式 @ return url格式串, 该串不包含sign字段值
	 */
	@SuppressWarnings("rawtypes")
	public String ToUrl(Map<String, String> params) {
		// 先将参数以其参数名的字典序升序进行排序
		TreeMap<String, String> sortedParams = new TreeMap<String, String>(
				params);
		// 去除参与的参数sign
		sortedParams.remove("sign");
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Entry<String, String> param : sortedParams.entrySet()) {
			String value = param.getValue();
			if (first) {
				first = false;
			} else {
				sb.append("&");
			}
			sb.append(param.getKey()).append("=");
			sb.append(value);
		}
		return sb.toString();
	}

	/**
	 * @生成签名，详见签名生成算法
	 * @return 签名, sign字段不参加签名
	 */
	public String MakeSign(Map<String, String> params) {
		String str = ToUrl(params);
		str += "&key=" + KEY;
		String sign = MD5.GetMD5Code(str, false);
		logger.info("签名算法：str=" + str + "   sign" + sign);
		return sign;
	}
	
	/**
	 * 统一下单
	 * 
	 * @param openid
	 * @param OrderId
	 * @param host
	 * @param ip
	 * @param Money
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String GetUnifiedOrderResultApi(String OrderId,
			String host, String ip, double Money) {
		try {
			String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";

			BigDecimal bd = BigDecimal.valueOf(Money);
			BigDecimal b2 = new BigDecimal(Double.valueOf("100"));
			bd = bd.multiply(b2);
			String total_fee = String.valueOf(bd.intValue());
			Date begindate = new Date();
			DateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String time_start = format.format(begindate);

			Date enddate = new Date(begindate.getTime() + 600000); // 10分钟后的时间
			String time_end = format.format(enddate);

			UUID uuid = UUID.randomUUID();

			Map<String, String> params = new HashMap<String, String>();
			params.put("appid", AppID);
			params.put("body", "TT");// 商品描述
			params.put("mch_id", MCHID);
			params.put("nonce_str", uuid.toString().replace("-", ""));
			params.put("notify_url","http://www.xmtroika.com/ttmall/pay/wxpay_notify_url.api");// 异步通知url
//			params.put("openid", openid);
			params.put("out_trade_no", OrderId);// 商户订单号
			params.put("spbill_create_ip", ip);
			params.put("time_expire", time_end);// 交易结束时间
			params.put("time_start", time_start);// 交易起始时间
			params.put("total_fee", total_fee);// 总金额

			params.put("trade_type", "APP");

			params.put("sign", MakeSign(params));

			String xml = ToXml(params);
			String result = HttpUtils.sendPostXml(url, xml);
			return result;
		} catch (Exception e) {
			return e.toString();
		}

	}
	
}
