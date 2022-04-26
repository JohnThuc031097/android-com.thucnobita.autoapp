package com.thucnobita.autoapp.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

public class Login extends BaseObservable {
    public ObservableField<String> username, password;
    public ObservableField<Boolean> isChecked;

    public Login(String username, String password, boolean isChecked){
        this.username.set(username);
        this.password.set(password);
        this.isChecked.set(isChecked);
    }
}
