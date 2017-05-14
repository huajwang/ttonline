package com.troika.emall.restapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.troika.emall.service.EvaluateService;
import com.troika.emall.service.OssService;

@Controller
@RequestMapping("/common/")
public class CommonController extends BaseController {

	@Autowired
	private EvaluateService evaluateService;
	@Autowired
	private OssService ossService;

	/**
	 * 评价列表
	 * 
	 * @param gId
	 * @return
	 */
	@RequestMapping("ajaxEvaluationList.api")
	@ResponseBody
	public String ajaxEvaluationList(Long gId) {
		List<Map<String, Object>> list = evaluateService.evalListByGid(gId);
		return genSuccessResult(list);
	}

	/**
	 * 上传图片
	 * 
	 * @param file
	 */
	@RequestMapping("ajaxUploadPic.api")
	@ResponseBody
	public String ajaxUploadPic(
			@RequestParam(value = "picFile", required = false) MultipartFile picFile) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String result = "";
		try {
			result = ossService.uploadSinglePic(picFile.getInputStream(),
					picFile.getSize());
		} catch (Exception e) {
			logger.error("上传图片发生异常：" + e.getMessage());
		}
		resultMap.put("picStr", result);
		return genSuccessResult(resultMap);
	}

	/**
	 * 多个图片上传
	 * 
	 * @param picFile
	 * @return
	 */
	@RequestMapping("multipleAjaxUploadPic.api")
	@ResponseBody
	public String multipleAjaxUploadPic(
			@RequestParam(value = "picFile", required = false) CommonsMultipartFile[] picFileList) {
		List<Map<String, String>> resultList = new ArrayList<>();
		String result = "";
		
		try {
			for (CommonsMultipartFile picFile : picFileList) {
				Map<String, String> resultMap = new HashMap<>();
				result = ossService.uploadSinglePic(picFile.getInputStream(),
						picFile.getSize());
				resultMap.put("picStr", result);
				resultList.add(resultMap);
			}
		} catch (Exception e) {
			logger.error("上传图片发生异常：" + e.getMessage());
		}
		return genSuccessResult(resultList);
	}
}
