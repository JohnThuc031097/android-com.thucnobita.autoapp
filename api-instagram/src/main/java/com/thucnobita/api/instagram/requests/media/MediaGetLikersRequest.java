package com.thucnobita.api.instagram.requests.media;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.feed.FeedUsersResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MediaGetLikersRequest extends IGGetRequest<FeedUsersResponse> {
    @NonNull
    private String _id;

    @Override
    public String path() {
        return "media/" + _id + "/likers/";
    }

    @Override
    public Class<FeedUsersResponse> getResponseType() {
        return FeedUsersResponse.class;
    }
}
