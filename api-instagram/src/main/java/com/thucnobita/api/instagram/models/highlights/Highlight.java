package com.thucnobita.api.instagram.models.highlights;

import com.thucnobita.api.instagram.models.IGBaseModel;
import com.thucnobita.api.instagram.models.user.Profile;

import lombok.Data;

@Data
public class Highlight extends IGBaseModel {
    private String id;
    private long latest_reel_media;
    private Profile user;
    private String title;
    private int media_count;
}
