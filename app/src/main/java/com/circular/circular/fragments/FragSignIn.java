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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.circular.circular.CircularApplication;
import com.circular.circular.Constant;
import com.circular.circular.MainActivity;
import com.circular.circular.R;
import com.circular.circular.SignUpActivity;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.local.PreferenceRepository;
import com.circular.circular.local.TinyDbManager;
import com.circular.circular.utils.Utils;
import com.circular.circular.view_model.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;

public class FragSignIn extends Fragment {
    private View mRootView;
    private EditText mEdEmailUserName;
    private EditText mEdPwd;
    private LoginViewModel viewModel;
    PreferenceRepository preferenceRepository;


    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_signin, null);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        preferenceRepository = new PreferenceRepository();
        initControls();
        return mRootView;
    }

    private void initControls() {
        mEdEmailUserName = mRootView.findViewById(R.id.ed_frag_signin_email_username);
        mEdPwd = mRootView.findViewById(R.id.ed_frag_signin_password);

        mRootView.findViewById(R.id.tv_frag_signin_submit).setOnClickListener(view -> {
            if (mEdEmailUserName.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.enter_name_email), Toast.LENGTH_LONG).show();
                mEdEmailUserName.requestFocus();
                return;
            }
            if (mEdPwd.getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.enter_pwd), Toast.LENGTH_LONG).show();
                mEdPwd.requestFocus();
                return;
            }

            String email = mEdEmailUserName.getText().toString();
            String password = mEdPwd.getText().toString();
            String device_key = Constant.DEVICE_KEY;

            viewModel.login(email, password,device_key);
            viewModel._loginData.observe(getViewLifecycleOwner(), response -> {
                if (response != null) {
                    if (response.isLoading()) {
                        showLoading();
                    } else if (!response.getError().isEmpty()) {
                        hideLoading();
                        showSnackBar(response.getError());
                    } else if (response.getData().getData() != null) {
                        hideLoading();
                        if (response.getData().getData().getUser().getIsApiUser() == 1){
                            TinyDbManager.saveUserType("App User");
                        }else {
                            TinyDbManager.saveUserType("Web User");
                        }
                        preferenceRepository.setString("token", "Bearer " + response.getData().getData().getToken());
                        TinyDbManager.saveUserData(response.getData().getData().getUser());
                        showDialogue(response.getData().getData().getUser().isIsPasswordChanged(),password);

                    }
                }
            });

        });

        mRootView.findViewById(R.id.tv_frag_signin_create_account).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity) {
                ((SignUpActivity) getActivity()).initFragment(R.id.fl_sign_container, new FragSignUp(), true);
            }
        });
        mRootView.findViewById(R.id.tv_frag_signin_forgot_pwd).setOnClickListener(view -> {
            if (getActivity() instanceof SignUpActivity) {
                ((SignUpActivity) getActivity()).addFragment(R.id.fl_sign_container, new FragResetPwdEnterEmail(), true);
            }
        });

        initFonts();
    }

    private void showLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.sign_in_loading)).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.sign_in_loading)).setVisibility(View.GONE);
    }


    private void showDialogue(int isPasswordChanged, String password) {
        DialogConfirm dlg = new DialogConfirm(requireActivity(),
                R.layout.dlg_confirm_proceed,
                R.id.tv_dlg_confirm_proceed_yes,
                R.id.tv_dlg_confirm_proceed_no,
                R.id.tv_dlg_confirm_proceed_msg,
                getString(R.string.confirm_proceed_msg),
                new ConfirmDialogInterface() {
                    @Override
                    public void onClickedConfirm() {
                        if (isPasswordChanged == 1){
                            preferenceRepository.setString("temp_password",password);
                            if (getActivity() instanceof SignUpActivity){
                                ((SignUpActivity)getActivity()).addFragment(R.id.fl_sign_container,
                                        new FragResetPwdSetNewPwd(),
                                        true);
                            }
                        }else {
                            Intent intent = new Intent(requireActivity(), MainActivity.class);
                            startActivity(intent);
                            requireActivity().finish();
                            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                        }
                }

                    @Override
                    public void onClickedNo() {

                    }
                });
        dlg.show();
        Utils.setDialogWidth(dlg, 0.8f, requireActivity());
    }

    private void showSnackBar(String msg) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_signin_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText) mRootView.findViewById(R.id.ed_frag_signin_email_username)).setTypeface(CircularApplication.mTfMainRegular);
        ((EditText) mRootView.findViewById(R.id.ed_frag_signin_password)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_signin_submit)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_signin_forgot_pwd)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_signin_create_account)).setTypeface(CircularApplication.mTfMainRegular);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel._loginData.removeObservers(this);
        viewModel = null;
    }
}
