package com.lasgcircular.softcitygroup.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import com.lasgcircular.softcitygroup.CircularApplication;
import com.circular.circular.R;
import com.lasgcircular.softcitygroup.model.data_points.DataPointsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ReportDataMainItemAdapter extends BaseAdapter {

    private Context mCtx;
    private LayoutInflater mInflater;
    private ArrayList<DataPointsItem> mArrData;
    boolean status;

    public ReportDataMainItemAdapter(Context ctx, List<DataPointsItem> arrData) {
        mCtx = ctx;
        mArrData = new ArrayList<>();
        if (arrData != null && arrData.size() > 0) {
            mArrData.addAll(arrData);
        }
        mInflater = LayoutInflater.from(mCtx);
    }

    @Override
    public int getCount() {
        return mArrData.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.lvitem_report_data_main, null);
        }
        DataPointsItem item = (DataPointsItem) getItem(i);
        status = true;

        ((EditText) view.findViewById(R.id.ed_lvitem_report_data_main_title)).setHint(item.getName());

        ((EditText) view.findViewById(R.id.ed_lvitem_report_data_main_title)).setTypeface(CircularApplication.mTfMainRegular);

        View finalView = view;
        View finalView1 = view;
        ((EditText) view.findViewById(R.id.ed_lvitem_report_data_main_title)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (count > 0) {
                        mArrData.get(i).setDescription(s.toString());
//                    }
//                        if (s.toString().contains("[-+.^:,]")){
//                            ((EditText) finalView1.findViewById(R.id.ed_lvitem_report_data_main_title)).setTextColor(Color.parseColor("#FFec3237"));
//                        }else {
//                            mArrData.get(i).setDescription(s.toString().replaceAll("[-+.^:,]",""));
//                        }
////                        if (hasAnySymbolExceptWhitespace(s.toString())){
////                            ((EditText) finalView1.findViewById(R.id.ed_lvitem_report_data_main_title)).setHintTextColor(Color.parseColor("#FFec3237"));
////                        }
                    }else {
                        mArrData.get(i).setDescription("0");
//                      ((EditText) finalView1.findViewById(R.id.ed_lvitem_report_data_main_title)).setHintTextColor(Color.parseColor("#FFec3237"));
                    }
                } catch (NumberFormatException e) {
                    ((EditText) finalView.findViewById(R.id.ed_lvitem_report_data_main_title)).setText("");
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

//                if (!s.toString().isEmpty()){
//                    try
//                    {
//                        int w = Integer.parseInt(s.toString());
//                        String data = ((EditText) finalView.findViewById(R.id.ed_lvitem_report_data_main_title)).getText().toString();
//                        mArrData.get(i).setDescription(data);
//
//                    }catch(NumberFormatException e) {
//                        ((EditText) finalView.findViewById(R.id.ed_lvitem_report_data_main_title)).setText("");
//                        e.printStackTrace();
//                    }
//
//                }
            }
        });


//        if (item.m_bUploadFlag){
//            ((EditText)view.findViewById(R.id.ed_lvitem_report_data_main_title)).setEnabled(false);
//        }
        return view;
    }


    public static boolean hasAnySymbolExceptWhitespace(String string){
        return Pattern.matches("(?=.*[^a-zA-Z0-9] ).*", string);
    }
}