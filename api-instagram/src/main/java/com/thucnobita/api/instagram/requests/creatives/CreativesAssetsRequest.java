package com.thucnobita.api.instagram.requests.creatives;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.creatives.CreativesAssetsResponse;

import lombok.Data;

public class CreativesAssetsRequest extends IGPostRequest<CreativesAssetsResponse> {

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new CreativesAssetsPayload();
    }

    @Override
    public String path() {
        return "creatives/assets/";
    }

    @Override
    public Class<CreativesAssetsResponse> getResponseType() {
        return CreativesAssetsResponse.class;
    }

    @Data
    public static class CreativesAssetsPayload extends IGPayload {
        private final String type = "static_stickers";
    }
}
