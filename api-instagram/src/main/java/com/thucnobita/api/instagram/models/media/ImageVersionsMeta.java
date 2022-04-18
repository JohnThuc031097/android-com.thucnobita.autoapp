package com.thucnobita.api.instagram.models.media;

import lombok.Data;

@Data
public class ImageVersionsMeta implements Meta {
    private String url;
    private int width;
    private int height;
}
