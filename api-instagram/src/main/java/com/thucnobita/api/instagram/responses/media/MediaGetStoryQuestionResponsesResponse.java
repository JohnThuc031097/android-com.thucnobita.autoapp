package com.thucnobita.api.instagram.responses.media;

import com.thucnobita.api.instagram.models.media.reel.ResponderInfo;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class MediaGetStoryQuestionResponsesResponse extends IGResponse implements IGPaginatedResponse {
    private ResponderInfo responder_info;

    @Override
    public String getNext_max_id() {
        return this.responder_info.getMax_id();
    }

    @Override
    public boolean isMore_available() {
        return this.responder_info.isMore_available();
    }
}
