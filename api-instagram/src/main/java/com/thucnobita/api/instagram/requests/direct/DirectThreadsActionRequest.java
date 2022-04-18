package com.thucnobita.api.instagram.requests.direct;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DirectThreadsActionRequest extends IGPostRequest<IGResponse> {
    @NonNull
    private String thread_id;
    @NonNull
    private DirectThreadsAction action;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new IGPayload();
    }

    @Override
    public String path() {
        return String.format("direct_v2/threads/%s/%s/", thread_id, action.name().toLowerCase());
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

    public static enum DirectThreadsAction {
        HIDE, MUTE, UNMUTE, LEAVE;
    }

}
