package com.thucnobita.autoapp.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.thucnobita.autoapp.BR;


public class Account extends BaseObservable {
    private String username, password;
    private boolean isActived;
    private String header;
    private String content;
    private String footer;

    public Account(String username, boolean isActived, String header, String content, String footer) {
        this.username = username;
        this.isActived = isActived;
        this.header = header;
        this.content = content;
        this.footer = footer;
    }

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public boolean isActived() {
        return isActived;
    }

    public void setActived(boolean actived) {
        isActived = actived;
        notifyPropertyChanged(BR.actived);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.actived);
    }

    @Bindable
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
        notifyPropertyChanged(BR.header);
    }

    @Bindable
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    @Bindable
    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyPropertyChanged(BR.footer);
    }
}
