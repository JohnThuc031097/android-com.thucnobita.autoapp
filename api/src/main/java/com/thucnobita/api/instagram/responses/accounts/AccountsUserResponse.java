package com.thucnobita.api.instagram.responses.accounts;

import com.thucnobita.api.instagram.models.user.User;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class AccountsUserResponse extends IGResponse {
    private User user;
}
