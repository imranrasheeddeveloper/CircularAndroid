package com.circular.circular.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ReportDataField {
    public String mStrName;
    public boolean m_bUploadFlag = false;

    public ReportDataField(String strName){
        mStrName = strName;
    }
    public ReportDataField(String strName, boolean bFlag){
        mStrName = strName;
        m_bUploadFlag = bFlag;
    }

    public ReportDataField(JSONObject jsonData){
        mStrName = jsonData.optString("rfn", "");
    }

    public JSONObject getAsJson(){
        JSONObject jsonData = new JSONObject();
        try {
            jsonData.put("rfn", mStrName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public boolean equals(ReportDataField data){
        if (mStrName == null || data == null || data.mStrName == null) return false;
        return mStrName.equals(data.mStrName);
    }
}
