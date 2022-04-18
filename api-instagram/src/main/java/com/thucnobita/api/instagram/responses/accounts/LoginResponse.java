package com.thucnobita.api.instagram.responses.accounts;

import com.thucnobita.api.instagram.models.user.User;
import com.thucnobita.api.instagram.responses.IGResponse;
import com.thucnobita.api.instagram.responses.challenge.Challenge;

import lombok.Data;

@Data
public class LoginResponse extends IGResponse {
    private User logged_in_user;
    private Challenge challenge;
    private TwoFactorInfo two_factor_info;

    @Data
    public class TwoFactorInfo {
        private String two_factor_identifier;
    }
}
