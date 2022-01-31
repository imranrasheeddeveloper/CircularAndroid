package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.Objects;

public class FragResetPwdEnterCode extends Fragment {
    private View mRootView;
    private EditText mEdCode1;
    private EditText mEdCode2;
    private EditText mEdCode3;
    private EditText mEdCode4;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_reset_pwd_enter_code, null);
        initControls();
        return mRootView;
    }

    private void initControls(){
        mEdCode1 = mRootView.findViewById(R.id.ed_frag_reset_pwd_enter_code_1);
        mEdCode2 = mRootView.findViewById(R.id.ed_frag_reset_pwd_enter_code_2);
        mEdCode3 = mRootView.findViewById(R.id.ed_frag_reset_pwd_enter_code_3);
        mEdCode4 = mRootView.findViewById(R.id.ed_frag_reset_pwd_enter_code_4);

        mEdCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mEdCode1.getText().toString().length() >= 1){
                    mEdCode2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEdCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mEdCode2.getText().toString().length() >= 1){
                    mEdCode3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEdCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mEdCode3.getText().toString().length() >= 1){
                    mEdCode4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_code_reset).setOnClickListener(view -> {
            if (mEdCode1.getText().toString().isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_valid_code, Toast.LENGTH_LONG).show();
                mEdCode1.requestFocus();
                return;
            }

            if (mEdCode2.getText().toString().isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_valid_code, Toast.LENGTH_LONG).show();
                mEdCode2.requestFocus();
                return;
            }

            if (mEdCode3.getText().toString().isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_valid_code, Toast.LENGTH_LONG).show();
                mEdCode3.requestFocus();
                return;
            }

            if (mEdCode4.getText().toString().isEmpty()){
                Toast.makeText(requireActivity(), R.string.enter_valid_code, Toast.LENGTH_LONG).show();
                mEdCode4.requestFocus();
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
                                ((SignUpActivity)getActivity()).addFragment(R.id.fl_sign_container,
                                        new FragResetPwdSetNewPwd(),
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

        mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_code_create_account).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).initFragment(R.id.fl_sign_container, new FragSignUp(), true);
            }
        });

        mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_code_login_account).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity){
                ((SignUpActivity)getActivity()).initFragment(R.id.fl_sign_container, new FragSignIn(), true);
            }
        });

        initFonts();
    }

    private void initFonts(){
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_code_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText)mRootView.findViewById(R.id.ed_frag_reset_pwd_enter_code_1)).setTypeface(CircularApplication.mTfMainRegular);
        ((EditText)mRootView.findViewById(R.id.ed_frag_reset_pwd_enter_code_2)).setTypeface(CircularApplication.mTfMainRegular);
        ((EditText)mRootView.findViewById(R.id.ed_frag_reset_pwd_enter_code_3)).setTypeface(CircularApplication.mTfMainRegular);
        ((EditText)mRootView.findViewById(R.id.ed_frag_reset_pwd_enter_code_4)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_code_reset)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_code_login_account)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_reset_pwd_enter_code_create_account)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
