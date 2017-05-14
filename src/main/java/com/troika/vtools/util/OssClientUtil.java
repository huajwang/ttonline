package com.troika.vtools.util;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.BucketReferer;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;

public class OssClientUtil {

	/**
	 * 生成唯一ID
	 * 
	 * @return
	 */
	public static String genReqID() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}

	/**
	 * 创建Bucket.
	 */
	public static void ensureBucket(OSSClient client, String bucketName) throws OSSException, ClientException {
		try {
			// boolean exists =
			// client.doesBucketExist(bucketName);//判断Bucket是否存在
			// 创建bucket
			client.createBucket(bucketName);
		} catch (ServiceException e) {
			if (!OSSErrorCode.BUCKET_ALREADY_EXISTS.equals(e.getErrorCode())) {
				// 如果Bucket已经存在，则忽略
				throw e;
			}
		}
	}

	/**
	 * 设置Bucket的ACL为Private(私有)
	 */
	public static void setBucketACLPrivate(OSSClient client, String bucketName) throws OSSException, ClientException {
		// 创建bucket
		ensureBucket(client, bucketName);

		// 设置bucket的访问权限，public-read-write权限
		client.setBucketAcl(bucketName, CannedAccessControlList.Private);
	}

	/**
	 * 设置Bucket的ACL为PublicReadWrite(公共读)
	 */
	public static void setBucketACLPublicRead(OSSClient client, String bucketName)
			throws OSSException, ClientException {
		// 创建bucket
		ensureBucket(client, bucketName);

		// 设置bucket的访问权限，public-read-write权限
		client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
	}

	/**
	 * 设置Bucket的ACL为PublicReadWrite(公共读写)
	 */
	public static void setBucketACLPublicReadWrite(OSSClient client, String bucketName)
			throws OSSException, ClientException {
		// 创建bucket
		ensureBucket(client, bucketName);

		// 设置bucket的访问权限，public-read-write权限
		client.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
	}

	/**
	 * 完全删除Bucket(不允许删除不为空的存储空间，故需先删除内部储存对象) ①删除存储的object;②删除Bucket
	 */
	public static void deleteBucket(OSSClient client, String bucketName) throws OSSException, ClientException {
		ObjectListing ObjectListing = client.listObjects(bucketName);
		List<OSSObjectSummary> listDeletes = ObjectListing.getObjectSummaries();
		for (int i = 0; i < listDeletes.size(); i++) {
			String objectName = listDeletes.get(i).getKey();
			// 如果不为空，先删除bucket下的文件
			client.deleteObject(bucketName, objectName);
		}
		client.deleteBucket(bucketName);
	}

	/**
	 * 删除单个文件对象
	 */
	public static void deleteObject(OSSClient client, String bucketName, String key) {
		client.deleteObject(bucketName, key);
	}

	/**
	 * 批量删除文件对象
	 */
	public static List<String> deleteObject(OSSClient client, String bucketName, List<String> keys) {
		DeleteObjectsResult deleteObjectsResult = client
				.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
		List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
		return deletedObjects;
	}

	/**
	 * 上传文件 通过InputStream
	 */
	public static void uploadFileByInputStream(OSSClient client, String bucketName, String key, InputStream is)
			throws OSSException, ClientException, IOException, FileNotFoundException {
		// 创建上传的Object的Metadata，用于自定义Object的Http Header
		ObjectMetadata objectMeta = new ObjectMetadata();
		// 设置ContentLength为输入流InputStream的大小
		objectMeta.setContentLength(is.available());
		// 可以在metadata中标记文件类型
		objectMeta.setContentType("image/jpeg");
		// 可以设置自定义元信息的值,当用户下载此文件时，元信息也一并得到，可以设置多个元信息，但总大小不能超过8kb，元信息大小写不敏感
		// objectMeta.addUserMetadata("name", "my-dataa");

		// 上传文件，四个参数分别表示：①Bucket的名字；②文件对象的名字；③文件对象的二进制流；④上传文件对象的Metadata信息对象
		client.putObject(bucketName, key, is, objectMeta);
	}
	
	/**
	 * 上传文件 通过byte
	 */
	public static void uploadFileByByteArrayInputStream(OSSClient client, String bucketName, String key, byte[] bytes)
			throws OSSException, ClientException, IOException, FileNotFoundException {
		// 上传文件，四个参数分别表示：①Bucket的名字；②文件对象的名字；③文件对象的二进制流；④上传文件对象的Metadata信息对象
		client.putObject(bucketName, key,new ByteArrayInputStream(bytes));
	}
	
	/**
	 * 获取限时签名URL授权访问
	 * (生成的url默认以GET方式访问)
	 * @param key 存储对象object的key
	 * @param seconds 过期时间(单位秒)
	 */
	public static URL getAuthorizationURL(OSSClient client,String bucketName,String key,int seconds){
		//设置URL过期时间
		Date expiration = new Date(new Date().getTime()+seconds);
		//生成URL
		URL url = client.generatePresignedUrl(bucketName, key, expiration);
		return url;
	}
	
	/**
	 * 设置Referer防盗链白名单
	 * @param list 白名单列表
	 * @param empty 是否允许referer字段为空
	 */
	public static void setRefererList(OSSClient client,List<String> list,boolean empty,String bucketName){
		BucketReferer br = new BucketReferer(empty,list);
		client.setBucketReferer(bucketName,br);
	}
	
	/**
	 * 获取白名单
	 */
	public static List<String> getRefererList(OSSClient client,String bucketName){
		BucketReferer br = client.getBucketReferer(bucketName);
		List<String> refererList = br.getRefererList();
		return refererList;
	}
	
	/**
	 * 清空白名单
	 */
	public static void clearRefererList(OSSClient client,String bucketName){
		BucketReferer br = new BucketReferer();
		client.setBucketReferer(bucketName,br);
	}
	
	/**
	 * 移除白名单的指定记录
	 */
	public static void removeRefererRecord(OSSClient client,String bucketName,String record){
		BucketReferer br = client.getBucketReferer(bucketName);
		List<String> list = br.getRefererList();
		for(int i=list.size()-1;i>=0;i--){
			if(list.get(i).equals(record))
				list.remove(i);
		}
		br.setRefererList(list);
	}
	
}
