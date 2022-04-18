package com.thucnobita.api.instagram.responses.live;

import com.thucnobita.api.instagram.responses.IGResponse;

import java.util.List;

import lombok.Data;

@Data
public class LiveBroadcastThumbnailsResponse extends IGResponse {
    private List<String> thumbnails;
}
