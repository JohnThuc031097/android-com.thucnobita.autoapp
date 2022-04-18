package com.thucnobita.api.instagram.requests.live;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.live.LiveBroadcastGetViewerListResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LiveBroadcastGetViewerListRequest
        extends IGGetRequest<LiveBroadcastGetViewerListResponse> {
    @NonNull
    private String broadcast_id;

    @Override
    public String path() {
        return "live/" + broadcast_id + "/get_viewer_list/";
    }

    @Override
    public Class<LiveBroadcastGetViewerListResponse> getResponseType() {
        return LiveBroadcastGetViewerListResponse.class;
    }

}
