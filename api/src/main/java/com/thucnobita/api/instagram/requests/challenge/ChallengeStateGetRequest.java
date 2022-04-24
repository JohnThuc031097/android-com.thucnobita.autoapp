package com.thucnobita.api.instagram.requests.challenge;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.challenge.ChallengeStateResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChallengeStateGetRequest extends IGGetRequest<ChallengeStateResponse> {
    @NonNull
    private String path, guid, device_id,challenge_context;

    @Override
    public String path() {
        return path.substring(1);
    }

    @Override
    public String getQueryString(IGClient client) {
        return this.mapQueryString("guid", guid, "device_id", device_id,"challenge_context",challenge_context);
    }

    @Override
    public Class<ChallengeStateResponse> getResponseType() {
        return ChallengeStateResponse.class;
    }

}
