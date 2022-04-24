package com.thucnobita.api.instagram.requests.media;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.media.MediaInfoResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MediaInfoRequest extends IGGetRequest<MediaInfoResponse> {
    @NonNull
    private String id;

    @Override
    public String path() {
        return "media/" + id + "/info/";
    }

    @Override
    public Class<MediaInfoResponse> getResponseType() {
        return MediaInfoResponse.class;
    }

}
