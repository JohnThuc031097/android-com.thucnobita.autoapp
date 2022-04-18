package com.thucnobita.api.instagram.requests.media;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

public class MediaBlockedRequest extends IGGetRequest<IGResponse> {

    @Override
    public String path() {
        return "media/blocked/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
