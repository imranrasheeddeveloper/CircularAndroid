package com.circular.circular.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ChartRoot extends HorizontalScrollView {

    private ArrayList<BarChartWithLabel> mArrBarCharts;
    private LinearLayout mLlChild;
    private int m_nBarWidth = 50;
    public void setBarCharts(ArrayList<BarChartWithLabel> arr){
        if (mArrBarCharts == null) mArrBarCharts = new ArrayList<>();
        else mArrBarCharts.clear();
        if (arr == null || arr.size() == 0) return;
        mArrBarCharts.addAll(arr);
    }

    public void setBarWidth(int nWidth){
        m_nBarWidth = nWidth;
    }

    public ChartRoot(Context context) {
        super(context);
        init();
    }

    public ChartRoot(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartRoot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ChartRoot(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init(){
        removeAllViews();
        mLlChild = new LinearLayout(getContext());
        ViewGroup.LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mLlChild.setLayoutParams(lp);
        addView(mLlChild);
    }

    public void invalidateAllCharts(){
        if (mLlChild != null){
            mLlChild.removeAllViews();
        }else{
            init();
        }
        if (mArrBarCharts != null) {
            for (int i = 0; i < mArrBarCharts.size(); i++){
                BarChartWithLabel item = mArrBarCharts.get(i);
                item.setGravity(Gravity.BOTTOM);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(m_nBarWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
                item.init();
//                item.setLayoutParams(lp);
                mLlChild.addView(item);
            }
        }
        invalidate();
    }

    public void hideChartLabels(){
        if (mLlChild == null) return;
        for (int i = 0; i < mLlChild.getChildCount(); i++){
            View v = mLlChild.getChildAt(i);
            if (v instanceof ViewGroup){
                View vLabel = ((ViewGroup)v).findViewWithTag("ChartLabel");
                if (vLabel != null) vLabel.setVisibility(View.GONE);
            }
        }
    }

    public void showChartLabels(){
        if (mLlChild == null) return;
        for (int i = 0; i < mLlChild.getChildCount(); i++){
            View v = mLlChild.getChildAt(i);
            if (v instanceof ViewGroup){
                View vLabel = ((ViewGroup)v).findViewWithTag("ChartLabel");
                if (vLabel != null) vLabel.setVisibility(View.VISIBLE);
            }
        }
    }
}
