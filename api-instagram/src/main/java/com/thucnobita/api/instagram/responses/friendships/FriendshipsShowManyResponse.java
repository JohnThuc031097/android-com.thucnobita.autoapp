package com.thucnobita.api.instagram.responses.friendships;

import java.util.Map;

import com.thucnobita.api.instagram.models.friendships.Friendship;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class FriendshipsShowManyResponse extends IGResponse {
    private Map<String, Friendship> friendship_statuses;
}
