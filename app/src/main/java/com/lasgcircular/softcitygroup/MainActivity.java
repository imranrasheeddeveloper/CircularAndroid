package com.lasgcircular.softcitygroup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.circular.circular.R;
import com.lasgcircular.softcitygroup.dialog.ConfirmDialogInterface;
import com.lasgcircular.softcitygroup.dialog.DialogConfirm;
import com.lasgcircular.softcitygroup.fragments.FragAbout;
import com.lasgcircular.softcitygroup.fragments.FragDashboard;
import com.lasgcircular.softcitygroup.fragments.FragHistory;
import com.lasgcircular.softcitygroup.fragments.FragNotifications;
import com.lasgcircular.softcitygroup.fragments.FragReportDataMain;
import com.lasgcircular.softcitygroup.local.PreferenceRepository;
import com.lasgcircular.softcitygroup.local.TinyDbManager;
import com.lasgcircular.softcitygroup.model.ReportDataField;
import com.lasgcircular.softcitygroup.utils.Utils;
import com.lasgcircular.softcitygroup.view_model.DataPointsViewModel;
import com.lasgcircular.softcitygroup.view_model.NotificationsViewModel;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawerLayout;
    private int[] m_ArrIvInActiveFooterIds = new int[]{
            R.id.iv_main_footer_dashboard_inactive,
            R.id.iv_main_footer_about_inactive,
            R.id.iv_main_footer_report_data_inactive,
            R.id.iv_main_footer_history_inactive,
            R.id.iv_main_footer_notification_inactive
    };

    private DataPointsViewModel viewModel;
    PreferenceRepository repository;
    private int unReadCount = 0;
    TextView unReadNotification;


    private int[] m_ArrIvActiveFooterIds = new int[]{
            R.id.iv_main_footer_dashboard_active,
            R.id.iv_main_footer_about_active,
            R.id.iv_main_footer_report_data_active,
            R.id.iv_main_footer_history_active,
            R.id.iv_main_footer_notification_active
    };
    private Animation mAnimFadeIn;
    private Animation mAnimFadeOut;
    private int m_nMainFooterCurActiveIndex;
    private View mIvMainShowMenu;
    private View mIvMainBack;
    private NotificationsViewModel notificationsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_nMainFooterCurActiveIndex = -1;
        repository = new PreferenceRepository();
        viewModel = new ViewModelProvider(this).get(DataPointsViewModel.class);
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        setProfileImage();
        initAnimation();
        initControls();
        showOrHideShowMenu(true);

    }

    private void setProfileImage() {
        try {

            if (TinyDbManager.getUserInformation() != null){
                Glide.with(MainActivity.this)
                        .load(Constant.IMG_PATH + TinyDbManager.getUserInformation().getProfilePic())
                        .placeholder(R.color.white_alpha)
                        .into((ImageView) findViewById(R.id.iv_main_avatar));
            }

        }catch (NullPointerException | IllegalStateException e){
            e.printStackTrace();
        }
    }

    private void initAnimation() {
        mAnimFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        mAnimFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
    }

    public void showOrHideShowMenu(boolean bShow){
        if (bShow){
            mIvMainShowMenu.setVisibility(View.VISIBLE);
            mIvMainBack.setVisibility(View.GONE);
        }else{
            mIvMainShowMenu.setVisibility(View.GONE);
            mIvMainBack.setVisibility(View.VISIBLE);
        }
    }

    private void initControls() {
        unReadNotification = (TextView) findViewById(R.id.unread_notification);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mIvMainBack = findViewById(R.id.iv_main_back);
        mIvMainShowMenu = findViewById(R.id.iv_main_open_menu);
        mIvMainShowMenu.setOnClickListener(view -> {
            openDrawer();
        });
        mIvMainBack.setOnClickListener(view->{
            onBackPressed();
        });
        findViewById(R.id.iv_main_avatar).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        String token = repository.getString("token");
        notificationsViewModel.getNotification(token);
        notificationsViewModel._notification.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                } else if (response.getError() != null) {
                    if (response.getError() == null){
                    }else {
                        Constant.getLoginError(CircularApplication.applicationContext,response.getError());
                    }
                } else if (response.getData() != null) {
                    if (response.getData().getErrors() == null) {
                        if (response.getData().getData() != null) {
                            if (response.getData().getData().getNotifications().size() > 0) {
                                for (int i = 0; i < response.getData().getData().getNotifications().size(); i++) {
                                    if (!response.getData().getData().getNotifications().get(i).isIsRead()){
                                        unReadCount = unReadCount + 1;
                                    }
                                }
                                if (unReadCount > 0){
                                    unReadNotification.setVisibility(View.VISIBLE);
                                    unReadNotification.setText(String.valueOf(unReadCount));
                                }else {
                                    unReadNotification.setVisibility(View.GONE);
                                }
                            }
                        }
                    }else {
                    }
                }
            }
        });

        initMainFooter();
        handleFooterMenu(0);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                handleFooterMenu(0);
            } else if (id == R.id.nav_about) {
                handleFooterMenu(1);
            } else if (id == R.id.nav_report_green_data) {
                handleFooterMenu(2);
            } else if (id == R.id.nav_data_history) {
                handleFooterMenu(3);
            } else if (id == R.id.nav_notifications) {
                handleFooterMenu(4);
            } else if (id == R.id.nav_toc) {
                Intent intent = new Intent(MainActivity.this, TocActivity.class);
                startActivity(intent);
            } else if (id == R.id.nav_logout) {
                DialogConfirm dlg = new DialogConfirm(MainActivity.this,
                        R.layout.dlg_confirm_proceed,
                        R.id.tv_dlg_confirm_proceed_yes,
                        R.id.tv_dlg_confirm_proceed_no,
                        R.id.tv_dlg_confirm_proceed_msg,
                        getString(R.string.confirm_proceed_msg),
                        new ConfirmDialogInterface() {
                            @Override
                            public void onClickedConfirm() {
                               repository.setString("token","nil");

                               TinyDbManager.saveRemainder(6);
                                TinyDbManager.clearUser();
                                clearSharedPreferances();
                                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            @Override
                            public void onClickedNo() {

                            }
                        });
                dlg.show();
                Utils.setDialogWidth(dlg, 0.8f, MainActivity.this);
            } else if (id == R.id.nav_profile_settings) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
            mDrawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
        initFonts();
    }

    private void clearSharedPreferances() {
        repository.setString(Constant.SH_KEY_SELECTED_INDUSTRY,"");
        repository.setString(Constant.SH_KEY_SELECTED_TEAM_SIZE,"");
        repository.setString(Constant.SH_KEY_SELECTED_BUDGET,"");
        repository.setString(Constant.SH_KEY_SELECTED_IMPACTS,"");

        SharedPreferences myPrefs = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        myPrefs.edit().remove(Constant.SH_KEY_SELECTED_INDUSTRY).apply();
        myPrefs.edit().remove(Constant.SH_KEY_SELECTED_TEAM_SIZE).apply();
        myPrefs.edit().remove(Constant.SH_KEY_SELECTED_BUDGET).apply();
        myPrefs.edit().remove(Constant.SH_KEY_SELECTED_IMPACTS).apply();
        myPrefs.edit().remove(Constant.SH_KEY_SELECTED_REPORT_DATA_FIELDS).apply();
        myPrefs.edit().remove(Constant.PROFILE_ACTIVITY_STARTUP_FRAG).apply();
        myPrefs.edit().remove(Constant.UPDATE_PROFILE_STARTUP_ACTION).apply();

    }

    private void initMainFooter() {
        findViewById(R.id.rl_main_footer_dashboard_parent).setOnClickListener(view -> {
            handleFooterMenu(0);
        });
        findViewById(R.id.rl_main_footer_about_parent).setOnClickListener(view -> {
            handleFooterMenu(1);
        });
        findViewById(R.id.rl_main_footer_report_data_parent).setOnClickListener(view -> {
            handleFooterMenu(2);
        });
        findViewById(R.id.rl_main_footer_history_parent).setOnClickListener(view -> {
            handleFooterMenu(3);
        });
        findViewById(R.id.rl_main_footer_notification_parent).setOnClickListener(view -> {
            handleFooterMenu(4);
        });
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private boolean checkIfEmptyReportDataFields() {
        ArrayList<ReportDataField> arrData = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String strJSON = sharedPreferences.getString(Constant.SH_KEY_SELECTED_REPORT_DATA_FIELDS, "[]");
        JSONArray arrJson = null;
        try {
            arrJson = new JSONArray(strJSON);
            for (int i = 0; i < arrJson.length(); i++) {
                arrData.add(new ReportDataField(arrJson.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrData.size() == 0;
    }

    public void handleFooterMenu(int iNewIndex) {
        if (m_nMainFooterCurActiveIndex == iNewIndex) return;
        if (iNewIndex == 0) {
            if (m_nMainFooterCurActiveIndex == -1) {
                initFragmentInMainActivity(R.id.fl_main_container, new FragDashboard(), iNewIndex, m_nMainFooterCurActiveIndex, false);
            } else {
                initFragmentInMainActivity(R.id.fl_main_container, new FragDashboard(), iNewIndex, m_nMainFooterCurActiveIndex, true);
            }
        } else if (iNewIndex == 1) {
            initFragmentInMainActivity(R.id.fl_main_container, new FragAbout(), iNewIndex, m_nMainFooterCurActiveIndex, true);
        } else if (iNewIndex == 2) {
            try {
                String token = repository.getString("token");
                viewModel.getPoints(token);
                viewModel._data_points.observe(this ,  response -> {
                    if (response != null){
                        if (response.isLoading()) {
                            // showLoading();
                        } else if (response.getError() != null) {
                            //hideLoading();
                            //showSnackBar(response.getError());
                        } else if (response.getData() != null) {
                            // hideLoading();
                            if (response.getData().getErrors() == null) {
                                if (response.getData().getData() != null) {
                                    if (response.getData().getData().getAssignedPreference() != null) {
                                        if (response.getData().getData().getAssignedPreference().size() > 0) {
                                            initFragmentInMainActivity(R.id.fl_main_container, new FragReportDataMain(), iNewIndex, m_nMainFooterCurActiveIndex, true);
                                        } else {
                                            switchClass();
                                        }
                                    } else {
                                        switchClass();
                                    }
                                }
                            }else {
                                Toast.makeText(this, response.getData().getErrors(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                });
            }catch (NullPointerException | IllegalStateException e){
                e.printStackTrace();
            }


//            if (checkIfEmptyReportDataFields()) {
//                DialogConfirm dlg = new DialogConfirm(this,
//                        R.layout.dlg_confirm_proceed,
//                        R.id.tv_dlg_confirm_proceed_yes,
//                        R.id.tv_dlg_confirm_proceed_no,
//                        R.id.tv_dlg_confirm_proceed_msg,
//                        getString(R.string.no_report_data_field_set),
//                        new ConfirmDialogInterface() {
//                            @Override
//                            public void onClickedConfirm() {
//                                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
//                                intent.putExtra(Constant.PROFILE_ACTIVITY_STARTUP_FRAG, "report_data_field_setting");
//                                startActivity(intent);
//                                initFragmentInMainActivity(R.id.fl_main_container, new FragReportDataMain(), iNewIndex, m_nMainFooterCurActiveIndex, true);
//                            }
//
//                            @Override
//                            public void onClickedNo() {
//                                initFragmentInMainActivity(R.id.fl_main_container, new FragReportDataMain(), iNewIndex, m_nMainFooterCurActiveIndex, true);
//                            }
//                        });
//                dlg.show();
//                Utils.setDialogWidth(dlg, 0.8f, this);
//            } else {
//                initFragmentInMainActivity(R.id.fl_main_container, new FragReportDataMain(), iNewIndex, m_nMainFooterCurActiveIndex, true);
//            }
        } else if (iNewIndex == 3) {
            initFragmentInMainActivity(R.id.fl_main_container, new FragHistory(), iNewIndex, m_nMainFooterCurActiveIndex, true);
        } else if (iNewIndex == 4) {
            unReadNotification.setVisibility(View.GONE);
            initFragmentInMainActivity(R.id.fl_main_container, new FragNotifications(), iNewIndex, m_nMainFooterCurActiveIndex, true);
        }
        if (m_nMainFooterCurActiveIndex != -1) {
            findViewById(m_ArrIvActiveFooterIds[m_nMainFooterCurActiveIndex]).startAnimation(mAnimFadeOut);
            findViewById(m_ArrIvInActiveFooterIds[m_nMainFooterCurActiveIndex]).startAnimation(mAnimFadeIn);
            findViewById(m_ArrIvActiveFooterIds[m_nMainFooterCurActiveIndex]).setVisibility(View.GONE);
            findViewById(m_ArrIvInActiveFooterIds[m_nMainFooterCurActiveIndex]).setVisibility(View.VISIBLE);

        }
        findViewById(m_ArrIvActiveFooterIds[iNewIndex]).startAnimation(mAnimFadeIn);
        findViewById(m_ArrIvInActiveFooterIds[iNewIndex]).startAnimation(mAnimFadeOut);
        findViewById(m_ArrIvInActiveFooterIds[iNewIndex]).setVisibility(View.GONE);
        findViewById(m_ArrIvActiveFooterIds[iNewIndex]).setVisibility(View.VISIBLE);

        m_nMainFooterCurActiveIndex = iNewIndex;
    }

    private void switchClass() {
        DialogConfirm dlg = new DialogConfirm(this,
                R.layout.dlg_confirm_proceed,
                R.id.tv_dlg_confirm_proceed_yes,
                R.id.tv_dlg_confirm_proceed_no,
                R.id.tv_dlg_confirm_proceed_msg,
                getString(R.string.no_report_data_field_set),
                new ConfirmDialogInterface() {
                    @Override
                    public void onClickedConfirm() {
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.putExtra(Constant.PROFILE_ACTIVITY_STARTUP_FRAG, "report_data_field_setting");
                        startActivity(intent);
                        initFragmentInMainActivity(R.id.fl_main_container, new FragReportDataMain(), 2, m_nMainFooterCurActiveIndex, true);
                    }

                    @Override
                    public void onClickedNo() {
                        initFragmentInMainActivity(R.id.fl_main_container, new FragReportDataMain(), 2, m_nMainFooterCurActiveIndex, true);
                    }
                });
        dlg.show();
        Utils.setDialogWidth(dlg, 0.8f, this);
    }

    public void initFragment(int id, Fragment frag, boolean bAnimation) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }
        if (bAnimation) {
            ft.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left,
                    R.anim.anim_enter_from_left, R.anim.anim_exit_to_right);
        }
        ft.add(id, frag);
        ft.commit();
    }

    public void initFragmentInMainActivity(int id, Fragment frag, int iInIndex, int iOutIndex, boolean bAnimation) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
            getSupportFragmentManager().popBackStack();
        }
        if (bAnimation) {
            if (iInIndex > iOutIndex) {
                ft.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left);
            } else {
                ft.setCustomAnimations(R.anim.anim_enter_from_left, R.anim.anim_exit_to_right);
            }
        }
        ft.replace(id, frag);
        ft.commit();
    }

    public void addFragment(int id, Fragment frag, boolean bAdd) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left,
                R.anim.anim_enter_from_left, R.anim.anim_exit_to_right);
        ft.add(id, frag);
        if (bAdd) {
            ft.addToBackStack(frag.getTag());
        }
        ft.commit();
    }

    public void addFragmentFromBottom(int id, Fragment frag, boolean bAdd) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.anim_enter_from_bottom, R.anim.anim_exit_to_bottom,
                R.anim.anim_enter_from_bottom, R.anim.anim_exit_to_bottom);
        ft.replace(id, frag);
        if (bAdd) {
            ft.addToBackStack(frag.getTag());
        }
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_dashboard) {
            handleFooterMenu(0);
        } else if (id == R.id.nav_notifications) {
            handleFooterMenu(4);
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initFonts() {
        ((TextView) findViewById(R.id.tv_main_footer_dashboard)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) findViewById(R.id.tv_main_footer_about)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) findViewById(R.id.tv_main_footer_report_data)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) findViewById(R.id.tv_main_footer_history)).setTypeface(CircularApplication.mTfMainRegular);
        ((TextView) findViewById(R.id.tv_main_footer_notification)).setTypeface(CircularApplication.mTfMainRegular);
    }
}