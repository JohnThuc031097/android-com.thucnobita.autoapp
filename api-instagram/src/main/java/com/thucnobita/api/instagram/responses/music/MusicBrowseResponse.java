package com.thucnobita.api.instagram.responses.music;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.StdConverter;
import com.thucnobita.api.instagram.models.music.MusicPlaylist;
import com.thucnobita.api.instagram.responses.IGResponse;
import com.thucnobita.api.instagram.utils.IGUtils;

import lombok.Data;

@Data
public class MusicBrowseResponse extends IGResponse {
    @JsonDeserialize(converter = BeanToIGMusicPlaylistConverter.class)
    private List<MusicPlaylist> items;

    private static class BeanToIGMusicPlaylistConverter
            extends StdConverter<List<Map<String, Object>>, List<MusicPlaylist>> {
        @Override
        public List<MusicPlaylist> convert(List<Map<String, Object>> value) {
            return value.stream()
                    .filter(m -> m.containsKey("playlist"))
                    .map(m -> IGUtils.convertToView(m.get("playlist"), MusicPlaylist.class))
                    .collect(Collectors.toList());
        }
    }
}
