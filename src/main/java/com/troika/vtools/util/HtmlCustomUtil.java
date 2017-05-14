package com.troika.vtools.util;

import static org.hamcrest.Matchers.instanceOf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.RegexFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlCustomUtil {
	public static Map<String, byte[]> getIMGInputStream(String url) throws Exception {
		//构造一个Parser，并设置相关的属性
		Parser parser = new Parser(url);
		parser.setEncoding("utf-8");
		List<String> links = new ArrayList<String>();
		List<byte[]> islist = new ArrayList<byte[]>();
		Map<String, byte[]> map = new HashMap<String,byte[]>();
		
		//获取img标签的图片路径
		NodeFilter filter = new TagNameFilter("img");
		NodeList nodes = parser.extractAllNodesThatMatch(filter);
		Node eachNode = null;
		ImageTag imageTag = null;
		String datasrc = null;
		String src = null;
		if (nodes != null) {
			// 遍历所有的img节点
			for (int i = 0; i < nodes.size(); i++) {
				eachNode = (Node) nodes.elementAt(i);
				if (eachNode instanceof ImageTag) {
					imageTag = (ImageTag) eachNode;
					// 获得html文本的原来的src属性
					datasrc = imageTag.getAttribute("data-src");
					if (datasrc != null && !datasrc.equals(""))
						links.add(datasrc);
					else {
						src = imageTag.getAttribute("src");
						if( src != null && !src.equals("")){
							links.add(src);
						}
							
					}
					// 设置新的src属性
					// imageTag.setImageURL(newSrcPath);
				}
			}
		}
		
		
		//返回带有background-image的标签
		Parser parser2 = new Parser(url);
		parser2.setEncoding("utf-8");
		NodeFilter attFilter = new HasAttributeFilter("style");
		NodeList attnodes = parser2.extractAllNodesThatMatch(attFilter);
		Node attNode = null;
		Tag attTag = null;
		String attSrc = null;
		if (attnodes != null) {
			// 遍历所有带"style"属性的节点
			for (int i = 0; i < attnodes.size(); i++) {
				attNode = (Node) attnodes.elementAt(i);
				if (attNode instanceof Tag) {
					attTag = (Tag) attNode;
					// 获取这些节点的background-image的url
					attSrc = attTag.getAttribute("style");
					if (attSrc != null && attSrc.contains("")&&attSrc.contains("background-image")&&attSrc.contains("url")){
						String[] strs = attSrc.split(";");
						for(String item:strs){
							if(item.contains("background-image") && item.contains("url")){
								String[] childs = item.split("\\(");
								for(String child:childs){
									if(child.contains("http")){
										child = child.replace(")", "");
										links.add(child.trim());
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		
		//使用所有获取的链接来获取图片的二进制流
		for (String item : links) {
			System.out.println("links:"+item);
			if(item.contains("http")||item.contains("HTTP")){
				URL iconurl = new URL(item);
	            HttpURLConnection conn = (HttpURLConnection)iconurl.openConnection();    
	            conn.setRequestMethod("GET");    
	            conn.setConnectTimeout(5 * 1000);
	            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据    
	            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据   
				map.put(item, btImg);
			}
		}
		return map;

	}
	
	/**  
     * 从输入流中获取数据  
     * @param inStream 输入流  
     * @return  
     * @throws Exception  
     */    
    public static byte[] readInputStream(InputStream inStream) throws Exception{    
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        while( (len=inStream.read(buffer)) != -1 ){    
            outStream.write(buffer, 0, len);    
        }    
        inStream.close();    
        return outStream.toByteArray();    
    }
    
    /**
     * 将传入的html代码中iframe的data-src属性赋值给src属性
     * @param html
     * @return html
     * @throws ParserException 
     */
    public static String setIFrameSrc(String url) throws ParserException{
    	//获取iframe标签的图片路径
		Parser parser = new Parser(url);
		parser.setEncoding("utf-8");
		String html = "";
        for(NodeIterator i=parser.elements();i.hasMoreNodes();){
            Node node = setChilNodesIFrameSrc(i.nextNode());
            html += node.toHtml();
            html += "\n";
        }
        return html;
    }
    
    /**
     * 遍历所有节点
     * @param node
     * @return
     */
    public static Node setChilNodesIFrameSrc(Node node){
    	if(node instanceof Tag){
    		Tag tag = (Tag)node;
    		if(tag.getTagName().equalsIgnoreCase("iframe")){
    			String datasrc = tag.getAttribute("data-src");
    			if (datasrc == null || datasrc.equals("")){
    				datasrc = tag.getAttribute("data-data-src");
    			}
    			if(datasrc != null && !datasrc.equals("")){
					datasrc = datasrc.replace("width", "");
					datasrc = datasrc.replace("height", "");
					tag.setAttribute("src", datasrc);
				}
				tag.setAttribute("width", "100%");
				tag.setAttribute("height", "450px");
				tag.removeAttribute("style");
//				String style = tag.getAttribute("style");
//				style = style.replace(" width", " max-width");
//				style = style.replace(" height", " max-height");
//				tag.setAttribute("style", style);
    		}
    	}
    	NodeList childs = new NodeList();
    	NodeList childnodes = node.getChildren();
    	if(childnodes!=null){
	    	for(int i = 0; i < childnodes.size(); i++){
	    		Node childNode = setChilNodesIFrameSrc(childnodes.elementAt(i));
	    		childs.add(childNode);
	    	}
	    	node.setChildren(childs);
    	}
    	return node;
    }
}
