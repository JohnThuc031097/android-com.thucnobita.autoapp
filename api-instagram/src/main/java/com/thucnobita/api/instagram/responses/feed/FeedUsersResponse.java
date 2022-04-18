package com.thucnobita.api.instagram.responses.feed;

import java.util.List;
import com.thucnobita.api.instagram.models.user.Profile;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class FeedUsersResponse extends IGResponse implements IGPaginatedResponse {
    private List<Profile> users;
    private String next_max_id;

    public boolean isMore_available() {
        return next_max_id != null;
    }
}
