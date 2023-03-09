package com.lasgcircular.softcitygroup.fragments;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lasgcircular.softcitygroup.CircularApplication;
import com.lasgcircular.softcitygroup.MainActivity;
import com.lasgcircular.softcitygroup.R;
import com.lasgcircular.softcitygroup.model.RecordsItem;


public class FragHistoryDetail extends Fragment {

    private View mRootView;
    private RecordsItem mData = new RecordsItem();


    public FragHistoryDetail(RecordsItem recordsItem) {
        mData = recordsItem;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRootView =  inflater.inflate(R.layout.fragment_frag_history_detail, container, false);
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
        initFonts();
        ((TextView) mRootView.findViewById(R.id.tv_frag_history_detail_name)).setText(mData.getDataPointName());
        ((TextView) mRootView.findViewById(R.id.tv_frag_history_detail_time)).setText(String.valueOf(mData.getValue()));
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_history_detail_page_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_history_detail_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_history_detail_name)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_history_detail_time)).setTypeface(CircularApplication.mTfMainRegular);
    }
}