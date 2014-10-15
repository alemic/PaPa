package com.sanrenx.funny.db;

import java.util.ArrayList;
import java.util.List;

import com.sanrenx.funny.entity.JokeEntity;
import com.sanrenx.funny.entity.TagEntity;
import com.sanrenx.funny.entity.UserEntity;
import com.sanrenx.funny.utils.Conf;
import com.sanrenx.funny.utils.LogUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class JokeHelper {
	private DBHelper.DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	public JokeHelper(Context context) {
		LogUtils.initialize(context);
		dbHelper = new DBHelper.DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
		db.close();
	}
	
	//查询笑话
	public List<JokeEntity> getJokeListFromCache(String _tag) {
		List<JokeEntity> lists = new ArrayList<JokeEntity>();
		String[] args = {"%,"+_tag+",%"};
		String sql="SELECT * FROM "+Conf.DB_JOKE_TABLE+" WHERE ','||Tags||',' LIKE ? ";
		Cursor cursor = db.rawQuery(sql, args);
		LogUtils.i("数据条数:"+ cursor.getCount());
		while (cursor != null && cursor.moveToNext()) {
			JokeEntity entity = new JokeEntity();
			entity.setId(cursor.isNull(0)?"":cursor.getString(0));
			entity.setUid(cursor.isNull(1)?"":cursor.getString(1));
			entity.setAvatar(cursor.isNull(2)?"":cursor.getString(2));
			entity.setNickname(cursor.isNull(3)?"":cursor.getString(3));
			entity.setTitle(cursor.isNull(4)?"":cursor.getString(4));
			entity.setContent(cursor.isNull(5)?"":cursor.getString(5));
			entity.setCover(cursor.isNull(6)?"":cursor.getString(6));
			entity.setPath(cursor.isNull(7)?"":cursor.getString(7));
			entity.setReward(cursor.isNull(10)? 0 :cursor.getInt(10));
			entity.setChoice(cursor.isNull(13)? 0 :cursor.getInt(13));
			entity.setTagLists(getTagLists(cursor.isNull(8)?"":cursor.getString(8)));
			entity.setUserLists(getUserLists(cursor.isNull(9)?"":cursor.getString(9)));
			lists.add(entity);
		}
		cursor.close();
		return lists;
	}
	//查询笑话标签
	private List<TagEntity> getTagLists(String str){
		LogUtils.i("标签组tags:"+str);
		String[] strArray=str.split(",");
		List<TagEntity> lists=new ArrayList<TagEntity>();
		for (int i = 0; i < strArray.length; i++) {
			String where = "Id= ? ";
			LogUtils.i("标签tag id:"+strArray[i]);
			String[] args = { strArray[i]};	
			Cursor cursor = db.query(Conf.DB_TAG_TABLE, null, where,args, null, null, null, null);
			if (cursor != null && cursor.getCount() != 0) {
				cursor.moveToFirst();
				TagEntity entity = new TagEntity();
				entity.setId(cursor.getString(0));
				entity.setTitle(cursor.getString(1));
				lists.add(entity);
			}
			cursor.close();
		}
		return lists;
	}
	//查询打赏用户
	private List<UserEntity> getUserLists(String str){
		LogUtils.i("标签组tags:"+str);
		String[] strArray=str.split(",");
		List<UserEntity> lists=new ArrayList<UserEntity>();
		for (int i = 0; i < strArray.length; i++) {
			String where = "Id= ? ";
			String[] args = { strArray[i]};
			Cursor cursor = db.query(Conf.DB_USER_TABLE, null, where,args, null, null, null, null);
			if (cursor != null && cursor.getCount() != 0) {
				cursor.moveToFirst();
				UserEntity entity = new UserEntity();
				entity.setId(cursor.getString(0));
				entity.setNickname(cursor.getString(1));
				entity.setAvatar(cursor.getString(2));
				lists.add(entity);
			}
			cursor.close();
		}
		return lists;
	}
	
	

	//插入笑话列表
	public void insertJokeListToCache(List<JokeEntity> list) {

		int size = list.size();
		for (int i = 0; i < size; i++) {
			ContentValues values = new ContentValues();
			JokeEntity entity=list.get(i);
			values.put("Id", entity.getId());
			values.put("UId", entity.getUid());
			values.put("Avatar", entity.getAvatar());
			values.put("NickName", entity.getNickname());
			values.put("Title", entity.getTitle());
			values.put("Content", entity.getContent());
			values.put("Cover", entity.getCover());
			values.put("Path", entity.getPath());
			values.put("Tags", entity.getTags());
			values.put("Users", entity.getUsers());
			values.put("Reward", entity.getReward());
			values.put("Brokerage", entity.getBrokerage());
			values.put("Type", entity.getType());
			values.put("Choice", entity.getChoice());
			values.put("Status", entity.getStatus());
			insertTagListToCache(entity.getTagLists());
			insertUserListToCache(entity.getUserLists());
			db.replace(Conf.DB_JOKE_TABLE, null, values);
			LogUtils.i("笑话ID:"+entity.getId()+" 笑话标签组:"+entity.getTags());
		}
	}
	//插入笑话标签列表
	public void insertTagListToCache(List<TagEntity> list) {

		int size = list.size();
		for (int i = 0; i < size; i++) {
			ContentValues values = new ContentValues();
			TagEntity entity=list.get(i);
			values.put("Id", entity.getId());
			values.put("Title", entity.getTitle());
			db.replace(Conf.DB_TAG_TABLE, null, values);
			LogUtils.i("标签ID:"+entity.getId()+" 标签标题:"+entity.getTitle());
		}
	}
	//插入打赏用户列表
	public void insertUserListToCache(List<UserEntity> list) {

		int size = list.size();
		for (int i = 0; i < size; i++) {
			ContentValues values = new ContentValues();
			UserEntity entity=list.get(i);
			values.put("Id", entity.getId());
			values.put("Nickname", entity.getNickname());
			values.put("Avatar", entity.getAvatar());
			db.replace(Conf.DB_USER_TABLE, null, values);
			LogUtils.i("用户ID:"+entity.getId()+" 用户昵称:"+entity.getNickname());
		}
	}
}
