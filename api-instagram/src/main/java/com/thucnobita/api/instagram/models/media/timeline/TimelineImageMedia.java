package com.thucnobita.api.instagram.models.media.timeline;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.ImageMedia;
import com.thucnobita.api.instagram.models.media.ImageVersions;
import com.thucnobita.api.instagram.models.media.ImageVersionsMeta;

import lombok.Data;

@Data
@JsonTypeName("1")
public class TimelineImageMedia extends TimelineMedia implements ImageMedia {
    private ImageVersions image_versions2;
    private List<ImageVersionsMeta> candidates;
    private long video_duration;
    private boolean has_audio;
    private int original_width;
    private int original_height;
    private int view_count;
}
