package com.thucnobita.api.instagram.responses.music;

import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.thucnobita.api.instagram.models.music.MusicPlaylist.BeanToTrackConverter;
import com.thucnobita.api.instagram.models.music.MusicTrack;
import com.thucnobita.api.instagram.responses.IGPaginatedResponse;
import com.thucnobita.api.instagram.responses.IGResponse;
import lombok.Data;

@Data
public class MusicTrackResponse extends IGResponse implements IGPaginatedResponse {
    @JsonDeserialize(converter = BeanToTrackConverter.class)
    private List<MusicTrack> items;
    private MusicTrackPageInfo page_info;

    public String getNext_max_id() {
        return page_info.getNext_max_id();
    }

    public boolean isMore_available() {
        return page_info.isMore_available();
    }

    @Data
    public static class MusicTrackPageInfo {
        private String next_max_id;
        private boolean more_available;
    }
}
