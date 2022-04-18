package com.thucnobita.api.instagram.requests.users;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.users.UsersBlockedListResponse;

public class UsersBlockedListRequest extends IGGetRequest<UsersBlockedListResponse> {

	@Override
    public String path() {
        return "users/blocked_list/";
    }

	@Override
	public Class<UsersBlockedListResponse> getResponseType() {
		return UsersBlockedListResponse.class;
	}

}
