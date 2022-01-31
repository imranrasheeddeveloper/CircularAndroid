package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.circular.circular.CircularApplication;
import com.circular.circular.R;
import com.circular.circular.model.FeaturedProjectData;
import com.circular.circular.ui.BarChartWithLabel;
import com.circular.circular.ui.ChartRoot;
import com.circular.circular.ui.SliderAdapterFeaturedProject;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class FragDashboard extends Fragment {
    private View mRootView;
//    private BarChart mCtReportedData;
    private boolean m_bShowDetailsStatus = false;
    private ChartRoot mChart;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_dashboard, null);
        initControls();
        return mRootView;
    }

    private void initControls(){
//        initChart();
        mChart = mRootView.findViewById(R.id.ct_frag_dashboard_reported_data_root);
        initChart1();

        mRootView.findViewById(R.id.tv_frag_dashboard_reported_data_show_hide_details1).setOnClickListener(view->{
            m_bShowDetailsStatus = !m_bShowDetailsStatus;
            if (m_bShowDetailsStatus){
                ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_reported_data_show_hide_details1))
                        .setTextColor(getResources().getColor(R.color.red));
                mChart.showChartLabels();
            }else{
                ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_reported_data_show_hide_details1))
                        .setTextColor(getResources().getColor(android.R.color.darker_gray));
                mChart.hideChartLabels();
            }
        });
        initSlider();
        initFonts();
    }

    private void initChart1(){
        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        ArrayList<BarChartWithLabel> arrCharts = new ArrayList<>();
        int nChartWidth = metrics.widthPixels / 15;
        int nPadding = metrics.widthPixels / 150;
        mChart.setBarWidth(nChartWidth);
        String[] strTitle = new String[]{
                "Co2 productivity",
                "Energy productivity",
                "Material productivity",
                "Freshwater resources",
                "Mineral resources",
                "R&D expenditure",
                "Energy pricing",
                "Water pricing",
                "Environment-related innovation",
                "Patents of importance",
                "Wildlife resources",
                "Sewage treatment",
                "Drinking water",
                "Co2 productivity",
                "Energy productivity",
                "Material productivity",
                "Freshwater resources",
                "Mineral resources",
                "R&D expenditure",
                "Energy pricing",
                "Water pricing",
                "Environment-related innovation",
                "Patents of importance",
                "Wildlife resources",
                "Sewage treatment"
        };
        for (int i = 0; i < 25; i++){
            BarChartWithLabel chartGraph = new BarChartWithLabel(getContext());
            chartGraph.setMaxHeight(250);
            chartGraph.setHorizontalPadding(nPadding);
            chartGraph.setMaxValue(250);
            chartGraph.setWidth(nChartWidth);
            chartGraph.setValue((int)(Math.random() * 100.0f) + 150);
            chartGraph.setColor(getResources().getColor(R.color.blue));
            chartGraph.setLabel(strTitle[i]);
            chartGraph.setTypeface(CircularApplication.mTfMainRegular);
            chartGraph.setTopDown(false);
            chartGraph.setFontSize(12);
            chartGraph.setPadding(0, 10, 0, 10);
            arrCharts.add(chartGraph);
        }
        mChart.setBarCharts(arrCharts);
        mChart.invalidateAllCharts();
        mChart.hideChartLabels();
    }

    private void initSlider(){
        SliderView sliderView = mRootView.findViewById(R.id.slider_dashboard);
        SliderAdapterFeaturedProject mSliderAdapter = new SliderAdapterFeaturedProject(requireActivity());
        sliderView.setSliderAdapter(mSliderAdapter);
        ArrayList<FeaturedProjectData> arrData = new ArrayList<>();
        int [] arrDrawables = new int[]{R.drawable.dummy_slide1, R.drawable.dummy_slide2, R.drawable.dummy_slide3, R.drawable.dummy_slide4};
        for (int i = 0; i < 4; i++){
            arrData.add(new FeaturedProjectData(arrDrawables[i],
                    "Inspection team inspects ongoing circular project along Makoko",
                    getString(R.string.dummy_featured_project_content)));
        }
        mSliderAdapter.renewItems(arrData);
    }

//    private void initChart(){
//        mCtReportedData = (BarChart)mRootView.findViewById(R.id.ct_frag_dashboard_reported_data_indicator);
////        mCtReportedData.setOnChartValueSelectedListener(this);
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

    private void initFonts(){
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_your_data_reports_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_your_data_reports_value)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_your_total_green_initiatives_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_your_total_green_initiatives_value)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_our_total_funding_partners_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_our_total_funding_partners_value)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_our_total_stakeholders_title)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_our_total_stakeholders_value)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_reported_data_indicator_title1)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_reported_data_show_hide_details1)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView)mRootView.findViewById(R.id.tv_frag_dashboard_featured_projects_title)).setTypeface(CircularApplication.mTfMainRegular);
    }
}
