package com.thucnobita.api.instagram.responses.media;

import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class RuploadPhotoResponse extends IGResponse {
    private String upload_id;
}
