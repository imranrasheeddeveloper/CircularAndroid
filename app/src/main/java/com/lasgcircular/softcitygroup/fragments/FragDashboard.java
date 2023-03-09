package com.lasgcircular.softcitygroup.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.lasgcircular.softcitygroup.CircularApplication;
import com.lasgcircular.softcitygroup.Constant;
import com.lasgcircular.softcitygroup.R;
import com.lasgcircular.softcitygroup.local.PreferenceRepository;
import com.lasgcircular.softcitygroup.model.DashboardData;
import com.lasgcircular.softcitygroup.model.FeaturedProjectData;
import com.lasgcircular.softcitygroup.model.ProjectsItem;
import com.lasgcircular.softcitygroup.model.ReportsItem;
import com.lasgcircular.softcitygroup.ui.BarChartWithLabel;
import com.lasgcircular.softcitygroup.ui.ChartRoot;
import com.lasgcircular.softcitygroup.ui.SliderAdapterFeaturedProject;
import com.lasgcircular.softcitygroup.view_model.DashboardViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class FragDashboard extends Fragment {
    private View mRootView;
    //    private BarChart mCtReportedData;
    private boolean m_bShowDetailsStatus = false;
    private ChartRoot mChart;
    private DashboardViewModel viewModel;
    PreferenceRepository preferenceRepository;
    SwipeRefreshLayout swipe;


    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_dashboard, null);
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        preferenceRepository = new PreferenceRepository();


        getDetails();
        initControls();
        swipe = mRootView.findViewById(R.id.home_swipe_container);

        swipe.setOnRefreshListener(() -> {
            getDetails();
            initControls();
            swipe.setRefreshing(false);
        });

        return mRootView;
    }

    private void getDetails() {
        String token = preferenceRepository.getString("token");
        viewModel.getDashboardData(token);
        viewModel._dashboard.observe(getViewLifecycleOwner(), response -> {
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

    private void setData(DashboardData data) {

        try {

            String report_count = String.valueOf(data.getStatistics().getReportCount());
            String project_count = String.valueOf(data.getStatistics().getProjectCount());
            String stake_holder = String.valueOf(data.getStatistics().getStakeHolderCount());
            String investment = String.valueOf(data.getStatistics().getInvestorCount());

            ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_your_data_reports_value)).setText(report_count);
            ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_your_total_green_initiatives_value)).setText(project_count);
            ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_our_total_stakeholders_value)).setText(stake_holder);
            ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_our_total_funding_partners_value)).setText(investment);

            if (data.getReports() != null) {
                initChart1(data.getReports());
            }

            if (data.getProjects() != null) {
                initSlider(data.getProjects());
            }

        } catch (NullPointerException | IllegalStateException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void showSnackBar(String msg) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    private void initControls() {
//        initChart();
        mChart = mRootView.findViewById(R.id.ct_frag_dashboard_reported_data_root);


        mRootView.findViewById(R.id.tv_frag_dashboard_reported_data_show_hide_details1).setOnClickListener(view -> {
            m_bShowDetailsStatus = !m_bShowDetailsStatus;
            if (m_bShowDetailsStatus) {
                ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_reported_data_show_hide_details1))
                        .setTextColor(getResources().getColor(R.color.red));
                mChart.showChartLabels();
            } else {
                ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_reported_data_show_hide_details1))
                        .setTextColor(getResources().getColor(android.R.color.darker_gray));
                mChart.hideChartLabels();
            }
        });

        initFonts();
    }

    private void initChart1(List<ReportsItem> reports) {
        try {

            DisplayMetrics metrics = new DisplayMetrics();
            requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
            ArrayList<BarChartWithLabel> arrCharts = new ArrayList<>();
            int nChartWidth = metrics.widthPixels / 15;
            int nPadding = metrics.widthPixels / 150;
            mChart.setBarWidth(nChartWidth);
//        String[] strTitle = new String[]{
//                "Co2 productivity",
//                "Energy productivity",
//                "Material productivity",
//                "Freshwater resources",
//                "Mineral resources",
//                "R&D expenditure",
//                "Energy pricing",
//                "Water pricing",
//                "Environment-related innovation",
//                "Patents of importance",
//                "Wildlife resources",
//                "Sewage treatment",
//                "Drinking water",
//                "Co2 productivity",
//                "Energy productivity",
//                "Material productivity",
//                "Freshwater resources",
//                "Mineral resources",
//                "R&D expenditure",
//                "Energy pricing",
//                "Water pricing",
//                "Environment-related innovation",
//                "Patents of importance",
//                "Wildlife resources",
//                "Sewage treatment"
//        };
//        chartGraph.setValue((int)(Math.random() * 100.0f) + 150);

            for (int i = 0; i < reports.size(); i++) {
                BarChartWithLabel chartGraph = new BarChartWithLabel(getContext());
                chartGraph.setMaxHeight(50);
                chartGraph.setHorizontalPadding(nPadding);
                chartGraph.setMaxValue(50);
                chartGraph.setWidth(nChartWidth);
                chartGraph.setValue(reports.get(i).getDataCount());
                chartGraph.setColor(Color.parseColor("#FF00a85a"));
                chartGraph.setLabel(reports.get(i).getName());
                chartGraph.setTypeface(CircularApplication.mTfMainRegular);
                chartGraph.setTopDown(false);
                chartGraph.setFontSize(14);
                chartGraph.setPadding(0, 10, 0, 10);
                arrCharts.add(chartGraph);
            }
            mChart.setBarCharts(arrCharts);
            mChart.invalidateAllCharts();
            mChart.hideChartLabels();


        } catch (NullPointerException | IllegalStateException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void initSlider(List<ProjectsItem> projects) {
        SliderView sliderView = mRootView.findViewById(R.id.slider_dashboard);
        SliderAdapterFeaturedProject mSliderAdapter = new SliderAdapterFeaturedProject(requireActivity());
        sliderView.setSliderAdapter(mSliderAdapter);
        ArrayList<FeaturedProjectData> arrData = new ArrayList<>();
        int[] arrDrawables = new int[]{R.drawable.dummy_slide1, R.drawable.dummy_slide2, R.drawable.dummy_slide3, R.drawable.dummy_slide4};
        for (int i = 0; i < 4; i++) {
            arrData.add(new FeaturedProjectData(arrDrawables[i],
                    "Inspection team inspects ongoing circular project along Makoko",
                    getString(R.string.dummy_featured_project_content)));
        }
        mSliderAdapter.renewItems(projects);
        sliderView.setCurrentPageListener(position -> ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_featured_projects_title)).setText(projects.get(position).getTitle()));
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_featured_projects_title)).setText(projects.get(sliderView.getCurrentPagePosition()).getTitle());
    }

