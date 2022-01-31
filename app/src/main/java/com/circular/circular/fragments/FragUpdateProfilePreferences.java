package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.circular.circular.CircularApplication;
import com.circular.circular.Constant;
import com.circular.circular.R;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.dialog.DialogMessageWithNoButtons;
import com.circular.circular.model.ReportDataField;
import com.circular.circular.utils.Utils;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.MPPointF;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class FragUpdateProfilePreferences extends Fragment {
    private View mRootView;
    private PieChart mChart;
    private RelativeLayout mRlSubContentRoot;
    private static final int PREFERENCES_COUNT = 5;
    private final String[] mArrIndustries = new String[]{
            "Information Technology",
            "Finance",
            "Heavy Industry",
            "Agriculture"
    };

    private final String[] mArrTeamSize = new String[]{
            "5~9 Team Members",
            "10~50 Team Members",
            "50~100 Team Members",
            "100~250 Team Members",
            "250~500 Team Members",
            "500~1,000 Team Members"
    };

    private final String[] mArrBudget = new String[]{
            "N10,000,000 Per Anum",
            "N100,000,000 Per Anum",
            "N1,000,000 Per Month"
    };

    private final String[] mArrImpacts = new String[]{
            "1 Impact",
            "5 Impacts",
            "10 Impacts"
    };

    private final String[] mArrReportFields = new String[]{
            "Measured Co2 Emission",
            "Estimated Co2 Emission",
            "Financial Worth of Co2 Emitted",
            "Co2 Emission Reduced",
            "Real Income Per Co2 Emitted",
            "Total Organic Waste",
            "Measured Total Primary Energy Supply",
            "Total Recycled Waste",
            "Estimated Total Primary Energy Supply",
            "Agric Output Increase",
            "Daily Organic Raw Materials Weigh",
            "Total In-Organic Waste",
            "Daily Organic Raw Materials Financial Value",
            "Agric Output Decrease"
    };

    private ArrayList<ReportDataField> mArrReportDataField = new ArrayList<>();
    private int m_nActionCode = -1;
    private HashMap<Integer, Integer> mMapInitialActions = new HashMap<>();
    private ArrayList<Boolean> mArrUpdateStatus = new ArrayList<>();

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_update_profile_preferences, null);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(Constant.UPDATE_PROFILE_STARTUP_ACTION)) {
            m_nActionCode = bundle.getInt(Constant.UPDATE_PROFILE_STARTUP_ACTION);
        }
        initData();
        initControls();
        if (mMapInitialActions.containsKey(m_nActionCode) && mMapInitialActions.get(m_nActionCode) != null) {
            mRootView.findViewById(mMapInitialActions.get(m_nActionCode)).performClick();
        }
        return mRootView;
    }

    private void initData() {
        mMapInitialActions.put(Constant.UPDATE_PROFILE_STARTUP_ACTION_REPORT_DATA, R.id.ll_frag_update_profile_preference_select_data_report_fields_root);
        for (int i = 0; i < PREFERENCES_COUNT; i++){
            mArrUpdateStatus.add(false);
        }
        initSelectedReportDataFields();
    }

    private void initSelectedReportDataFields() {
        mArrReportDataField = new ArrayList<>();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(),
                Context.MODE_PRIVATE);
        String strJSON = sharedPreferences.getString(Constant.SH_KEY_SELECTED_REPORT_DATA_FIELDS, "[]");
        JSONArray arrJson = null;
        try {
            arrJson = new JSONArray(strJSON);
            for (int i = 0; i < arrJson.length(); i++) {
                mArrReportDataField.add(new ReportDataField(arrJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mArrReportDataField.size() == 0){
            mArrUpdateStatus.set(4, false);
        }else{
            mArrUpdateStatus.set(4, true);
        }
    }

    private void initControls() {
        mRlSubContentRoot = (RelativeLayout) mRootView.findViewById(R.id.rl_frag_update_profile_preferences_sub_content_root);
        mRlSubContentRoot.setVisibility(View.GONE);
        mRlSubContentRoot.removeAllViews();
        initFonts();
        retrieveSavedData();
        initPieChart();
        initEvents();
    }

    private void retrieveSavedData(){
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);
        String strSavedData = sharedPreferences.getString(Constant.SH_KEY_SELECTED_INDUSTRY, getString(R.string.select_your_industry));
        mArrUpdateStatus.set(0, !strSavedData.equals(getString(R.string.select_your_industry)));
        ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_preference_industry)).setText(strSavedData);

        strSavedData = sharedPreferences.getString(Constant.SH_KEY_SELECTED_TEAM_SIZE, getString(R.string.team_size));
        mArrUpdateStatus.set(1, !strSavedData.equals(getString(R.string.team_size)));
        ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_preference_team_size)).setText(strSavedData);

        strSavedData = sharedPreferences.getString(Constant.SH_KEY_SELECTED_BUDGET, getString(R.string.green_project_budget));
        mArrUpdateStatus.set(2, !strSavedData.equals(getString(R.string.green_project_budget)));
        ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_preference_green_project_budget)).setText(strSavedData);

        strSavedData = sharedPreferences.getString(Constant.SH_KEY_SELECTED_IMPACTS, getString(R.string.impacts_of_project));
        mArrUpdateStatus.set(3, !strSavedData.equals(getString(R.string.impacts_of_project)));
        ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_preference_impacts_of_project)).setText(strSavedData);
    }

    private void initEvents() {

        mRootView.findViewById(R.id.tv_frag_update_profile_preference_complete_setup).setOnClickListener(view -> {
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            requireActivity().onBackPressed();
                        }

                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        mRootView.findViewById(R.id.tv_frag_update_profile_preference_industry_hint).setOnClickListener(view -> {
            DialogMessageWithNoButtons dlg = new DialogMessageWithNoButtons(requireActivity(),
                    R.layout.dlg_message_withno_buttons,
                    R.id.tv_dlg_message_withno_buttons_msg,
                    "This is a text providing explanation about this feature is about; this will guide users on what it expected from them.");
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        mRootView.findViewById(R.id.tv_frag_update_profile_preference_team_size_hint).setOnClickListener(view -> {
            DialogMessageWithNoButtons dlg = new DialogMessageWithNoButtons(requireActivity(),
                    R.layout.dlg_message_withno_buttons,
                    R.id.tv_dlg_message_withno_buttons_msg,
                    "This is a text providing explanation about this feature is about; this will guide users on what it expected from them.");
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        mRootView.findViewById(R.id.tv_frag_update_profile_preference_green_project_budget_hint).setOnClickListener(view -> {
            DialogMessageWithNoButtons dlg = new DialogMessageWithNoButtons(requireActivity(),
                    R.layout.dlg_message_withno_buttons,
                    R.id.tv_dlg_message_withno_buttons_msg,
                    "This is a text providing explanation about this feature is about; this will guide users on what it expected from them.");
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        mRootView.findViewById(R.id.tv_frag_update_profile_preference_impacts_of_project_hint).setOnClickListener(view -> {
            DialogMessageWithNoButtons dlg = new DialogMessageWithNoButtons(requireActivity(),
                    R.layout.dlg_message_withno_buttons,
                    R.id.tv_dlg_message_withno_buttons_msg,
                    "This is a text providing explanation about this feature is about; this will guide users on what it expected from them.");
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        mRootView.findViewById(R.id.tv_frag_update_profile_preference_select_data_report_fields_hint).setOnClickListener(view -> {
            DialogMessageWithNoButtons dlg = new DialogMessageWithNoButtons(requireActivity(),
                    R.layout.dlg_message_withno_buttons,
                    R.id.tv_dlg_message_withno_buttons_msg,
                    "This is a text providing explanation about this feature is about; this will guide users on what it expected from them.");
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        mRootView.findViewById(R.id.ll_frag_update_profile_preference_industry_root).setOnClickListener(view -> {
            handleIndustryRootClickEvent();
        });

        mRootView.findViewById(R.id.ll_frag_update_profile_preference_team_size_root).setOnClickListener(view -> {
            handleTeamSizeRootClickEvent();
        });

        mRootView.findViewById(R.id.ll_frag_update_profile_preference_green_project_budget_root).setOnClickListener(view -> {
            handleBudgetRootClickEvent();
        });

        mRootView.findViewById(R.id.ll_frag_update_profile_preference_impacts_of_project_root).setOnClickListener(view -> {
            handleImpactsRootClickEvent();
        });

        mRootView.findViewById(R.id.ll_frag_update_profile_preference_select_data_report_fields_root).setOnClickListener(view -> {
            handleReportDataFields();
        });
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preferences_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_industry)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_industry_hint)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_team_size)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_team_size_hint)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_green_project_budget)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_green_project_budget_hint)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_impacts_of_project)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_impacts_of_project_hint)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_select_data_report_fields)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_select_data_report_fields_hint)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_complete_setup)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
    }

    private void initPieChart() {
        mChart = (PieChart) mRootView.findViewById(R.id.cht_frag_update_profile_progress);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
//        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterTextTypeface(CircularApplication.mTfMainBold);
        mChart.setCenterText("Your\nProgress");
        mChart.setCenterTextSize(14f);
        mChart.setCenterTextColor(getResources().getColor(R.color.red));

//        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(85f);
        mChart.setTransparentCircleRadius(85f);

        mChart.setDrawCenterText(true);
        mChart.setDrawEntryLabels(false);

        mChart.setRotationAngle(270);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(false);
        mChart.setHighlightPerTapEnabled(true);
        mChart.getLegend().setEnabled(false);
        setPieChartData(PREFERENCES_COUNT);
    }

    private void setPieChartData(int count) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry(100 / count));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Your Progress");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 70));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();
        ArrayList<Integer> sampleColors = new ArrayList<>();
        sampleColors.add(getResources().getColor(R.color.blue));
        sampleColors.add(getResources().getColor(R.color.green));
        sampleColors.add(getResources().getColor(R.color.yellow));
        sampleColors.add(getResources().getColor(R.color.red));
        sampleColors.add(getResources().getColor(R.color.purple));
        int nProgress = 0;
        for (int i = 0; i < mArrUpdateStatus.size(); i++){
            if (mArrUpdateStatus.get(i)){
                nProgress++;
            }
        }
        for (int i = 0; i < count; i++) {
            if (i >= nProgress) {
                colors.add(getResources().getColor(android.R.color.transparent));
            } else {
                colors.add(sampleColors.get(i));
            }
        }

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.animateY(1500, Easing.EaseInOutQuad);
    }

    private void handleIndustryRootClickEvent() {
        mRlSubContentRoot.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        if (inflater == null) return;
        RelativeLayout rlRoot = (RelativeLayout) inflater.inflate(R.layout.update_profile_preference_action_industry_root, null);
        ((TextView) rlRoot.findViewById(R.id.tv_update_profile_preference_action_industry_root)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) rlRoot.findViewById(R.id.tv_update_profile_preference_action_industry_confirm)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        LinearLayout llContent = (LinearLayout) rlRoot.findViewById(R.id.ll_update_profile_preference_action_industry_content);
        rlRoot.findViewById(R.id.tv_update_profile_preference_action_industry_confirm).setOnClickListener(view -> {
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            mRlSubContentRoot.setVisibility(View.GONE);
                            mArrUpdateStatus.set(0, true);
                            setPieChartData(PREFERENCES_COUNT);
                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);
                            sharedPreferences.edit().putString(Constant.SH_KEY_SELECTED_INDUSTRY,
                                    ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_industry)).getText().toString()).apply();
                        }

                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        for (int i = 0; i < mArrIndustries.length; i++) {
            TextView tv = new TextView(requireActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 5, 0, 5);
            tv.setLayoutParams(lp);
            tv.setTextColor(getResources().getColor(R.color.txtclr_dark_gray));
            tv.setTag(mArrIndustries[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv.setText(mArrIndustries[i]);
            llContent.addView(tv);
            tv.setOnClickListener(view -> {
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_industry)).setText(String.valueOf(view.getTag()));
            });
        }
        mRlSubContentRoot.addView(rlRoot);
        mRlSubContentRoot.setVisibility(View.VISIBLE);
    }

    private void handleTeamSizeRootClickEvent() {
        mRlSubContentRoot.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        if (inflater == null) return;
        RelativeLayout rlRoot = (RelativeLayout) inflater.inflate(R.layout.update_profile_preference_action_teamsize_root, null);
        ((TextView) rlRoot.findViewById(R.id.tv_update_profile_preference_action_teamsize_root)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) rlRoot.findViewById(R.id.tv_update_profile_preference_action_teamsize_confirm)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        LinearLayout llContent = (LinearLayout) rlRoot.findViewById(R.id.ll_update_profile_preference_action_teamsize_content);
        rlRoot.findViewById(R.id.tv_update_profile_preference_action_teamsize_confirm).setOnClickListener(view -> {
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            mRlSubContentRoot.setVisibility(View.GONE);
                            mArrUpdateStatus.set(1, true);
                            setPieChartData(PREFERENCES_COUNT);
                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);
                            sharedPreferences.edit().putString(Constant.SH_KEY_SELECTED_TEAM_SIZE,
                                    ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_team_size)).getText().toString()).apply();
                        }

                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        for (int i = 0; i < mArrTeamSize.length; i++) {
            TextView tv = new TextView(requireActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 5, 0, 5);
            tv.setLayoutParams(lp);
            tv.setTextColor(getResources().getColor(R.color.txtclr_dark_gray));
            tv.setTag(mArrTeamSize[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv.setText(mArrTeamSize[i]);
            llContent.addView(tv);
            tv.setOnClickListener(view -> {
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_team_size)).setText(String.valueOf(view.getTag()));
            });
        }
        mRlSubContentRoot.addView(rlRoot);
        mRlSubContentRoot.setVisibility(View.VISIBLE);
    }

    private void handleBudgetRootClickEvent() {
        mRlSubContentRoot.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        if (inflater == null) return;
        RelativeLayout rlRoot = (RelativeLayout) inflater.inflate(R.layout.update_profile_preference_action_budget_root, null);
        ((TextView) rlRoot.findViewById(R.id.tv_update_profile_preference_action_budget_root)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) rlRoot.findViewById(R.id.tv_update_profile_preference_action_budget_confirm)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        LinearLayout llContent = (LinearLayout) rlRoot.findViewById(R.id.ll_update_profile_preference_action_budget_content);
        rlRoot.findViewById(R.id.tv_update_profile_preference_action_budget_confirm).setOnClickListener(view -> {
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            mRlSubContentRoot.setVisibility(View.GONE);
                            mArrUpdateStatus.set(2, true);
                            setPieChartData(PREFERENCES_COUNT);
                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);
                            sharedPreferences.edit().putString(Constant.SH_KEY_SELECTED_BUDGET,
                                    ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_green_project_budget)).getText().toString()).apply();
                        }

                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        for (int i = 0; i < mArrBudget.length; i++) {
            TextView tv = new TextView(requireActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 5, 0, 5);
            tv.setLayoutParams(lp);
            tv.setTextColor(getResources().getColor(R.color.txtclr_dark_gray));
            tv.setTag(mArrBudget[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv.setText(mArrBudget[i]);
            llContent.addView(tv);
            tv.setOnClickListener(view -> {
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_green_project_budget)).setText(String.valueOf(view.getTag()));
            });
        }
        mRlSubContentRoot.addView(rlRoot);
        mRlSubContentRoot.setVisibility(View.VISIBLE);
    }

    private void handleImpactsRootClickEvent() {
        mRlSubContentRoot.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        if (inflater == null) return;
        RelativeLayout rlRoot = (RelativeLayout) inflater.inflate(R.layout.update_profile_preference_action_impacts_root, null);
        ((TextView) rlRoot.findViewById(R.id.tv_update_profile_preference_action_impacts_root)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) rlRoot.findViewById(R.id.tv_update_profile_preference_action_impacts_confirm)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        LinearLayout llContent = (LinearLayout) rlRoot.findViewById(R.id.ll_update_profile_preference_action_impacts_content);
        rlRoot.findViewById(R.id.tv_update_profile_preference_action_impacts_confirm).setOnClickListener(view -> {
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            mRlSubContentRoot.setVisibility(View.GONE);
                            mArrUpdateStatus.set(3, true);
                            setPieChartData(PREFERENCES_COUNT);
                            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(), Context.MODE_PRIVATE);
                            sharedPreferences.edit().putString(Constant.SH_KEY_SELECTED_IMPACTS,
                                    ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_impacts_of_project)).getText().toString()).apply();
                        }

                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        for (int i = 0; i < mArrImpacts.length; i++) {
            TextView tv = new TextView(requireActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 5, 0, 5);
            tv.setLayoutParams(lp);
            tv.setTextColor(getResources().getColor(R.color.txtclr_dark_gray));
            tv.setTag(mArrImpacts[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            tv.setText(mArrImpacts[i]);
            llContent.addView(tv);
            tv.setOnClickListener(view -> {
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_preference_impacts_of_project)).setText(String.valueOf(view.getTag()));
            });
        }
        mRlSubContentRoot.addView(rlRoot);
        mRlSubContentRoot.setVisibility(View.VISIBLE);
    }

    private void handleReportDataFields() {
        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mRlSubContentRoot.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        if (inflater == null) return;
        RelativeLayout rlRoot = (RelativeLayout) inflater.inflate(R.layout.update_profile_preference_action_report_data_fields_root,
                null);
        LinearLayout llRoot = (LinearLayout) rlRoot.findViewById(R.id.ll_update_profile_preference_action_report_data_fields_content_root);
        int iCurrentRowWidth = 0;
        LinearLayout llRow = null;

        for (int i = 0; i < mArrReportFields.length; i++) {
            if (llRow == null) {
                llRow = new LinearLayout(requireActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llRow.setPadding(10, 5, 10, 5);
                llRow.setOrientation(LinearLayout.HORIZONTAL);
                llRow.setLayoutParams(lp);
                llRoot.addView(llRow);
            }
            ReportDataField reportDataField = new ReportDataField(mArrReportFields[i]);
            RelativeLayout rlItem = (RelativeLayout) inflater.inflate(R.layout.report_data_field_item, null);
            rlItem.setTag(reportDataField);
            LinearLayout.LayoutParams lpItem = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            rlItem.setLayoutParams(lpItem);
            ((TextView) rlItem.findViewById(R.id.tv_report_data_field_item_title)).setText(mArrReportFields[i]);
            ((TextView) rlItem.findViewById(R.id.tv_report_data_field_item_title)).setTypeface(CircularApplication.mTfMainRegular);
            rlItem.findViewById(R.id.ll_report_data_field_item_content_root).setBackgroundResource(R.drawable.round_rect_white_with_black_corner_normal);
            rlItem.findViewById(R.id.ll_report_data_field_item_content_root).setOnClickListener(view -> {
                rlItem.findViewById(R.id.iv_report_data_field_item_remove).setVisibility(View.VISIBLE);
                rlItem.findViewById(R.id.ll_report_data_field_item_content_root).setBackgroundResource(R.drawable.round_rect_blue_with_black_corner_normal);
                ReportDataField reportDataFieldSelected = (ReportDataField) rlItem.getTag();
                for (int j = 0; j < mArrReportDataField.size(); j++) {
                    if (mArrReportDataField.get(j).equals(reportDataFieldSelected)) {
                        return;
                    }
                }
                mArrReportDataField.add(reportDataFieldSelected);
            });
            rlItem.findViewById(R.id.iv_report_data_field_item_remove).setVisibility(View.GONE);
            rlItem.findViewById(R.id.iv_report_data_field_item_remove).setOnClickListener(view -> {
                rlItem.findViewById(R.id.iv_report_data_field_item_remove).setVisibility(View.GONE);
                rlItem.findViewById(R.id.ll_report_data_field_item_content_root).setBackgroundResource(R.drawable.round_rect_white_with_black_corner_normal);
                ReportDataField reportDataFieldSelected = (ReportDataField) rlItem.getTag();
                if (reportDataFieldSelected != null) {
                    for (int j = 0; j < mArrReportDataField.size(); j++) {
                        if (mArrReportDataField.get(j).equals(reportDataFieldSelected)) {
                            mArrReportDataField.remove(j);
                            return;
                        }
                    }
                }
            });

            rlItem.findViewById(R.id.tv_report_data_field_item_hint).setOnClickListener(view->{
                ReportDataField reportDataFieldSelected = (ReportDataField) rlItem.getTag();
                if (reportDataFieldSelected == null) return;
                DialogMessageWithNoButtons dlg = new DialogMessageWithNoButtons(requireActivity(),
                        R.layout.dlg_message_withno_buttons,
                        R.id.tv_dlg_message_withno_buttons_msg,
                        "This is a text providing explanation about this feature is about; this will guide users on what it expected from them.");
                dlg.show();
                Utils.setDialogWidth(dlg, 0.8f, requireActivity());
            });
            for (int j = 0; j < mArrReportDataField.size(); j++) {
                if (mArrReportDataField.get(j).equals(reportDataField)) {
                    rlItem.findViewById(R.id.iv_report_data_field_item_remove).setVisibility(View.VISIBLE);
                    rlItem.findViewById(R.id.ll_report_data_field_item_content_root).setBackgroundResource(R.drawable.round_rect_blue_with_black_corner_normal);
                    break;
                }
            }
            rlItem.measure(0, 0);
            int nItemWidth = rlItem.getMeasuredWidth();
            if (iCurrentRowWidth == 0 && nItemWidth + 140 > metrics.widthPixels) {
                llRow.addView(rlItem);
                llRow = null;
                iCurrentRowWidth = 0;
                continue;
            } else if (iCurrentRowWidth + nItemWidth + 140 > metrics.widthPixels) {
                llRow = null;
                i--;
                iCurrentRowWidth = 0;
                continue;
            }
            llRow.addView(rlItem);
            iCurrentRowWidth += nItemWidth;
        }

        rlRoot.findViewById(R.id.tv_update_profile_preference_action_report_data_fields_confirm).setOnClickListener(view -> {
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            mRlSubContentRoot.setVisibility(View.GONE);
                            if (mArrReportDataField.size() > 0){
                                mArrUpdateStatus.set(4, true);
                            }else{
                                mArrUpdateStatus.set(4, false);
                            }
                            setPieChartData(PREFERENCES_COUNT);
                            saveCurrentSelectedReportDataFields();
                        }

                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });
        ((TextView) rlRoot.findViewById(R.id.tv_update_profile_preference_action_report_data_fields_confirm)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        mRlSubContentRoot.addView(rlRoot);
        mRlSubContentRoot.setVisibility(View.VISIBLE);
    }

    private void saveCurrentSelectedReportDataFields() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(),
                Context.MODE_PRIVATE);
        JSONArray arrJson = new JSONArray();
        for (int i = 0; i < mArrReportDataField.size(); i++) {
            arrJson.put(mArrReportDataField.get(i).getAsJson());
        }
        sharedPreferences.edit().putString(Constant.SH_KEY_SELECTED_REPORT_DATA_FIELDS, arrJson.toString()).apply();
    }
}
