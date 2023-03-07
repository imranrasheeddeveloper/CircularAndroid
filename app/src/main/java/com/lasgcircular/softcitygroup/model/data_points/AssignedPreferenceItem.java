package com.lasgcircular.softcitygroup.model.data_points;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AssignedPreferenceItem{

	@SerializedName("data_points")
	private List<DataPointsItem> dataPoints;

	@SerializedName("data_preference")
	private DataPreference dataPreference;

	public List<DataPointsItem> getDataPoints(){
		return dataPoints;
	}

	public DataPreference getDataPreference(){
		return dataPreference;
	}
}