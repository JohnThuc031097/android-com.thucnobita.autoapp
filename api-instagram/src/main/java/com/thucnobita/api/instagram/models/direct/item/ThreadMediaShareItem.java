package com.thucnobita.api.instagram.models.direct.item;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.Media;

import lombok.Data;

@Data
@JsonTypeName("media_share")
public class ThreadMediaShareItem extends ThreadItem {
    private Media media_share;
}
