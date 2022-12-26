package com.circular.circular.adapters;

import android.content.Context;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.circular.circular.CircularApplication;
import com.circular.circular.R;
import com.circular.circular.model.ReportDataField;
import com.circular.circular.model.data_points.DataPointsItem;
import com.circular.circular.utils.CustomWatcher;

import java.util.ArrayList;

public class ReportDataMainItemAdapter extends BaseAdapter {

    private Context mCtx;
    private LayoutInflater mInflater;
    private ArrayList<DataPointsItem> mArrData;
    public ReportDataMainItemAdapter(Context ctx, ArrayList<DataPointsItem> arrData){
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
        DataPointsItem item = (DataPointsItem)getItem(i);
        ((EditText)view.findViewById(R.id.ed_lvitem_report_data_main_title)).setHint(item.getName());
        ((EditText)view.findViewById(R.id.ed_lvitem_report_data_main_title)).setTypeface(CircularApplication.mTfMainRegular);

        View finalView = view;
        ((EditText)view.findViewById(R.id.ed_lvitem_report_data_main_title)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().isEmpty()){
                    try
                    {
                        int w = Integer.parseInt(s.toString());
                        String data = ((EditText) finalView.findViewById(R.id.ed_lvitem_report_data_main_title)).getText().toString();
                        mArrData.get(i).setName(data);

                    }catch(NumberFormatException e) {
                        ((EditText) finalView.findViewById(R.id.ed_lvitem_report_data_main_title)).setText("");
                        e.printStackTrace();
                    }

                }
            }
        });


//        if (item.m_bUploadFlag){
//            ((EditText)view.findViewById(R.id.ed_lvitem_report_data_main_title)).setEnabled(false);
//        }
        return view;
    }
}