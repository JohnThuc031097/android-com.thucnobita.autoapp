package com.thucnobita.api.instagram.responses.igtv;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.thucnobita.api.instagram.models.igtv.Channel;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class IgtvChannelResponse extends IGResponse implements IGPaginatedResponse {
    @JsonUnwrapped
    private Channel channel;
    @JsonProperty("max_id")
    private String next_max_id;
    private boolean more_available;
}
