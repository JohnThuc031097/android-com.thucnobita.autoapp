package com.thucnobita.api.instagram.models.feed;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.thucnobita.api.instagram.models.IGBaseModel;
import com.thucnobita.api.instagram.models.media.reel.ReelMedia;
import com.thucnobita.api.instagram.models.user.User;

import lombok.Data;

@Data
public class Reel extends IGBaseModel {
    private long pk;
    private String id;
    private long latest_reel_media;
    private long expiring_at;
    private int seen;
    private boolean can_reply;
    private String reel_type;
    @JsonProperty("is_sensitive_vertical_ad")
    private boolean is_sensitive_vertical_ad;
    private User user;
    private int ranked_position;
    private int seen_ranked_position;
    private boolean muted;
    private int media_count;
    private long[] media_ids;
    private List<ReelMedia> items;
}
