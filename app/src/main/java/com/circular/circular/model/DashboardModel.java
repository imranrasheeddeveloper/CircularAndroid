package com.circular.circular.model;

import com.google.gson.annotations.SerializedName;

public class DashboardModel{

	@SerializedName("data")
	private DashboardData data;

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private Object errors;

	@SerializedName("status")
	private boolean status;

	public DashboardData getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public Object getErrors(){
		return errors;
	}

	public boolean isStatus(){
		return status;
	}
}