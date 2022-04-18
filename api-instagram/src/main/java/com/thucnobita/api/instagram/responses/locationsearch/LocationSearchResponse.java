package com.thucnobita.api.instagram.responses.locationsearch;

import java.util.List;

import com.thucnobita.api.instagram.models.location.Location.Venue;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class LocationSearchResponse extends IGResponse {
    private List<Venue> venues;
    private String request_id;
    private String rank_token;
}
