package com.lasgcircular.softcitygroup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.circular.circular.R;
import com.lasgcircular.softcitygroup.fragments.FragTocMain;
import com.lasgcircular.softcitygroup.local.TinyDbManager;

public class TocActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_toc);

        setProfileImage();

        initFragment(R.id.fl_toc_container, new FragTocMain(), false);

        findViewById(R.id.iv_toc_back_menu).setOnClickListener(view->{
            onBackPressed();
        });

        findViewById(R.id.iv_toc_avatar).setOnClickListener(view->{
            Intent intent = new Intent(TocActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    private void setProfileImage() {
        try {

            if (TinyDbManager.getUserInformation() != null){
                Glide.with(TocActivity.this)
                        .load(Constant.IMG_PATH + TinyDbManager.getUserInformation().getProfilePic())
                        .placeholder(R.color.white_alpha)
                        .into((ImageView) findViewById(R.id.iv_toc_avatar));
            }

        }catch (NullPointerException | IllegalStateException e){
            e.printStackTrace();
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
        ft.add(id, frag);
        ft.commit();
    }

    public void initFragmentInMainActivity(int id, Fragment frag, int iInIndex, int iOutIndex, boolean bAnimation){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++){
            getSupportFragmentManager().popBackStack();
        }
        if (bAnimation){
            if (iInIndex > iOutIndex) {
                ft.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left);
            }else{
                ft.setCustomAnimations(R.anim.anim_enter_from_left, R.anim.anim_exit_to_right);
            }
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
    public void addFragmentFromBottom(int id, Fragment frag, boolean bAdd){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.anim_enter_from_bottom, R.anim.anim_exit_to_bottom,
                R.anim.anim_enter_from_bottom, R.anim.anim_exit_to_bottom);
        ft.replace(id, frag);
        if (bAdd){
            ft.addToBackStack(frag.getTag());
        }
        ft.commit();
    }
}