package com.thucnobita.api.instagram.actions.users;

import java.util.concurrent.CompletableFuture;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.user.User;
import com.thucnobita.api.instagram.requests.users.UsersInfoRequest;
import com.thucnobita.api.instagram.requests.users.UsersSearchRequest;
import com.thucnobita.api.instagram.requests.users.UsersUsernameInfoRequest;
import com.thucnobita.api.instagram.responses.users.UserResponse;
import com.thucnobita.api.instagram.responses.users.UsersSearchResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsersAction {
    @NonNull
    private IGClient client;

    public CompletableFuture<UserAction> findByUsername(String username) {
        return new UsersUsernameInfoRequest(username).execute(client)
                .thenApply(response -> new UserAction(client, response.getUser()));
    }

    public CompletableFuture<User> info(long pk) {
        return new UsersInfoRequest(pk).execute(client)
                .thenApply(UserResponse::getUser);
    }

    public CompletableFuture<UsersSearchResponse> search(String query) {
        return new UsersSearchRequest(query).execute(client);
    }

}
