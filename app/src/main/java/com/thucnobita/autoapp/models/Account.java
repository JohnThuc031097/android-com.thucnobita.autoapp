package com.thucnobita.autoapp.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thucnobita.autoapp.BR;

public class Account extends BaseObservable {
    private String username;
    private String password;
    private boolean isActived;
    private String header;
    private String content1;
    private String content2;
    private String content3;
    private String footer;
    private String link;

    @JsonCreator
    public Account(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("isActived") boolean isActived,
            @JsonProperty("header") String header,
            @JsonProperty("content1") String content1,
            @JsonProperty("content2") String content2,
            @JsonProperty("content3") String content3,
            @JsonProperty("footer")String footer,
            @JsonProperty("link")String link
    )
    {
        super();
        this.username = username;
        this.password = password;
        this.isActived = isActived;
        this.header = header;
        this.content1 = content1;
        this.content2 = content2;
        this.content3 = content3;
        this.footer = footer;
        this.link = link;
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
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
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
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
        notifyPropertyChanged(BR.header);
    }

    @Bindable
    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
        notifyPropertyChanged(BR.content1);
    }

    @Bindable
    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
        notifyPropertyChanged(BR.content2);
    }

    @Bindable
    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
        notifyPropertyChanged(BR.content3);
    }

    @Bindable
    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
        notifyPropertyChanged(BR.footer);
    }


    @Bindable
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
        notifyPropertyChanged(BR.link);
    }

}
