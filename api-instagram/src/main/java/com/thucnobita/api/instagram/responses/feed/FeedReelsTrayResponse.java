package com.thucnobita.api.instagram.responses.feed;

import java.util.List;

import com.thucnobita.api.instagram.models.feed.Reel;
import com.thucnobita.api.instagram.models.live.Broadcast;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class FeedReelsTrayResponse extends IGResponse {
    private List<Reel> tray;
    private List<Broadcast> broadcasts;
}
