package com.thucnobita.api.instagram.models.direct;

import java.util.List;

import com.thucnobita.api.instagram.models.direct.item.ThreadRavenMediaItem;

import lombok.Data;

@Data
public class DirectStory {
    private List<ThreadRavenMediaItem> items;
    private long last_activity_at;
    private boolean has_newer;
    private String next_cursor;
}
