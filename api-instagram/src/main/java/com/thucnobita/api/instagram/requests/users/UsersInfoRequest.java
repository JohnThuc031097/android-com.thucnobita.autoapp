package com.thucnobita.api.instagram.requests.users;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.users.UserResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsersInfoRequest extends IGGetRequest<UserResponse> {
    private long userId;

    @Override
    public String path() {
        return "users/" + userId + "/info/?from_module=blended_search";
    }

    @Override
    public Class<UserResponse> getResponseType() {
        return UserResponse.class;
    }

}
