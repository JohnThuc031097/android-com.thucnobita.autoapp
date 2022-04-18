package com.thucnobita.api.instagram.responses.direct;

import com.thucnobita.api.instagram.models.direct.IGThread;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class DirectThreadsResponse extends IGResponse {
    private IGThread thread;
}
