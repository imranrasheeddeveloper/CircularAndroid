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

public class FragResetPwdSetNewPwd extends Fragment {
    private View mRootView;
    private EditText mEdNewPwd;
    private EditText mEdConfirmPwd;


    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_reset_pwd_new_pwd, null);
        initControls();
        return mRootView;
    }

    private void initControls(){
        mEdNewPwd = mRootView.findViewById(R.id.ed_frag_reset_pwd_new_pwd_enter);
        mEdConfirmPwd = mRootView.findViewById(R.id.ed_frag_reset_pwd_new_pwd_confirm);

        mRootView.findViewById(R.id.tv_frag_reset_pwd_new_pwd_submit).setOnClickListener(view -> {
            if (mEdNewPwd.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_pwd), Toast.LENGTH_LONG).show();
                mEdNewPwd.requestFocus();
                return;
            }
            if (mEdConfirmPwd.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.confirm_pwd), Toast.LENGTH_LONG).show();
                mEdConfirmPwd.requestFocus();
                return;
            }
            if (!mEdNewPwd.getText().toString().equals(mEdConfirmPwd.getText().toString())){
                Toast.makeText(getActivity(), getString(R.string.pwd_mismatch), Toast.LENGTH_LONG).show();
                mEdNewPwd.requestFocus();
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
                                ((SignUpActivity)getActivity()).addFragment(R.id.fl_sign_container, new FragResetPwdSuccess(), true);
                            }
                        }
                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        mRootView.findViewById(R.id.tv_frag_reset_pwd_new_pwd_create_account).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).initFragment(R.id.fl_sign_container, new FragSignUp(), true);
            }
        });

        mRootView.findViewById(R.id.tv_frag_reset_pwd_new_pwd_login_account).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).initFragment(R.id.fl_sign_container, new FragSignIn(), true);
            }
        });

        initFonts();
    }

    private void initFonts(){
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_new_pwd_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText)mRootView.findViewById(R.id.ed_frag_reset_pwd_new_pwd_enter)).setTypeface(CircularApplication.mTfMainRegular);
        ((EditText)mRootView.findViewById(R.id.ed_frag_reset_pwd_new_pwd_confirm)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_new_pwd_submit)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_new_pwd_login_account)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_new_pwd_create_account)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
