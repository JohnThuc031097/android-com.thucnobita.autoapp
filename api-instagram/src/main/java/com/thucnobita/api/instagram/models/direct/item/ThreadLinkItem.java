package com.thucnobita.api.instagram.models.direct.item;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.thucnobita.api.instagram.models.media.Link;

import lombok.Data;

@Data
@JsonTypeName("link")
public class ThreadLinkItem extends ThreadItem {
    private Link link;
}
