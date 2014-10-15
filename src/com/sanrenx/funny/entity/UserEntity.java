package com.sanrenx.funny.entity;

public class UserEntity {
	private String id;
	private String imei;
	private String nickname;
	private String password;
	private String avatar;

	public UserEntity() {

	}

	public UserEntity(String _id,String _avatar) {
		id=_id;
		avatar = _avatar;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
