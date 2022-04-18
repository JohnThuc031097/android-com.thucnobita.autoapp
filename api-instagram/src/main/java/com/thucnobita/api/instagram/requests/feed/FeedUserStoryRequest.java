package com.thucnobita.api.instagram.requests.feed;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.feed.FeedUserStoryResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeedUserStoryRequest extends IGGetRequest<FeedUserStoryResponse> {
    @NonNull
    private Long pk;

    @Override
    public String path() {
        return "feed/user/" + pk.toString() + "/story/";
    }

    @Override
    public Class<FeedUserStoryResponse> getResponseType() {
        return FeedUserStoryResponse.class;
    }

}
