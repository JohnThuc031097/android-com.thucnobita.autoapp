package com.thucnobita.api.instagram.responses.feed;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.thucnobita.api.instagram.models.feed.Reel;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class FeedUserReelsMediaResponse extends IGResponse {
    @JsonUnwrapped
    private Reel reel;
}
