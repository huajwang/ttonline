package com.troika.emall.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.ServiceException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * 该示例代码展示了如果在OSS中创建和删除一个Bucket，以及如何上传和下载一个文件。
 * 
 * 该示例代码的执行过程是： 1. 创建一个Bucket（如果已经存在，则忽略错误码）； 2. 上传一个文件到OSS； 3. 下载这个文件到本地； 4.
 * 清理测试资源：删除Bucket及其中的所有Objects。
 * 
 * 尝试运行这段示例代码时需要注意： 1. 为了展示在删除Bucket时除了需要删除其中的Objects,
 * 示例代码最后为删除掉指定的Bucket，因为不要使用您的已经有资源的Bucket进行测试！ 2.
 * 请使用您的API授权密钥填充ACCESS_ID和ACCESS_KEY常量； 3.
 * 需要准确上传用的测试文件，并修改常量uploadFilePath为测试文件的路径； 修改常量downloadFilePath为下载文件的路径。 4.
 * 该程序仅为示例代码，仅供参考，并不能保证足够健壮。
 *
 */
public class OSSObjectUtil {

	// 创建Bucket.
	public static void ensureBucket(OSSClient client, String bucketName)
			throws OSSException, ClientException {
		try {
			// 创建bucket
			client.createBucket(bucketName);
		} catch (ServiceException e) {
			if (!OSSErrorCode.BUCKET_ALREADY_EXISTS.equals(e.getErrorCode())) {
				// 如果Bucket已经存在，则忽略
				throw e;
			}
		}
	}

	// 删除一个Bucket和其中的Objects
	public static void deleteBucket(OSSClient client, String bucketName)
			throws OSSException, ClientException {
		ObjectListing ObjectListing = client.listObjects(bucketName);
		List<OSSObjectSummary> listDeletes = ObjectListing.getObjectSummaries();
		for (int i = 0; i < listDeletes.size(); i++) {
			String objectName = listDeletes.get(i).getKey();
			// 如果不为空，先删除bucket下的文件
			client.deleteObject(bucketName, objectName);
		}
		client.deleteBucket(bucketName);
	}

	// 把Bucket设置为所有人可读
	public static void setBucketPublicReadable(OSSClient client,
			String bucketName) throws OSSException, ClientException {
		// 创建bucket
		client.createBucket(bucketName);

		// 设置bucket的访问权限，public-read-write权限
		client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
	}

	// 上传文件
	public static void uploadFile(OSSClient client, String bucketName,
			String key, InputStream input,long len) throws OSSException, ClientException,
			FileNotFoundException {
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(len);
		// 可以在metadata中标记文件类型
		objectMeta.setContentType("image/jpeg");
		client.putObject(bucketName, key, input, objectMeta);
	}
	
	// 上传文件
	public static void uploadFileWithFileType(OSSClient client, String bucketName,
			String key, File file, String contype) throws OSSException, ClientException,
			FileNotFoundException {
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(file.length());
		// 可以在metadata中标记文件类型
		objectMeta.setContentType(contype);

		InputStream input = new FileInputStream(file);
		client.putObject(bucketName, key, input, objectMeta);
	}

	// 下载文件
	public static void downloadFile(OSSClient client, String bucketName,
			String key, String filename) throws OSSException, ClientException {
		client.getObject(new GetObjectRequest(bucketName, key), new File(
				filename));
	}

	// 上传文件
	public static void uploadFileByIs(OSSClient client, String bucketName,
			String key, InputStream is) throws OSSException, ClientException,IOException,
			FileNotFoundException {
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(is.available());
		// 可以在metadata中标记文件类型
		objectMeta.setContentType("image/jpeg");
		client.putObject(bucketName, key, is, objectMeta);
	}
	// 上传文件
	public static void uploadFileWithFileType(OSSClient client, String bucketName,
			String key, InputStream is, String contype) throws OSSException, ClientException,IOException,
			FileNotFoundException {
		ObjectMetadata objectMeta = new ObjectMetadata();
		objectMeta.setContentLength(is.available());
		// 可以在metadata中标记文件类型
		objectMeta.setContentType(contype);
		client.putObject(bucketName, key, is, objectMeta);
	}
}
