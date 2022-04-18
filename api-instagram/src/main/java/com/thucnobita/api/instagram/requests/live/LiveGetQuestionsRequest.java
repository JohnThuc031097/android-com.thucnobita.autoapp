package com.thucnobita.api.instagram.requests.live;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

public class LiveGetQuestionsRequest extends IGGetRequest<IGResponse> {

    @Override
    public String path() {
        return "live/get_questions/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
