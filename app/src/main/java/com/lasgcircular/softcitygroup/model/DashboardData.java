package com.lasgcircular.softcitygroup.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DashboardData {

	@SerializedName("reports")
	private List<ReportsItem> reports;

	@SerializedName("projects")
	private List<ProjectsItem> projects;

	@SerializedName("statistics")
	private Statistics statistics;

	public List<ReportsItem> getReports(){
		return reports;
	}

	public List<ProjectsItem> getProjects(){
		return projects;
	}

	public Statistics getStatistics(){
		return statistics;
	}
}