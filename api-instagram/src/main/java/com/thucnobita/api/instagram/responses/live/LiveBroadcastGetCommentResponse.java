package com.thucnobita.api.instagram.responses.live;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thucnobita.api.instagram.models.media.timeline.Comment;
import com.thucnobita.api.instagram.models.media.timeline.Comment.Caption;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class LiveBroadcastGetCommentResponse extends IGResponse {
    private boolean comment_likes_enabled;
    private List<Comment> comments;
    private int comment_count;
    private Caption caption;
    @JsonProperty("is_first_fetch")
    private boolean is_first_fetch;
}
