package com.thucnobita.api.instagram.requests.music;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.music.MusicBrowseResponse;

import lombok.Getter;

public class MusicBrowseRequest extends IGPostRequest<MusicBrowseResponse> {

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new IGPayload() {
            @Getter
            private String session_id = client.getSessionId();
        };
    }

    @Override
    public String path() {
        return "music/browse/";
    }

    @Override
    public Class<MusicBrowseResponse> getResponseType() {
        return MusicBrowseResponse.class;
    }

}
