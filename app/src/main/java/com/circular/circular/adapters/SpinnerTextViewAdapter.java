package com.circular.circular.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.circular.circular.CircularApplication;
import com.circular.circular.R;

import java.util.ArrayList;

public class SpinnerTextViewAdapter extends BaseAdapter {

    private Context mCtx;
    private LayoutInflater mInflater;
    private ArrayList<String> mArrData;
    public SpinnerTextViewAdapter(Context ctx, ArrayList<String> arrData){
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
            view = mInflater.inflate(R.layout.spitem_textview, null);
        }
        String item = (String)getItem(i);
        ((TextView)view.findViewById(R.id.tv_spitem_textview)).setText(item);
        ((TextView)view.findViewById(R.id.tv_spitem_textview)).setTypeface(CircularApplication.mTfMainRegular);
        return view;
    }
}
