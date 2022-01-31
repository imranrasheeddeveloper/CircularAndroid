package com.circular.circular.fragments;

import android.annotation.SuppressLint;
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
import com.circular.circular.R;
import com.circular.circular.SignUpActivity;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.utils.Utils;

public class FragResetPwdEnterEmail extends Fragment {
    private View mRootView;
    private EditText mEdEmail;


    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_reset_pwd_enter_email, null);
        initControls();
        return mRootView;
    }

    private void initControls(){
        mEdEmail = mRootView.findViewById(R.id.ed_frag_reset_pwd_enter_email);

        mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_email_submit).setOnClickListener(view -> {
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
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            if (getActivity() instanceof SignUpActivity){
                                ((SignUpActivity)getActivity()).addFragment(R.id.fl_sign_container, new FragResetPwdRequestSuccessful(),
                                        true);
                            }
                        }
                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_email_create_account).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).initFragment(R.id.fl_sign_container, new FragSignUp(), true);
            }
        });

        mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_email_login_account).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).initFragment(R.id.fl_sign_container, new FragSignIn(), true);
            }
        });

        initFonts();
    }

    private void initFonts(){
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_email_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText)mRootView.findViewById(R.id.ed_frag_reset_pwd_enter_email)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_email_submit)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_email_login_account)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_email_create_account)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
