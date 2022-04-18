package com.thucnobita.api.instagram.requests.direct;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

public class DirectCreateGroupThreadRequest extends IGPostRequest<IGResponse> {
    private String title;
    private String[] userIds;

    public DirectCreateGroupThreadRequest(String title, String... user_ids) {
        this.title = title;
        this.userIds = user_ids;
    }

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new DirectCreateGroupThreadPayload();
    }

    @Override
    public String path() {
        return "direct_v2/create_group_thread/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

    @Data
    public class DirectCreateGroupThreadPayload extends IGPayload {
        private String[] recipient_users = userIds;
        private String thread_title = title;
    }

}
