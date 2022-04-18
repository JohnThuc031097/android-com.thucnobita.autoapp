package com.thucnobita.api.instagram.models.direct.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.reel.ReelMedia;

import lombok.Data;

@Data
@JsonTypeName("reel_share")
public class ThreadReelShareItem extends ThreadItem {
    private String text;
    private String type;
    @JsonProperty("is_reel_persisted")
    private boolean is_reel_persisted;
    private String reel_owner_id;
    private String reel_type;
    private ReelMedia media;
}
