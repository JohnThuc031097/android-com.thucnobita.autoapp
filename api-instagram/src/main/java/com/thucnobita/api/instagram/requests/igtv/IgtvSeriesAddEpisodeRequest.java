package com.thucnobita.api.instagram.requests.igtv;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.igtv.IgtvSeriesResponse;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IgtvSeriesAddEpisodeRequest extends IGPostRequest<IgtvSeriesResponse> {
    @NonNull
    private String _series;
    @NonNull
    private Long pk;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return new IgtvSeriesAddEpisodePayload();
    }

    @Override
    public String path() {
        return "igtv/series/" + _series + "/add_episode/";
    }

    @Override
    public Class<IgtvSeriesResponse> getResponseType() {
        return IgtvSeriesResponse.class;
    }

    @Data
    public class IgtvSeriesAddEpisodePayload extends IGPayload {
        private String media_id = pk.toString();
    }

}
