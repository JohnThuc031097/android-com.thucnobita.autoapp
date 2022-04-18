package com.thucnobita.api.instagram.requests.linkedaccounts;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

public class LinkedAccountsGetLinkageStatusRequest extends IGGetRequest<IGResponse> {

    @Override
    public String path() {
        return "linked_accounts/get_linkage_status/";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
