package com.thucnobita.api.instagram.responses.igtv;

import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class IgtvSeriesResponse extends IGResponse {
    private String id;
    private String title;
    private String description;
}
