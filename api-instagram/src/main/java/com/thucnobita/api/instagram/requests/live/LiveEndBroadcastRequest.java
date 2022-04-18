package com.thucnobita.api.instagram.requests.live;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class LiveEndBroadcastRequest extends IGPostRequest<IGResponse> {
    @NonNull
    private String broadcastId;
    private boolean endAfterCopyrightWarning;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new IGPayload() {
            @Getter
            private boolean end_after_copyright_warning = endAfterCopyrightWarning;
        };
    }

    @Override
    public String path() {
        return "live/" + broadcastId + "/end_broadcast/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
