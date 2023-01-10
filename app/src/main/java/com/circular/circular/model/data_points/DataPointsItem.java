package com.circular.circular.model.data_points;

import com.google.gson.annotations.SerializedName;

public class DataPointsItem{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("deleted_at")
	private Object deletedAt;

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getName(){
		return name;
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

	public Object getDeletedAt(){
		return deletedAt;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}