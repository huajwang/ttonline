package com.troika.emall.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class MyClient {
	public static String post(String url,Map<String, String> paramsMap) throws Exception {  
		HttpClient  client = new DefaultHttpClient();  
        HttpPost post = new HttpPost(url);  
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {  
        	params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());  
        }
        HttpEntity formEntity = new UrlEncodedFormEntity(params,"UTF-8");  
        post.setEntity(formEntity);  
        HttpResponse response = client.execute(post);
        String result = null;
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
            InputStream is = response.getEntity().getContent();  
            result = inStream2String(is);
        } 
        return result;
    }
	
	private static String inStream2String(InputStream is) throws Exception {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        byte[] buf = new byte[1024];  
        int len = -1;  
        while ((len = is.read(buf)) != -1) {  
            baos.write(buf, 0, len);  
        }  
        return new String(baos.toByteArray());  
    }  
}
