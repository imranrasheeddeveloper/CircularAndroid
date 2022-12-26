package com.circular.circular.model.notifications;

import com.google.gson.annotations.SerializedName;

public class NotificationModel{

	@SerializedName("data")
	private NotificationData data;

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private Object errors;

	@SerializedName("status")
	private boolean status;

	public NotificationData getData(){
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