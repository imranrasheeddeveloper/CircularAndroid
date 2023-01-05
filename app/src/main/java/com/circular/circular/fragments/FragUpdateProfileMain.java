package com.circular.circular.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.circular.circular.CircularApplication;
import com.circular.circular.Constant;
import com.circular.circular.MainActivity;
import com.circular.circular.ProfileActivity;
import com.circular.circular.R;
import com.circular.circular.SignUpActivity;
import com.circular.circular.adapters.SpinnerTextViewAdapter;
import com.circular.circular.dialog.ConfirmDialogInterface;
import com.circular.circular.dialog.DialogConfirm;
import com.circular.circular.dialog.DialogMessageWithNoButtons;
import com.circular.circular.local.PreferenceRepository;
import com.circular.circular.local.TinyDbManager;
import com.circular.circular.model.ReportDataField;
import com.circular.circular.model.User;
import com.circular.circular.utils.GetRealPathFromUri;
import com.circular.circular.utils.Utils;
import com.circular.circular.view_model.ProfileViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class FragUpdateProfileMain extends Fragment {
    private View mRootView;
    private ProfileViewModel viewModel;
    MultipartBody.Part multiPart;
    PreferenceRepository repository;
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
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        repository = new PreferenceRepository();
        setProfileData();

        initData();
        initControls();
        return mRootView;
    }

    private void setProfileData() {
        try {

            if (TinyDbManager.getUserType().equalsIgnoreCase("App User")){
                mRootView.findViewById(R.id.tv_frag_update_profile_main_data_report_preference_edit).setVisibility(View.VISIBLE);
            }else {
                mRootView.findViewById(R.id.tv_frag_update_profile_main_data_report_preference_edit).setVisibility(View.GONE);
            }

        if (TinyDbManager.getUserInformation() != null){
            User user = TinyDbManager.getUserInformation();
            Glide.with(requireContext())
                    .load(Constant.IMG_PATH + user.getProfilePic())
                    .placeholder(R.color.white_alpha)
                    .into((ImageView) mRootView.findViewById(R.id.iv_frag_update_profile_main_info_avatar));

            String first_name  = user.getName().replaceAll("[^a-zA-Z0-9]","");
            String last_name  = user.getLastName().replaceAll("[^a-zA-Z0-9]","");
            String number  = user.getPhone().replaceAll("[^a-zA-Z0-9]","");

            ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_first_name)).setText(first_name);
            ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_last_name)).setText(last_name);
            ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).setText(user.getEmail());
            ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_phone)).setText(number);

        }

        }catch (NullPointerException | IllegalStateException | NumberFormatException e){
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

            if (((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_first_name)).getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_first_name), Toast.LENGTH_LONG).show();
                ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_first_name)).requestFocus();
                return;
            }
            if (((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_last_name)).getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_last_name), Toast.LENGTH_LONG).show();
                ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_last_name)).requestFocus();
                return;
            }
            if (((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_email), Toast.LENGTH_LONG).show();
                ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).requestFocus();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).getText().toString()).matches()){
                Toast.makeText(getActivity(), getString(R.string.enter_valid_email), Toast.LENGTH_LONG).show();
                ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).requestFocus();
                return;
            }
            if (((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_phone)).getText().toString().isEmpty()){
                Toast.makeText(getActivity(), getString(R.string.enter_phone), Toast.LENGTH_LONG).show();
                ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_phone)).requestFocus();
                return;
            }

            if (multiPart == null){
                return;
            }

            updateUserData();


        });

        mRootView.findViewById(R.id.iv_frag_update_profile_main_info_avatar).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
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

    private void updateUserData() {
        String first_name = ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_first_name)).getText().toString().trim();
        String last_name = ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_last_name)).getText().toString().trim();
        String phone = ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_phone)).getText().toString().trim();
        String email = ((TextView)mRootView.findViewById(R.id.tv_frag_update_profile_main_info_email)).getText().toString().trim();
        String token = repository.getString("token");
        viewModel.updateUser(token ,first_name,last_name,email,phone,multiPart);
        viewModel._update_user.observe(getViewLifecycleOwner(), response -> {
            if (response != null){
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    if (response.getError().isEmpty() || response.getError() == null){
                        showSnackBar("Something went wrong!!");
                    }else {
                        showSnackBar(response.getError());
                    }
                } else if (response.getData().isStatus()) {
                    hideLoading();
                    TinyDbManager.saveUserData(response.getData().getData().getUser());
                    showDialogue(response.getData().getMessage());
                }
            }
        });
    }

    private void showDialogue(String message) {
        DialogConfirm dlg = new DialogConfirm(requireActivity(),
                R.layout.dlg_confirm_proceed,
                R.id.tv_dlg_confirm_proceed_yes,
                R.id.tv_dlg_confirm_proceed_no,
                R.id.tv_dlg_confirm_proceed_msg,
                getString(R.string.confirm_proceed_msg),
                new ConfirmDialogInterface() {
                    @Override
                    public void onClickedConfirm() {
                        Intent intent = new Intent(requireContext(), MainActivity.class);
                        startActivity(intent);
                        requireActivity().finish();
//                        showSnackBar(message);
                    }

                    @Override
                    public void onClickedNo() {

                    }
                });
        dlg.show();
        Utils.setDialogWidth(dlg, 0.8f, requireActivity());
    }

    private void showSnackBar(String msg) {
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show();
    }

    private void showLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.profile_loading)).setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        ((ConstraintLayout)mRootView.findViewById(R.id.profile_loading)).setVisibility(View.GONE);
    }
    private void initFonts() {
        ((TextView) mRootView.findViewById(R.id.tv_frag_update_profile_main_title)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_first_name)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
        ((EditText) mRootView.findViewById(R.id.tv_frag_update_profile_main_info_last_name)).setTypeface(CircularApplication.mTfMainBold, Typeface.BOLD);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                try {

                    Uri uri = data.getData();
                Glide.with(requireContext())
                        .load(uri)
                        .placeholder(R.color.white_alpha)
                        .into((ImageView) mRootView.findViewById(R.id.iv_frag_update_profile_main_info_avatar));

                File file1 = new File(getRealPathFromURI(uri));

                 multiPart = MultipartBody.Part.createFormData("profile_pic",
                        file1.getName(),
                        RequestBody.create(
                                file1,
                                MediaType.parse("*/*")
                        )
                );

                }catch (NullPointerException | IllegalStateException e){
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
