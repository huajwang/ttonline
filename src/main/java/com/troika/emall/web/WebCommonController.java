package com.troika.emall.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.troika.emall.restapi.BaseController;
import com.troika.emall.service.EvaluateService;
import com.troika.emall.service.OssService;

@Controller
@RequestMapping("/webcommon")
public class WebCommonController extends BaseController{

	@Autowired
	private EvaluateService evaluateService;
	@Autowired
	private OssService ossService;
	/**
	 * 评价列表
	 * @param gId
	 * @return
	 */
	@RequestMapping("ajaxEvaluationList.api")
	@ResponseBody
	public String ajaxEvaluationList(Long gId){
		List<Map<String,Object>> list = evaluateService.evalListByGid(gId);
		return genSuccessResult(list);
    }
	/**
	 * 上传图片
	 * @param file
	 * @return
	 */
	@RequestMapping("ajaxUploadPic.api")
	@ResponseBody
	public String ajaxUploadPic(HttpServletRequest request,HttpServletResponse response){
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		MultipartFile picFile = multipartRequest.getFile("file");
		Map<String,String> resultMap = new HashMap<String,String>();
		String result = "";
		try {
			result = ossService.uploadSinglePic(picFile.getInputStream(), picFile.getSize());
		} catch (Exception e) {
//			logger.error("上传图片发生异常："+e.getMessage());
		}
		resultMap.put("picStr", result);
		return genSuccessResult(resultMap);
	}
	
	/**
	 * 上传图片并设置前后缀
	 * @param file
	 * @return
	 */
	@RequestMapping("ajaxUploadPicWithFix.api")
	@ResponseBody
	public String ajaxUploadPicWithFix(HttpServletRequest request,HttpServletResponse response){
		MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
		MultipartFile picFile = multipartRequest.getFile("file");
		Map<String,String> resultMap = new HashMap<String,String>();
		String result = "";
		try {
			result = ossService.uploadSinglePic(picFile.getInputStream(), picFile.getSize(),"wxad_","");
		} catch (Exception e) {
//			logger.error("上传图片发生异常："+e.getMessage());
		}
		resultMap.put("picStr", result);
		return genSuccessResult(resultMap);
	}
}
