package com.thucnobita.api.instagram.requests.direct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

public class DirectThreadsDeclineRequest extends IGPostRequest<IGResponse> {
    private final String[] _thread_ids;

    public DirectThreadsDeclineRequest(String... thread_ids) {
        this._thread_ids = thread_ids;
    }

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new DirectThreadsDeclineRequestPayload();
    }

    @Override
    public String path() {
        return String.format("direct_v2/threads/%s/",
                _thread_ids.length > 1 ? "decline_multiple" : (_thread_ids[0] + "/decline"));
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

    @Data
    @JsonInclude(Include.NON_NULL)
    public class DirectThreadsDeclineRequestPayload extends IGPayload {
        private final String[] thread_ids = _thread_ids.length > 1 ? _thread_ids : null;
    }
}
