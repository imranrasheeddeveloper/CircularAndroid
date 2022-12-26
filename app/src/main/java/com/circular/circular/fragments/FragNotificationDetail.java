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
import androidx.lifecycle.ViewModelProvider;

import com.circular.circular.CircularApplication;
import com.circular.circular.Constant;
import com.circular.circular.MainActivity;
import com.circular.circular.R;
import com.circular.circular.local.PreferenceRepository;
import com.circular.circular.model.NotificationItem;
import com.circular.circular.model.notifications.NotificationsItem;
import com.circular.circular.view_model.NotificationsViewModel;

public class FragNotificationDetail extends Fragment {
    private View mRootView;
    private NotificationsItem mData;
    NotificationsViewModel viewModel;
    PreferenceRepository preferenceRepository;

    public FragNotificationDetail(NotificationsItem data){
        mData = data;
    }

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_notification_detail, null);
        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        preferenceRepository = new PreferenceRepository();
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
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_title)).setText(mData.getHeading());
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_name)).setText(mData.getText());
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_time)).setText(Constant.constant.formatDate(mData.getCreatedAt()));
       // ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_content)).setText(mData.mStrContent);
        ((ImageView)mRootView.findViewById(R.id.iv_frag_notification_detail_eye))
                .setImageResource(mData.isIsRead() ? R.drawable.eye_green : R.drawable.eye_gray);
//        ((ImageView)mRootView.findViewById(R.id.iv_frag_notification_detail_image))
//                .setImageResource(mData.m_nDrawableId);
        ((ImageView)mRootView.findViewById(R.id.iv_frag_notification_detail_eye)).setOnClickListener(v -> {
            try {
                String token = preferenceRepository.getString("token");
                viewModel.readNotification(token, mData.getId());
                viewModel._notification_status.observe(getViewLifecycleOwner(), response -> {
                    if (response != null) {
                        if (response.isLoading()) {
                            // showLoading();
                        } else if (!response.getError().isEmpty()) {
                            //hideLoading();
                            //showSnackBar(response.getError());
                        } else if (response.getData().isStatus()) {
                            // hideLoading();
                            ((MainActivity) requireActivity()).onBackPressed();
                        }
                    }
                });
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        });

    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_page_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_name)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_time)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_notification_detail_content)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
