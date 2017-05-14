package com.troika.emall.service;

public interface WeixinService {
	public String getQrcodeUrl(int gId,int userId) throws Exception;
	public String getQrcodeIMGUrl(int gId,int userId) throws Exception;
}
