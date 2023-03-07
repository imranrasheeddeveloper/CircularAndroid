package com.lasgcircular.softcitygroup.model.data_points;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("data_points")
	private List<DataPointsItem> dataPoints;

	@SerializedName("assigned_preference")
	private List<AssignedPreferenceItem> assignedPreference;

	@SerializedName("data_types")
	private List<DataTypesItem> dataTypes;

	public List<DataPointsItem> getDataPoints(){
		return dataPoints;
	}

	public List<AssignedPreferenceItem> getAssignedPreference(){
		return assignedPreference;
	}

	public List<DataTypesItem> getDataTypes(){
		return dataTypes;
	}
}