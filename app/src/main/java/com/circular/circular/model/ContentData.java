package com.circular.circular.model;

import com.google.gson.annotations.SerializedName;

public class ContentData {

	@SerializedName("content")
	private String content;

	public String getContent(){
		return content;
	}
}