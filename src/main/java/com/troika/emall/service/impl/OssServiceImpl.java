package com.troika.emall.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aliyun.oss.OSSClient;
import com.troika.emall.service.OssService;
import com.troika.emall.util.Constant;
import com.troika.emall.util.OSSObjectUtil;
import com.troika.vtools.util.OssClientUtil;

@Service
public class OssServiceImpl extends BaseServiceImpl implements OssService{
	
	/**
	 * 上传单张图片到OSS
	 * @return 该图片在OSS上的文件名
	 */
	public String uploadSinglePic(InputStream input,long len,String prefix,String suffix){
		OSSClient client = new OSSClient(Constant.UP_PHOTO_URL, Constant.ACCESS_ID,Constant.ACCESS_KEY);
		try{
			String objectKey = prefix+UUID.randomUUID().toString().replace("-", "")+suffix;
			OSSObjectUtil.ensureBucket(client, Constant.OSS_BUCKET_NAME);
			OSSObjectUtil.setBucketPublicReadable(client, Constant.OSS_BUCKET_NAME);
			//上传
			OSSObjectUtil.uploadFile(client, Constant.OSS_BUCKET_NAME, objectKey, input,len);
			System.out.println(objectKey);
			return objectKey;
		}catch(Exception e){
			logger.error("上传图片异常："+e.getMessage());
		}
		return "";
	}
	
	/**
	 * 上传单张图片到OSS
	 * @return 该图片在OSS上的文件名
	 */
	public String uploadSinglePic(InputStream input,long len){
		OSSClient client = new OSSClient(Constant.UP_PHOTO_URL, Constant.ACCESS_ID,Constant.ACCESS_KEY);
		try{
			String objectKey = UUID.randomUUID().toString().replace("-", "");
			OSSObjectUtil.ensureBucket(client, Constant.OSS_BUCKET_NAME);
			OSSObjectUtil.setBucketPublicReadable(client, Constant.OSS_BUCKET_NAME);
			//上传
			OSSObjectUtil.uploadFile(client, Constant.OSS_BUCKET_NAME, objectKey, input,len);
			return objectKey;
		}catch(Exception e){
			logger.error("上传图片异常："+e.getMessage());
		}
		return "";
	}
	
	/**
	 * 上传文件
	 */
	@Override
	public String uploadPicture(byte[] bt,String prefix,String suffix) {
		OSSClient client = null;
		String pic = "";
		if(bt != null){
			client = new OSSClient(Constant.UP_PHOTO_URL, Constant.ACCESS_ID,Constant.ACCESS_KEY);
			OssClientUtil.setBucketACLPublicRead(client, Constant.OSS_BUCKET_NAME);
			try {
				pic = prefix+OssClientUtil.genReqID()+suffix;
				OssClientUtil.uploadFileByByteArrayInputStream(client, Constant.OSS_BUCKET_NAME, pic, bt);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		//关闭实例并释放资源
		client.shutdown();
		return pic;
	}

}
