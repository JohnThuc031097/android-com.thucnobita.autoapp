package com.thucnobita.api.instagram.responses.media;

import java.util.List;
import com.thucnobita.api.instagram.models.media.timeline.Comment;
import com.thucnobita.api.instagram.models.media.timeline.Comment.Caption;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class MediaGetCommentsResponse extends IGResponse implements IGPaginatedResponse {
    private List<Comment> comments;
    private Caption caption;
    private String next_max_id;

    public boolean isMore_available() {
        return next_max_id != null;
    }
}
