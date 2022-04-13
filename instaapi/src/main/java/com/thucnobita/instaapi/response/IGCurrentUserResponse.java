package com.thucnobita.instaapi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thucnobita.instaapi.model.user.CurrentUser;

public class IGCurrentUserResponse extends BaseResponse {

    @SerializedName("user")
    @Expose
    private CurrentUser user;

    public CurrentUser getUser() {
        return user;
    }

    public void setUser(CurrentUser user) {
        this.user = user;
    }
}
