package com.thucnobita.api.instagram.responses.live;

import com.thucnobita.api.instagram.models.media.timeline.Comment;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class LiveBroadcastCommentResponse extends IGResponse {
    private Comment comment;
}
