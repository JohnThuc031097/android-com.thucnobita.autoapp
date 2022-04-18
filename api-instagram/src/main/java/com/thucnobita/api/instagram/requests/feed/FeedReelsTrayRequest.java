package com.thucnobita.api.instagram.requests.feed;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.feed.FeedReelsTrayResponse;

public class FeedReelsTrayRequest extends IGGetRequest<FeedReelsTrayResponse> {

    @Override
    public String path() {
        return "feed/reels_tray/";
    }

    @Override
    public Class<FeedReelsTrayResponse> getResponseType() {
        return FeedReelsTrayResponse.class;
    }

}
