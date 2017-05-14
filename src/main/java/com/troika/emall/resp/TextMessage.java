package com.troika.emall.resp;

/**
 * 文本消息
 * 
 * @author liufeng
 * @date 2013-05-19
 */
public class TextMessage extends BaseMessage {
	// 回复的消息内�?
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}