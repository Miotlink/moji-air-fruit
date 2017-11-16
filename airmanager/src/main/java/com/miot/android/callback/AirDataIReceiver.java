package com.miot.android.callback;

import com.miot.android.entity.AirResult;

/**
 * 作者：{杭州妙联物联网技术有限公司}
 * 邮箱：qiaozhuang@miotlink.com
 * 时间：2017/11/15 0015 13:15
 */
public interface AirDataIReceiver {
	/**
	 * 获取空气果数据
	 * @param error
	 * @param message
	 * @param data
	 */
	public void airOnReceiver(int error,String message,String data);

	/**
	 * 播放
	 * @param error
	 * @param errorMessage
	 * @param data
	 */
	public void airPlayVoiceOnReceiver(int error,String errorMessage,String data);

	/**

	 */
	public void airDetectOnReceiver(int type,AirResult airResult);

	/**
	 * 音量
	 * @param error
	 * @param errorMessage
	 * @param data
	 */
	public void airVolumeOnReceiver(int error,String errorMessage,String data);

	/**
	 * 获取定时语音播报列表
	 */
	public void airVoiceTimeOnReceiver(int type, AirResult air);



}
