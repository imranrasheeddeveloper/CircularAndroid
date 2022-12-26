package com.circular.circular.model.data_points;

import com.google.gson.annotations.SerializedName;

public class DataPointsModel{

	@SerializedName("data")
	private Data data;

	@SerializedName("message")
	private String message;

	@SerializedName("errors")
	private Object errors;

	@SerializedName("status")
	private boolean status;

	public Data getData(){
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