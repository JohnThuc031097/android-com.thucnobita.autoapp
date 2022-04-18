package com.thucnobita.api.instagram.actions.users;

import java.util.concurrent.CompletableFuture;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.actions.feed.FeedIterable;
import com.thucnobita.api.instagram.models.friendships.Friendship;
import com.thucnobita.api.instagram.models.user.User;
import com.thucnobita.api.instagram.requests.friendships.FriendshipsActionRequest;
import com.thucnobita.api.instagram.requests.friendships.FriendshipsActionRequest.FriendshipsAction;
import com.thucnobita.api.instagram.requests.friendships.FriendshipsFeedsRequest;
import com.thucnobita.api.instagram.requests.friendships.FriendshipsFeedsRequest.FriendshipsFeeds;
import com.thucnobita.api.instagram.requests.friendships.FriendshipsShowRequest;
import com.thucnobita.api.instagram.responses.feed.FeedUsersResponse;
import com.thucnobita.api.instagram.responses.friendships.FriendshipStatusResponse;
import com.thucnobita.api.instagram.responses.friendships.FriendshipsShowResponse;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAction {
    @NonNull
    private IGClient client;
    @NonNull
    @Getter
    private User user;

    public FeedIterable<FriendshipsFeedsRequest, FeedUsersResponse> followersFeed() {
        return new FeedIterable<>(client, () ->
                new FriendshipsFeedsRequest(user.getPk(), FriendshipsFeeds.FOLLOWERS));
    }

    public FeedIterable<FriendshipsFeedsRequest, FeedUsersResponse> followingFeed() {
        return new FeedIterable<>(client, () ->
                new FriendshipsFeedsRequest(user.getPk(), FriendshipsFeeds.FOLLOWING));
    }

    public CompletableFuture<Friendship> getFriendship() {
        return new FriendshipsShowRequest(user.getPk()).execute(client)
                .thenApply(FriendshipsShowResponse::getFriendship);
    }

    public CompletableFuture<FriendshipStatusResponse> action(FriendshipsAction action) {
        return new FriendshipsActionRequest(user.getPk(), action).execute(client);
    }
}
