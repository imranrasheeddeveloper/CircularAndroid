package com.lasgcircular.softcitygroup.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.lasgcircular.softcitygroup.CircularApplication;
import com.lasgcircular.softcitygroup.Constant;
import com.lasgcircular.softcitygroup.MainActivity;
import com.lasgcircular.softcitygroup.R;
import com.lasgcircular.softcitygroup.adapters.HistoryAdapter;
import com.lasgcircular.softcitygroup.local.PreferenceRepository;
import com.lasgcircular.softcitygroup.model.HistoryData;
import com.lasgcircular.softcitygroup.view_model.HistoryViewModel;
import com.google.android.material.snackbar.Snackbar;

public class FragHistory extends Fragment {
    private View mRootView;
    private HistoryViewModel viewModel;
    PreferenceRepository preferenceRepository;
    private ListView mLvData;
    private HistoryAdapter mAdapter;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_history, null);
        viewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        preferenceRepository = new PreferenceRepository();
        initData();
        getHistory();
        initControls();
        return mRootView;
    }

    private void getHistory() {
        String token = preferenceRepository.getString("token");
        viewModel.getHistoryData(token);
        viewModel._history.observe(getViewLifecycleOwner() , response ->{
            if (response != null){
                if (response.isLoading()) {
                    showLoading();
                } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBar("Something went wrong!!");
                    }else {
                        Constant.getLoginError(CircularApplication.applicationContext,response.getError());
                    }
                } else if (response.getData() != null) {
                    hideLoading();
                    if (response.getData().getErrors() == null) {
                        if (response.getData().getData() != null) {
                            setData(response.getData().getData());
                        }
                    }else {
                        showSnackBar(response.getData().getErrors());
                    }
                }
            }
        });
    }

    private void setData(HistoryData data) {
        mAdapter = new HistoryAdapter(requireActivity(), data.getRecords());
        mLvData.setAdapter(mAdapter);
        ((BaseAdapter) mLvData.getAdapter()).notifyDataSetChanged();
        initFonts();
        mLvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (requireActivity() instanceof MainActivity)
                    ((MainActivity)requireActivity()).addFragmentFromBottom(R.id.fl_main_container,
                            new FragHistoryDetail(data.getRecords().get(i)), true);
            }
        });
    }

    private void showSnackBar(String msg) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    private void initData() {
        mLvData = mRootView.findViewById(R.id.rv_frag_history_data);
    }

    private void initControls() {
        initFonts();
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_history_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
    }
    private void showLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.history_loading)).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        ((ConstraintLayout) mRootView.findViewById(R.id.history_loading)).setVisibility(View.GONE);
    }

        @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel._history.removeObservers(this);
        viewModel = null;
    }
}
