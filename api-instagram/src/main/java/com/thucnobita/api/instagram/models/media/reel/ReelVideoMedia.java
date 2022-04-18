package com.thucnobita.api.instagram.models.media.reel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.ImageVersions;
import com.thucnobita.api.instagram.models.media.VideoVersionsMeta;

import lombok.Data;

@Data
@JsonTypeName("2")
public class ReelVideoMedia extends ReelMedia {
    private boolean has_audio;
    private int number_of_qualities;
    private double video_duration;
    private ImageVersions image_versions2;
    private List<VideoVersionsMeta> video_versions;
}
