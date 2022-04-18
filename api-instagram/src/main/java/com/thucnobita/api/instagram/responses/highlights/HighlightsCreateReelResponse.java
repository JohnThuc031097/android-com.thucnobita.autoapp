package com.thucnobita.api.instagram.responses.highlights;

import com.thucnobita.api.instagram.models.feed.Reel;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class HighlightsCreateReelResponse extends IGResponse {
    private Reel reel;
}
