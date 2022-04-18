package com.thucnobita.api.instagram.requests.media;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.media.MediaResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class MediaEditRequest extends IGPostRequest<MediaResponse> {
    @NonNull
    private String id, _caption;
    private boolean _igtv_feed_preview;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new MediaEditPayload();
    }

    @Override
    public String path() {
        return "media/" + id + "/edit_media/";
    }

    @Override
    public Class<MediaResponse> getResponseType() {
        return MediaResponse.class;
    }

    @Data
    public class MediaEditPayload extends IGPayload {
        private String media_id = id;
        private String caption = _caption;
        private boolean igtv_feed_preview = _igtv_feed_preview;
    }

}
