package com.thucnobita.api.instagram.models.discover;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.thucnobita.api.instagram.models.IGBaseModel;

import lombok.Data;


@Data
@JsonTypeInfo(defaultImpl = SectionalItem.class, use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY, property = "layout_type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SectionalMediaGridItem.class)
})
public class SectionalItem extends IGBaseModel {
    private String layout_type;
    private String feed_type;
}
