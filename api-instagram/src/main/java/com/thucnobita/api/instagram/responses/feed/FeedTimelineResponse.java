package com.thucnobita.api.instagram.responses.feed;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.thucnobita.api.instagram.models.media.timeline.TimelineMedia;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import com.thucnobita.api.instagram.utils.IGUtils;
import lombok.Data;

@Data
public class FeedTimelineResponse extends IGResponse implements IGPaginatedResponse {
    private boolean auto_load_more_enabled;
    private int num_results;
    private String next_max_id;
    @JsonDeserialize(converter = FilterToIGTimelineMedia.class)
    private List<TimelineMedia> feed_items;
    private boolean more_available;

    private static class FilterToIGTimelineMedia
            extends StdConverter<List<Map<String, Object>>, List<TimelineMedia>> {
        @Override
        public List<TimelineMedia> convert(List<Map<String, Object>> value) {
            return value.stream()
                    .filter(m -> m.containsKey("media_or_ad"))
                    .map(m -> IGUtils.convertToView(m.get("media_or_ad"), TimelineMedia.class))
                    .collect(Collectors.toList());
        }
    }
}
