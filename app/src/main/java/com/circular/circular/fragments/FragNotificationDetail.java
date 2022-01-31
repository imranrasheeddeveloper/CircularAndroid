package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.circular.circular.CircularApplication;
import com.circular.circular.MainActivity;
import com.circular.circular.R;
import com.circular.circular.model.NotificationItem;

public class FragNotificationDetail extends Fragment {
    private View mRootView;
    private NotificationItem mData = new NotificationItem();

    public FragNotificationDetail(NotificationItem data){
        mData = data;
    }

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_notification_detail, null);
        initData();
        initControls();
        if (requireActivity() instanceof MainActivity){
            ((MainActivity)requireActivity()).showOrHideShowMenu(false);
        }
        return mRootView;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (requireActivity() instanceof MainActivity){
            ((MainActivity)requireActivity()).showOrHideShowMenu(true);
        }
    }

    private void initData() {

    }

    private void initControls() {
        initFonts();
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_title)).setText(mData.mStrTitle);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_name)).setText(mData.mStrName);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_time)).setText(mData.mStrTime);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_content)).setText(mData.mStrContent);
        ((ImageView)mRootView.findViewById(R.id.iv_frag_notification_detail_eye))
                .setImageResource(mData.m_bStatus ? R.drawable.eye_green : R.drawable.eye_gray);
        ((ImageView)mRootView.findViewById(R.id.iv_frag_notification_detail_image))
                .setImageResource(mData.m_nDrawableId);
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_page_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_name)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_time)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_content)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