//    private void initChart(){
//        mCtReportedData = (BarChart)mRootView.findViewById(R.id.ct_frag_dashboard_reported_data_indicator);
//        mCtReportedData.setOnChartValueSelectedListener(this);
//
//        mCtReportedData.setDrawBarShadow(false);
//        mCtReportedData.setDrawValueAboveBar(true);
//        mCtReportedData.getAxisLeft().setDrawGridLines(false);
//        mCtReportedData.getAxisLeft().setEnabled(false);
//        mCtReportedData.getAxisRight().setDrawGridLines(false);
//        mCtReportedData.getAxisRight().setEnabled(false);
//        mCtReportedData.getXAxis().setDrawGridLines(false);
//        mCtReportedData.getXAxis().setEnabled(false);
//
//        mCtReportedData.getDescription().setEnabled(false);
//
//        // if more than 60 entries are displayed in the chart, no values will be
//        // drawn
//        mCtReportedData.setMaxVisibleValueCount(60);
//
//        // scaling can now only be done on x- and y-axis separately
//        mCtReportedData.setPinchZoom(false);
//
//        mCtReportedData.setDrawGridBackground(false);
//
//        Legend l = mCtReportedData.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(true);
//        l.setForm(LegendForm.EMPTY);
//        l.setFormSize(0f);
//        l.setTextSize(0);
//        l.setXEntrySpace(0f);
//
//        setData(13, 200.0f);
//        mCtReportedData.invalidate();
//    }
//
//    private void setData(int count, float range) {
//
//        float start = 1f;
//
//        ArrayList<BarEntry> values = new ArrayList<>();
//
//        for (int i = (int) start; i < start + count; i++) {
//            float val = (float) (Math.random() * (range + 1));
//
////            if (Math.random() * 100 < 25) {
////                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.round_rect_green_normal)));
////            } else {
//                values.add(new BarEntry(i, val));
////            }
//        }
//
//        BarDataSet set1;
//
//        if (mCtReportedData.getData() != null &&
//                mCtReportedData.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet) mCtReportedData.getData().getDataSetByIndex(0);
//            set1.setValues(values);
//            mCtReportedData.getData().notifyDataChanged();
//            mCtReportedData.notifyDataSetChanged();
//
//        } else {
//            set1 = new BarDataSet(values, "");
//
//            set1.setDrawIcons(false);
//            set1.setDrawValues(false);
//
//            int startColor1 = ContextCompat.getColor(requireActivity(), R.color.blue);
//            int endColor1 = ContextCompat.getColor(requireActivity(), R.color.blue);
//
//            List<Fill> gradientFills = new ArrayList<>();
//            gradientFills.add(new Fill(startColor1, endColor1));
//
//            set1.setFills(gradientFills);
//
//            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//            dataSets.add(set1);
//
//            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
////            data.setValueTypeface(tfLight);
//            data.setBarWidth(0.75f);
//
//            mCtReportedData.setData(data);
//        }
//    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_your_data_reports_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_your_data_reports_value)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_your_total_green_initiatives_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_your_total_green_initiatives_value)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_our_total_funding_partners_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_our_total_funding_partners_value)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_our_total_stakeholders_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_our_total_stakeholders_value)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_reported_data_indicator_title1)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_reported_data_show_hide_details1)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_dashboard_featured_projects_title)).setTypeface(CircularApplication.mTfMainRegular);
    }

    private void showLoading() {
        ((ConstraintLayout) mRootView.findViewById(R.id.home_loading)).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        ((ConstraintLayout) mRootView.findViewById(R.id.home_loading)).setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        viewModel._dashboard.removeObservers(this);
        viewModel = null;
    }
}
