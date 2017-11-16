package com.miot.android.callback;

/**
 * 作者：{杭州妙联物联网技术有限公司}
 * 邮箱：qiaozhuang@miotlink.com
 * 时间：2017/11/15 0015 11:23
 */
public interface AirIReceiver {

	public void loginOnReceiver(int errorCode,String errorMessage,String data);

	public void activateAirOnReceiver(int errorCode,String errorMessage,String data);

}
