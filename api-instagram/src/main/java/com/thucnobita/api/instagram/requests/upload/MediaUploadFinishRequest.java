package com.thucnobita.api.instagram.requests.upload;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MediaUploadFinishRequest extends IGPostRequest<IGResponse> {
    @NonNull
    private String uploadId;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new MediaUploadFinishPayload();
    }

    @Override
    public String path() {
        return "media/upload_finish/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

    @Data
    public class MediaUploadFinishPayload extends IGPayload {
        private String upload_id = uploadId;
    }
}
