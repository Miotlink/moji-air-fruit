package com.miot.android.air;

import android.content.Context;

import com.miot.android.callback.AirDataIReceiver;
import com.miot.android.callback.AirIReceiver;
import com.miot.android.entity.AirResult;
import com.moji.airnut.sdk.MJAirnut;
import com.moji.airnut.sdk.bean.Alarm;
import com.moji.airnut.sdk.http.CheckTimeResp;
import com.moji.airnut.sdk.http.ClockResp;
import com.moji.airnut.sdk.logic.AirnutType;
import com.moji.airnut.sdk.logic.ErrorType;
import com.moji.airnut.sdk.logic.OnActivateListener;
import com.moji.airnut.sdk.logic.OnAirnutDataRequestListener;
import com.moji.airnut.sdk.logic.OnAlarmClockGetListener;
import com.moji.airnut.sdk.logic.OnAlarmClockSetListener;
import com.moji.airnut.sdk.logic.OnDetectIntervalGetListener;
import com.moji.airnut.sdk.logic.OnDetectIntervalSetListener;
import com.moji.airnut.sdk.logic.OnDetectListener;
import com.moji.airnut.sdk.logic.OnLoginListener;
import com.moji.airnut.sdk.logic.OnRemoveListener;
import com.moji.airnut.sdk.logic.OnVoiceListener;
import com.moji.airnut.sdk.logic.OnVolumeListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：{杭州妙联物联网技术有限公司}
 * 邮箱：qiaozhuang@miotlink.com
 * 时间：2017/11/15 0015 12:36
 */
