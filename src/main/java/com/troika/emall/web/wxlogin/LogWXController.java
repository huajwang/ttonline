package com.troika.emall.web.wxlogin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.alipay.config.WxPayConfig;
import com.alipay.util.httpClient.HttpResponse;
import com.mysql.jdbc.StringUtils;
import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;
import com.troika.emall.util.EmojiFilter;
import com.troika.emall.util.HttpConnect;
import com.troika.emall.util.MD5Encode;

/**
 * 微信登录控制
 * 
 * @author kkw
 * @date 2016年2月16日 上午10:01:00
 */
@Controller
@RequestMapping("/logwx")
public class LogWXController extends BaseController {
	@Autowired
	private TMallUserService tMallUserService;
	/**
	 * 微信事件集合
	 */
	WxPayConfig wx = new WxPayConfig();

	// 用微信浏览器打开的！
	@RequestMapping("/index")
	public void index(HttpServletRequest request, HttpServletResponse response,
			Model model) throws IOException {
		String goPage = request.getParameter("goPage");// 跳转页面
		logger.debug("whj: goPage from request = " + goPage);
		goPage = "http://www.xmtroika.com/ttmall";
		String userId = request.getParameter("userId");//
		String productId = request.getParameter("productId");//
		if (StringUtils.isNullOrEmpty(goPage)) {
			goPage = "";
		}
		TMallUser member_session = (TMallUser) CommonUtil.ValidateUser(request);
		if (member_session == null) { // 用户未登录
			logger.debug("用户未登录，去微信登录...");
			// 共账号及商户相关参数
			String backUri = Constant.TTMALL_HTTP_URL + "logwx/toindex";
			// 授权后要跳转的链接所需的参数一般有会员号，金额，订单号之类，
			// 最好自己带上一个加密字符串将金额加上一个自定义的key用MD5签名或者自己写的签名,
			backUri = backUri + "?goPage=" + goPage + "&productId=" + productId
					+ "&userId=" + userId;
			backUri = URLEncoder.encode(backUri, "UTF-8");
			// scope 参数视各自需求而定，这里用scope=snsapi_base
			// 不弹出授权页面直接授权目的只获取统一支付接口的openid
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
					+ "appid="
					+ WxPayConfig.AppID
					+ "&redirect_uri="
					+ backUri
					+ "&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
			logger.debug("url = " + url);
			response.sendRedirect(url);
			return;
		} else { // 用户已经登录
			goPage = buildGoPage(goPage, productId, userId); // "../" + 
			logger.debug("用户已登录, goPage = " + goPage);
		}
		response.sendRedirect(goPage);
	}

