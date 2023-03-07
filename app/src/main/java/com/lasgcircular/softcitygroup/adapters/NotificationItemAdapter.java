package com.lasgcircular.softcitygroup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lasgcircular.softcitygroup.CircularApplication;
import com.lasgcircular.softcitygroup.Constant;
import com.circular.circular.R;
import com.lasgcircular.softcitygroup.model.notifications.NotificationsItem;

import java.util.List;

public class NotificationItemAdapter extends RecyclerView.Adapter<NotificationItemAdapter.ViewHolder> {

    private Context mCtx;
    private LayoutInflater mInflater;
    private List<NotificationsItem> mArrData;
    OnItemClickListener listener;

    public void setOnNotificationClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public NotificationItemAdapter(List<NotificationsItem> data, Context context) {
        this.mArrData = data;
        this.mCtx = context;
    }

    @NonNull
    @Override
    public NotificationItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lvitem_notification, parent, false);
        return new NotificationItemAdapter.ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationItemAdapter.ViewHolder holder, int position) {
        NotificationsItem item = mArrData.get(position);
        holder.title.setText(item.getHeading());
        holder.name.setText(item.getText());
        holder.time.setText(Constant.constant.getDate(item.getCreatedAt()));
        holder.title.setTypeface(CircularApplication.mTfMainRegular);
        holder.name.setTypeface(CircularApplication.mTfMainRegular);
        holder.time.setTypeface(CircularApplication.mTfMainRegular);
        holder.eyeIcon.setImageResource(
                item.isIsRead() ? R.drawable.eye_green : R.drawable.eye_gray
        );
    }


    @Override
    public int getItemCount() {
        return mArrData.size();
    }

    public interface OnItemClickListener {
        void onRead(int position);
        void onDetail(int position);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name , title, time;
        ImageView eyeIcon;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_lvitem_notification_title);
            name = itemView.findViewById(R.id.tv_lvitem_notification_name);
            time = itemView.findViewById(R.id.tv_lvitem_notification_time);
            eyeIcon = itemView.findViewById(R.id.iv_lvitem_notification_eye);

            eyeIcon.setOnClickListener(v -> {
               if (listener != null){
                   if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                       listener.onRead(getAdapterPosition());
                   }
               }
            });

            itemView.setOnClickListener(v -> {
                if (listener != null){
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        listener.onDetail(getAdapterPosition());
                    }
                }
            });

        }
    }

}
