package com.thucnobita.api.instagram.requests.live;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LiveBroadcastGetPostLiveRequest extends IGGetRequest<IGResponse> {
    @NonNull
    private String _broadcast_string;

    @Override
    public String path() {
        return "live/" + _broadcast_string + "/get_post_live/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
