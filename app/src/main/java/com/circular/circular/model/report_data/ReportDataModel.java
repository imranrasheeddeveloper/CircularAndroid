package com.circular.circular.model.report_data;

import com.circular.circular.model.data_points.AssignedPreferenceItem;
import com.circular.circular.model.data_points.DataPointsItem;
import com.circular.circular.model.data_points.DataTypesItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportDataModel {

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("errors")
    private String errors;

    @SerializedName("data")
    private String data;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getErrors() {
        return errors;
    }

    public String getData() {
        return data;
    }
}
