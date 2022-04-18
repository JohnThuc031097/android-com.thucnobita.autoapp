package com.thucnobita.api.instagram.models.media.thread;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thucnobita.api.instagram.models.IGBaseModel;
import com.thucnobita.api.instagram.models.user.User;
import lombok.Data;

@Data
public class ThreadAnimatedMedia extends IGBaseModel {
    private ThreadImages images;
    @JsonProperty("is_random")
    private boolean is_random;
    @JsonProperty("is_sticker")
    private boolean is_sticker;
    private User user;

    @Data
    public static class ThreadImages {
        private FixedHeight fixed_height;
    }

    @Data
    public static class FixedHeight {
        private String height;
        private String mp4;
        private String mp4_size;
        private String size;
        private String url;
        private String webp;
        private String webp_size;
        private String width;
    }
}
