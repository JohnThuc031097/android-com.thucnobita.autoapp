package com.thucnobita.api.instagram.responses.music;

import com.thucnobita.api.instagram.models.music.MusicLyrics;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class MusicTrackLyricsResponse extends IGResponse {
    private MusicLyrics lyrics;
}
