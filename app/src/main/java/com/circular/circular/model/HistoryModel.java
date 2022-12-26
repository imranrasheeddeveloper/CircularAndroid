package com.circular.circular.model;

import com.google.gson.annotations.SerializedName;

public class HistoryModel{

	@SerializedName("data")
	private HistoryData data;

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private String errors;

	@SerializedName("status")
	private boolean status;

	public HistoryData getData(){
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