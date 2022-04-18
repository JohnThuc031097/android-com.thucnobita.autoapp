package com.thucnobita.api.instagram.requests.accounts;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.IGConstants;
import com.thucnobita.api.instagram.models.IGBaseModel;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

public class AccountsGetPrefillCandidatesRequest extends IGPostRequest<IGResponse> {

    @Override
    public String baseApiUrl() {
        return IGConstants.B_BASE_API_URL;
    }

    @Override
    protected IGBaseModel getPayload(IGClient client) {
        return new PrePayload(client.getDeviceId(), client.getPhoneId(), client.getGuid());
    }

    @Override
    public String path() {
        return "accounts/get_prefill_candidates/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

    @Data
    private class PrePayload extends IGBaseModel {
        private final String android_device_id;
        private final String phone_id;
        private final String device_id;
        private final String usages = "[\"account_recovery_omnibox\"]";
    }

}
