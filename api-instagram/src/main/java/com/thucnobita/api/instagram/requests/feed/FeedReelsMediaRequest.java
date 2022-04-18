package com.thucnobita.api.instagram.requests.feed;

import java.util.stream.Stream;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;

import com.thucnobita.api.instagram.responses.feed.FeedReelsMediaResponse;
import lombok.Getter;

public class FeedReelsMediaRequest extends IGPostRequest<FeedReelsMediaResponse> {
    private String[] _ids;

    public FeedReelsMediaRequest(Object... ids) {
        _ids = Stream.of(ids).map(Object::toString).toArray(String[]::new);
    }

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new IGPayload() {
            @Getter
            private String[] user_ids = _ids;
        };
    }

    @Override
    public String path() {
        return "feed/reels_media/";
    }

    @Override
    public Class<FeedReelsMediaResponse> getResponseType() {
        return FeedReelsMediaResponse.class;
    }

}
