package com.thucnobita.api.instagram.responses.friendships;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.thucnobita.api.instagram.models.friendships.Friendship;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class FriendshipsShowResponse extends IGResponse {
    @JsonUnwrapped
    private Friendship friendship;
}
