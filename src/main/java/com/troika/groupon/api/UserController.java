package com.troika.groupon.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.troika.groupon.controller.BaseController;
import com.troika.groupon.service.GroupService;

@Controller
@RequestMapping("/groupon/api/user")
public class UserController extends BaseController{

	@Autowired
	private GroupService groupService;
	/**
	 * 
	 * @param projectId
	 * @param userId
	 * @param phone
	 * @param realName
	 * @param IDCare
	 * @param content
	 * @param Icon
	 * @return
	 */
	@RequestMapping("/addComplain")
	@ResponseBody
	public String addComplain(Integer projectId,Integer userId,String phone,String realName
			,String IDCare,String content,List<String> Icon){
		groupService.addComplain(projectId, userId, phone, realName, IDCare, content, Icon);
		return genSuccessResult();
	}
	/**
	 * 获取投诉数据
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getComplainList")
	@ResponseBody
	public String getComplainList(Integer userId){
		Map<Integer,List<Map<String,Object>>> result = groupService.getComplainList(userId);
		return genSuccessResult(result);
	}
}
