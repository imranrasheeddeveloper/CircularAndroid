package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.circular.circular.CircularApplication;
import com.circular.circular.Constant;
import com.circular.circular.MainActivity;
import com.circular.circular.ProfileActivity;
import com.circular.circular.R;
import com.circular.circular.model.FeaturedProjectData;
import com.circular.circular.model.ProjectsItem;

public class FragFeaturedProject extends Fragment {
    private View mRootView;
    private ProjectsItem mData;

    public FragFeaturedProject(ProjectsItem data){
        mData = data;
    }
    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_featured_projects, null);
        initData();
        initControls();
        if (requireActivity() instanceof MainActivity){
            ((MainActivity)requireActivity()).showOrHideShowMenu(false);
        }
        return mRootView;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (requireActivity() instanceof MainActivity){
            ((MainActivity)requireActivity()).showOrHideShowMenu(true);
        }
    }

    private void initData() {
    }

    private void initControls() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_featured_project_title)).setText(mData == null || mData.getTitle() == null ? "" : mData.getTitle());
        ((TextView) mRootView.findViewById(R.id.tv_frag_featured_project_content)).setText(mData == null || mData.getDescription() == null ? "" : mData.getDescription());
      //  ((ImageView)mRootView.findViewById(R.id.iv_frag_featured_project_image)).setImageResource(mData == null ? 0 : mData.getImg());
        Glide.with(requireContext())
                .load(Constant.IMG_PATH + mData.getImg())
                .placeholder(R.color.white_alpha)
                .into((ImageView)mRootView.findViewById(R.id.iv_frag_featured_project_image));

        initFonts();
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_featured_project_page_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_featured_project_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_featured_project_content)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
