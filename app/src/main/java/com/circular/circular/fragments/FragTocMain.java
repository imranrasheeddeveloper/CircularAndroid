package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.circular.circular.CircularApplication;
import com.circular.circular.R;
import com.circular.circular.TocActivity;
import com.circular.circular.model.ContentData;
import com.circular.circular.view_model.AppInformationViewModel;
import com.google.android.material.snackbar.Snackbar;

public class FragTocMain extends Fragment {
    private View mRootView;
    private AppInformationViewModel viewModel;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_toc_main, null);
        viewModel = new ViewModelProvider(this).get(AppInformationViewModel.class);
        getTermsContent();
        initControls();
        return mRootView;
    }

    private void getTermsContent() {
        viewModel.about();
        viewModel._about_content.observe(getViewLifecycleOwner(), response -> {
            if (response != null){
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    if (response.getError().isEmpty() || response.getError() == null){
                        showSnackBar("Something went wrong!!");
                    }else {
                        showSnackBar(response.getError());
                    }
                } else if (response.getData().isStatus()) {
                    hideLoading();
                    if (response.getData().getData() != null) {
                       setData(response.getData().getData());
                    }
                }
            }
        });
    }

    private void showLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.terms_loading)).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.terms_loading)).setVisibility(View.GONE);
    }


    private void setData(ContentData data) {
        try {
            ((TextView)mRootView.findViewById(R.id.tv_frag_toc_main_content)).setText(data.getContent());
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    private void initControls() {
        initFonts();
        mRootView.findViewById(R.id.tv_frag_toc_main_gdpr_rights).setOnClickListener(view->{
            if (requireActivity() instanceof TocActivity){
                ((TocActivity)requireActivity()).addFragment(R.id.fl_toc_container, new FragGdpr(), true);
            }
        });
    }

    private void showSnackBar(String msg) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show();
    }


    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_toc_main_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_toc_main_content)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_toc_main_gdpr_rights)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel._about_content.removeObservers(this);
        viewModel = null;
    }
}
