package com.thucnobita.api.instagram.requests.usertags;


import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.requests.IGPaginatedRequest;
import com.thucnobita.api.instagram.responses.usertags.UserTagsFeedResponse;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class UserTagsFeedRequest extends IGGetRequest<UserTagsFeedResponse> 
					implements IGPaginatedRequest {
	
    private long userId;
    
    @Setter 
    private String max_id;
    
    @Override
    public String path() {
        return "usertags/"+userId+"/feed/";
    }

    @Override
    public Class<UserTagsFeedResponse> getResponseType() {
        return UserTagsFeedResponse.class;
    }

    @Override
    public String getQueryString(IGClient client) {
        return mapQueryString("max_id", max_id);
    }
}
