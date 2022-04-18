package com.thucnobita.api.instagram.models.media.reel;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.ImageMedia;
import com.thucnobita.api.instagram.models.media.ImageVersions;

import lombok.Data;

@Data
@JsonTypeName("1")
public class ReelImageMedia extends ReelMedia implements ImageMedia {
    private ImageVersions image_versions2;
}
