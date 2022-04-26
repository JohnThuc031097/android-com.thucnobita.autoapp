package com.thucnobita.autoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.databinding.ActivityLoginBinding;
import com.thucnobita.autoapp.models.Login;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding activityLoginBinding;
    private Login loginModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginModel = new Login("", "", false);
        activityLoginBinding.setLogin(loginModel);
        init();
    }

    private void init(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            loginModel.username.set(bundle.getString("username"));
            loginModel.password.set(bundle.getString("password"));
            loginModel.isChecked.set(bundle.getBoolean("isChecked"));
        }
    }
}