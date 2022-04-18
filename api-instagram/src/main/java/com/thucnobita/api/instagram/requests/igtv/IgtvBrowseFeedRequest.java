package com.thucnobita.api.instagram.requests.igtv;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.requests.IGPaginatedRequest;
import com.thucnobita.api.instagram.responses.igtv.IgtvBrowseFeedResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class IgtvBrowseFeedRequest extends IGGetRequest<IgtvBrowseFeedResponse>
        implements IGPaginatedRequest {
    @Setter
    private String max_id;

    @Override
    public String path() {
        return "igtv/browse_feed/";
    }

    @Override
    public String getQueryString(IGClient client) {
        return mapQueryString("max_id", max_id);
    }

    @Override
    public Class<IgtvBrowseFeedResponse> getResponseType() {
        return IgtvBrowseFeedResponse.class;
    }

}
