package com.lasgcircular.softcitygroup.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.lasgcircular.softcitygroup.CircularApplication;
import com.lasgcircular.softcitygroup.Constant;
import com.lasgcircular.softcitygroup.MainActivity;
import com.lasgcircular.softcitygroup.ProfileActivity;
import com.lasgcircular.softcitygroup.R;
import com.lasgcircular.softcitygroup.adapters.SpinnerTextViewAdapter;
import com.lasgcircular.softcitygroup.dialog.ConfirmDialogInterface;
import com.lasgcircular.softcitygroup.dialog.DialogConfirm;
import com.lasgcircular.softcitygroup.local.PreferenceRepository;
import com.lasgcircular.softcitygroup.local.TinyDbManager;
import com.lasgcircular.softcitygroup.model.ReportDataField;
import com.lasgcircular.softcitygroup.model.User;
import com.lasgcircular.softcitygroup.model.data_points.AssignedPreferenceItem;
import com.lasgcircular.softcitygroup.model.data_points.DataPointsItem;
import com.lasgcircular.softcitygroup.utils.AlarmReceiver;
import com.lasgcircular.softcitygroup.utils.Utils;
import com.lasgcircular.softcitygroup.view_model.DataPointsViewModel;
import com.lasgcircular.softcitygroup.view_model.ProfileViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FragUpdateProfileMain extends Fragment {
    private View mRootView;
    private ProfileViewModel viewModel;
    MultipartBody.Part multiPart;
    private DataPointsViewModel dataPointsViewModel;
    Uri profileUri;

    PreferenceRepository repository;
    int selectedLocalizationContentPosition = 10, selectedIndustryContentPosition = 10;
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
    private List<AssignedPreferenceItem> assignedPreferenceItems;
    private ArrayList<ReportDataField> mArrReportDataField = new ArrayList<>();

    @SuppressLint("InflateParams")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frag_update_profile_main, null);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        dataPointsViewModel = new ViewModelProvider(this).get(DataPointsViewModel.class);
        repository = new PreferenceRepository();
        assignedPreferenceItems = new ArrayList<>();

        setProfileData();
        getAssignedPreferences();

        initData();
        initControls();
        return mRootView;
    }

    private void setProfileData() {
        try {

            if (TinyDbManager.getUserType().equalsIgnoreCase("App User")) {
                mRootView.findViewById(R.id.tv_frag_update_profile_main_data_report_preference_edit).setVisibility(View.VISIBLE);
            } else {
                mRootView.findViewById(R.id.tv_frag_update_profile_main_data_report_preference_edit).setVisibility(View.GONE);
            }

            if (TinyDbManager.getUserInformation() != null) {
                User user = TinyDbManager.getUserInformation();
                Glide.with(requireContext())
                        .load(Constant.IMG_PATH + user.getProfilePic())
                        .placeholder(R.color.white_alpha)
                        .into((ImageView) mRootView.findViewById(R.id.iv_frag_update_profile_main_info_avatar));

                String first_name = user.getName().replaceAll("[^a-zA-Z0-9]", "");
                String last_name = user.getLastName().replaceAll("[^a-zA-Z0-9]", "");
                String number = user.getPhone().replaceAll("[^a-zA-Z0-9]", "");

                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_first_name)).setText(first_name);
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_last_name)).setText(last_name);
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).setText(user.getEmail());
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_phone)).setText(number);

            }

        } catch (NullPointerException | IllegalStateException | NumberFormatException e) {
            e.printStackTrace();
        }
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
        //  initSelectedReportDataFields();
    }

    private void getAssignedPreferences() {
        String token = repository.getString("token");
        dataPointsViewModel.getPoints(token);
        dataPointsViewModel._data_points.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null) {
                        showSnackBar("Something went wrong!!");
                    } else {
                        Constant.getLoginError(CircularApplication.applicationContext, response.getError());
                    }
                } else if (response.getData() != null) {
                    hideLoading();

                    if (response.getData().getErrors() == null) {
                        if (response.getData().getData() != null) {
                            if (response.getData().getData().getAssignedPreference() != null) {
                                assignedPreferenceItems.addAll(response.getData().getData().getAssignedPreference());
                                if (assignedPreferenceItems.size() > 0) {
                                    handleReportDataFields();
                                }
                            }
                        }
                    } else {
                        showSnackBar(response.getData().getErrors());
                    }

                }
            }

        });
    }


    //    private void initSelectedReportDataFields() {
