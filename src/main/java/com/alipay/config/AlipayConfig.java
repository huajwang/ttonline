package com.alipay.config;
import com.alipay.util.UtilDate;
/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
	//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

		// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
		public static String partner = "2088121906822214";
		
		// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
		public static String seller_id = partner;

		//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
		public static String private_key = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALqVfhXBboobqrHJ"+
				"pcI2MHJs8Lm14ZgmHnb80xjeQ8ozncCJ8eJnjH/86im6rxuEWu5i+1r54nKZBqXv"+
				"C2Lno/rSNR4t+ttiJoNQMP6gjhUvTJi6bvL4hwvbLls7Q7tT7Aj5UGUFe9prjNAP"+
				"AOGpuG/rFm0pggfxFNx0uW2xvwaVAgMBAAECgYABGVROyH7gLV2KAogJZsxOQDoC"+
				"vgGAJelUBhUwNk7r5/Xr5mQYU0fOkSzH5iOV4+etM8QLOyGMTz5sx8WnZXnfw72Z"+
				"e2tNj3vIpcmaclkhVip1WvtepgvhIZrEIe0qmEMzccJKPGU5GuI20+xmWFcwh3dE"+
				"MIxa/YD0vpbVTh4BgQJBAONdWit5RVK430SCtYJB4NNBvW7Te1rOqFv87H7A4dIq"+
				"Ogwpq1LA8qNu3UHeXcckZLjiGEiL0MQq0IEqc2xrYrECQQDSFUv0uKV0q+VTJC6f"+
				"PdKrw4wUvjMHe+siaVEYf9niuLe9k37hg4bhxUTyyXd0yy0/mrHZ99kXuiF5zrdR"+
				"uLMlAkBZCdjjB4XXZGDDErEERspF0QvfV+CJ6j5Sb9Upul11EWZUFwlpyff7etIZ"+
				"Fn0BHMZ5BMnftylVeTLYMoirH86xAkBKg20+bqOY30NmmuXCa34mmCQ+UzvsQqKi"+
				"wPIXm3lfth7NqdwOBsW3nG2ZI6YwEcSJ08eTRRqGFOm8t3pEDRvVAkA/Y2I1WNIk"+
				"IlT6gRPacSo+t7GPEnH7Ou0+9ouvu5VU0AlwBYqeLAlWg1cD9xBPs1hBOpopb8OE"+
				"mkFLspVOlK9q";
		
		// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
		public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
		

		// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
		public static String notify_url = "http://192.168.0.101:8080/alipay/notify_url.jsp";

		// 页面跳转同步通知页面路径   商城的跳转页面
		public static String return_url = "http://www.xmtroika.com/ttmall/orders/alipaySuccess";
		

		// 页面跳转同步通知页面路径   拼团的跳转页面
		public static String return_url1 = "http://www.xmtroika.com/ttmall/groupon/alipaySuccess";

		// 签名方式
		public static String sign_type = "RSA";
		
		// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
		public static String log_path = "C:\\";
			
		// 字符编码格式 目前支持utf-8
		public static String input_charset = "utf-8";
			
		// 支付类型 ，无需修改
		public static String payment_type = "1";
			
		// 调用的接口名，无需修改
		public static String service = "alipay.wap.create.direct.pay.by.user";


	//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

}

