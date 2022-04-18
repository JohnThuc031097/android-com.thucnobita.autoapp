package com.thucnobita.api.instagram.requests.music;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.music.MusicGetResponse;

public class MusicGetGenresRequest extends IGPostRequest<MusicGetResponse> {

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new IGPayload();
    }

    @Override
    public String path() {
        return "music/genres/";
    }

    @Override
    public Class<MusicGetResponse> getResponseType() {
        return MusicGetResponse.class;
    }

}
