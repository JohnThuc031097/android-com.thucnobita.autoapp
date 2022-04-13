package com.thucnobita.instaapi.model.story;

import com.google.gson.annotations.SerializedName;

public class StoryHashtag extends BaseStoryItem {

    @SerializedName("hashtag")
    private Hashtag hashtag;

    public Hashtag getHashtag() {
        return hashtag;
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }
}
