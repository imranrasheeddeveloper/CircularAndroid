package com.circular.circular;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.circular.circular.fragments.FragUpdateProfileMain;
import com.circular.circular.fragments.FragUpdateProfilePreferences;

public class ProfileActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        findViewById(R.id.iv_act_update_profile_back_menu).setOnClickListener(view-> onBackPressed());
        if (getIntent() != null && getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            if (bundle.containsKey(Constant.PROFILE_ACTIVITY_STARTUP_FRAG) &&
                    bundle.getString(Constant.PROFILE_ACTIVITY_STARTUP_FRAG).equals("report_data_field_setting")){
                FragUpdateProfilePreferences frag = new FragUpdateProfilePreferences();
                Bundle paramsToFragPreferences = new Bundle();
                paramsToFragPreferences.putInt(Constant.UPDATE_PROFILE_STARTUP_ACTION, Constant.UPDATE_PROFILE_STARTUP_ACTION_REPORT_DATA);
                frag.setArguments(paramsToFragPreferences);
                initFragment(R.id.fl_act_update_profile_container, frag, false);
            }else{
                initFragment(R.id.fl_act_update_profile_container, new FragUpdateProfileMain(), false);
            }
        }else{
            initFragment(R.id.fl_act_update_profile_container, new FragUpdateProfileMain(), false);
        }
    }

    public void initFragment(int id, Fragment frag, boolean bAnimation){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++){
            getSupportFragmentManager().popBackStack();
        }
        if (bAnimation){
            ft.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left,
                    R.anim.anim_enter_from_left, R.anim.anim_exit_to_right);
        }
        ft.replace(id, frag);
        ft.commit();
    }

    public void addFragment(int id, Fragment frag, boolean bAdd){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left,
                R.anim.anim_enter_from_left, R.anim.anim_exit_to_right);
        ft.replace(id, frag);
        if (bAdd){
            ft.addToBackStack(frag.getTag());
        }
        ft.commit();
    }
}