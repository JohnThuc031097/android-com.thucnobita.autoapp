package com.thucnobita.api.instagram.requests.friendships;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.friendships.FriendshipsShowResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FriendshipsShowRequest extends IGGetRequest<FriendshipsShowResponse> {
    @NonNull
    private Long pk;

    @Override
    public String path() {
        return "friendships/show/" + pk + "/";
    }

    @Override
    public Class<FriendshipsShowResponse> getResponseType() {
        return FriendshipsShowResponse.class;
    }

}
