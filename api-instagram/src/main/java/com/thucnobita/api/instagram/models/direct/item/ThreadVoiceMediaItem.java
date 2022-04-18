package com.thucnobita.api.instagram.models.direct.item;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.VoiceMedia;

import lombok.Data;

@Data
@JsonTypeName("voice_media")
public class ThreadVoiceMediaItem extends ThreadItem {
    private VoiceMedia media;
}