public class AirManager implements OnLoginListener,OnActivateListener,OnAirnutDataRequestListener,
		OnVoiceListener,OnDetectListener,OnAlarmClockGetListener,OnVolumeListener,OnAlarmClockSetListener,
		OnDetectIntervalGetListener,OnDetectIntervalSetListener,OnRemoveListener {

	private static AirManager instance=null;

	private MJAirnut mjAirnut=null;

	public static synchronized  AirManager getInstance() {
		if (instance==null){
			synchronized (AirManager.class){
				if (instance==null){
					instance=new AirManager();
				}
			}
		}
		return instance;
	}

	private AirIReceiver onIReceiver=null;

	private AirDataIReceiver airDataiReceiver=null;

	public void setAirDataiReceiver(AirDataIReceiver airDataiReceiver) {
		this.airDataiReceiver = airDataiReceiver;
	}

	public void setOnIReceiver(AirIReceiver onIReceiver) {
		this.onIReceiver = onIReceiver;
	}

	private AirManager(){}
	private Context context=null;
	public  void init(Context cx)throws Exception{
		this.context=cx;
		mjAirnut=new MJAirnut(cx);
	}

	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	public void login(String userName,String password)throws Exception{
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		mjAirnut.login(userName,password,this);
	}

	/**
	 * 配置
	 * @param routeName
	 * @param routePassword
	 * @param latitude
	 * @param longitude
	 * @param type
	 * @throws Exception
	 */
	public void airSmartConfig(String routeName,String routePassword,float latitude,float longitude,int type)throws Exception{
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		AirnutType airnutType=null;
		if (type==1){
			airnutType=AirnutType.AIRNUT_ONE;
		}else if (type==2){
			airnutType=AirnutType.AIRNUT_ONE_S;
		}else if (type==3){
			airnutType=AirnutType.AIRNUT_TWO;
		}
		mjAirnut.activate(routeName,routePassword,latitude,longitude,airnutType,this);
	}


	/**
	 * 播放声音
	 * @param mac
	 * @throws Exception
	 */
	public void playVoice(String mac)throws Exception{
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		mjAirnut.playVoice(mac,this);
	}

	/**
	 * 查询数据
	 * @param mac
	 * @throws Exception
	 */
	public void airDataRequest(String mac)throws Exception{
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		mjAirnut.dataRequest(mac,this);
	}

	/**
	 * 发送监测指令
	 */

	public void airDetcet(String mac){
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		mjAirnut.detect(mac,this);
	}

	public void airDetectIntervalRequest(String mac)throws Exception{
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		mjAirnut.detectIntervalRequest(mac,this);
	}
	public void airSetDetectInterval(String mac,String s)throws Exception{
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		JSONObject jsonObject=null;
		try{
			jsonObject=new JSONObject(s);
			if (jsonObject==null){
				throw new NullPointerException("");
			}
		}catch (Exception e){

			e.printStackTrace();
		}
		String dayInterval=jsonObject.getString("time");
		String nightInterval=jsonObject.getString("night_time");
		String nightStart=jsonObject.getString("night_start");
		String nightLength=jsonObject.getString("night_length");;
		mjAirnut.detectIntervalSet(mac,dayInterval,nightInterval,nightStart,nightLength,this);
	}
	public void airSetVoice(String mac,int voice)throws Exception{
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		mjAirnut.setVolume(mac,voice,this);
	}
	public void airGetVoiceAlarmList(String mac)throws Exception{
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		mjAirnut.getVoiceAlarmList(mac,this);
	}
	public void airSetVoiceAlarmList(String mac,String  voice)throws Exception{
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		JSONArray jsonArray=new JSONArray(voice);
		List<Alarm> alarms=new ArrayList<>();
		if (jsonArray!=null){
			for (int i=0;i<jsonArray.length();i++){
				JSONObject jsonObject1=new JSONObject(jsonArray.get(i).toString());
				Alarm alarm=new Alarm();
				alarm.t=Integer.parseInt(jsonObject1.getString("t"));
				alarm.hour=Integer.parseInt(jsonObject1.getString("hour"));
				alarm.minute=Integer.parseInt(jsonObject1.getString("minute"));
				alarm.l=Integer.parseInt(jsonObject1.getString("l"));
				alarm.il=Integer.parseInt(jsonObject1.getString("il"));
				alarm.p=Integer.parseInt(jsonObject1.getString("p"));
				alarms.add(alarm);
			}
		}
		mjAirnut.setVoiceAlarmList(mac,alarms,this);
	}


	public void deleteAir(String mac){
		if (mjAirnut==null){
			mjAirnut=new MJAirnut(context);
		}
		mjAirnut.removeAirnut(mac,this);
	}

	@Override
	public void loginFailed(ErrorType errorType) {
		if (onIReceiver!=null){
			onIReceiver.loginOnReceiver(errorType.getErrorType(),errorType.getErrorInfo(),"");
		}
	}

	@Override
	public void loginSuccess() {
		if (onIReceiver!=null){
			onIReceiver.loginOnReceiver(10,"success","");
		}
	}

	@Override
	public void activateSuccess(String s) {
		if (onIReceiver!=null){
			onIReceiver.activateAirOnReceiver(1,"success",s);
		}
	}

	@Override
	public void activateFailed(ErrorType errorType) {
		if (onIReceiver!=null){
			onIReceiver.activateAirOnReceiver(errorType.getErrorType(),errorType.getErrorInfo(),"");
		}
	}

	@Override
	public void dataRequestFailed(ErrorType errorType) {

		if (airDataiReceiver!=null){
			airDataiReceiver.airOnReceiver(errorType.getErrorType(),errorType.getErrorInfo(),"");
		}
	}

	@Override
	public void dataRequestSuccess(String s) {
		airDataiReceiver.airOnReceiver(1,"success",s);
	}

	@Override
	public void playVoiceFailed(ErrorType errorType) {
		if (airDataiReceiver!=null){
			airDataiReceiver.airPlayVoiceOnReceiver(errorType.getErrorType(),errorType.getErrorInfo(),"");
		}
	}

	@Override
	public void playVoiceSuccess() {
		if (airDataiReceiver!=null){
			airDataiReceiver.airPlayVoiceOnReceiver(1,"success","");
		}
	}

	@Override
	public void detectFailed(ErrorType errorType) {
		if (airDataiReceiver!=null){
			AirResult airResult=new AirResult(errorType.getErrorType(),errorType.getErrorInfo(),"");
			airDataiReceiver.airDetectOnReceiver(200,airResult);
		}
	}

	@Override
	public void detectSuccess() {
		if (airDataiReceiver!=null){
			AirResult airResult=new AirResult(1,"","");
			airDataiReceiver.airDetectOnReceiver(200,airResult);
		}
	}

	@Override
	public void alarmClockGetFailed(ErrorType errorType) {
		if (airDataiReceiver!=null){
			AirResult airResult=new AirResult(errorType.getErrorType(),errorType.getErrorInfo(),"");
			airDataiReceiver.airVoiceTimeOnReceiver(101,airResult);
		}
	}

	@Override
	public void alarmClockGetSuccess(ClockResp clockResp) {
		if (airDataiReceiver!=null){
			AirResult airResult=new AirResult(1,"",clockResp);
			airDataiReceiver.airVoiceTimeOnReceiver(101,airResult);
		}
	}

//	@Override
//	public void alarmClockSetSuccess(ClockResp clockResp) {
//		if (airDataiReceiver!=null){
//			AirResult airResult=new AirResult(1,"",clockResp);
//			airDataiReceiver.airVoiceTimeOnReceiver(101,airResult);
//		}
//	}

	@Override
	public void setVolumeFailed(ErrorType errorType) {
		if (airDataiReceiver!=null){
			airDataiReceiver.airPlayVoiceOnReceiver(errorType.getErrorType(),errorType.getErrorInfo(),"");
		}
	}

	@Override
	public void setVolumeSuccess() {
		if (airDataiReceiver!=null){
			airDataiReceiver.airVolumeOnReceiver(1,"","");
		}
	}

	@Override
	public void alarmClockSetFailed(ErrorType errorType) {
		if (airDataiReceiver!=null){
			AirResult airResult=new AirResult(errorType.getErrorType(),errorType.getErrorInfo(),"");
			airDataiReceiver.airVoiceTimeOnReceiver(102,airResult);
		}
	}

	@Override
	public void alarmClockSetSuccess() {
		if (airDataiReceiver!=null){
			AirResult airResult=new AirResult(1,"","");
			airDataiReceiver.airVoiceTimeOnReceiver(102,airResult);
		}
	}

	@Override
	public void timeCheckSuccess(CheckTimeResp checkTimeResp) {
		if (airDataiReceiver!=null){
			AirResult airResult=new AirResult(1,"",checkTimeResp);
			airDataiReceiver.airDetectOnReceiver(201,airResult);
		}
	}

	@Override
	public void timeCheckFailed(ErrorType errorType) {
		if (airDataiReceiver!=null){
			AirResult airResult=new AirResult(errorType.getErrorType(),errorType.getErrorInfo(),"");
			airDataiReceiver.airDetectOnReceiver(201,airResult);
		}
	}

	@Override
	public void timeSetSuccess() {
		if (airDataiReceiver!=null){
			AirResult airResult=new AirResult(1,"","");
			airDataiReceiver.airDetectOnReceiver(202,airResult);
		}
	}

	@Override
	public void timeSetFailed(ErrorType errorType) {
		if (airDataiReceiver!=null){
			AirResult airResult=new AirResult(errorType.getErrorType(),errorType.getErrorInfo(),"");
			airDataiReceiver.airDetectOnReceiver(202,airResult);
		}
	}

	@Override
	public void removeSuccess() {

	}

	@Override
	public void removeFailed(ErrorType errorType) {

	}
}
