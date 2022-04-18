package com.thucnobita.api.instagram.requests.live;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.live.LiveCreateResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LiveCreateRequest extends IGPostRequest<LiveCreateResponse> {
    private int width = 720, height = 1280;
    private String broadcastMessage = "test", broadcastType = "RTMP";

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new LiveCreatePayload(width, height, broadcastMessage, broadcastType);
    }

    @Override
    public String path() {
        return "live/create/";
    }

    @Override
    public Class<LiveCreateResponse> getResponseType() {
        return LiveCreateResponse.class;
    }

    @Data
    @AllArgsConstructor
    public static class LiveCreatePayload extends IGPayload {
        private int preview_width;
        private int preview_height;
        private String broadcast_message;
        private String broadcast_type;
    }

}
