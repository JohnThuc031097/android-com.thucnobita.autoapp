package com.thucnobita.api.instagram.models.direct.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.reel.ReelMedia;

import lombok.Data;

@Data
@JsonTypeName("story_share")
public class ThreadStoryShareItem extends ThreadItem {
    private String text;
    private String story_share_type;
    @JsonProperty("is_reel_persisted")
    private boolean is_reel_persisted;
    private String reel_id;
    private String reel_type;
    private ReelMedia media;
}
