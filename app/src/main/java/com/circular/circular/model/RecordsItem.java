package com.circular.circular.model;

import com.google.gson.annotations.SerializedName;

public class RecordsItem{

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("data_point_name")
	private String dataPointName;

	@SerializedName("value")
	private double value;

	@SerializedName("data_point_id")
	private int dataPointId;

	public String getCreatedAt(){
		return createdAt;
	}

	public String getDataPointName(){
		return dataPointName;
	}

	public double getValue(){
		return value;
	}

	public int getDataPointId(){
		return dataPointId;
	}
}