package com.lasgcircular.softcitygroup.model;

import com.google.gson.annotations.SerializedName;

public class PasswordUpdateData {

	@SerializedName("user")
	private UserDetail user;

	public UserDetail getUser(){
		return user;
	}
}