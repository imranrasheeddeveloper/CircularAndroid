package com.circular.circular;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.circular.circular.fragments.FragSignIn;
import com.circular.circular.fragments.FragSignUp;

public class SignUpActivity extends AppCompatActivity {
    public static SignUpActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup);
        instance = this;
        initFragment(R.id.fl_sign_container, new FragSignIn(), false);
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

    public void addFragment(int id, Fragment frag, boolean bAdd){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.anim_enter_from_right, R.anim.anim_exit_to_left,
                R.anim.anim_enter_from_left, R.anim.anim_exit_to_right);
        ft.add(id, frag);
        if (bAdd){
            ft.addToBackStack(frag.getTag());
        }
        ft.commit();
    }
}