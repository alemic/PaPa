package com.sanrenx.funny.entity;

import java.util.List;

public class JokeEntity {
	private String id;// ID
	private String uid;// 用户ID
	private String avatar;// 用户头像
	private String nickname;// 用户昵称
	private String title;// 笑话标题
	private String content;// 笑话内容
	private String cover;// 封面
	private String path;// 附件路径
	private String tags;//标签
	private String users;//用户
	private int reward;// 打赏金币
	private int brokerage;//佣金
	private int type;// 类型
	private int choice;//精选
	private int status;//状态
	private List<TagEntity> tagLists;
	private List<UserEntity> userLists;

	public JokeEntity() {

	}

	public JokeEntity(String _avatar, String _nickname, String _content,
			String _path, int _reward, List<TagEntity> _tag,
			List<UserEntity> _user) {
		avatar = _avatar;
		nickname = _nickname;
		content = _content;
		path = _path;
		reward = _reward;
		tagLists = _tag;
		userLists = _user;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public int getBrokerage() {
		return brokerage;
	}

	public void setBrokerage(int brokerage) {
		this.brokerage = brokerage;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<TagEntity> getTagLists() {
		return tagLists;
	}

	public void setTagLists(List<TagEntity> tagLists) {
		this.tagLists = tagLists;
	}

	public List<UserEntity> getUserLists() {
		return userLists;
	}

	public void setUserLists(List<UserEntity> userLists) {
		this.userLists = userLists;
	}

	

}
