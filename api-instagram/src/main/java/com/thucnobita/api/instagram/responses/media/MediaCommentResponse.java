package com.thucnobita.api.instagram.responses.media;

import com.thucnobita.api.instagram.models.media.timeline.Comment;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class MediaCommentResponse extends IGResponse {
    private Comment comment;
}
