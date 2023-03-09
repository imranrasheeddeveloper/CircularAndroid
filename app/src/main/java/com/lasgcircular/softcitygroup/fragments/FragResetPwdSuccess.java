package com.lasgcircular.softcitygroup.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.lasgcircular.softcitygroup.CircularApplication;
import com.lasgcircular.softcitygroup.R;
import com.lasgcircular.softcitygroup.SignUpActivity;

public class FragResetPwdSuccess extends Fragment {
    private View mRootView;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_reset_pwd_success, null);
        initControls();
        return mRootView;
    }

    private void initControls(){
        mRootView.findViewById(R.id.tv_frag_reset_pwd_success_login).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).initFragment(R.id.fl_sign_container, new FragSignIn(), true);
            }
        });

        initFonts();
    }

    private void initFonts(){
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_success_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_success_login)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
    }
}
