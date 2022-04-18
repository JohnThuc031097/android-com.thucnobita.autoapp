package com.thucnobita.api.instagram.requests.accounts;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.accounts.AccountsUserResponse;

public class AccountsCurrentUserRequest extends IGGetRequest<AccountsUserResponse> {

    @Override
    public String path() {
        return "accounts/current_user/";
    }

    @Override
    public Class<AccountsUserResponse> getResponseType() {
        return AccountsUserResponse.class;
    }

}
