package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.circular.circular.CircularApplication;
import com.circular.circular.R;

public class FragHistory extends Fragment {
    private View mRootView;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_history, null);
        initControls();
        return mRootView;
    }

    private void initData() {
    }

    private void initControls() {
        initFonts();
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_history_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_history_coming_soon)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
