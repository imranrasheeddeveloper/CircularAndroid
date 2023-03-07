package com.lasgcircular.softcitygroup.model.data_points;

import com.google.gson.annotations.SerializedName;

public class DataTypesItem{

	@SerializedName("color")
	private String color;

	@SerializedName("updated_at")
	private Object updatedAt;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("created_at")
	private Object createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("deleted_at")
	private Object deletedAt;

	public String getColor(){
		return color;
	}

	public Object getUpdatedAt(){
		return updatedAt;
	}

	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	public Object getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public Object getDeletedAt(){
		return deletedAt;
	}
}