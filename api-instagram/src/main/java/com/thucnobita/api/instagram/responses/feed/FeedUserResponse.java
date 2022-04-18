package com.thucnobita.api.instagram.responses.feed;

import java.util.List;
import com.thucnobita.api.instagram.models.media.timeline.TimelineMedia;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class FeedUserResponse extends IGResponse implements IGPaginatedResponse {
    private List<TimelineMedia> items;
    private String next_max_id;
    private int num_results;

    public boolean isMore_available() {
        return next_max_id != null;
    }
}