//        mArrReportDataField = new ArrayList<>();
//        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(requireActivity().getPackageName(),
//                Context.MODE_PRIVATE);
//        String strJSON = sharedPreferences.getString(Constant.SH_KEY_SELECTED_REPORT_DATA_FIELDS, "[]");
//        JSONArray arrJson = null;
//        try {
//            arrJson = new JSONArray(strJSON);
//            for (int i = 0; i < arrJson.length(); i++) {
//                mArrReportDataField.add(new ReportDataField(arrJson.getJSONObject(i)));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
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

        mRootView.findViewById(R.id.tv_frag_update_profile_main_update).setOnClickListener(view -> {

            if (((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_first_name)).getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.enter_first_name), Toast.LENGTH_LONG).show();
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_first_name)).requestFocus();
                return;
            }
            if (((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_last_name)).getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.enter_last_name), Toast.LENGTH_LONG).show();
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_last_name)).requestFocus();
                return;
            }
            if (((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.enter_email), Toast.LENGTH_LONG).show();
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).requestFocus();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).getText().toString()).matches()) {
                Toast.makeText(getActivity(), getString(R.string.enter_valid_email), Toast.LENGTH_LONG).show();
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).requestFocus();
                return;
            }
            if (((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_phone)).getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), getString(R.string.enter_phone), Toast.LENGTH_LONG).show();
                ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_phone)).requestFocus();
                return;
            }

            if (multiPart == null) {
                if (TinyDbManager.getUserInformation().getProfilePic() == null || TinyDbManager.getUserInformation().getProfilePic().isEmpty()) {
//                    Toast.makeText(getActivity(), getString(R.string.select_image), Toast.LENGTH_LONG).show();
//                    return;
                }
            }

            DialogConfirm dlg = new DialogConfirm(requireActivity(),
                    R.layout.dlg_confirm_proceed,
                    R.id.tv_dlg_confirm_proceed_yes,
                    R.id.tv_dlg_confirm_proceed_no,
                    R.id.tv_dlg_confirm_proceed_msg,
                    getString(R.string.confirm_proceed_msg),
                    new ConfirmDialogInterface() {
                        @Override
                        public void onClickedConfirm() {
                            updateUserData();
                        }

                        @Override
                        public void onClickedNo() {

                        }
                    });
            dlg.show();
            Utils.setDialogWidth(dlg, 0.8f, requireActivity());


        });

        mRootView.findViewById(R.id.iv_frag_update_profile_main_info_avatar).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            //  intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(intent, 1);
        });

        mAdapterLocalization = new SpinnerTextViewAdapter(requireActivity(), mArrLocalization);
        ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_localization_content)).setAdapter(mAdapterLocalization);
        mAdapterIndustry = new SpinnerTextViewAdapter(requireActivity(), mArrIndustry);
        ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_industry_content)).setAdapter(mAdapterIndustry);
        mAdapterReminder = new SpinnerTextViewAdapter(requireActivity(), mArrReminder);
        ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_report_frequency_reminder_content)).setAdapter(mAdapterReminder);

        ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_localization_content)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedLocalizationContentPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
        ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_industry_content)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedIndustryContentPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        try {
            int locPos = repository.getInt("selected_localization_content");
            if (locPos != -1) {
                ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_localization_content)).setSelection(locPos);
            }
        } catch (NullPointerException | IllegalStateException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        try {
            int indPos = repository.getInt("selected_industry_content");
            if (indPos != -1) {
                ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_industry_content)).setSelection(indPos);
            }
        } catch (NullPointerException | IllegalStateException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        try {
            if (TinyDbManager.getRemainder() != 6) {
                int remainder_status = TinyDbManager.getRemainder();
                if (mArrReminder.size() > remainder_status) {
                    ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_report_frequency_reminder_content)).setSelection(remainder_status);
                }
            }
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_report_frequency_reminder_content)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                switch (position) {
                    case 0:
                        TinyDbManager.saveRemainder(position);
                        setRemainder(3.6e+6);
                        break;
                    case 1:
                        TinyDbManager.saveRemainder(position);
                        setRemainder(8.64e+7);
                        break;
                    case 2:
                        TinyDbManager.saveRemainder(position);
                        setRemainder(6.048e+8);
                        break;
                    case 3:
                        TinyDbManager.saveRemainder(position);
                        setRemainder(2.628e+9);
                        break;
                    case 4:
                        TinyDbManager.saveRemainder(position);
                        setRemainder(3.154e+10);
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
//        String selectedReminder = ((Spinner) mRootView.findViewById(R.id.sp_frag_update_profile_main_report_frequency_reminder_content)).getSelectedItem().toString();


        initFonts();
    }

    private void setRemainder(double interval_period) {
        Calendar updateTime = Calendar.getInstance();

        updateTime.set(Calendar.SECOND, 5);

        Intent alarmIntent = new Intent(requireContext(), AlarmReceiver.class);
        PendingIntent recurringDownload = PendingIntent.getActivity(requireContext(), 1, alarmIntent
                , PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarms = (AlarmManager) requireActivity().getSystemService(Context.ALARM_SERVICE);
        alarms.cancel(recurringDownload);
        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP, updateTime.getTimeInMillis(), (long) interval_period, recurringDownload); //will run it after every 5 seconds.

    }

    private void updateUserData() {
        String first_name = ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_first_name)).getText().toString().trim();
        String last_name = ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_last_name)).getText().toString().trim();
        String phone = ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_phone)).getText().toString().trim();
        String email = ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).getText().toString().trim();
        String token = repository.getString("token");
        viewModel.updateUser(token, first_name, last_name, email, phone, multiPart);
        viewModel._update_user.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null) {
                        showSnackBar("Something went wrong!!");
                    } else {
                        Constant.getLoginError(CircularApplication.applicationContext, response.getError());
                    }
                } else if (response.getData() != null) {
                    hideLoading();
                    if (response.getData().getErrors() == null) {
                        if (response.getData().getData() != null) {
                            TinyDbManager.saveUserData(response.getData().getData().getUser());
                            if (selectedLocalizationContentPosition != 10) {
                                repository.setInt("selected_localization_content", selectedLocalizationContentPosition);
                            }
                            if (selectedIndustryContentPosition != 10) {
                                repository.setInt("selected_industry_content", selectedIndustryContentPosition);
                            }
                            Intent intent = new Intent(requireContext(), MainActivity.class);
                            startActivity(intent);
                            requireActivity().finish();
                        }
                    } else {
                        showSnackBar(response.getData().getErrors());
                    }
                }
            }
        });
    }


    private void showSnackBar(String msg) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    private void showLoading() {
        ((ConstraintLayout) mRootView.findViewById(R.id.profile_loading)).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        ((ConstraintLayout) mRootView.findViewById(R.id.profile_loading)).setVisibility(View.GONE);
    }

    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_first_name)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_last_name)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).setTypeface(CircularApplication.mTfMainRegular);
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

        for (int i = 0; i < assignedPreferenceItems.get(0).getDataPoints().size(); i++) {
            DataPointsItem dataPointsItem = assignedPreferenceItems.get(0).getDataPoints().get(i);
            if (llRow == null) {
                llRow = new LinearLayout(requireActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                llRow.setPadding(10, 5, 10, 5);
                llRow.setOrientation(LinearLayout.HORIZONTAL);
                llRow.setLayoutParams(lp);
                llRoot.addView(llRow);
            }
            RelativeLayout rlItem = (RelativeLayout) inflater.inflate(R.layout.report_data_field_item, null);
            rlItem.setTag(dataPointsItem);
            LinearLayout.LayoutParams lpItem = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            rlItem.setLayoutParams(lpItem);
            ((TextView) rlItem.findViewById(R.id.tv_report_data_field_item_title)).setText(dataPointsItem.getName());
            ((TextView) rlItem.findViewById(R.id.tv_report_data_field_item_title)).setTypeface(CircularApplication.mTfMainRegular);
            rlItem.findViewById(R.id.ll_report_data_field_item_content_root).setBackgroundResource(R.drawable.round_rect_blue_with_black_corner_normal);
            rlItem.findViewById(R.id.iv_report_data_field_item_remove).setVisibility(View.GONE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                try {

                    profileUri = data.getData();
                    Glide.with(requireContext())
                            .load(profileUri)
                            .placeholder(R.color.white_alpha)
                            .into((ImageView) mRootView.findViewById(R.id.iv_frag_update_profile_main_info_avatar));

                    File file1 = new File(getRealPathFromURI(profileUri));

                    multiPart = MultipartBody.Part.createFormData("profile_pic",
                            file1.getName(),
                            RequestBody.create(
                                    file1,
                                    MediaType.parse("*/*")
                            )
                    );

                } catch (NullPointerException | IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // viewModel..removeObservers(this);
        viewModel = null;
    }


    private String getRealPathFromURI(Uri uri) {
        Uri returnUri = uri;
        Cursor returnCursor = requireActivity().getContentResolver().query(returnUri, null, null, null, null);
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String name = (returnCursor.getString(nameIndex));
        String size = (Long.toString(returnCursor.getLong(sizeIndex)));
        File file = new File(requireActivity().getFilesDir(), name);
        try {
            InputStream inputStream = requireActivity().getContentResolver().openInputStream(uri);
            FileOutputStream outputStream = new FileOutputStream(file);
            int read = 0;
            int maxBufferSize = 1 * 1024 * 1024;
            int bytesAvailable = inputStream.available();

            //int bufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);

            final byte[] buffers = new byte[bufferSize];
            while ((read = inputStream.read(buffers)) != -1) {
                outputStream.write(buffers, 0, read);
            }
            Log.e("File Size", "Size " + file.length());
            inputStream.close();
            outputStream.close();
            Log.e("File Path", "Path " + file.getPath());
            Log.e("File Size", "Size " + file.length());
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
        return file.getPath();
    }
}
