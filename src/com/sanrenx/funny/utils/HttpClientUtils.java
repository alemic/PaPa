package com.sanrenx.funny.utils;

import android.content.Context;

import com.loopj.android.http.*;

public class HttpClientUtils {
  private static final String BASE_URL = "";

  private static AsyncHttpClient client = new AsyncHttpClient();

  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
      client.get(getAbsoluteUrl(url), params, responseHandler);
  }

  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
      client.post(getAbsoluteUrl(url), params, responseHandler);
  }
  
  public static void cancelRequests(Context context){
	  client.cancelRequests(context, true);
  }

  private static String getAbsoluteUrl(String relativeUrl) {
      return BASE_URL + relativeUrl;
  }

}