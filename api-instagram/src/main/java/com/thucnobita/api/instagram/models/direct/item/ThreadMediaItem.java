package com.thucnobita.api.instagram.models.direct.item;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.thread.ThreadMedia;

import lombok.Data;

@Data
@JsonTypeName("media")
public class ThreadMediaItem extends ThreadItem {
    private ThreadMedia media;
}
