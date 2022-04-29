package com.thucnobita.autoapp.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import java.util.ArrayList;

public class Account extends BaseObservable {
    public ObservableField<String> username, password;
    public ObservableField<Boolean> isActived;
    public ObservableField<ArrayList<String>> header;
    public ObservableField<ArrayList<String>> content;
    public ObservableField<ArrayList<String>> footer;

    public Account(String username,
                   String password,
                   boolean isActived,
                   ArrayList<String> header,
                   ArrayList<String> content,
                   ArrayList<String> footer) {
        this.username.set(username);
        this.password.set(password);
        this.isActived.set(isActived);
        this.header.set(header);
        this.content.set(content);
        this.footer.set(footer);
    }
}
