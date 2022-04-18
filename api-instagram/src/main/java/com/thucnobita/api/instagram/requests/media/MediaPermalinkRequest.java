package com.thucnobita.api.instagram.requests.media;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.media.MediaPermalinkResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MediaPermalinkRequest extends IGGetRequest<MediaPermalinkResponse> {
    @NonNull
    private String media_id;
    
    @Override
    public String path() {
        return "media/" + media_id + "/permalink/";
    }

    @Override
    public Class<MediaPermalinkResponse> getResponseType() {
        return MediaPermalinkResponse.class;
    }

}
