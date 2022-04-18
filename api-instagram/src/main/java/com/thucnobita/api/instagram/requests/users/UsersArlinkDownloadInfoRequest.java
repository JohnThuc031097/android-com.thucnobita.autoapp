package com.thucnobita.api.instagram.requests.users;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.IGResponse;

public class UsersArlinkDownloadInfoRequest extends IGGetRequest<IGResponse> {

    @Override
    public String path() {
        return "users/arlink_download_info/?version_override=2.2.1";
    }

    @Override
    public Class<IGResponse> getResponseType() {
        return IGResponse.class;
    }

}
