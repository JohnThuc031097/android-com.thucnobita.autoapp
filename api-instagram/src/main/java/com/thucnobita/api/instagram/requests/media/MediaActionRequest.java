package com.thucnobita.api.instagram.requests.media;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MediaActionRequest extends IGPostRequest<IGResponse> {
    @NonNull
    private String _media_id;
    @NonNull
    private MediaAction action;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new IGPayload() {
            @Getter
            private String media_id = _media_id;
        };
    }

    @Override
    public String path() {
        return String.format("media/%s/%s/", _media_id, action.name().toLowerCase());
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

    public static enum MediaAction {
        SAVE, UNSAVE, ONLY_ME, UNDO_ONLY_ME, DELETE, LIKE, UNLIKE, ENABLE_COMMENTS, DISABLE_COMMENTS;
    }

}
