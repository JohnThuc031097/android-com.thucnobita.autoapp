package com.thucnobita.api.instagram.requests.friendships;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.requests.IGPaginatedRequest;
import com.thucnobita.api.instagram.responses.feed.FeedUsersResponse;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
public class FriendshipsFeedsRequest extends IGGetRequest<FeedUsersResponse>
        implements IGPaginatedRequest {
    @NonNull
    private Long _id;
    @NonNull
    private FriendshipsFeeds action;
    @Setter
    private String max_id;

    @Override
    public String getQueryString(IGClient client) {
        return mapQueryString("max_id", max_id);
    }

    @Override
    public String path() {
        return String.format("friendships/%s/%s/", _id, action.name().toLowerCase());
    }

    @Override
    public Class<FeedUsersResponse> getResponseType() {
        return FeedUsersResponse.class;
    }

    public static enum FriendshipsFeeds {
        FOLLOWERS, FOLLOWING;
    }
}
