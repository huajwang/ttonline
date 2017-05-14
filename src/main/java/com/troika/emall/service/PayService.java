package com.troika.emall.service;

import java.io.IOException;
import java.util.Map;

public interface PayService {
	public void aplipaySave(Map<String,String> params,boolean isOk);
	public void notifyVendors(String orderId) throws IOException;
	public void notifyIntroducer(String orderId) throws IOException;
}
