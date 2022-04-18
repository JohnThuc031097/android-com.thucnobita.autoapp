package com.thucnobita.api.instagram.responses.feed;

import java.util.List;
import com.thucnobita.api.instagram.models.feed.Reel;
import com.thucnobita.api.instagram.models.media.timeline.TimelineMedia;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class FeedTagResponse extends IGResponse implements IGPaginatedResponse {
    private List<TimelineMedia> ranked_items;
    private List<TimelineMedia> items;
    private Reel story;
    private int num_results;
    private String next_max_id;
    private boolean more_available;
}
