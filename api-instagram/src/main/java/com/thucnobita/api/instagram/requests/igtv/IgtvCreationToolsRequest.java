package com.thucnobita.api.instagram.requests.igtv;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

public class IgtvCreationToolsRequest extends IGGetRequest<IGResponse> {

    @Override
    public String path() {
        return "igtv/igtv_creation_tools/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }
}
