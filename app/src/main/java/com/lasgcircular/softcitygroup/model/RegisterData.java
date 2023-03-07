package com.lasgcircular.softcitygroup.model;

import com.google.gson.annotations.SerializedName;

public class RegisterData {

	@SerializedName("user")
	private RegUser user;

	@SerializedName("token")
	private String token;

	public RegUser getUser(){
		return user;
	}

	public String getToken(){
		return token;
	}
}