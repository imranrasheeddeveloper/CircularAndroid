package com.circular.circular.model.create_preference;

import com.google.gson.annotations.SerializedName;

public class Data {

	@SerializedName("data_preference")
	private DataPreference dataPreference;

	public DataPreference getDataPreference(){
		return dataPreference;
	}
}