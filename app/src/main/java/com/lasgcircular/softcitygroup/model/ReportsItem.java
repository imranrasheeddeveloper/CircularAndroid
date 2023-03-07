package com.lasgcircular.softcitygroup.model;

import com.google.gson.annotations.SerializedName;

public class ReportsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("data_count")
	private int dataCount;

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public int getDataCount(){
		return dataCount;
	}
}