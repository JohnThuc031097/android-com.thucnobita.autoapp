package com.thucnobita.api.instagram.responses.igtv;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thucnobita.api.instagram.models.igtv.Channel;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class IgtvBrowseFeedResponse extends IGResponse implements IGPaginatedResponse {
    private Channel my_channel;
    private List<Channel> channels;
    @JsonProperty("max_id")
    private String next_max_id;
    private boolean more_available;
}
