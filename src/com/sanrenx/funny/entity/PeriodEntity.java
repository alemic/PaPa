package com.sanrenx.funny.entity;

import java.util.List;

public class PeriodEntity {

	private String id;// ID
	private String title;// 标题
	private String cover;// 封面
	private String disp;// 摘要
	private String editor;// 编辑
	private List<ExcerptEntity> excerptList;// 片段

	public PeriodEntity() {

	}

	public PeriodEntity(String _id, String _title, String _disp, String _cover) {
		id=_id;
		title=_title;
		disp=_disp;
		cover=_cover;
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

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getDisp() {
		return disp;
	}

	public void setDisp(String disp) {
		this.disp = disp;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public List<ExcerptEntity> getExcerptList() {
		return excerptList;
	}

	public void setExcerptList(List<ExcerptEntity> excerptList) {
		this.excerptList = excerptList;
	}

}
