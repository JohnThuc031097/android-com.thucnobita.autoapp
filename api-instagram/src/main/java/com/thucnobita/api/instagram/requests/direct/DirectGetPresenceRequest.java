package com.thucnobita.api.instagram.requests.direct;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.direct.DirectGetPresenceResponse;

public class DirectGetPresenceRequest extends IGGetRequest<DirectGetPresenceResponse> {

    @Override
    public String path() {
        return "direct_v2/get_presence/";
    }

    @Override
    public Class<DirectGetPresenceResponse> getResponseType() {
        return DirectGetPresenceResponse.class;
    }

}
