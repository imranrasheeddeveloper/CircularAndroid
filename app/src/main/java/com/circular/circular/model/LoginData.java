package com.circular.circular.model;

import android.service.autofill.UserData;

import com.google.gson.annotations.SerializedName;

public class LoginData {

	@SerializedName("user")
	private User user;

	@SerializedName("token")
	private String token;

	public User getUser(){
		return user;
	}

	public String getToken(){
		return token;
	}
}