package com.thucnobita.api.instagram.requests.direct;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.direct.DirectThreadsResponse;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DirectThreadsUpdateTitleRequest extends IGPostRequest<DirectThreadsResponse> {
    @NonNull
    private String thread_id;
    @NonNull
    private String _title;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new IGPayload() {
            @Getter
            private String title = _title;
        };
    }

    @Override
    public String path() {
        return "direct_v2/threads/" + thread_id + "/update_title/";
    }

    @Override
    public Class<DirectThreadsResponse> getResponseType() {
        return DirectThreadsResponse.class;
    }

}
