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
    private String splitHeader;
    private String header;
    private String splitContent;
    private String content;
    private String splitFooter;
    private String footer;
    private String linkTShirt;

    @JsonCreator
    public Account(
            @JsonProperty("username") String username,
            @JsonProperty("password") String password,
            @JsonProperty("isActived") boolean isActived,
            @JsonProperty("splitHeader") String splitHeader,
            @JsonProperty("header") String header,
            @JsonProperty("splitContent") String splitContent,
            @JsonProperty("content") String content,
            @JsonProperty("splitFooter")String splitFooter,
            @JsonProperty("footer")String footer,
            @JsonProperty("linkTShirt")String linkTShirt)
    {
        super();
        this.username = username;
        this.password = password;
        this.isActived = isActived;
        this.splitHeader = splitHeader;
        this.header = header;
        this.splitContent = splitContent;
        this.content = content;
        this.splitFooter = splitFooter;
        this.footer = footer;
        this.linkTShirt = linkTShirt;
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
    public String getSplitHeader() {
        return splitHeader;
    }

    public void setSplitHeader(String splitHeader) {
        this.splitHeader = splitHeader;
        notifyPropertyChanged(BR.splitHeader);
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
    public String getSplitContent() {
        return splitContent;
    }

    public void setSplitContent(String splitContent) {
        this.splitContent = splitContent;
        notifyPropertyChanged(BR.splitContent);
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
    public String getSplitFooter() {
        return splitFooter;
    }

    public void setSplitFooter(String splitFooter) {
        this.splitFooter = splitFooter;
        notifyPropertyChanged(BR.splitFooter);
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
    public String getLinkTShirt() {
        return linkTShirt;
    }

    public void setLinkTShirt(String linkTShirt) {
        this.linkTShirt = linkTShirt;
        notifyPropertyChanged(BR.linkTShirt);
    }
}
