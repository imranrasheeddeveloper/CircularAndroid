package com.circular.circular.model;

import com.google.gson.annotations.SerializedName;

public class PasswordUpdateModel{

	@SerializedName("data")
	private PasswordUpdateData passwordUpdateData;

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private String errors;

	@SerializedName("status")
	private boolean status;

	public PasswordUpdateData getData(){
		return passwordUpdateData;
	}

	public String getMessage(){
		return message;
	}

	public String getErrors(){
		return errors;
	}

	public boolean isStatus(){
		return status;
	}
}