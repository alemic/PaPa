package com.sanrenx.funny;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.FrontiaUser;
import com.baidu.frontia.FrontiaUser.FrontiaUserDetail;
import com.baidu.frontia.api.FrontiaAuthorization;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaAuthorizationListener.AuthorizationListener;
import com.baidu.frontia.api.FrontiaAuthorizationListener.UserInfoListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sanrenx.funny.entity.UserEntity;
import com.sanrenx.funny.utils.Conf;
import com.sanrenx.funny.utils.HttpClientUtils;
import com.sanrenx.funny.utils.LogUtils;
import com.sanrenx.funny.utils.PreferenceUtils;
import com.sanrenx.funny.utils.TelephonyUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends Activity {
	private FrontiaAuthorization mAuthorization;
	private RelativeLayout weiboBtn, qqBtn;
	private TextView emailBtn;
	private Context mContext;
	private String mediaType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext=this;
		mAuthorization = Frontia.getAuthorization();
		LogUtils.initialize(getApplicationContext());
		setupViews();
		setupListener();
	}

	protected void setupViews() {

		weiboBtn = (RelativeLayout) findViewById(R.id.rl_weibo);
		qqBtn = (RelativeLayout) findViewById(R.id.rl_qq);
		emailBtn = (TextView) findViewById(R.id.tv_email);
	}
	
	protected void setupListener(){
		weiboBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mediaType=MediaType.SINAWEIBO.toString();
				startThirdPartyLogin(mediaType, Conf.QQ_APP_KEY);
			}
		});
		qqBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mediaType=MediaType.QZONE.toString();
				startThirdPartyLogin(mediaType, Conf.QQ_APP_KEY);
			}
		});
		emailBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
			}
		});
		
	}

	// 第三方登陆
	private void startThirdPartyLogin(String accessToken, String appKey) {
		mAuthorization.enableSSO(accessToken, appKey);
		mAuthorization.authorize(this,accessToken, new AuthorizationListener() {

			@Override
			public void onSuccess(FrontiaUser result) {
				checkExist(result);
				Frontia.setCurrentAccount(result);
				PreferenceUtils.setPrefString(mContext, "social", result.getId());
				
				String logstr="social id: " + result.getId() + "\n"+"name: "
						+ result.getName() + "\n" + "token: "
						+ result.getAccessToken() + "\n" + "expired: "
						+ result.getExpiresIn()+ "\n" + "type: "
						+ result.getType();
				LogUtils.d(logstr);	
			}

			@Override
			public void onFailure(int errorCode, String errorMessage) {

				LogUtils.d("errCode:" + errorCode + ", errMsg:" + errorMessage);

			}

			@Override
			public void onCancel() {

				LogUtils.d("cancel");

			}

		});
	}

	// 获取第三方用户信息
	private void userinfo(String mediaType) {
		mAuthorization.getUserInfo(mediaType, new UserInfoListener() {

			@Override
			public void onSuccess(FrontiaUserDetail result) {
				registerNewUser(result);
				String resultStr = "username:" + result.getName() + "\n"
						+ "birthday:" + result.getBirthday() + "\n" + "city:"
						+ result.getCity() + "\n" + "province:"
						+ result.getProvince() + "\n" + "sex:"
						+ result.getSex() + "\n" + "pic url:"
						+ result.getHeadUrl() + "\n";
				LogUtils.d(resultStr);

			}

			@Override
			public void onFailure(int errCode, String errMsg) {

				LogUtils.d("errCode:" + errCode + ", errMsg:" + errMsg);

			}

		});
	}
	
	private void checkExist(FrontiaUser social){
		RequestParams params = new RequestParams();
		params.put("social", social.getId());
		params.put("bid", PreferenceUtils.getPrefString(mContext, "bid", ""));
		params.put("channel", PreferenceUtils.getPrefString(mContext, "channel", ""));
		params.put("imei", TelephonyUtils.getIMEI(mContext));
		HttpClientUtils.post(Conf.APIKEY, params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				try {
					int status=response.isNull("status") ? 0 : response.getInt("status");
					String info=response.isNull("info") ? "" : response.getString("info");
					if(status==1){
						jsonToUserMemory(response);
					}else{
						userinfo(mediaType);
						LogUtils.d(info);
				
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				
			}
			
		});
	}
	
	private void registerNewUser(FrontiaUserDetail result){
		RequestParams params = new RequestParams();
		params.put("social", PreferenceUtils.getPrefString(mContext, "socail", ""));
		params.put("bid", PreferenceUtils.getPrefString(mContext, "bid", ""));
		params.put("channel", PreferenceUtils.getPrefString(mContext, "channel", ""));
		params.put("imei", TelephonyUtils.getIMEI(mContext));
		params.put("nickname", result.getName());
		params.put("avatar", result.getHeadUrl());
		HttpClientUtils.post(Conf.APIKEY, params, new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(int statusCode, Header[] headers,
					JSONObject response) {
				try {
					int status=response.isNull("status") ? 0 : response.getInt("status");
					String info=response.isNull("info") ? "" : response.getString("info");
					if(status==1){
						jsonToUserMemory(response);
					}else{
						LogUtils.d(info);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					Throwable throwable, JSONObject errorResponse) {
				
			}
			
		});
	}
	
	private void jsonToUserMemory(JSONObject response) throws JSONException{
		JSONObject jObj=response.isNull("data") ? null : response.getJSONObject("data");
		UserEntity Entity=new UserEntity();
		Entity.setId(jObj.isNull("id") ? null : jObj.getString("id"));
		Entity.setNickname(jObj.isNull("nickname") ? null : jObj.getString("nickname"));
		Entity.setAvatar(jObj.isNull("avatar") ? null : jObj.getString("avatar"));
		PreferenceUtils.setPrefString(mContext, "uid", Entity.getId());
		PreferenceUtils.setPrefString(mContext, "nickname", Entity.getNickname());
		PreferenceUtils.setPrefString(mContext, "avatar", Entity.getAvatar());
		LogUtils.d("uid:"+Entity.getId()+"nickname:"+Entity.getNickname());
	}

}
