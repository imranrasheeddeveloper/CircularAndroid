package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.circular.circular.TocActivity;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.local.PreferenceRepository;
import com.circular.circular.local.TinyDbManager;
import com.circular.circular.utils.Utils;
import com.circular.circular.view_model.LoginViewModel;
import com.circular.circular.view_model.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;

public class FragSignUp extends Fragment {
    private View mRootView;
    private EditText mEdFirstName;
    private EditText mEdLastName;
    private EditText mEdEmail;
    private EditText mEdPhone;
    private EditText mEdPassword,mEdConfirmPassword;
    private RegisterViewModel viewModel;
    private ImageView showPass, showConfirmPass;
    private LoginViewModel loginViewModel;

    PreferenceRepository preferenceRepository;


    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_signup, null);
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        preferenceRepository = new PreferenceRepository();

        initControls();
        return mRootView;
    }

    private void initControls(){
        mEdFirstName    = mRootView.findViewById(R.id.ed_frag_signup_firstname);
        mEdLastName     = mRootView.findViewById(R.id.ed_frag_signup_lastname);
        mEdEmail        = mRootView.findViewById(R.id.ed_frag_signup_email);
        mEdPhone        = mRootView.findViewById(R.id.ed_frag_signup_phone);
        mEdPassword     = mRootView.findViewById(R.id.ed_frag_signup_password);
        mEdConfirmPassword     = mRootView.findViewById(R.id.ed_frag_signup_confirm_password);
        showPass     = mRootView.findViewById(R.id.show_signup_password);
        showConfirmPass     = mRootView.findViewById(R.id.show_signup_confirm_password);

        mEdPassword.setTransformationMethod(new PasswordTransformationMethod());
        mEdConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());

        showPass.setOnClickListener(v -> {
            if (showPass.getTag().equals("hide")) {
                mEdPassword.setTransformationMethod(null);
                showPass.setImageResource(R.drawable.show_password);
                showPass.setTag("show");
            } else {
                mEdPassword.setTransformationMethod(new PasswordTransformationMethod());
                showPass.setImageResource(R.drawable.hide_password);
                showPass.setTag("hide");
            }
        });

        showConfirmPass.setOnClickListener(v -> {
            if (showConfirmPass.getTag().equals("hide")) {
                mEdConfirmPassword.setTransformationMethod(null);
                showConfirmPass.setImageResource(R.drawable.show_password);
                showConfirmPass.setTag("show");
            } else {
                mEdConfirmPassword.setTransformationMethod(new PasswordTransformationMethod());
                showConfirmPass.setImageResource(R.drawable.hide_password);
                showConfirmPass.setTag("hide");
            }
        });

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

            if (mEdPassword.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_password), Toast.LENGTH_LONG).show();
                mEdPassword.requestFocus();
                return;
            }

            if (mEdConfirmPassword.getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_confirm_password), Toast.LENGTH_LONG).show();
                mEdConfirmPassword.requestFocus();
                return;
            }

            if (!mEdConfirmPassword.getText().toString().equalsIgnoreCase(mEdPassword.getText().toString())){
                Toast.makeText(getActivity(), getString(R.string.incorrect_password), Toast.LENGTH_LONG).show();
                mEdConfirmPassword.requestFocus();
                return;
            }

            String first_name = mEdFirstName.getText().toString();
            String last_name = mEdLastName.getText().toString();
            String email = mEdEmail.getText().toString();
            String number = mEdPhone.getText().toString();
            String password = mEdPassword.getText().toString();
            String device_key = Constant.DEVICE_KEY;

            viewModel.register(first_name,last_name,email,number, password,device_key);
            viewModel._register.observe(getViewLifecycleOwner(), response -> {
                if (response != null) {
                    if (response.isLoading()) {
                        showLoading();
                    } else if (!response.getError().isEmpty()) {
                         hideLoading();
                        if (response.getError().isEmpty() || response.getError() == null){
                            showSnackBar("Something went wrong!!");
                        }else {
                            showSnackBar(response.getError());
                        }
                    } else if (response.getData().getData() != null) {
                        loginUser(email, password,Constant.DEVICE_KEY);
                    }
                }
            });

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

    private void loginUser(String email, String password, String deviceKey) {
        loginViewModel.login(email, password,deviceKey);
        loginViewModel._loginData.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    if (response.getError().isEmpty() || response.getError() == null){
                        showSnackBar("Something went wrong!!");
                    }else {
                        showSnackBar(response.getError());
                    }
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    preferenceRepository.setString("token", "Bearer " + response.getData().getData().getToken());
                    TinyDbManager.saveUserData(response.getData().getData().getUser());
                    showDialogue();
                }
            }
        });
    }

    private void showLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.loading)).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.loading)).setVisibility(View.GONE);
    }

    private void showDialogue() {
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
                        requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel._register.removeObservers(this);
        viewModel = null;
    }
}
