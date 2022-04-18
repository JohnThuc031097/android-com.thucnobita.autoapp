package com.thucnobita.api.instagram.models.media.thread;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.ImageVersions;
import com.thucnobita.api.instagram.models.media.VideoVersionsMeta;

import lombok.Data;

@Data
@JsonTypeName("2")
public class ThreadVideoMedia extends ThreadMedia {
    private ImageVersions image_versions2;
    private List<VideoVersionsMeta> video_versions;
}
