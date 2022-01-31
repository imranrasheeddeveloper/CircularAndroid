package com.circular.circular.model;

public class NotificationItem {
    public String mStrTitle;
    public String mStrName;
    public String mStrTime;
    public String mStrContent;
    public int m_nDrawableId;
    public boolean m_bStatus;

    public NotificationItem(){
        mStrTitle = "";
        mStrName = "";
        mStrTime = "";
        mStrContent = "";
        m_nDrawableId = 0;
        m_bStatus = false;
    }

    public NotificationItem(String strTitle, String strName, String strTime, boolean bFlag){
        mStrTitle = strTitle;
        mStrName = strName;
        mStrTime = strTime;
        mStrContent = "";
        m_nDrawableId = 0;
        m_bStatus = bFlag;
    }

    public NotificationItem(String strTitle, String strName, String strTime, String strContent, int nDrawableId, boolean bFlag){
        mStrTitle = strTitle;
        mStrName = strName;
        mStrTime = strTime;
        mStrContent = strContent;
        m_nDrawableId = nDrawableId;
        m_bStatus = bFlag;
    }

}
