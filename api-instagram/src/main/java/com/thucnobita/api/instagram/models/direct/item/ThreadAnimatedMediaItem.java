package com.thucnobita.api.instagram.models.direct.item;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.thread.ThreadAnimatedMedia;

import lombok.Data;

@Data
@JsonTypeName("animated_media")
public class ThreadAnimatedMediaItem extends ThreadItem {
    private ThreadAnimatedMedia media;
}
