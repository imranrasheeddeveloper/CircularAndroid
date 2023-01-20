package com.circular.circular.model;

import com.google.gson.annotations.SerializedName;

public class GetAppContentModel{

	@SerializedName("data")
	private ContentData data;

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private String errors;

	@SerializedName("status")
	private boolean status;

	public ContentData getData(){
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