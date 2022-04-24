package com.thucnobita.api.instagram.requests.challenge;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGBaseModel;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.challenge.ChallengeStateResponse;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChallengeSendPhonenumberRequest extends IGPostRequest<ChallengeStateResponse> {
    @NonNull
    private final String path;
    @NonNull
    private final String _phone_number;
    
    @Override
    protected IGBaseModel getPayload(IGClient client) {
        return new IGPayload() {
            @Getter
            private String phone_number = _phone_number; 
        };
    }

    @Override
    public String path() {
        return path.substring(1);
    }

    @Override
    public Class<ChallengeStateResponse> getResponseType() {
        return ChallengeStateResponse.class;
    }

}
