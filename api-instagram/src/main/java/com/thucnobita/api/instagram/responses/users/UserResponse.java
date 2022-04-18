package com.thucnobita.api.instagram.responses.users;

import com.thucnobita.api.instagram.models.user.User;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class UserResponse extends IGResponse {
    private User user;
}
