package com.circular.circular.model;

public class FeaturedProjectData {
    public int m_nDrawableResId;
    public String mStrLabel;
    public String mStrContent;

    public FeaturedProjectData(){
        m_nDrawableResId = 0;
        mStrLabel = "";
        mStrContent = "";
    }

    public FeaturedProjectData(int nDrawableResId, String strLabel){
        m_nDrawableResId = nDrawableResId;
        mStrLabel = strLabel;
        mStrContent = "";
    }

    public FeaturedProjectData(int nDrawableResId, String strLabel, String strContent){
        m_nDrawableResId = nDrawableResId;
        mStrLabel = strLabel;
        mStrContent = strContent;
    }
}
