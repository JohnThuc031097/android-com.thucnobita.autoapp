package com.thucnobita.api.instagram.requests.direct;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.direct.DirectThreadsResponse;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class DirectThreadsRequest extends IGGetRequest<DirectThreadsResponse> {
    @NonNull
    private String thread_id;
    private String cursor;

    @Override
    public String path() {
        return "direct_v2/threads/" + thread_id + "/";
    }

    @Override
    public String getQueryString(IGClient client) {
        return mapQueryString("cursor", cursor);
    }

    @Override
    public Class<DirectThreadsResponse> getResponseType() {
        return DirectThreadsResponse.class;
    }

}
