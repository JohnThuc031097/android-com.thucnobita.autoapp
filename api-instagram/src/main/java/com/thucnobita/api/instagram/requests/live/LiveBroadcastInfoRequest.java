package com.thucnobita.api.instagram.requests.live;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LiveBroadcastInfoRequest extends IGGetRequest<IGResponse> {
    @NonNull
    private String _broadcast_id;

    @Override
    public String path() {
        return "live/" + _broadcast_id + "/info/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
