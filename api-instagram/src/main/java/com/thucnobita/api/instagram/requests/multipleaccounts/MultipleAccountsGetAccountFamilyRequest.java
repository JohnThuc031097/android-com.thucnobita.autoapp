package com.thucnobita.api.instagram.requests.multipleaccounts;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

public class MultipleAccountsGetAccountFamilyRequest extends IGGetRequest<IGResponse> {

    @Override
    public String path() {
        return "multiple_accounts/get_account_family/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
