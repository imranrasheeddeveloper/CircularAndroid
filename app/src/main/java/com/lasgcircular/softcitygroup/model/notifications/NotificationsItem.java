package com.lasgcircular.softcitygroup.model.notifications;

import com.google.gson.annotations.SerializedName;

public class NotificationsItem{

	@SerializedName("is_read")
	private boolean isRead;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("heading")
	private String heading;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("text")
	private String text;

	@SerializedName("deleted_at")
	private String deletedAt;

	public boolean isIsRead(){
		return isRead;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getHeading(){
		return heading;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getText(){
		return text;
	}

	public String getDeletedAt(){
		return deletedAt;
	}
}