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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.circular.circular.CircularApplication;
import com.circular.circular.Constant;
import com.circular.circular.MainActivity;
import com.circular.circular.R;
import com.circular.circular.adapters.NotificationItemAdapter;
import com.circular.circular.adapters.ReportDataMainItemAdapter;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.model.NotificationItem;
import com.circular.circular.model.ReportDataField;
import com.circular.circular.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FragReportDataMain extends Fragment {
    private View mRootView;
    private ListView mLvData;
    private ArrayList<ReportDataField> mArrData;
    private ReportDataMainItemAdapter mAdapter;

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_report_data_main, null);
        return mRootView;
    }

    @Override
    public void onResume(){
        super.onResume();
        initData();
        initControls();
    }

    private void initData() {
        mArrData = new ArrayList<>();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(),
                Context.MODE_PRIVATE);
        String strJSON = sharedPreferences.getString(Constant.SH_KEY_SELECTED_REPORT_DATA_FIELDS, "[]");
        JSONArray arrJson = null;
        try {
            arrJson = new JSONArray(strJSON);
            for (int i = 0; i < arrJson.length(); i++){
                mArrData.add(new ReportDataField(arrJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mArrData.add(new ReportDataField("Upload Evidence of Green Project", true));
    }

    private void initControls() {
        mLvData = mRootView.findViewById(R.id.lv_frag_report_data_main_data);
        mAdapter = new ReportDataMainItemAdapter(requireActivity(), mArrData);
        mLvData.setAdapter(mAdapter);
        ((BaseAdapter) mLvData.getAdapter()).notifyDataSetChanged();
        initFonts();
        mRootView.findViewById(R.id.tv_frag_report_data_main_submit).setOnClickListener(view->{
            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {

                        }

                        @Override
                        public void onClickedNo() {

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
