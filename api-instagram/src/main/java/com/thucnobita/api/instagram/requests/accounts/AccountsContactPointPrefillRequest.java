package com.thucnobita.api.instagram.requests.accounts;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.IGConstants;
import com.thucnobita.api.instagram.models.IGBaseModel;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

public class AccountsContactPointPrefillRequest extends IGPostRequest<IGResponse> {

    @Override
    public String baseApiUrl() {
        return IGConstants.B_BASE_API_URL;
    }

    @Override
    protected IGBaseModel getPayload(IGClient client) {
        return new IGPayload();
    }

    @Override
    public String path() {
        return "accounts/contact_point_prefill/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

    @Data
    private static class PrePayload extends IGBaseModel {
        private final String phone_id;
        private final String usage = "prefill";
    }

}
