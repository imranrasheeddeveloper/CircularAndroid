package com.circular.circular.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.circular.circular.CircularApplication;
import com.circular.circular.R;
import com.circular.circular.model.NotificationItem;

import java.util.ArrayList;

public class NotificationItemAdapter extends BaseAdapter {

    private Context mCtx;
    private LayoutInflater mInflater;
    private ArrayList<NotificationItem> mArrData;
    public NotificationItemAdapter(Context ctx, ArrayList<NotificationItem> arrData){
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
        NotificationItem item = (NotificationItem)getItem(i);
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_title)).setText(item.mStrTitle);
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_name)).setText(item.mStrName);
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_time)).setText(item.mStrTime);
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_name)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)view.findViewById(R.id.tv_lvitem_notification_time)).setTypeface(CircularApplication.mTfMainRegular);
        ((ImageView)view.findViewById(R.id.iv_lvitem_notification_eye)).setImageResource(
                item.m_bStatus ? R.drawable.eye_green : R.drawable.eye_gray
        );
        return view;
    }
}
