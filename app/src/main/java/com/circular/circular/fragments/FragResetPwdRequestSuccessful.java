package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.circular.circular.CircularApplication;
import com.circular.circular.R;
import com.circular.circular.SignUpActivity;

public class FragResetPwdRequestSuccessful extends Fragment {
    private View mRootView;


    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_reset_pwd_request_successful, null);
        initControls();
        return mRootView;
    }

    private void initControls(){
        mRootView.findViewById(R.id.tv_frag_reset_pwd_request_successful_continue).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).addFragment(R.id.fl_sign_container, new FragResetPwdEnterCode(), true);
            }
        });

        mRootView.findViewById(R.id.tv_frag_reset_pwd_request_successful_create_account).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).initFragment(R.id.fl_sign_container, new FragSignUp(), true);
            }
        });

        mRootView.findViewById(R.id.tv_frag_reset_pwd_request_successful_login_account).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).initFragment(R.id.fl_sign_container, new FragSignIn(), true);
            }
        });
        initFonts();
    }

    private void initFonts(){
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_request_successful_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_request_successful_check_email)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_request_successful_continue)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_request_successful_login_account)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_request_successful_create_account)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
