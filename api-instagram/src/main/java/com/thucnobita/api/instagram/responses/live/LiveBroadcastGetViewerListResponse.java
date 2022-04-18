package com.thucnobita.api.instagram.responses.live;

import java.util.List;

import com.thucnobita.api.instagram.models.user.Profile;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class LiveBroadcastGetViewerListResponse extends IGResponse {
    private List<Profile> users;
}
