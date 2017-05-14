package com.troika.emall.dao;

import com.troika.emall.model.WxTBusQrcode;

public interface QrcodeDao {
	public WxTBusQrcode saveOrUpQrcode(WxTBusQrcode qrcode);
	public WxTBusQrcode findQrcodeById(int id);
}
