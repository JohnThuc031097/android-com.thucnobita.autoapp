package com.thucnobita.api.instagram.requests.loom;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

public class LoomFetchConfigRequest extends IGGetRequest<IGResponse> {

    @Override
    public String path() {
        return "loom/fetch_config/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
