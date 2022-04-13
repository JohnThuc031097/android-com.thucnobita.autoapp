package com.thucnobita.instaapi.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thucnobita.instaapi.model.direct.IGThread;

public class IGDirectChatResponse extends BaseResponse {

    @SerializedName("thread")
    @Expose
    private IGThread IGThread;

    public IGThread getIGThread() {
        return IGThread;
    }

    public void setIGThread(IGThread IGThread) {
        this.IGThread = IGThread;
    }
}
