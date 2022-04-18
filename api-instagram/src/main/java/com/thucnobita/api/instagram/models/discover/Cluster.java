package com.thucnobita.api.instagram.models.discover;

import java.util.List;

import com.thucnobita.api.instagram.models.IGBaseModel;

import lombok.Data;

@Data
public class Cluster extends IGBaseModel {
    private String id;
    private String title;
    private String context;
    private String description;
    private List<String> labels;
    private String name;
    private String type;
}
