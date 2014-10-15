package com.sanrenx.funny.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class TelephonyUtils {
	public static TelephonyManager getTelephonyManager(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager;
	}

	/**
	 * 获取当前设置的电话号码
	 */
	public static String getNativePhoneNumber(Context context) {
		return getTelephonyManager(context).getLine1Number();
	}
	/**
	 * 获取IMEI
	 */
	public static String getIMEI(Context context) {
		return getTelephonyManager(context).getDeviceId();
	}
	/**
	 * 获取IMSI
	 */
	public static String getIMSI(Context context) {
		return getTelephonyManager(context).getSubscriberId();
	}

	/**
	 * 获取手机服务商信息 需要加入权限<uses-permission
	 * android:name="android.permission.READ_PHONE_STATE"/>
	 */
	public String getProvidersName(Context context) {
		String ProvidersName = null;
		String IMSI = getIMSI(context);
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
		System.out.println(IMSI);
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001")) {
			ProvidersName = "中国联通";
		} else if (IMSI.startsWith("46003")) {
			ProvidersName = "中国电信";
		}
		return ProvidersName;
	}
}
