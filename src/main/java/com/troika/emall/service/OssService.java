package com.troika.emall.service;

import java.io.InputStream;

public interface OssService {
	public String uploadSinglePic(InputStream input,long len);
	public String uploadSinglePic(InputStream input,long len,String prefix,String suffix);
	public String uploadPicture(byte[] bt,String prefix,String suffix);
}
