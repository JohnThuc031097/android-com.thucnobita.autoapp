package com.thucnobita.api.instagram.models.media;

import com.thucnobita.api.instagram.models.media.timeline.Comment.Caption;
import com.thucnobita.api.instagram.models.user.User;

public interface ImageMedia {
    long getPk();

    String getId();

    long getTaken_at();

    long getDevice_timestamp();

    String getMedia_type();

    String getCode();

    String getClient_cache_key();

    int getFilter_type();

    User getUser();

    Caption getCaption();

    ImageVersions getImage_versions2();

}
