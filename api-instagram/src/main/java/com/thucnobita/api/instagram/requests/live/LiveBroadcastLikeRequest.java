package com.thucnobita.api.instagram.requests.live;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.live.LiveBroadcastLikeResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class LiveBroadcastLikeRequest extends IGPostRequest<LiveBroadcastLikeResponse> {
    @NonNull
    private String broadcast_id;
    private int count = 1;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new IGPayload() {
            @Getter
            private int user_like_count = count;
        };
    }

    @Override
    public String path() {
        return "live/" + broadcast_id + "/like/";
    }

    @Override
    public Class<LiveBroadcastLikeResponse> getResponseType() {
        return LiveBroadcastLikeResponse.class;
    }

}
