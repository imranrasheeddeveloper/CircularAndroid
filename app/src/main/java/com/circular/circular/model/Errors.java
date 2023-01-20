package com.circular.circular.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Errors{

//	@SerializedName("email")
	private List<String> email;

	public List<String> getEmail(){
		return email;
	}
}