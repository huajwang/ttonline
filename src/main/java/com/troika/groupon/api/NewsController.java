package com.troika.groupon.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.groupon.common.ReadStatus;
import com.troika.groupon.controller.BaseController;
import com.troika.groupon.service.GroupService;

@Controller
@RequestMapping("/groupon/api/news")
public class NewsController extends BaseController{
	@Autowired
	private GroupService groupService;
	/**
	 * 获取消息数据
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getNewsList")
	@ResponseBody
	public String getNewsList(Integer userId){
		Map<String, Object> result = groupService.findNewsByUserId(userId);
		return genSuccessResult(result);
	}
	/**
	 * 修改消息状态
	 * @param id
	 * @param userId
	 * @return
	 */
	@RequestMapping("/updNewsStatus")
	@ResponseBody
	public String updNewsStatus(Integer id,Integer userId){
		boolean isSuc = groupService.updNewsStatus(id,userId,ReadStatus.READ.getCode());
		if(isSuc){
			return genSuccessResult();
		}
		return genFailResult();
	}
	/**
	 * 添加消息
	 * @param type
	 * @param title
	 * @param content
	 * @param userId
	 * @return
	 */
	@RequestMapping("/addNews")
	@ResponseBody
	public String addNews(Integer type,String title,String content,Integer userId){
		boolean isSuc = groupService.addNews(type, title, content, userId);
		if(isSuc){
			return genSuccessResult();
		}
		return genFailResult();
	}
}
