package com.thucnobita.instaapi.response;

import com.google.gson.annotations.SerializedName;
import com.thucnobita.instaapi.model.story.Story;

public class IGStoryUpdateResponse extends BaseResponse {

    @SerializedName("updated_media")
    private Story story;

    public Story getStory() {
        return story;
    }

    public void setStory(Story story) {
        this.story = story;
    }
}
