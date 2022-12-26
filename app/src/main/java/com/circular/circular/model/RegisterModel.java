package com.circular.circular.model;

import com.google.gson.annotations.SerializedName;

public class RegisterModel{

	@SerializedName("data")
	private RegisterData registerData;

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private String errors;

	@SerializedName("status")
	private boolean status;

	public RegisterData getData(){
		return registerData;
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