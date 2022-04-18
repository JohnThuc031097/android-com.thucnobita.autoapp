package com.thucnobita.api.instagram.models.direct.item;

import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.Data;

@Data
@JsonTypeName("text")
public class ThreadTextItem extends ThreadItem {
    private String text;
}
