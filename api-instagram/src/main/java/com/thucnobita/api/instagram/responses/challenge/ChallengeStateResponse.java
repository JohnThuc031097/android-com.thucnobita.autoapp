package com.thucnobita.api.instagram.responses.challenge;

import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ChallengeStateResponse extends IGResponse {
    private String step_name;
    private StepData step_data;

    @Getter
    @Setter
    public static class StepData {
        private String choice;
        private String email;
    }
}
