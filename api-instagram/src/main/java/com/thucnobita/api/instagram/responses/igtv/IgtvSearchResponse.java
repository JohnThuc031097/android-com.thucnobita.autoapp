package com.thucnobita.api.instagram.responses.igtv;

import java.util.List;

import com.thucnobita.api.instagram.models.igtv.Channel;
import com.thucnobita.api.instagram.models.user.User;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class IgtvSearchResponse extends IGResponse {
    private List<IgtvSearchResult> results;
    private int num_results;
    private boolean has_more;
    private String rank_token;

    @Data
    public static class IgtvSearchResult {
        private String type;
        private User user;
        private Channel channel;
    }
}
