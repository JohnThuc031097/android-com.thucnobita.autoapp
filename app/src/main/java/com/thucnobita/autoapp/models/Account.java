package com.thucnobita.autoapp.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.thucnobita.autoapp.BR;


public class Account extends BaseObservable {
    private String username;
    private boolean isActived;

    public Account(String username, boolean isActived) {
        this.username = username;
        this.isActived = isActived;
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
}
