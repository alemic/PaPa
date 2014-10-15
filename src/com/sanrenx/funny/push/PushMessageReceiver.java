package com.sanrenx.funny.push;

import java.util.List;

import android.content.Context;
import android.util.Log;

import com.baidu.frontia.api.FrontiaPushMessageReceiver;
import com.sanrenx.funny.utils.PreferenceUtils;

public class PushMessageReceiver extends FrontiaPushMessageReceiver {

	private final static String TAG = "PushMessageReceiver";

	@Override
	public void onBind(Context context, int errorCode, String appid,
			String userId, String channelId, String requestId) {
		StringBuffer sb = new StringBuffer();
		sb.append("绑定成功\n");
		sb.append("errCode:"+errorCode);
		sb.append("appid:"+appid+"\n");
		sb.append("userId:"+userId+"\n");
		sb.append("channelId:"+channelId+"\n");
		sb.append("requestId"+requestId+"\n");
		Log.d(TAG,sb.toString());
		PreferenceUtils.setPrefString(context, "appid", appid);
		PreferenceUtils.setPrefString(context, "bid", userId);
		PreferenceUtils.setPrefString(context, "channel", channelId);
	}

	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
		StringBuffer sb = new StringBuffer();
		sb.append("解绑成功\n");
		sb.append("errCode:"+errorCode);
		sb.append("requestId"+requestId+"\n");
		Log.d(TAG,sb.toString());
	}

	@Override
	public void onSetTags(Context context, int errorCode,
			List<String> successTags, List<String> failTags,
			String requestId) {
		StringBuffer sb = new StringBuffer();
		sb.append("设置tag成功\n");
		sb.append("errCode:"+errorCode);
		sb.append("success tags:");
		for(String tag:successTags){
			sb.append(tag+"\n");
		}
		sb.append("fail tags:");
		for(String tag:failTags){
			sb.append(tag+"\n");
		}
		sb.append("requestId"+requestId+"\n");
		Log.d(TAG,sb.toString());
	}

	@Override
	public void onDelTags(Context context, int errorCode,
			List<String> successTags, List<String> failTags,
			String requestId) {
		StringBuffer sb = new StringBuffer();
		sb.append("删除tag成功\n");
		sb.append("errCode:"+errorCode);
		sb.append("success tags:");
		for(String tag:successTags){
			sb.append(tag+"\n");
		}
		sb.append("fail tags:");
		for(String tag:failTags){
			sb.append(tag+"\n");
		}
		sb.append("requestId"+requestId+"\n");
		Log.d(TAG,sb.toString());
	}

	@Override
	public void onListTags(Context context, int errorCode,
			List<String> tags, String requestId) {
		StringBuffer sb = new StringBuffer();
		sb.append("list tag成功\n");
		sb.append("errCode:"+errorCode);
		sb.append("tags:");
		for(String tag:tags){
			sb.append(tag+"\n");
		}
		sb.append("requestId"+requestId+"\n");
		Log.d(TAG,sb.toString());
	}

	@Override
	public void onMessage(Context context, String message,
			String customContentString) {
		StringBuffer sb = new StringBuffer();
		sb.append("收到消息\n");
		sb.append("内容是:"+customContentString+"\n");
		sb.append("tags:");
		sb.append("message:"+message+"\n");
		Log.d(TAG,sb.toString());
	}

	@Override
	public void onNotificationClicked(Context context, String title,
			String description, String customContentString) {
		StringBuffer sb = new StringBuffer();
		sb.append("通知被点击\n");
		sb.append("title:"+title+"\n");
		sb.append("description:"+description);
		sb.append("customContentString:"+customContentString+"\n");
		Log.d(TAG,sb.toString());
	}

	

}
