package com.thucnobita.api.instagram.responses.live;

import java.util.List;

import com.thucnobita.api.instagram.models.user.Profile;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class LiveGetQuestionsResponse extends IGResponse {
    private List<LiveQuestions> questions;

    @Data
    public static class LiveQuestions {
        private String text;
        private long qid;
        private String source;
        private Profile user;
        private long timestamp;
    }
}
