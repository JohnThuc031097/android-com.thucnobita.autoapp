package com.thucnobita.api.instagram.requests.status;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

public class StatusGetViewableStatusesRequest extends IGGetRequest<IGResponse> {

    @Override
    public String path() {
        return "status/get_viewable_statuses/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
