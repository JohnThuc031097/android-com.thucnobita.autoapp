package com.thucnobita.api.instagram.responses.feed;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.thucnobita.api.instagram.models.feed.Reel;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class FeedReelsMediaResponse extends IGResponse {
    @JsonUnwrapped
    private Map<String, Reel> reels = new HashMap<String, Reel>();
}
