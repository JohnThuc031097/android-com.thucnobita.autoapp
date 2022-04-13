package com.thucnobita.instaapi.model.story;

import com.google.gson.annotations.SerializedName;
import com.thucnobita.instaapi.model.user.TinyUser;

public class ReelMention extends BaseStoryItem{

    @SerializedName("display_type")
    private String displayType;
    @SerializedName("user")
    private TinyUser user;

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public TinyUser getUser() {
        return user;
    }

    public void setUser(TinyUser user) {
        this.user = user;
    }
}