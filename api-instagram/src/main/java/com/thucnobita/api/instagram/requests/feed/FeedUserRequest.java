package com.thucnobita.api.instagram.requests.feed;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.requests.IGPaginatedRequest;
import com.thucnobita.api.instagram.responses.feed.FeedUserResponse;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
public class FeedUserRequest extends IGGetRequest<FeedUserResponse>
        implements IGPaginatedRequest {
    @NonNull
    private Long pk;
    @Setter
    private String max_id;

    @Override
    public String path() {
        return "feed/user/" + pk + "/";
    }

    @Override
    public String getQueryString(IGClient client) {
        return this.mapQueryString("max_id", max_id);
    }

    @Override
    public Class<FeedUserResponse> getResponseType() {
        return FeedUserResponse.class;
    }

}
