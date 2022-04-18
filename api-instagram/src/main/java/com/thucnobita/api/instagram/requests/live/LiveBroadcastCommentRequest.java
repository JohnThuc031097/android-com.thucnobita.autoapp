package com.thucnobita.api.instagram.requests.live;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.live.LiveBroadcastCommentResponse;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LiveBroadcastCommentRequest extends IGPostRequest<LiveBroadcastCommentResponse> {
    @NonNull
    private String broadcast_id, _message;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new LiveCommentPayload();
    }

    @Override
    public String path() {
        return "live/" + broadcast_id + "/comment/";
    }

    @Override
    public Class<LiveBroadcastCommentResponse> getResponseType() {
        return LiveBroadcastCommentResponse.class;
    }

    @Data
    public class LiveCommentPayload extends IGPayload {
        private String comment_text = _message;
    }

}
