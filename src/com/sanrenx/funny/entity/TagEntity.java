package com.sanrenx.funny.entity;

import android.util.Log;

public class TagEntity {
	private String id;// ID
	private String title;// 标题
	private String disp;
	private String path;// 图片路径
	private String sort;// 排序
	private int hot;// 热门

	public TagEntity() {

	}

	public TagEntity(String _title) {
		title = _title;
	}

	public TagEntity(String _id, String _title, String _path, String _sort,
			int _hot) {
		id = _id;
		title = _title;
		path = _path;
		sort = _sort;
		hot = _hot;

	}

	@Override
	public String toString() {
		String tagStr = "tagEntity--> id:" + id + " title:" + title + " path:"
				+ path + " sort:" + sort + " hot:" + hot;
		Log.e("tagStr-->", tagStr);
		return tagStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDisp() {
		return disp;
	}

	public void setDisp(String disp) {
		this.disp = disp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

}
