package com.lasgcircular.softcitygroup.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.lasgcircular.softcitygroup.CircularApplication;
import com.lasgcircular.softcitygroup.Constant;
import com.lasgcircular.softcitygroup.MainActivity;
import com.lasgcircular.softcitygroup.R;
import com.lasgcircular.softcitygroup.adapters.ReportDataMainItemAdapter;
import com.lasgcircular.softcitygroup.dialog.ConfirmDialogInterface;
import com.lasgcircular.softcitygroup.dialog.DialogConfirm;
import com.lasgcircular.softcitygroup.local.PreferenceRepository;
import com.lasgcircular.softcitygroup.local.TinyDbManager;
import com.lasgcircular.softcitygroup.model.data_points.AssignedPreferenceItem;
import com.lasgcircular.softcitygroup.model.data_points.DataPointsItem;
import com.lasgcircular.softcitygroup.utils.Utils;
import com.lasgcircular.softcitygroup.view_model.DataPointsViewModel;
import com.lasgcircular.softcitygroup.view_model.ReportDataViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        //initControls();
    }

    private void getAssignedPreferences() {
        String token = repository.getString("token");
        viewModel.getPoints(token);
        viewModel._data_points.observe(getViewLifecycleOwner() ,  response -> {
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
                            if (response.getData().getData().getAssignedPreference() != null) {
                                assignedPreferenceItems.addAll(response.getData().getData().getAssignedPreference());
                                initControls();
                            }
                        }
                    }else {
                        showSnackBar(response.getData().getErrors());
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
                mArrData.addAll(TinyDbManager.getSelectedDatPoints());
            }
        } catch (NullPointerException |IllegalArgumentException |IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void initControls() {
        mLvData = mRootView.findViewById(R.id.lv_frag_report_data_main_data);
        mAdapter = new ReportDataMainItemAdapter(requireActivity(), assignedPreferenceItems.get(0).getDataPoints());
        mLvData.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        ((BaseAdapter) mLvData.getAdapter()).notifyDataSetChanged();

        initFonts();
        mRootView.findViewById(R.id.tv_frag_report_data_main_submit).setOnClickListener(view->{

            String token = repository.getString("token");
            List<Integer> data_ids = new ArrayList<>();
            List<Integer> data_values = new ArrayList<>();

            try {

            for (int i = 0; i < assignedPreferenceItems.get(0).getDataPoints().size(); i++) {
                Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
                Matcher matcher = pattern.matcher(assignedPreferenceItems.get(0).getDataPoints().get(i).getDescription());
                boolean isStringContainsSpecialCharacter = matcher.find();
                if (isStringContainsSpecialCharacter){
                    showSnackBar(assignedPreferenceItems.get(0).getDataPoints().get(i).getName() + " is Invalid");
                    return;
                }else if (!TextUtils.isDigitsOnly(assignedPreferenceItems.get(0).getDataPoints().get(i).getDescription())){
                    showSnackBar(assignedPreferenceItems.get(0).getDataPoints().get(i).getName() + " is Invalid, Use Numbers.");
                    return;
                }else if (Integer.parseInt(assignedPreferenceItems.get(0).getDataPoints().get(i).getDescription()) == 0) {
                    showSnackBar(assignedPreferenceItems.get(0).getDataPoints().get(i).getName() + " is Required.");
                    return;
                }
            }

            }catch (NumberFormatException e){
                e.printStackTrace();
            }



            try {


            for (int i = 0; i < assignedPreferenceItems.get(0).getDataPoints().size(); i++) {
                data_ids.add(assignedPreferenceItems.get(0).getDataPoints().get(i).getId());
                data_values.add(Integer.valueOf(assignedPreferenceItems.get(0).getDataPoints().get(i).getDescription()));
            }

            }catch (NumberFormatException e){
                e.printStackTrace();
                return;
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
                                            if (response.getData().isStatus()) {
                                                Intent intent = new Intent(requireContext(), MainActivity.class);
                                                startActivity(intent);
                                                requireActivity().finish();
                                            }
                                        }else {
                                            showSnackBar(response.getData().getErrors());
                                        }

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
