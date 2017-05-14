package com.troika.emall.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.troika.emall.common.AlipayStatus;
import com.troika.emall.common.OrderStatus;
import com.troika.emall.dao.OrderDao;
import com.troika.emall.dao.PayDao;
import com.troika.emall.model.TMallAlipayDetail;
import com.troika.emall.model.TMallOrder;
import com.troika.emall.model.TMallUser;
import com.troika.emall.service.OrderProcess;
import com.troika.emall.service.PayService;
import com.troika.emall.service.TMallUserService;
import com.troika.emall.util.Constant;
import com.troika.emall.util.DateUtil;

@Service
@Transactional
public class PayServiceImpl extends BaseServiceImpl implements PayService{

	@Autowired
	private PayDao payDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderProcess orderProcess;
	@Autowired
	private TMallUserService userService;

	@Override
	/**
	 * 支付宝支付成功以后，异步通知回调
	 */
	public void aplipaySave(Map<String, String> params,boolean isOk) {
		String orderId = params.get("out_trade_no");
		String notifyType = params.get("notify_type");
		String notifyId = params.get("notify_id");
		String paymentType = params.get("payment_type");
		String tradeNo = params.get("trade_no");
		String tradeStatus = params.get("trade_status");
		String sellerId = params.get("seller_id");
		String sellerEmail = params.get("seller_email");
		String buyerId = params.get("buyer_id");
		String buyerEmail = params.get("buyer_email");
		String totalFee = params.get("total_fee");
		String gmtCreate = params.get("gmt_create");
		String gmtPayment = params.get("gmt_payment");
		TMallAlipayDetail detail = new TMallAlipayDetail();
		detail.setOrderId(orderId);
		detail.setNotifyType(notifyType);
		detail.setNotifyId(notifyId);
		detail.setPaymentType(paymentType);
		detail.setTradeNo(tradeNo);
		detail.setTradeStatus(tradeStatus);
		detail.setSellerId(sellerId);
		detail.setSellerEmail(sellerEmail);
		detail.setBuyerId(buyerId);
		detail.setBuyerEmail(buyerEmail);
		detail.setTotalFee(new BigDecimal(totalFee));
		detail.setGmtCreate(DateUtil.parseDatetime(gmtCreate));
		detail.setGmtPayment(DateUtil.parseDatetime(gmtPayment));
		detail.setCreateTime(new Date());
		logger.info("保存支付宝异步返回相关数据");
		payDao.aplipaySave(detail);
		if(detail.getTradeStatus().equals(AlipayStatus.TRADE_SUCCESS.getCode()) && isOk){//交易成功
			orderProcess.updateOrderWithPaid(orderId);//更新订单相关状态
			logger.info("更新订单【"+orderId+"】状态成功");
		}
	}

	@Override
	/**
	 * 在一笔订单支付成功以后， 把该笔订单里的供应商拆分出来， 然后通过微信分别推送给相应的供应商； 
	 * (TODO 这样，当供应商收到消息并点击该消息，可以打开网页去查看跟他相关的已支付未发货的订单。)
	 * @param orderId String 订单"编"号 - 支付成功的订单号
	 */
	public void notifyVendors(String orderId) throws IOException {
		String urlStr = "http://www.buyontt.com/vendor/admin";
		urlStr = URLEncoder.encode(urlStr, "utf-8");
		TMallOrder order = orderDao.findOrderByOrderId(orderId);
		String ooderdate = order.getCreateTime().toString();
		String orderType = "一般订单";
		String remark = order.getRemark();
		logger.debug("备注 = " + remark);
		// 取出该笔订单里的所有供应商对应在微信上的openId;
		List<String> vendors = new ArrayList<String>();
		List<Map<String,Object>> listSupplier = orderDao.getSupplierByOrderId(orderId);//获取商家所有数据可能有用
		for(Map<String,Object> suMap : listSupplier){
			Object openId = suMap.get("openId");
			if(openId != null){
				logger.debug("厂商的openId = " + openId.toString());
				vendors.add(openId.toString());
			}
		}
		
		for (int i = 0; i < vendors.size(); i++) {
			logger.debug("完成支付后，准备通过微信发送消息给供应商： openId = " + vendors.get(i));
			Map<String, Object> orderDetail = orderDao.findOrderDetailByOrderIdAndOpenId(orderId, vendors.get(i));
			String orderItemName = (String) orderDetail.get("gName");
			
			String userName = orderDetail.get("userName")==null?"":(String)orderDetail.get("userName");
			String phone = orderDetail.get("phone")==null?"":"("+orderDetail.get("phone")+")";
			String customerInfo = userName+phone;
			String orderItemData = "×"+orderDetail.get("quantity")+orderDetail.get("unit");
			
			logger.debug("orderItemName = " + orderItemName + ", customerInfo = " + customerInfo);
			
			String param = "url="+urlStr+"&ooderdate="+ooderdate+"&orderType="+orderType
					+"&orderItemName="+orderItemName+"&customerInfo="+customerInfo
					+"&orderItemData="+orderItemData+"&remark="+remark+"&openId=" + vendors.get(i);
			String resp = sendPost("http://121.40.69.138/wx/test/sendMbDDMsg", param);
			logger.info("订单通知厂商: " + orderId + "微信推送订单消息返回结果: " + resp);
			
		}
	}

	@Override
	/**
	 * @param orderId - long 订单号
	 */
	public void notifyIntroducer(String orderId) throws IOException {
		TMallOrder order = orderDao.findOrderByOrderId(orderId);
		logger.debug("1. order介绍人= " + order.getIntroducer());
		TMallUser introducer = userService.findUserById(order.getIntroducer());
		logger.debug("2. order introducer = " + introducer.getUserName());
		BigDecimal commission = order.getAmount().multiply(new BigDecimal(Constant.COMMISSION_RATIO));
		double commission2d = commission.setScale(2,BigDecimal.ROUND_FLOOR).doubleValue(); //保留2位小数
		logger.debug("3. 佣金 = " + commission2d);
		String urlStr = "http://www.xmtroika.com/ttmall/logwx/index/?goPage=member";
		urlStr = URLEncoder.encode(urlStr, "utf-8");
		logger.debug("4. 连接url= " + urlStr);
		String ooderdate = order.getCreateTime().toString();
		logger.debug("5. 下单时间= " + ooderdate);
		
		String param = "money=" + commission2d + "&ooderdate=" + ooderdate
				+"&url=" + urlStr + "&openId=" + introducer.getOpenId();
		String resp = sendPost("http://121.40.69.138/wx/test/sendMbYJMsg", param);
		
		logger.debug("完成支付后，准备通过微信发送消息给介绍人: " + introducer.getUserName() + 
				", openId = " + introducer.getOpenId());
		logger.info("分享利润消息: " + orderId + "微信推送消息返回结果: " + resp); 
		
	}
	
	
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
	
	
	
	
	
	

}
