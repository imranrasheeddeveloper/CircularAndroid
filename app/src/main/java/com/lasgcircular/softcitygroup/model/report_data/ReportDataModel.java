package com.lasgcircular.softcitygroup.model.report_data;

import com.google.gson.annotations.SerializedName;

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
