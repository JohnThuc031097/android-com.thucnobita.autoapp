package com.thucnobita.api.instagram.requests.live;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.live.LiveBroadcastThumbnailsResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LiveBroadcastGetPostLiveThumbnailsRequest extends IGGetRequest<LiveBroadcastThumbnailsResponse> {
    @NonNull
    private String _broadcast_id;

    @Override
    public String path() {
        return "live/" + _broadcast_id + "/get_post_live_thumbnails/";
    }

    @Override
    public Class<LiveBroadcastThumbnailsResponse> getResponseType() {
        return LiveBroadcastThumbnailsResponse.class;
    }

}
