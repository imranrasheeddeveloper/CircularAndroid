package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.circular.circular.CircularApplication;
import com.circular.circular.Constant;
import com.circular.circular.MainActivity;
import com.circular.circular.R;
import com.circular.circular.adapters.NotificationItemAdapter;
import com.circular.circular.adapters.ReportDataMainItemAdapter;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.local.PreferenceRepository;
import com.circular.circular.local.TinyDB;
import com.circular.circular.local.TinyDbManager;
import com.circular.circular.model.NotificationItem;
import com.circular.circular.model.ReportDataField;
import com.circular.circular.model.data_points.AssignedPreferenceItem;
import com.circular.circular.model.data_points.DataPointsItem;
import com.circular.circular.utils.Utils;
import com.circular.circular.view_model.DataPointsViewModel;
import com.circular.circular.view_model.ReportDataViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FragReportDataMain extends Fragment {
    private View mRootView;
    private ListView mLvData;
   // private ArrayList<ReportDataField> mArrData;
    private ArrayList<DataPointsItem> mArrData;

    private ReportDataMainItemAdapter mAdapter;
    private DataPointsViewModel viewModel;
    private PreferenceRepository repository;
    private ReportDataViewModel reportDataViewModel;
    private List<AssignedPreferenceItem> assignedPreferenceItems;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_report_data_main, null);

        viewModel = new ViewModelProvider(this).get(DataPointsViewModel.class);
        reportDataViewModel = new ViewModelProvider(this).get(ReportDataViewModel.class);
        assignedPreferenceItems = new ArrayList<>();

        return mRootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        repository = new PreferenceRepository();
        getAssignedPreferences();
        initData();
        initControls();
    }

    private void getAssignedPreferences() {
        String token = repository.getString("token");
        viewModel.getPoints(token);
        viewModel._data_points.observe(getViewLifecycleOwner() ,  response -> {
            if (response != null){
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBar(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();

                    if (response.getData().getData().getAssignedPreference() != null){
                        assignedPreferenceItems.addAll(response.getData().getData().getAssignedPreference());
                    }

                }
            }

        });
    }
    private void showLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.report_loading)).setVisibility(View.VISIBLE);
    }

    private void showSnackBar(String msg) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show();
    }


    private void hideLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.report_loading)).setVisibility(View.GONE);
    }
    private void initData() {
//        mArrData = new ArrayList<>();
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(),
//                Context.MODE_PRIVATE);
//        String strJSON = sharedPreferences.getString(Constant.SH_KEY_SELECTED_REPORT_DATA_FIELDS, "[]");
//        JSONArray arrJson = null;
//        try {
//            arrJson = new JSONArray(strJSON);
//            for (int i = 0; i < arrJson.length(); i++){
//                mArrData.add(new ReportDataField(arrJson.getJSONObject(i)));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
       // mArrData.add(new ReportDataField("Upload Evidence of Green Project", true));

        mArrData = new ArrayList<>();
        try {
            if (TinyDbManager.getSelectedDatPoints().size() > 0){
                for (int i = 0; i < TinyDbManager.getSelectedDatPoints().size(); i++) {
                    mArrData.add(TinyDbManager.getSelectedDatPoints().get(i));
                }
            }
        } catch (NullPointerException |IllegalArgumentException |IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void initControls() {
        mLvData = mRootView.findViewById(R.id.lv_frag_report_data_main_data);
        mAdapter = new ReportDataMainItemAdapter(requireActivity(), mArrData);
        mLvData.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        ((BaseAdapter) mLvData.getAdapter()).notifyDataSetChanged();

        initFonts();
        mRootView.findViewById(R.id.tv_frag_report_data_main_submit).setOnClickListener(view->{

            String token = repository.getString("token");
            List<Integer> data_ids = new ArrayList<>();
            List<Integer> data_values = new ArrayList<>();

            for (int i = 0; i < mArrData.size(); i++) {
                data_ids.add(mArrData.get(i).getId());
                data_values.add(Integer.valueOf(mArrData.get(i).getName()));
            }

            JsonObject object = new JsonObject();
            Gson gson = new Gson();
            object.add("data_point_id",gson.toJsonTree(data_ids));
            object.add("value",gson.toJsonTree(data_values));


            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            reportDataViewModel.report_data(token,object);
                            reportDataViewModel._report.observe(getViewLifecycleOwner(), response -> {
                                if (response != null) {
                                    if (response.isLoading()) {
                                        showLoading();
                                    } else if (!response.getError().isEmpty()) {
                                        hideLoading();
                                        showSnackBar(response.getError());
                                    } else if (response.getData().isStatus()) {
                                        hideLoading();
                                        Intent intent = new Intent(requireContext(), MainActivity.class);
                                        startActivity(intent);
                                        requireActivity().finish();

                                    }
                                }
                            });
                        }

                        @Override
                        public void onClickedNo() {
                            Intent intent = new Intent(requireContext(), MainActivity.class);
                            startActivity(intent);
                            requireActivity().finish();
                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_report_data_main_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_report_data_main_submit)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
    }
}
