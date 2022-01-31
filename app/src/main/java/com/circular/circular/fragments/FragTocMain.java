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
import com.circular.circular.TocActivity;

public class FragTocMain extends Fragment {
    private View mRootView;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_toc_main, null);
        initControls();
        return mRootView;
    }

    private void initControls() {
        initFonts();
        mRootView.findViewById(R.id.tv_frag_toc_main_gdpr_rights).setOnClickListener(view->{
            if (requireActivity() instanceof TocActivity){
                ((TocActivity)requireActivity()).addFragment(R.id.fl_toc_container, new FragGdpr(), true);
            }
        });
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_toc_main_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_toc_main_content)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_toc_main_gdpr_rights)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
    }
}
