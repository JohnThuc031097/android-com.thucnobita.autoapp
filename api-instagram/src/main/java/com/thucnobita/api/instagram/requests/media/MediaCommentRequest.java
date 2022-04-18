package com.thucnobita.api.instagram.requests.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.media.MediaCommentResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class MediaCommentRequest extends IGPostRequest<MediaCommentResponse> {
    @NonNull
    private String id, _comment_text;
    private String _replied_to_comment_id;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new MediaCommentPayload();
    }

    @Override
    public String path() {
        return "media/" + id + "/comment/";
    }

    @Override
    public Class<MediaCommentResponse> getResponseType() {
        return MediaCommentResponse.class;
    }

    @Data
    @JsonInclude(Include.NON_NULL)
    private class MediaCommentPayload extends IGPayload {
        private String comment_text = _comment_text;
        private String replied_to_comment_id = _replied_to_comment_id;
    }

}
