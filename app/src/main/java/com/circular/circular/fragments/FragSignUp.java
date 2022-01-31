package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.circular.circular.CircularApplication;
import com.circular.circular.MainActivity;
import com.circular.circular.R;
import com.circular.circular.SignUpActivity;
import com.circular.circular.TocActivity;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.utils.Utils;

public class FragSignUp extends Fragment {
    private View mRootView;
    private EditText mEdFirstName;
    private EditText mEdLastName;
    private EditText mEdEmail;
    private EditText mEdPhone;


    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_signup, null);
        initControls();
        return mRootView;
    }

    private void initControls(){
        mEdFirstName    = mRootView.findViewById(R.id.ed_frag_signup_firstname);
        mEdLastName     = mRootView.findViewById(R.id.ed_frag_signup_lastname);
        mEdEmail        = mRootView.findViewById(R.id.ed_frag_signup_email);
        mEdPhone        = mRootView.findViewById(R.id.ed_frag_signup_phone);

        mRootView.findViewById(R.id.tv_frag_signup_submit).setOnClickListener(view -> {
            if (mEdFirstName.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_first_name), Toast.LENGTH_LONG).show();
                mEdFirstName.requestFocus();
                return;
            }
            if (mEdLastName.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_last_name), Toast.LENGTH_LONG).show();
                mEdLastName.requestFocus();
                return;
            }
            if (mEdEmail.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_email), Toast.LENGTH_LONG).show();
                mEdEmail.requestFocus();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEdEmail.getText().toString()).matches()){
                Toast.makeText(getActivity(), getString(R.string.enter_valid_email), Toast.LENGTH_LONG).show();
                mEdEmail.requestFocus();
                return;
            }
            if (mEdPhone.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_phone), Toast.LENGTH_LONG).show();
                mEdPhone.requestFocus();
                return;
            }
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                            startActivity(intent);
                            requireActivity().finish();
                        }

                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        mRootView.findViewById(R.id.tv_frag_signup_login).setOnClickListener(view ->
        {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).addFragment(R.id.fl_sign_container, new FragSignIn(), true);
            }
        });

        mRootView.findViewById(R.id.tv_frag_signup_forgot_pwd).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).addFragment(R.id.fl_sign_container, new FragResetPwdEnterEmail(), true);
            }
        });

        mRootView.findViewById(R.id.tv_frag_signup_toc).setOnClickListener(view->{
            Intent intent = new Intent(requireActivity(), TocActivity.class);
            requireActivity().startActivity(intent);
        });
        initFonts();
    }

    private void initFonts(){
        ((TextView)mRootView.findViewById(R.id.tv_frag_signup_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText)mRootView.findViewById(R.id.ed_frag_signup_firstname)).setTypeface(CircularApplication.mTfMainRegular);
        ((EditText)mRootView.findViewById(R.id.ed_frag_signup_lastname)).setTypeface(CircularApplication.mTfMainRegular);
        ((EditText)mRootView.findViewById(R.id.ed_frag_signup_email)).setTypeface(CircularApplication.mTfMainRegular);
        ((EditText)mRootView.findViewById(R.id.ed_frag_signup_phone)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_signup_toc)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_signup_submit)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_signup_forgot_pwd)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_signup_login)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
