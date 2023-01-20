package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.circular.circular.CircularApplication;
import com.circular.circular.Constant;
import com.circular.circular.MainActivity;
import com.circular.circular.R;
import com.circular.circular.adapters.NotificationItemAdapter;
import com.circular.circular.model.ContentData;
import com.circular.circular.model.NotificationItem;
import com.circular.circular.view_model.AppInformationViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class FragAbout extends Fragment {
    private View mRootView;
    private AppInformationViewModel viewModel;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_about, null);
        viewModel = new ViewModelProvider(this).get(AppInformationViewModel.class);
        getAboutContent();
        initControls();
        return mRootView;
    }

    private void getAboutContent() {
        viewModel.about();
        viewModel._about_content.observe(getViewLifecycleOwner(), response -> {
            if (response != null){
                if (response.isLoading()) {
                    showLoading();
                } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBar("Something went wrong!!");
                    }else {
                        Constant.getLoginError(CircularApplication.applicationContext,response.getError());
                    }
                } else if (response.getData() != null) {
                    hideLoading();
                    if (response.getData().getErrors() == null) {
                        if (response.getData().getData() != null) {
                            setData(response.getData().getData());
                        }
                    }else {
                        showSnackBar(response.getData().getErrors());
                    }
                }
            }
        });
    }

    private void setData(ContentData data) {
        try {
            ((TextView)mRootView.findViewById(R.id.tv_frag_about_main_content)).setText(data.getContent());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void initData() {
    }

    private void showLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.about_loading)).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.about_loading)).setVisibility(View.GONE);
    }


    private void showSnackBar(String msg) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    private void initControls() {
        initFonts();
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_about_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_about_main_content)).setTypeface(CircularApplication.mTfMainRegular);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel._about_content.removeObservers(this);
        viewModel = null;
    }
}
