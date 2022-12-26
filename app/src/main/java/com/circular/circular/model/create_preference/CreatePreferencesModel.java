package com.circular.circular.model.create_preference;

import com.google.gson.annotations.SerializedName;

public class CreatePreferencesModel{

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private String errors;

	@SerializedName("status")
	private boolean status;

	public Data getData(){
		return data;
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