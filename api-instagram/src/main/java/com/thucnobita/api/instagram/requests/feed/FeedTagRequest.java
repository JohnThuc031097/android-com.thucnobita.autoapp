package com.thucnobita.api.instagram.requests.feed;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.requests.IGPaginatedRequest;
import com.thucnobita.api.instagram.responses.feed.FeedTagResponse;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
public class FeedTagRequest extends IGGetRequest<FeedTagResponse>
        implements IGPaginatedRequest {
    @NonNull
    private String tag;
    @Setter
    private String max_id;

    @Override
    public String path() {
        return "feed/tag/" + tag + "/";
    }

    @Override
    public String getQueryString(IGClient client) {
        return mapQueryString("max_id", max_id);
    }

    @Override
    public Class<FeedTagResponse> getResponseType() {
        return FeedTagResponse.class;
    }

}
