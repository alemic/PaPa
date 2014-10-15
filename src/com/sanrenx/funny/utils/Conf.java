package com.sanrenx.funny.utils;

import android.os.Environment;

public class Conf {
	/**调试模式*/
	public static final boolean DEBUG=true;  
	public final static String APIKEY = "SCTBqZIgFcbqjqG7mknA1Paz";
	//微博key
	public final static String SINA_APP_KEY = "1841549420";
	//腾讯key
	public final static String QQ_APP_KEY = "101079818";
	/** 手机SD存储卡 */
	public static final String SDCARD = Environment.getExternalStorageDirectory().getPath();
	/** 本地文件夹 */
	public static final String FILE_LOCATION = SDCARD + "/sanrenx/funny/";

	/** 数据库文件名 */
	public static final String DB_FILE_NAME = "funny_db";
	/** 数据库版本号 */
	public static final int DB_NOW_VERSION = 1;
	/** 标签表名 */
	public static final String DB_TAG_TABLE = "Tag";
	/** 笑话表名 */
	public static final String DB_JOKE_TABLE = "Joke";
	/** 用户表名 */
	public static final String DB_USER_TABLE = "User";
	/** 服务器地址 */
	public static final String SERVER = "http://joke.sanrenx.com";
	/** 文件地址 */
	public static final String FILE = SERVER + "/Mobile";
	/** 图片文件地址 */
	public static final String IMAGE_FILE = FILE + "/image";
	/** 数据地址 */
	public static final String JOSON = FILE + "/Joke";
	/** 登录地址 */
	public static final String TAG_URL = JOSON + "/getTags";
	/** 登录地址 */
	public static final String TAG_JOKE_URL = JOSON + "/getJokesByTagId";
	

}
