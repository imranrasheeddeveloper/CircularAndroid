package com.lasgcircular.softcitygroup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lasgcircular.softcitygroup.CircularApplication;
import com.lasgcircular.softcitygroup.R;
import com.lasgcircular.softcitygroup.model.RecordsItem;
import com.lasgcircular.softcitygroup.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {

    private Context mCtx;
    private LayoutInflater mInflater;
    private List<RecordsItem> mArrData;
    public HistoryAdapter(Context ctx, List<RecordsItem> arrData){
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
            view = mInflater.inflate(R.layout.lvitem_notification, null);
        }
        try {

        RecordsItem item = (RecordsItem)getItem(i);
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_title))
                .setText("Lagos N25bn Bond to boost circular economy");
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_name)).setVisibility(View.GONE);
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_time))
                .setText("You submitted a data on " + Utils.formatDate(item.getCreatedAt()));
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_name)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_time)).setTypeface(CircularApplication.mTfMainRegular);
        ((ImageView)view.findViewById(R.id.iv_lvitem_notification_eye)).setVisibility(View.GONE);
//            ((ImageView)view.findViewById(R.id.iv_lvitem_notification_eye)).setImageResource(
//                    item.m_bStatus ? R.drawable.eye_green : R.drawable.eye_gray
//            );

        }catch (NullPointerException | IllegalStateException | NumberFormatException e){
            e.printStackTrace();
        }
        return view;
    }
}
