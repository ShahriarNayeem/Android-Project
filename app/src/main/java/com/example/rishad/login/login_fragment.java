package com.example.rishad.login;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.rishad.login.Utils.ForgotPassword_Fragment;
import static com.example.rishad.login.Utils.SignUp_Fragment;

public class login_fragment extends Fragment implements View.OnClickListener{
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;

    public login_fragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    //initiate views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.textview_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }
    //set listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                // If it is checked then show password else hide password
                if(isChecked) {
                    show_hide_password.setText(R.string.hide_pwd); //change checkbox text
                    password.setInputType(InputType.TYPE_CLASS_TEXT);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //show password
                } else {
                    show_hide_password.setText(R.string.show_pwd); //change checkbox text
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } }
        }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                checkValidation();
                break;
            case R.id.forgot_password:
                //replace forgot password fragment with animation
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_exit).replace(R.id.FrameContainer, new ForgotPassword_Fragment(), Utils.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:
                // Replace signup frgament with animation
                fragmentManager.beginTransaction().setCustomAnimations(R.anim.right_enter, R.anim.left_exit).replace(R.id.FrameContainer, new SignUp_Fragment(), Utils.SignUp_Fragment).commit();
                break;
        }
    }
    // Check Validation before Login
    private void checkValidation() {
        //Get Email Id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();

        //check pattern for email Id
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        //check for both fields are empty or not
        if(getEmailId.equals("") || getEmailId.length() == 0 || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view, "Enter both credentials.");
        }
        //check if email id is valid or not
        else if(!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view, "Your Email Id is invalid");
        }
        //else do login and do your stuff
        else {
            Toast.makeText(getActivity(), "Do Login", Toast.LENGTH_SHORT).show();
        }


    }

}
