package com.circular.circular.model;

import com.google.gson.annotations.SerializedName;

public class Statistics{

	@SerializedName("stakeHolder_count")
	private int stakeHolderCount;

	@SerializedName("project_count")
	private int projectCount;

	@SerializedName("report_count")
	private int reportCount;

	@SerializedName("investor_count")
	private int investorCount;

	public int getStakeHolderCount(){
		return stakeHolderCount;
	}

	public int getProjectCount(){
		return projectCount;
	}

	public int getReportCount(){
		return reportCount;
	}

	public int getInvestorCount(){
		return investorCount;
	}
}