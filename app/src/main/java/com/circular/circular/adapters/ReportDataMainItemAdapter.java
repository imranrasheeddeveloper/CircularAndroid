package com.circular.circular.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.circular.circular.CircularApplication;
import com.circular.circular.R;
import com.circular.circular.model.ReportDataField;

import java.util.ArrayList;

public class ReportDataMainItemAdapter extends BaseAdapter {

    private Context mCtx;
    private LayoutInflater mInflater;
    private ArrayList<ReportDataField> mArrData;
    public ReportDataMainItemAdapter(Context ctx, ArrayList<ReportDataField> arrData){
        mCtx = ctx;
        mArrData = new ArrayList<>();
        if (arrData != null && arrData.size() > 0){
            mArrData.addAll(arrData);
        }
        mInflater = LayoutInflater.from(mCtx);
    }

    @Override
    public int getCount() {
        return mArrData.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = mInflater.inflate(R.layout.lvitem_report_data_main, null);
        }
        ReportDataField item = (ReportDataField)getItem(i);
        ((EditText)view.findViewById(R.id.ed_lvitem_report_data_main_title)).setText(item.mStrName);
        ((EditText)view.findViewById(R.id.ed_lvitem_report_data_main_title)).setTypeface(CircularApplication.mTfMainRegular);
        if (item.m_bUploadFlag){
            ((EditText)view.findViewById(R.id.ed_lvitem_report_data_main_title)).setEnabled(false);
        }
        return view;
    }
}
