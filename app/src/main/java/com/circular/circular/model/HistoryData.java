package com.circular.circular.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HistoryData {

	@SerializedName("records")
	private List<RecordsItem> records;

	public List<RecordsItem> getRecords(){
		return records;
	}
}