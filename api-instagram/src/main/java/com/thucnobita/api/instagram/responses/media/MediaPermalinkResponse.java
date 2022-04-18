package com.thucnobita.api.instagram.responses.media;

import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class MediaPermalinkResponse extends IGResponse {
    private String permalink;
}
