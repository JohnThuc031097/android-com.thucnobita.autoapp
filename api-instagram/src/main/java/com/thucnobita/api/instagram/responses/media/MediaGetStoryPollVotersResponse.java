package com.thucnobita.api.instagram.responses.media;

import com.thucnobita.api.instagram.models.media.reel.VoterInfo;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class MediaGetStoryPollVotersResponse extends IGResponse implements IGPaginatedResponse {
    private VoterInfo voter_info;

    @Override
    public String getNext_max_id() {
        return voter_info.getMax_id();
    }

    @Override
    public boolean isMore_available() {
        return voter_info.isMore_available();
    }

}
