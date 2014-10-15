package com.sanrenx.funny.db;

import java.util.ArrayList;
import java.util.List;

import com.sanrenx.funny.entity.TagEntity;
import com.sanrenx.funny.utils.Conf;
import com.sanrenx.funny.utils.LogUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TagHelper {
	private DBHelper.DatabaseHelper dbHelper;
	private SQLiteDatabase db;

	public TagHelper(Context context) {
		LogUtils.initialize(context);
		dbHelper = new DBHelper.DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
		db.close();
	}
	

	public List<TagEntity> getTagListFromCache() {
		List<TagEntity> lists = new ArrayList<TagEntity>();
		Cursor cursor = db.query(Conf.DB_TAG_TABLE, null, null, null, null, null, null);
		LogUtils.i("数据条数:"+ cursor.getCount());
		while (cursor != null && cursor.moveToNext()) {
			TagEntity entity = new TagEntity();
			entity.setId(cursor.getString(0));
			entity.setTitle(cursor.getString(1));
			entity.setDisp(cursor.getString(2));
			entity.setPath(cursor.getString(3));
			entity.setSort(cursor.getString(4));
			entity.setHot(cursor.getInt(5));
			lists.add(entity);
		}
		cursor.close();
		return lists;

	}
	
	public void insertTagListToCache(List<TagEntity> list) {

		int size = list.size();
		for (int i = 0; i < size; i++) {
			ContentValues values = new ContentValues();
			TagEntity entity=list.get(i);
			values.put("Id", entity.getId());
			values.put("Title", entity.getTitle());
			values.put("Disp", entity.getDisp());
			values.put("Path", entity.getPath());
			values.put("Sort", entity.getSort());
			values.put("Hot", entity.getHot());
			db.replace(Conf.DB_TAG_TABLE, null, values);
			LogUtils.i("标签ID:"+entity.getId()+" 标签标题:"+entity.getTitle());
		}
	}
}
