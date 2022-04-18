package com.thucnobita.api.instagram.exceptions;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.responses.accounts.LoginResponse;

import lombok.Getter;

@Getter
public class IGLoginException extends IGResponseException {
    private final IGClient client;
    private final LoginResponse loginResponse;

    public IGLoginException(IGClient client, LoginResponse body) {
        super(body);
        this.client = client;
        this.loginResponse = body;
    }

}
