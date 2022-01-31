package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.circular.circular.CircularApplication;
import com.circular.circular.Constant;
import com.circular.circular.MainActivity;
import com.circular.circular.ProfileActivity;
import com.circular.circular.R;
import com.circular.circular.adapters.SpinnerTextViewAdapter;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.dialog.DialogMessageWithNoButtons;
import com.circular.circular.model.ReportDataField;
import com.circular.circular.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FragUpdateProfileMain extends Fragment {
    private View mRootView;

    private final String[] arrLocalization = new String[]{
            "Alimosho Local Government",
            "Lagos State Government"
    };

    private final String[] arrIndustries = new String[]{
            "Information Technology",
            "Finance",
            "Heavy Industry",
            "Agriculture"
    };

    private final String[] arrReminder = new String[]{
            "Hourly",
            "Daily",
            "Weekly",
            "Monthly",
            "Yearly"
    };

    private SpinnerTextViewAdapter mAdapterLocalization;
    private SpinnerTextViewAdapter mAdapterIndustry;
    private SpinnerTextViewAdapter mAdapterReminder;
    private ArrayList<String> mArrLocalization;
    private ArrayList<String> mArrIndustry;
    private ArrayList<String> mArrReminder;
    private ArrayList<ReportDataField> mArrReportDataField = new ArrayList<>();

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_update_profile_main, null);
        initData();
        initControls();
        return mRootView;
    }

    private void initData() {
        int i;
        mArrLocalization = new ArrayList<>();
        for (i = 0; i < arrLocalization.length; i++) {
            mArrLocalization.add(arrLocalization[i]);
        }
        mArrIndustry = new ArrayList<>();
        for (i = 0; i < arrIndustries.length; i++) {
            mArrIndustry.add(arrIndustries[i]);
        }
        mArrReminder = new ArrayList<>();
        for (i = 0; i < arrReminder.length; i++) {
            mArrReminder.add(arrReminder[i]);
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
    }
    private void initControls() {
        mRootView.findViewById(R.id.tv_frag_update_profile_main_data_report_preference_edit).setOnClickListener(view -> {
            if (requireActivity() instanceof ProfileActivity) {
                FragUpdateProfilePreferences frag = new FragUpdateProfilePreferences();
                Bundle paramsToFragPreferences = new Bundle();
                paramsToFragPreferences.putInt(Constant.UPDATE_PROFILE_STARTUP_ACTION, Constant.UPDATE_PROFILE_STARTUP_ACTION_REPORT_DATA);
                frag.setArguments(paramsToFragPreferences);
                ((ProfileActivity) requireActivity()).addFragment(R.id.fl_act_update_profile_container,
                        frag, true);
            }
        });

        mRootView.findViewById(R.id.tv_frag_update_profile_main_update).setOnClickListener(view->{
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            requireActivity().finish();
                        }

                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());
        });

        mAdapterLocalization = new SpinnerTextViewAdapter(requireActivity(), mArrLocalization);
        ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_localization_content)).setAdapter(mAdapterLocalization);
        mAdapterIndustry = new SpinnerTextViewAdapter(requireActivity(), mArrIndustry);
        ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_industry_content)).setAdapter(mAdapterIndustry);
        mAdapterReminder = new SpinnerTextViewAdapter(requireActivity(), mArrReminder);
        ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_report_frequency_reminder_content)).setAdapter(mAdapterReminder);
        handleReportDataFields();
        initFonts();
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_name)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).setTypeface(CircularApplication.mTfMainRegular);
        ((EditText) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_phone)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_localization_industry)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_data_report_preference)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_data_report_preference_edit)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_report_frequency_reminder)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_update)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
    }

    private void handleReportDataFields() {
        DisplayMetrics metrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        LayoutInflater inflater = LayoutInflater.from(requireActivity());
        if (inflater == null) return;
        LinearLayout llRoot = (LinearLayout) mRootView.findViewById(R.id.ll_frag_update_profile_main_report_data_fields_content_root);
        llRoot.removeAllViews();
        int iCurrentRowWidth = 0;
        LinearLayout llRow = null;

        for (int i = 0; i < mArrReportDataField.size(); i++) {
            if (llRow == null) {
                llRow = new LinearLayout(requireActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llRow.setPadding(10, 5, 10, 5);
                llRow.setOrientation(LinearLayout.HORIZONTAL);
                llRow.setLayoutParams(lp);
                llRoot.addView(llRow);
            }
            ReportDataField reportDataField = mArrReportDataField.get(i);
            RelativeLayout rlItem = (RelativeLayout) inflater.inflate(R.layout.report_data_field_item, null);
            rlItem.setTag(reportDataField);
            LinearLayout.LayoutParams lpItem = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            rlItem.setLayoutParams(lpItem);
            ((TextView) rlItem.findViewById(R.id.tv_report_data_field_item_title)).setText(reportDataField.mStrName);
            ((TextView) rlItem.findViewById(R.id.tv_report_data_field_item_title)).setTypeface(CircularApplication.mTfMainRegular);
            rlItem.findViewById(R.id.ll_report_data_field_item_content_root).setBackgroundResource(R.drawable.round_rect_blue_with_black_corner_normal);            rlItem.findViewById(R.id.iv_report_data_field_item_remove).setVisibility(View.GONE);
            rlItem.findViewById(R.id.tv_report_data_field_item_hint).setVisibility(View.GONE);
            rlItem.measure(0, 0);
            int nItemWidth = rlItem.getMeasuredWidth();
            if (iCurrentRowWidth == 0 && nItemWidth + 120 > metrics.widthPixels) {
                llRow.addView(rlItem);
                llRow = null;
                iCurrentRowWidth = 0;
                continue;
            } else if (iCurrentRowWidth + nItemWidth + 120 > metrics.widthPixels) {
                llRow = null;
                i--;
                iCurrentRowWidth = 0;
                continue;
            }
            llRow.addView(rlItem);
            iCurrentRowWidth += nItemWidth;
        }
    }
}
