package com.thucnobita.api.instagram.responses.live;

import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class LiveStartResponse extends IGResponse {
    private String media_id;
}
