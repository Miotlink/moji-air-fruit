package com.miot.android.air;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.miot.android.callback.AirDataIReceiver;
import com.miot.android.callback.AirIReceiver;
import com.miot.android.entity.AirResult;

public class MainActivity extends AppCompatActivity implements AirIReceiver,AirDataIReceiver,View.OnClickListener{

	private AirManager airManager=null;

	private Button add=null;
	private Button get=null;
	private Button jiance=null;
	private Button set_jiance_time=null;
	private Button play=null;
	private Button get_dingshi_play=null;
	private Button dingshi_play=null;
	private Button get_jiance_time=null;
	private SeekBar probar=null;

	private void initView(){
		add=(Button)findViewById(R.id.add);
		get=(Button)findViewById(R.id.getData);
		jiance=(Button)findViewById(R.id.jiance);
		set_jiance_time=(Button)findViewById(R.id.set_jiance_time);
		play=(Button)findViewById(R.id.play);
		get_dingshi_play=(Button)findViewById(R.id.get_dingshi_play);
		get_jiance_time=(Button)findViewById(R.id.get_jiance_time);
		dingshi_play=(Button)findViewById(R.id.dingshi_play);
		probar=(SeekBar)findViewById(R.id.probar);
		add.setOnClickListener(this);
		get.setOnClickListener(this);
		jiance.setOnClickListener(this);
		dingshi_play.setOnClickListener(this);
		get_jiance_time.setOnClickListener(this);
		get_dingshi_play.setOnClickListener(this);
		play.setOnClickListener(this);
		set_jiance_time.setOnClickListener(this);
		probar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				try {
					airManager.airSetVoice("c89346c61c10",seekBar.getProgress());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mian);
		initView();
		try {
			airManager=AirManager.getInstance();
			airManager.init(this);
			airManager.setOnIReceiver(this);
			airManager.setAirDataiReceiver(this);
			airManager.login("18969978170","miotlink7400");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void loginOnReceiver(int errorCode, String errorMessage, String data) {
		Log.e(errorMessage,errorCode+"");
	}

	@Override
	public void activateAirOnReceiver(int errorCode, String errorMessage, String data) {
		Log.e(AirManager.class.getName(),"激活状态:"+errorMessage+"结果"+data);

	}

	@Override
	public void airOnReceiver(int error, String message, String data) {
		Log.e(AirManager.class.getName(),"获取数据状态："+"错误码"+error+message+"空气果"+data);
	}

	@Override
	public void airPlayVoiceOnReceiver(int error, String errorMessage, String data) {

		Log.e(AirManager.class.getName(),"获取数据状态："+"错误码"+error+errorMessage+"空气果"+data);
	}

	@Override
	public void airDetectOnReceiver(int type, AirResult airResult) {
		Log.e(AirManager.class.getName(),"获取数据状态："+"错误码"+airResult.getErrorCode()+airResult.getErrorMessage()+"空气果"+airResult.getData().toString());
	}

	@Override
	public void airVolumeOnReceiver(int error, String errorMessage, String data) {
		Log.e(AirManager.class.getName(),"获取数据状态："+"错误码"+error+errorMessage+"空气果"+data);
	}

	@Override
	public void airVoiceTimeOnReceiver(int type, AirResult air) {
		Log.e(AirManager.class.getName()+type,"获取数据状态："+"错误码"+air.getErrorCode()+air.getErrorMessage()+"空气果"+air.getData());
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.add:
				try {
					airManager.airSmartConfig("TP-LINK-WH","12345678",(float) 30.184504,(float)120.165253,2);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.getData:
				try {
//					airManager.airDataRequest("C8:93:46:C6:1C:10");

					airManager.airDataRequest("c89346c61c10");
				} catch (Exception e) {
					e.printStackTrace();
				}
			case R.id.jiance:
				try {
//					airManager.airDataRequest("C8:93:46:C6:1C:10");
					airManager.airDetcet("c89346c61c10");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.get_jiance_time:
				try {
//					airManager.airDataRequest("C8:93:46:C6:1C:10");
					airManager.airDetectIntervalRequest("c89346c61c10");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.set_jiance_time:
				try {
					airManager.airSetDetectInterval("c89346c61c10","{\"time\":\"1800\",\"night_time\":\"3600\",\"night_start\":\"92800\",\"night_length\":\"29800\"}");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.get_dingshi_play:
				try {
					airManager.airGetVoiceAlarmList("c89346c61c10");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case R.id.dingshi_play:
				try {
					airManager.airSetVoiceAlarmList("c89346c61c10","");
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
		}
	}
}
