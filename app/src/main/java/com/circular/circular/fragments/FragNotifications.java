package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.circular.circular.CircularApplication;
import com.circular.circular.MainActivity;
import com.circular.circular.R;
import com.circular.circular.adapters.NotificationItemAdapter;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.local.PreferenceRepository;
import com.circular.circular.model.NotificationItem;
import com.circular.circular.model.notifications.NotificationsItem;
import com.circular.circular.utils.Utils;
import com.circular.circular.view_model.NotificationsViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FragNotifications extends Fragment {
    private View mRootView;
    private RecyclerView mLvData;
    private ArrayList<NotificationItem> mArrData;
    List<NotificationsItem> openItemList;
    private NotificationItemAdapter mAdapter;
    private NotificationsViewModel notificationsViewModel;
    PreferenceRepository preferenceRepository;
    String token;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_notification, null);

        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);
        preferenceRepository = new PreferenceRepository();
        token = preferenceRepository.getString("token");
//        initData();
        initControls();
        getAllNotifications(token);
        return mRootView;
    }

    private void getAllNotifications(String token) {
        notificationsViewModel.getNotification(token);
        notificationsViewModel._notification.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBar(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    if (response.getData().getData().getNotifications().size() > 0) {
                        openItemList = new ArrayList<>();
                        openItemList.addAll(response.getData().getData().getNotifications());
                        setList(openItemList);
                    }
                }
            }
        });
    }

    private void setList(List<NotificationsItem> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        mLvData.setLayoutManager(layoutManager);
        mAdapter = new NotificationItemAdapter(list, requireContext());
        mAdapter.notifyDataSetChanged();
        mLvData.setAdapter(mAdapter);
        initFonts();

        mAdapter.setOnNotificationClickListener(new NotificationItemAdapter.OnItemClickListener() {
            @Override
            public void onRead(int position) {
                try {
                    notificationsViewModel.readNotification(token, list.get(position).getId());
                    notificationsViewModel._notification_status.observe(getViewLifecycleOwner(), response -> {
                        if (response != null) {
                            if (response.isLoading()) {
                                // showLoading();
                            } else if (!response.getError().isEmpty()) {
                                //hideLoading();
                                //showSnackBar(response.getError());
                            } else if (response.getData().isStatus()) {
                                // hideLoading();
                                getAllNotifications(token);
                            }
                        }
                    });
                }catch (NullPointerException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onDetail(int position) {
                if (requireActivity() instanceof MainActivity)
                    ((MainActivity) requireActivity()).addFragmentFromBottom(R.id.fl_main_container,
                            new FragNotificationDetail(list.get(position)), true);
            }
        });
//        mAdapter = new NotificationItemAdapter(requireActivity(), list);
//        mLvData.setAdapter(mAdapter);
//        ((BaseAdapter) mLvData.getAdapter()).notifyDataSetChanged();
//        mLvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (requireActivity() instanceof MainActivity)
//                    ((MainActivity)requireActivity()).addFragmentFromBottom(R.id.fl_main_container,
//                            new FragNotificationDetail(list.get(i)), true);
//            }
//        });

    }

    private void initData() {
        mArrData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NotificationItem item = new NotificationItem(
                    "Lagos N25bn Bond to boost circular economy",
                    "-Sanwu-Olu",
                    "4 hours ago",
                    getString(R.string.dummy_featured_project_content),
                    R.drawable.dummy_slide1,
                    Math.random() > 0.4f
            );
            mArrData.add(item);
        }
    }

    private void initControls() {
        mLvData = mRootView.findViewById(R.id.lv_frag_notification_data);

    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_notifications_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
    }

    private void showSnackBar(String msg) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    private void showLoading() {
        ((ConstraintLayout) mRootView.findViewById(R.id.notification_loading)).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        ((ConstraintLayout) mRootView.findViewById(R.id.notification_loading)).setVisibility(View.GONE);
    }
}
