package com.troika.emall.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.WxPayConfig;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.troika.emall.model.TMallCategory;
import com.troika.emall.model.TMallEvaluationImg;
import com.troika.emall.model.TMallSubcategory;
import com.troika.emall.model.TMallUser;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.AddressService;
import com.troika.emall.service.CartService;
import com.troika.emall.service.CategoryService;
import com.troika.emall.service.EvaluateService;
import com.troika.emall.service.GoodsService;
import com.troika.emall.service.HomePageService;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.util.CommonUtil;
import com.troika.emall.util.Constant;


@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

	@Autowired
	private HomePageService homePageService;// 首页

	@Autowired
	private GoodsService goodsService;// 商品

	@Autowired
	private CartService cartService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private EvaluateService evaluateService;// 评价

	@Autowired
	private TMallUserService tMallUserService;
	
	/**
	 * 微信
	 */
	private WxPayConfig wpc = new WxPayConfig();

	/**
	 * 默认首页
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(method = GET)
	public String home(HttpServletRequest request, Model model) {
		List<Map<String, Object>> list = homePageService.findAllPic();
		logger.info("topPic: " + list.get(0));
		model.addAttribute("TopPic", list);
		List<Map<String, Object>> map = homePageService.getFrontCategory();
		logger.info("front category: " + map.get(0));
		model.addAttribute("category", map);
		List<Map<String, Object>> bighitmap = homePageService.findBigHitAll();
		logger.info("bigHit: " + bighitmap.get(0));
		int toIndex = Constant.DEFAULT_FIRST_COUNT;
		if(toIndex > bighitmap.size())
			toIndex = bighitmap.size();
		model.addAttribute("bighit", bighitmap.subList(0, toIndex));
		model.addAttribute("bighitCount", toIndex);
		// 显示购物车商品数量
		TMallUser user = CommonUtil.ValidateUser(request);
		//判断用户是否登录
		if (user != null) {
			List<Map<String, Object>> list1 = cartService
					.getCartListByUserId(user.getId());
				model.addAttribute("cartsize", list1.size());
				model.addAttribute("carts", list1);
				model.addAttribute("flag", Constant.LOGIN_STATUS_SUC);
				request.getSession().setAttribute("subscribe", user.getSubscribe());
			}else{
				request.getSession().setAttribute("subscribe", 1);
				model.addAttribute("flag", Constant.NOT_LOGGED_IN);
			}
		return "index";
	}
	
	/**
	 * 首页推荐商品追加
	 */
	@RequestMapping(value="/superaddhit", produces = "plain/text; charset=UTF-8")
	@ResponseBody
	public String superaddHit(int current){
		List<Map<String, Object>> bighitmap = homePageService.findBigHitAll();
		int len = bighitmap.size();
		int last = len-current;
		if(last<=0)
			last = 0;
		int toIndex = current+Constant.HIT_PER_PAGE_COUNT;
		if(last<Constant.HIT_PER_PAGE_COUNT)
			toIndex = current+last; 
		return new Gson().toJson(bighitmap.subList(current, toIndex));
	}

	/**
	 * 商品详情
	 * 
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/productDetail")
	public String productDetail(HttpServletRequest request, Model model,
			int productId) {
		TMallUser userinfo = CommonUtil.ValidateUser(request);
		Map<String, Object> good = null;
		if(userinfo == null){
			good = goodsService.findGoodsById(productId);
		}else{
			good = goodsService.findGoodsByIdUser(productId,userinfo.getId());
			model.addAttribute("userId", userinfo.getId());//获取当前用户Id
		}
		//商品图片列表
		List<Map<String, Object>> ImgList = goodsService
				.findGoodsImg(productId);
		good.put("DetialImg", ImgList);
		String photos = "";
		if (good.get("photoUrl") != null)
			photos = good.get("photoUrl").toString();
		// 获取商品图片路径
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (String path : photos.split(",")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("photo", path);
			list.add(map);
		}
		// List<TMallProvince> Provinces =
		// systemCodeService.findAllProvince();// 获取省数据
		// 获取商品属性数据
		String subcategoryId = good.get("subcategoryId").toString();// 子类数据
		//获取商品的属性
		List<Map<String, Object>> property = goodsService.findAllProperties(
				Long.valueOf(productId), Integer.valueOf(subcategoryId));

		model.addAttribute("good", good);
		model.addAttribute("photos", list);
		// model.addAttribute("Provinces", Provinces);
		int type = 0;
		double min = -1;// 最低价格
		double max = -1;// 最高价格
		if ((Integer.parseInt(good.get("showPopup").toString().trim())) == 1 && property != null) {
			List<Map<String, Object>> colorList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> sizeList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> styleList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : property) {
				Map<String, Object> m = new HashMap<String, Object>();
				Map<String, Object> m2 = new HashMap<String, Object>();
				Map<String, Object> m3 = new HashMap<String, Object>();
				String color = (String) map.get("color");
				String size = (String) map.get("size");
				String style = (String) map.get("style");
				m.put("color", color);
				m2.put("size", size);
				m3.put("style", style);
				if (!colorList.contains(m))// 判断元素是否存在
				{
					colorList.add(m);
				}
				if (!sizeList.contains(m2))// 判断元素是否存在
				{
					sizeList.add(m2);
				}
				if (!styleList.contains(m3))// 判断元素是否存在
				{
					styleList.add(m3);
				}
				if (min == -1) {
					min = Double.valueOf(map.get("price").toString());
					max = Double.valueOf(map.get("price").toString());
				} else {
					double value = Double.valueOf(map.get("price").toString());
					if (max < value)
						max = value;
					if (min > value)
						min = value;
				}
			}
			model.addAttribute("colorList", colorList);
			model.addAttribute("sizeList", sizeList);
			model.addAttribute("styleList", styleList);
			String JSON = new Gson().toJson(property);
			int groupType = 0;
			if (JSON.indexOf("color") >= 0){// 颜色
				type++;
				groupType++;
			}
			if (JSON.indexOf("size") >= 0){// 尺寸
				groupType += 2;
				type++;
			}
			if (JSON.indexOf("style") >= 0){// 风格
				groupType += 4;
				type++;
			}
			model.addAttribute("groupType", groupType);
			model.addAttribute("propertyJSON", JSON);
		}
		model.addAttribute("type", type);
		model.addAttribute("min", min);
		model.addAttribute("max", max);
		// 显示最新商品评价数据
		List<Map<String, Object>> evalList = evaluateService.evalListByGid(Long
				.valueOf(good.get("id").toString()));
		//评论名字的处理
		for (Map<String, Object> map2 : evalList) {
			if(map2.get("userName")!=null&&map2.get("userName")!=""
					&&(map2.get("userName")+"").length()>3&&!"null".equals(map2.get("userName")+"")){
				String lastOne = map2.get("userName").toString().substring(map2.get("userName").toString().length()-1);
				map2.put("userName", map2.get("userName").toString().substring(0,2)+"xxx"+lastOne);
			}
		}
		
		// 返回最新评价数据和统计好评率
		if (evalList.size() > 0) {
			model.addAttribute("evalMap", evalList.get(0));
			//获取评价图片
			Map<String,Object> evalMapImg=evalList.get(0);
			List<TMallEvaluationImg> evalImg=(List<TMallEvaluationImg>) evalMapImg.get("imgList");
			model.addAttribute("evalImg", evalImg);
			double hao = 0;// 统计好评
			for (Map<String, Object> eval : evalList) {
				if (Integer.valueOf(eval.get("rating").toString()) >= 3) {
					hao = hao + 1;
				}
			}
			if (hao != 0)
				model.addAttribute("evalHao", hao / (evalList.size()) * 100);
			else
				model.addAttribute("evalHao", 0);
		} else {
			model.addAttribute("evalHao", 100);
		}
		model.addAttribute("evalSize", evalList.size());
		// 显示购物车商品数量
		TMallUser user = CommonUtil.ValidateUser(request);
		//判断用户是否登录
		if (user != null) {
			List<Map<String, Object>> list1 = cartService
					.getCartListByUserId(user.getId());
				model.addAttribute("cartsize", list1.size());
				model.addAttribute("carts", list1);
			model.addAttribute("subscribe", user.getSubscribe());
		}

		return "goods/productDetail";

	}

	/**
	 * 跳转到二维码下载界面
	 * @throws WriterException 
	 * @throws IOException 
	 */
	@RequestMapping(value="/qrcode")
	public String qrcode(Integer gid,HttpServletRequest request,Model model){
		TMallUser userinfo = CommonUtil.ValidateUser(request);
		if(userinfo==null)
			model.addAttribute("notice", "您当前尚未登陆，分享该二维码可能无法获取利润分享，请登录账号并重新获取二维码！");
		else
			model.addAttribute("notice", "长按图片即可下载二维码！");
		model.addAttribute("gid", gid);
		return "goods/qrCode";
	}
	
	@RequestMapping("/getQrcode")
	public void getQrcode(Integer gid,HttpServletRequest request,HttpServletResponse response) throws IOException, WriterException{
		TMallUser userinfo = CommonUtil.ValidateUser(request);
		//登录前置时使用的url
		String url="http://www.xmtroika.com/ttmall/logwx/index?goPage=productDetail&productId="+gid+"&userId=";
		if(userinfo!=null)
			url += userinfo.getId();
		ServletOutputStream stream = null;
		//使用google插件生成二维码
		int width = 720;
		int height = 720;
		stream = response.getOutputStream();
		QRCodeWriter writer = new QRCodeWriter();
		String format = "png";
		BitMatrix bitMatrix = writer.encode(url, BarcodeFormat.QR_CODE, width, height);
		MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
		if(stream !=null){
			stream.flush();
			stream.close();
		}
	}
	
	/**
	 * 登录
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/Login")
	public String Login(HttpServletRequest request, Model model) {
		System.out.println("HomeController Login method...");
		request.getSession().removeAttribute(Constant.LOGIN_USER);
		String code = request.getParameter("code");// 使用微信登录方式
		if (code != null) {
			String json = wpc.GetOpenidAndAccessTokenFromCode(code);
			JSONObject obj = JSONObject.fromObject(json);
			String OpenId = obj.getString("openid");
			model.addAttribute("OpenId", OpenId);
			TMallUser tempUser = tMallUserService.findUserByOpenId(OpenId);
			if (tempUser == null) {
				model.addAttribute("IsTrue", "true");
			} else {
				model.addAttribute("IsTrue", "false");
			}
			// JSONObject UserInfo = wpc.getUserInfor(OpenId);
			// Map<String, Object> map = tMallUserService.register(OpenId);
		}
		return "login";
	}

	/**
	 * 注册
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register")
	public String register(Model model) {
		return "register";
	}

	/**
	 * 商品大类分类
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/category")
	public String category(HttpServletRequest request,Model model) {
		List<TMallCategory> list = categoryService.getAllCategory();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		for (TMallCategory category : list) {
			int categoryid = category.getId();
			Map<String, Object> map = new HashMap<String, Object>();
			String typeName = category.getTypeName();
			List<TMallSubcategory> subcategory = categoryService
					.getSubcategoryByCategoryId(categoryid);
			if (subcategory.size() > 0) {
				map.put("typeName", typeName);
				map.put("subcategory", subcategory);
				listMap.add(map);
			}
		}
		model.addAttribute("category", listMap);
		// 显示购物车商品数量
		TMallUser user = CommonUtil.ValidateUser(request);
		//判断用户是否登录
		if (user != null) {
			List<Map<String, Object>> list1 = cartService
					.getCartListByUserId(user.getId());
				model.addAttribute("cartsize", list1.size());
				model.addAttribute("carts", list1);
			}
		return "goods/category";
	}

	/**
	 * 获取商品列表
	 * @param model
	 * @param CId
	 * @param type 1 商品分类数据  2 首页分类数据
	 * @return
	 */
	@RequestMapping(value = "/goodlist")
	public String goodlist(HttpServletRequest request,Model model, int CId, int type) {
		//类表显示商品的排序方式
		String sort = request.getParameter("sort");
		List<Map<String, Object>> list = null;
		if (type == 1) {
			list = goodsService.findGoodsByCategoryId(CId);
		} else {
			list = goodsService.findFrontGoods(CId);
		}
		model.addAttribute("goods", list);
		//将集合转Json数据格式--主要用于销量 价格等排序
		String resultJson = new Gson().toJson(list);
		model.addAttribute("DataJson", resultJson);
		return "goods/goodlist";
	}

	@RequestMapping(value = "/WXLogin")
	@ResponseBody
	public String WXLogin(HttpServletRequest request) throws UnsupportedEncodingException {
		String url = "http://www.xmtroika.com/ttmall/Login";
		String redirect_uri = URLEncoder.encode(url, "UTF-8");
		String result = wpc.getCode(redirect_uri);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", result);
		String resultJson = new Gson().toJson(map);
		return resultJson;
	}

	@RequestMapping(value = "/WXLoginByOpenId")
	@ResponseBody
	public String WXLoginByOpenId(HttpServletRequest request, String phone,
			String OpenId) {
		TMallUser tempUser;
		tempUser = tMallUserService.findUserByOpenId(OpenId);
		if (StringUtils.isBlank(OpenId)) {
			return genFailResult();
		}
		if (tempUser == null) {
			Map<String, Object> map = tMallUserService.register(OpenId, phone,
					null);
			tempUser = tMallUserService.findUserByOpenId(OpenId);
		}
		TMallUser user = (TMallUser) request.getSession().getAttribute(
				Constant.LOGIN_USER);// 判断是否已经登录
		if (user != null) {
			return genSuccessResult();
		}
		String token = CommonUtil.buildToken();
		request.getSession().setAttribute(Constant.LOGIN_TOKEN, token);
		request.getSession().setAttribute(Constant.LOGIN_USER, tempUser);
		request.getSession().setAttribute(token, new Date());// token放入的时间
		// String successResult = genSuccessResult(result);
		return genSuccessResult();
	}

}
