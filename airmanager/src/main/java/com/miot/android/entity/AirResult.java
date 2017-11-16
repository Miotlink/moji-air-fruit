package com.miot.android.entity;

/**
 * 作者：{杭州妙联物联网技术有限公司}
 * 邮箱：qiaozhuang@miotlink.com
 * 时间：2017/11/15 0015 14:01
 */
public class AirResult {

	private int errorCode=0;

	private String errorMessage="";

	private Object data="";

	public AirResult(int errorCode, String errorMessage, Object data) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.data = data;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
