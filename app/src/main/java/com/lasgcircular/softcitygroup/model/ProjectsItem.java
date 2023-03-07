package com.lasgcircular.softcitygroup.model;

import com.google.gson.annotations.SerializedName;

public class ProjectsItem{

	@SerializedName("img")
	private String img;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("deleted_at")
	private Object deletedAt;

	public String getImg(){
		return img;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getUserId(){
		return userId;
	}

	public String getDescription(){
		return description;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}
}