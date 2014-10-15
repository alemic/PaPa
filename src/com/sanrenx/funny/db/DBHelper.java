package com.sanrenx.funny.db;
import java.util.List;

import com.sanrenx.funny.utils.Conf;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper {
	private static final String TAG = "DBHelper";
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;
	public final static byte[] _writeLock = new byte[0];
	
	
	/** 打开数据库*/
	public void OpenDB(Context context) {
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
	}
	/** 关闭数据库*/
	public void Close() {
		dbHelper.close();
		if(db!=null){
			db.close();
		}
	}

	public void insert(List<ContentValues> list, String tableName) {
		synchronized (_writeLock) {
			db.beginTransaction();
			try {
				db.delete(tableName, null, null);
				for (int i = 0, len = list.size(); i < len; i++)
					db.insert(tableName, null, list.get(i));
				db.setTransactionSuccessful();
			} finally {
				db.endTransaction();
			}
		}
	}
	
	public DBHelper(Context context) {
		this.dbHelper = new DatabaseHelper(context);
	}
	/**
	 * 用于初始化数据库
	 * 
	 */
	public static class DatabaseHelper extends SQLiteOpenHelper {
		// 定义数据库文件
		private static final String DB_NAME = Conf.DB_FILE_NAME;
		// 定义数据库版本
		private static final int DB_VERSION = Conf.DB_NOW_VERSION;
		public DatabaseHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onOpen(SQLiteDatabase db) {
			super.onOpen(db);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			CreateTagDb(db);
			Log.i(TAG, "创建【标签tag表】成功");
			CreateJokeDb(db);
			Log.i(TAG, "创建【笑话joke表】成功");
			CreateUserDb(db);
			Log.i(TAG, "创建【用户user表】成功");

		}

		
		/**
		 * 创建 【标签表】 表
		 */
		private void CreateTagDb(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE ["+Conf.DB_TAG_TABLE+"] (");
			sb.append("[Id] INTEGER PRIMARY KEY, ");
			sb.append("[Title] NVARCHAR(50), ");
			sb.append("[Disp] NVARCHAR(250), ");
			sb.append("[Path] NVARCHAR(500), ");
			sb.append("[Sort] INTEGER(11), ");
			sb.append("[Hot] INTEGER(1)) ");
			db.execSQL(sb.toString());
		}
		
		/**
		 * 创建 【笑话表】 表
		 */
		private void CreateJokeDb(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE ["+Conf.DB_JOKE_TABLE+"] (");
			sb.append("[Id] INTEGER PRIMARY KEY, ");
			sb.append("[UId] INTEGER(11), ");
			sb.append("[Avatar] NVARCHAR(500), ");
			sb.append("[NickName] NVARCHAR(250), ");
			sb.append("[Title] NVARCHAR(500), ");
			sb.append("[Content] NVARCHAR(500), ");
			sb.append("[Cover] NVARCHAR(500), ");
			sb.append("[Path] NVARCHAR(500), ");
			sb.append("[Tags] NVARCHAR(500), ");
			sb.append("[Users] NVARCHAR(500), ");
			sb.append("[Reward] INTEGER(11), ");
			sb.append("[Brokerage] INTEGER(11), ");
			sb.append("[Type] INTEGER(11), ");
			sb.append("[Choice] INTEGER(1), ");
			sb.append("[Status] INTEGER(1)) ");
			db.execSQL(sb.toString());
		}
		
		/**
		 * 创建 【用户表】 表
		 */
		private void CreateUserDb(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("CREATE TABLE ["+Conf.DB_USER_TABLE+"] (");
			sb.append("[Id] INTEGER PRIMARY KEY, ");
			sb.append("[Nickname] NVARCHAR(50), ");
			sb.append("[Avatar] NVARCHAR(500)) ");
			db.execSQL(sb.toString());
		}
	
		
		
		/**
		 * 更新版本时更新表
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			DropTable(db);
			onCreate(db);
			Log.e("User", "onUpgrade");
		}
		/**
		 * 删除表
		 * 
		 * @param db
		 */
		private void DropTable(SQLiteDatabase db) {
			StringBuilder sb = new StringBuilder();
			sb.append("DROP TABLE IF EXISTS " + Conf.DB_TAG_TABLE + ";");

			db.execSQL(sb.toString());
		}
		/**
		 * 清空数据表（仅清空无用数据）
		 * @param db
		 */
		public static void ClearData(Context context){
			DatabaseHelper dbHelper = new DBHelper.DatabaseHelper(context);
			SQLiteDatabase db=dbHelper.getWritableDatabase();			
			db.execSQL("DELETE FROM "+Conf.DB_TAG_TABLE );//清空
	
			dbHelper.close();
		}
		
	}
}
