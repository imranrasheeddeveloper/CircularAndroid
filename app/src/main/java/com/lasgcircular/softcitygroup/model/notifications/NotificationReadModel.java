package com.lasgcircular.softcitygroup.model.notifications;

import com.google.gson.annotations.SerializedName;

public class NotificationReadModel{

	@SerializedName("data")
	private Object data;

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private Object errors;

	@SerializedName("status")
	private boolean status;

	public Object getData(){
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