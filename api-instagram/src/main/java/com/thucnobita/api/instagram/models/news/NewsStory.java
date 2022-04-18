package com.thucnobita.api.instagram.models.news;

import com.thucnobita.api.instagram.models.IGBaseModel;

import lombok.Data;

@Data
public class NewsStory extends IGBaseModel {
    private int type;
    private int story_type;
    private String pk;
}
