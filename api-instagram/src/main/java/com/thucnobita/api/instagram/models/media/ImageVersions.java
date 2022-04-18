package com.thucnobita.api.instagram.models.media;

import java.util.List;

import lombok.Data;

@Data
public class ImageVersions {
    private List<ImageVersionsMeta> candidates;
}
