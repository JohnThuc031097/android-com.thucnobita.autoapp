package com.thucnobita.api.instagram.responses.media;


import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class MediaInfoResponse extends IGResponse {
    private int num_results;
    private boolean more_available;
}
