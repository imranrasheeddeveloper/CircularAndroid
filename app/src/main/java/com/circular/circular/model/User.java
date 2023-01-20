package com.circular.circular.model;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("profile_pic")
	private String profilePic;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("email_verified_at")
	private Object emailVerifiedAt;

	@SerializedName("is_password_changed")
	private int isPasswordChanged;

	@SerializedName("user_type")
	private String userType;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("phone")
	private String phone;

	@SerializedName("role_id")
	private int roleId;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("is_api_user")
	private int isApiUser;

	public String getProfilePic(){
		return profilePic;
	}

	public String getLastName(){
		return lastName;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public Object getEmailVerifiedAt(){
		return emailVerifiedAt;
	}

	public int isIsPasswordChanged(){
		return isPasswordChanged;
	}

	public String getUserType(){
		return userType;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getPhone(){
		return phone;
	}

	public int getRoleId(){
		return roleId;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public int getIsApiUser(){
		return isApiUser;
	}
}