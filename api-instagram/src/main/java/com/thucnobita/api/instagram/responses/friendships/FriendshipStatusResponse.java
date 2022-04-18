package com.thucnobita.api.instagram.responses.friendships;

import com.thucnobita.api.instagram.models.friendships.Friendship;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class FriendshipStatusResponse extends IGResponse {
    private Friendship friendship_status;
}
