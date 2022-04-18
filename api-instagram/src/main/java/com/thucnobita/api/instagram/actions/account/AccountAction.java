package com.thucnobita.api.instagram.actions.account;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.requests.accounts.*;
import com.thucnobita.api.instagram.requests.accounts.AccountsActionRequest.AccountsAction;
import com.thucnobita.api.instagram.responses.IGResponse;
import com.thucnobita.api.instagram.responses.accounts.AccountsUserResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountAction {
    @NonNull
    private IGClient client;

    public CompletableFuture<AccountsUserResponse> setProfilePicture(byte[] photo) {
        return client.actions().upload()
                .photo(photo, String.valueOf(System.currentTimeMillis()))
                .thenCompose(res -> new AccountsChangeProfilePictureRequest(res.getUpload_id())
                        .execute(client));
    }
    
    public CompletableFuture<AccountsUserResponse> setProfilePicture(File file) {
        try {
            return setProfilePicture(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    
    public CompletableFuture<IGResponse> setBio(String bio) {
        return new AccountsSetBiographyRequest(bio).execute(client);
    }
    
    public CompletableFuture<AccountsUserResponse> action(AccountsAction action) {
        return new AccountsActionRequest(action).execute(client);
    }
    
    public CompletableFuture<AccountsUserResponse> currentUser() {
        return new AccountsCurrentUserRequest().execute(client);
    }
}
