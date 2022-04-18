package com.thucnobita.api.instagram.responses.media;

import java.util.List;
import com.thucnobita.api.instagram.models.media.reel.ReelMedia;
import com.thucnobita.api.instagram.models.user.Profile;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class MediaListReelMediaViewerResponse extends IGResponse implements IGPaginatedResponse {
    private List<Profile> users;
    private String next_max_id;
    private int user_count;
    private int total_viewer_count;
    private ReelMedia updated_media;

    public boolean isMore_available() {
        return next_max_id != null;
    }
}
