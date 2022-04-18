package com.thucnobita.api.instagram.models.direct.item;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.timeline.TimelineMedia;
import com.thucnobita.api.instagram.models.user.Profile;

import lombok.Data;

@Data
@JsonTypeName("profile")
public class ThreadProfileItem extends ThreadItem {
    private Profile profile;
    private List<TimelineMedia> preview_medias;
}
