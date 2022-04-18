package com.thucnobita.api.instagram.responses.media;

import java.util.List;

import com.thucnobita.api.instagram.models.media.timeline.TimelineMedia;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class MediaInfoResponse extends IGResponse {
    private List<TimelineMedia> items;
    private int num_results;
    private boolean more_available;
}
