package com.thucnobita.api.instagram.requests.commerce;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.requests.IGPaginatedRequest;
import com.thucnobita.api.instagram.responses.commerce.CommerceDestinationResponse;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CommerceDestinationRequest extends IGGetRequest<CommerceDestinationResponse>
        implements IGPaginatedRequest {
    @Setter
    private String max_id = "0";

    @Override
    public String path() {
        return "commerce/destination/";
    }

    @Override
    public String getQueryString(IGClient client) {
        return mapQueryString("max_id", max_id, "cluster_id", "shopping");
    }

    @Override
    public Class<CommerceDestinationResponse> getResponseType() {
        return CommerceDestinationResponse.class;
    }

}
