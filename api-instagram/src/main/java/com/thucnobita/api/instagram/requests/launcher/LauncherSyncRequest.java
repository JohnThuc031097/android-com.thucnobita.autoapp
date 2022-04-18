package com.thucnobita.api.instagram.requests.launcher;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.IGConstants;
import com.thucnobita.api.instagram.models.IGBaseModel;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class LauncherSyncRequest extends IGPostRequest<IGResponse> {
    private boolean preLogin;

    @Override
    public String baseApiUrl() {
        return preLogin ? IGConstants.B_BASE_API_URL : super.baseApiUrl();
    }

    @Override
    protected IGBaseModel getPayload(IGClient client) {
        return preLogin ? new PrePayload(client.getPhoneId()) : new PostPayload();
    }

    @Override
    public String path() {
        return "launcher/sync/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

    @Data
    private class PrePayload extends IGBaseModel {
        private final String id;
        private final String server_config_retrieval = "1";
    }

    @Data
    private class PostPayload extends IGPayload {
        private final String server_config_retrieval = "1";
    }

}
