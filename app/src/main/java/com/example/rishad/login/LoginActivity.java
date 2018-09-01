package com.example.rishad.login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.example.rishad.login.Utils.Login_Fragment;

public class LoginActivity extends AppCompatActivity {
    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fragmentManager = getSupportFragmentManager();

        //if savedInstanceSate is null then replace login fragment
        if(savedInstanceState == null) {
            fragmentManager.beginTransaction().replace(R.id.FrameContainer, new login_fragment(), Utils.Login_Fragment).commit();
        }


    }
    //Replace Login_Fragment with animation
    protected void replaceLoginFragment() {
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.left_enter, R.anim.right_exit).replace(R.id.FrameContainer, new login_fragment(), Utils.Login_Fragment).commit();

    }
    @Override
    public void onBackPressed() {
        //Find the tag SignUp and ForgotPassword_Fragment
        Fragment SignUp_Fragment = fragmentManager.findFragmentByTag(Utils.SignUp_Fragment);
        Fragment ForgotPassword_Fragment = fragmentManager.findFragmentByTag(Utils.ForgotPassword_Fragment);

        //Check if both are null or not
        // If both are not null then replace login fragment else do backpressed task
        if (SignUp_Fragment != null)
            replaceLoginFragment();
        else if (ForgotPassword_Fragment != null)
            replaceLoginFragment();
        else
            super.onBackPressed();
    }
}
