package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.circular.circular.CircularApplication;
import com.circular.circular.MainActivity;
import com.circular.circular.R;
import com.circular.circular.SignUpActivity;
import com.circular.circular.adapters.NotificationItemAdapter;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.model.FeaturedProjectData;
import com.circular.circular.model.NotificationItem;
import com.circular.circular.utils.Utils;

import java.util.ArrayList;

public class FragNotifications extends Fragment {
    private View mRootView;
    private ListView mLvData;
    private ArrayList<NotificationItem> mArrData;
    private NotificationItemAdapter mAdapter;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_notification, null);
        initData();
        initControls();
        return mRootView;
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
        mAdapter = new NotificationItemAdapter(requireActivity(), mArrData);
        mLvData.setAdapter(mAdapter);
        ((BaseAdapter) mLvData.getAdapter()).notifyDataSetChanged();
        initFonts();
        mLvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (requireActivity() instanceof MainActivity)
                ((MainActivity)requireActivity()).addFragmentFromBottom(R.id.fl_main_container,
                        new FragNotificationDetail(mArrData.get(i)), true);
            }
        });
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_notifications_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
    }
}
