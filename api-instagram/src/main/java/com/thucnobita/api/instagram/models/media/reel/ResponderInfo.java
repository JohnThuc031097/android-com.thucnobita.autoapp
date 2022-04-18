package com.thucnobita.api.instagram.models.media.reel;

import java.util.List;

import com.thucnobita.api.instagram.models.IGBaseModel;
import com.thucnobita.api.instagram.models.user.Profile;

import lombok.Data;

@Data
public class ResponderInfo extends IGBaseModel {
    private Long question_id;
    private String question;
    private String question_type;
    private List<Responder> responders;
    private String max_id;
    private boolean more_available;

    @Data
    public static class Responder {
        private Profile user;
        private String response;
        private String id;
        private Long ts;
    }
}
