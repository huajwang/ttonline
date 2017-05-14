package com.troika.groupon.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.model.TMallUser;
import com.troika.emall.service.TMallUserService;
import com.troika.groupon.dao.GroupOrderDao;
import com.troika.groupon.dao.GroupProjectDao;
import com.troika.groupon.model.TGrouponOrder;
import com.troika.groupon.model.TGrouponProject;
import com.troika.groupon.service.SendMsgWXService;

@Service
@Transactional
public class SendMsgWXServiceImpl implements SendMsgWXService {
	public Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	private GroupOrderDao groupOrderDao;
	
	@Autowired
	private TMallUserService userService;
	
	@Autowired
	private GroupProjectDao groupProjectDao;
	/**
	 * 通过微信推送消息给项目发起者
	 */
	@Override
	public void notifyVendors(Integer orderId) throws IOException {
		// TODO Auto-generated method stub
		// 获取项目发起人的相关信息
		Map<String, Object> vendor = groupOrderDao.findCreatedUserByOrder(orderId);
		if (vendor != null) {
			String urlStr = "http%3a%2f%2fwww.xmtroika.com%2fttmall%2fgroupon%2flaunch%2finitiate";
			logger.debug("1.url:"+urlStr);
			String OpendId = vendor.get("openId").toString();// 获取发起人的OpenId
			logger.debug("2.openId:"+OpendId);
			TGrouponOrder order = groupOrderDao.findTGrouponOrderById(orderId);
			String ooderdate = order.getCreatetime().toString();
			logger.debug("3.ooderdate:"+ooderdate);
			String orderType = "一般订单";
			logger.debug("4.订单类型："+orderType);
			TMallUser customer = userService.findUserById(order.getUserid());
			String customerInfo = customer.getUserName()+"("+customer.getPhone()+")";
			logger.debug("5.用户信息："+customerInfo);
			String remark = order.getRemark();
			logger.debug("6.备注信息："+remark);
			TGrouponProject tproject = groupProjectDao.findProjectById(order.getProjectid());
			String orderItemName = tproject.getName();
			logger.debug("7.项目名称："+orderItemName);
			// 以下先不写，我是觉得这个入参最好能发多个OPENDID，由接口处进行处理
			URL url = new URL("http://121.40.69.138/wx/test/sendMbDDMsg");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "utf-8");

			logger.debug("拼团完成支付后，准备通过微信发送消息给项目发起人： openId = " + OpendId);
			out.write("url="+urlStr + "&ooderdate=" + ooderdate + "&orderType=" + orderType
					+"&custommerInfo=" + customerInfo + "&orderItemData=" + ooderdate
					+ "&remark=" + remark + "&orderItemName=" + orderItemName + "&openId=" + OpendId);
			
			out.flush();
			out.close();
			// 一旦发送成功，用以下方法就可以得到服务器的回应：
			String sCurrentLine;
			String sTotalString;
			sCurrentLine = "";
			sTotalString = "";
			InputStream l_urlStream;
			l_urlStream = connection.getInputStream();
			// 传说中的三层包装阿！
			BufferedReader l_reader = new BufferedReader(new InputStreamReader(
					l_urlStream));
			while ((sCurrentLine = l_reader.readLine()) != null) {
				sTotalString += sCurrentLine + "\r\n";

			}
			logger.info("订单通知项目发起人: " + vendor.get("orderNO").toString()
					+ "推送成功" + sTotalString);
		}
	}

}
