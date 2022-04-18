package com.thucnobita.api.instagram.requests.accounts;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.requests.accounts.AccountsLoginRequest.LoginPayload;
import com.thucnobita.api.instagram.responses.accounts.LoginResponse;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountsTwoFactorLoginRequest extends IGPostRequest<LoginResponse> {
    @NonNull
    private String username, password, code, identifier;

    public AccountsTwoFactorLoginRequest(String username, String encryptPassword, String code, String identifier) {
        super();
    }

    @Override
    public IGPayload getPayload(IGClient client) {
        return new LoginPayload(username, password) {
            @Getter
            private final String verification_code = code;
            @Getter
            private final String two_factor_identifier = identifier;
        };
    }

    @Override
    public String path() {
        return "accounts/two_factor_login/";
    }

    @Override
    public Class<LoginResponse> getResponseType() {
        return LoginResponse.class;
    }

}