	/**
	 * 微信授权 微信登录跳转页面
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */
	@RequestMapping("/toindex")
	public void toindex(HttpServletRequest request,
			HttpServletResponse response, Model model) throws SQLException,
	IOException {
		// 获取页面传过来会员的id
		String code = request.getParameter("code");
		String goPage = request.getParameter("goPage");
		String productId = request.getParameter("productId");
		String userId = request.getParameter("userId");

		// 商户相关资料
		String openId = "";
		// 获取微信用户的OpenId
		String tempValue = wx.GetOpenidAndAccessTokenFromCode(code);
		try {
			JSONObject jsonObj = JSONObject.parseObject(tempValue);
			if (jsonObj.containsKey("errcode")) {
				model.addAttribute("error", "网络不给力，请稍等。");
				response.sendRedirect("error/error.jsp");
			}
			openId = jsonObj.getString("openid");
			logger.debug("获取到用户的openid = " + openId);
			// 获取用户基本信息(UnionID机制)
			Integer subscribe = isFollow(openId);
			if (subscribe == Constant.WEIXIN_NON_SUBSCRIBED) {// 您还未关注公众号，请先关注后登陆！
				logger.debug("subscribe==0,未关注公众号");
				TMallUser memberSession = tMallUserService
						.findUserByOpenId(openId);// 未关注的用户是否已经存在数据库
				if (memberSession == null) {// 以前从未关注过公众号，不存在数据库的用户表里
					// 保存用户
					TMallUser member = saveUser(jsonObj);// 保存尚未关注的用户
					logger.debug("把授权登录,但尚未关注的用户存入session id = "
							+ request.getSession().getId());
					request.getSession().setAttribute(Constant.LOGIN_USER,
							member);
				} else { // 该用户以前关注过该公众号，数据库里已经有该用户
					logger.debug("把授权登录,已经存在数据库里,但尚未关注的用户存入session id = "
							+ request.getSession().getId());
					request.getSession().setAttribute(Constant.LOGIN_USER,
							memberSession);
				}

			} else { // 用户已经关注。更新用户在数据库里的信息。
				logger.debug("获取微信用户的详细信息...");
				String userinfo_url = "https://api.weixin.qq.com/sns/userinfo?access_token="
						+ jsonObj.getString("access_token")
						+ "&openid="
						+ openId + "&lang=zh_CN";
				HttpResponse tempUserinfo = HttpConnect.getInstance().doGetStr(
						userinfo_url);

				tempValue = tempUserinfo.getStringResult();
				jsonObj = JSONObject.parseObject(tempValue);
				if (!jsonObj.containsKey("errcode")) { 
					TMallUser memberSession = tMallUserService
							.findUserByOpenId(openId);
					logger.debug("通过openid从用户表里获取用户信息: " + memberSession);
					if (memberSession != null) {// 会员已经存在数据库里
					// String nickname = EmojiFilter.filterEmoji(jsonObj
					// .getString("nickname"));// 用户的昵称
					// String headimgurl = jsonObj.getString("headimgurl");
					// //用户的微信头像
					// memberSession.setUserName(nickname);
					// memberSession.setSubscribe("0");
					// memberSession.setIconUrl(headimgurl);//使用微信头像作为用户头像
					// logger.debug("获取用户昵称: " + nickname);
					// memberSession = tMallUserService
					// .saveOrUpdateUser(memberSession);
						request.getSession().setAttribute(Constant.LOGIN_USER,
								memberSession);
					} else {// 正常情况下，这个分支是不会执行的。 用户一旦关注了公众号， 在数据库里肯定有记录
						logger.error("用户已经关注了公众号， 在数据库里却没有记录");
						request.getSession().setAttribute(Constant.LOGIN_USER,
								saveUserInfo(jsonObj));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error", "网络不给力，请稍等。");
			// response.sendRedirect("/mobile/error/error.jsp");
		}

		if (!goPage.equalsIgnoreCase("member")) {
			goPage = buildGoPage(goPage, productId, userId);
		}
		logger.info("微信登录后, goPage = " + goPage);
		response.sendRedirect(goPage); // "../" + 
	}

	/**
	 * 根据传过来的url参数构建跳转页面
	 * @param goPage
	 * @param productId
	 * @param userId
	 * @return
	 */
	private String buildGoPage(String goPage, String productId, String userId) {
		if (!StringUtils.isNullOrEmpty(productId) && !"null".equals(productId)) {
			if (!StringUtils.isNullOrEmpty(userId) && !"null".equals(userId)) {
				goPage = goPage + "?productId=" + productId + "&userId="
						+ userId;
			} else {
				goPage = goPage + "?productId=" + productId;
			}
		} else if (goPage.equalsIgnoreCase("member")) { // 跳转到商城个人中心
			goPage = "../" + goPage; // here, goPage represents "/logwx/member"
		}
		return goPage;
	}

	/**
	 * 保存未关注用户
	 * 
	 * @param jsonObj
	 * @return
	 */
	private TMallUser saveUser(JSONObject jsonObj) {
		String openId = jsonObj.getString("openid");// 用户的标识，对当前公众号唯一
		String headimgurl = jsonObj.getString("headimgurl");//
		String sex = jsonObj.getString("sex");//
		logger.debug("用户还未关注: openId = " + openId + "headimgurl = "
				+ headimgurl);
		TMallUser menberTemp = new TMallUser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		menberTemp.setUserId(sdf.format(new Date()));
		menberTemp.setUserName("尚未关注");
		menberTemp.setPassword(MD5Encode.MD5("123456"));
		if ("2".equals(sex)) {
			menberTemp.setSex("0");
		} else {
			menberTemp.setSex("1");
		}
		menberTemp.setCreateTime(new Date());
		menberTemp.setSubscribe("1");
		menberTemp.setIconUrl(headimgurl);
		// menberTemp.setPhone(""); 注意：
		// 不能把用户的电话号码设成空字符串。否则会导致用户数据没办法插入，因为电话号码是不能重复的。
		menberTemp.setOpenId(openId);
		logger.debug("获取微信的用户资料并存入到用户的记录中: id = " + menberTemp.getId()
		+ ", userId = " + menberTemp.getUserId() + ", userName = "
		+ menberTemp.getUserName() + ", openId = " + openId);
		return tMallUserService.saveOrUpdateUser(menberTemp);
	}

	/**
	 * 保存关注用户
	 * 
	 * @param jsonObj
	 * @return
	 */
	private TMallUser saveUserInfo(JSONObject jsonObj) {
		String openId = jsonObj.getString("openid");// 鐢ㄦ埛鐨勬爣璇嗭紝瀵瑰綋鍓嶅叕浼楀彿鍞竴
		String nickname = EmojiFilter
				.filterEmoji(jsonObj.getString("nickname"));// 鐢ㄦ埛鐨勬樀绉�
		String headimgurl = jsonObj.getString("headimgurl");//
		String sex = jsonObj.getString("sex");//
		TMallUser menberTemp = new TMallUser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		menberTemp.setUserId(sdf.format(new Date()));
		menberTemp.setUserName(nickname);
		menberTemp.setPassword(MD5Encode.MD5("123456"));
		if ("2".equals(sex)) {
			menberTemp.setSex("0");
		} else {
			menberTemp.setSex("1");
		}
		menberTemp.setCreateTime(new Date());
		menberTemp.setSubscribe("0");
		menberTemp.setIconUrl(headimgurl);
		// menberTemp.setPhone(""); 注意：
		// 不能把用户的电话号码设成空字符串。否则会导致用户数据没办法插入，因为电话号码是不能重复的。
		menberTemp.setOpenId(openId);
		return tMallUserService.saveOrUpdateUser(menberTemp);
	}

	/**
	 * 判断是否关注公众号
	 * 
	 * @param openid
	 * @return
	 */
	private int isFollow(String openid) {
		String urltoken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ WxPayConfig.AppID + "&secret=" + WxPayConfig.AppSecret;
		HttpResponse temp = HttpConnect.getInstance().doGetStr(urltoken);
		String access_token = "";
		try {
			if (temp != null) {
				JSONObject jsonObj = JSONObject.parseObject(temp
						.getStringResult());
				access_token = jsonObj.getString("access_token");

				String urluser = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="
						+ access_token + "&openid=" + openid + "&lang=zh_CN";
				temp = HttpConnect.getInstance().doGetStr(urluser);
				if (temp != null) {
					jsonObj = JSONObject.parseObject(temp.getStringResult());
					Integer subscribe = jsonObj.getInteger("subscribe");
					return subscribe;
				}
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}
}
