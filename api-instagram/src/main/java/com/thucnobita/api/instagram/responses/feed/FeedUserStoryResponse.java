package com.thucnobita.api.instagram.responses.feed;

import com.thucnobita.api.instagram.models.feed.Reel;
import com.thucnobita.api.instagram.models.live.Broadcast;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class FeedUserStoryResponse extends IGResponse {
    private Reel reel;
    private Broadcast broadcast;
}
