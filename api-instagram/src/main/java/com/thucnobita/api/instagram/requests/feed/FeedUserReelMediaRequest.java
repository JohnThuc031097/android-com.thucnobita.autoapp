package com.thucnobita.api.instagram.requests.feed;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.feed.FeedUserReelsMediaResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeedUserReelMediaRequest extends IGGetRequest<FeedUserReelsMediaResponse> {
    @NonNull
    private Long pk;

    @Override
    public String path() {
        return "feed/user/" + pk.toString() + "/reel_media/";
    }

    @Override
    public Class<FeedUserReelsMediaResponse> getResponseType() {
        return FeedUserReelsMediaResponse.class;
    }

}
