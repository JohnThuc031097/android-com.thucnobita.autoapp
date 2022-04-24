package com.thucnobita.api.instagram.requests.qp;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;
import com.thucnobita.api.instagram.utils.IGUtils;

public class QpGetCooldowns extends IGGetRequest<IGResponse> {

    @Override
    public String path() {
        return "qp/get_cooldowns/";
    }

    @Override
    public String getQueryString(IGClient client) {
        return mapQueryString("signature", IGUtils.generateSignature("{}"));
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
