package com.thucnobita.api.instagram.actions.simulate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.actions.async.AsyncAction;
import com.thucnobita.api.instagram.requests.IGRequest;
import com.thucnobita.api.instagram.requests.accounts.AccountsContactPointPrefillRequest;
import com.thucnobita.api.instagram.requests.accounts.AccountsGetPrefillCandidatesRequest;
import com.thucnobita.api.instagram.requests.direct.DirectGetPresenceRequest;
import com.thucnobita.api.instagram.requests.direct.DirectInboxRequest;
import com.thucnobita.api.instagram.requests.discover.DiscoverTopicalExploreRequest;
import com.thucnobita.api.instagram.requests.feed.FeedReelsTrayRequest;
import com.thucnobita.api.instagram.requests.feed.FeedTimelineRequest;
import com.thucnobita.api.instagram.requests.launcher.LauncherSyncRequest;
import com.thucnobita.api.instagram.requests.linkedaccounts.LinkedAccountsGetLinkageStatusRequest;
import com.thucnobita.api.instagram.requests.loom.LoomFetchConfigRequest;
import com.thucnobita.api.instagram.requests.media.MediaBlockedRequest;
import com.thucnobita.api.instagram.requests.multipleaccounts.MultipleAccountsGetAccountFamilyRequest;
import com.thucnobita.api.instagram.requests.news.NewsInboxRequest;
import com.thucnobita.api.instagram.requests.qe.QeSyncRequest;
import com.thucnobita.api.instagram.requests.qp.QpGetCooldowns;
import com.thucnobita.api.instagram.requests.status.StatusGetViewableStatusesRequest;
import com.thucnobita.api.instagram.requests.users.UsersArlinkDownloadInfoRequest;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimulateAction {
    @NonNull
    private IGClient client;

    private static final IGRequest<?>[] preLoginFlow = {
            new LauncherSyncRequest(true),
            new QeSyncRequest(true),
            new AccountsContactPointPrefillRequest(),
            new AccountsGetPrefillCandidatesRequest()
    };

    private static final IGRequest<?>[] postLoginFlow = {
            new LauncherSyncRequest(),
            new QpGetCooldowns(),
            new MultipleAccountsGetAccountFamilyRequest(),
            new LinkedAccountsGetLinkageStatusRequest(),
            new LoomFetchConfigRequest(),
            new MediaBlockedRequest(),
            new FeedTimelineRequest(),
            new FeedReelsTrayRequest(),
            new UsersArlinkDownloadInfoRequest(),
            new DiscoverTopicalExploreRequest().is_prefetch(true),
            new NewsInboxRequest(false),
            new DirectGetPresenceRequest(),
            new DirectInboxRequest().limit(0).visual_message_return_type("unseen")
                    .persistent_badging(true),
            new DirectInboxRequest().limit(20).fetch_reason("initial_snapshot")
                    .thread_message_limit(10).visual_message_return_type("unseen")
                    .persistent_badging(true),
            new StatusGetViewableStatusesRequest()
    };

    public List<CompletableFuture<?>> preLoginFlow() {
        return AsyncAction.executeRequestsAsync(client, preLoginFlow);
    }

    public List<CompletableFuture<?>> postLoginFlow() {
        return AsyncAction.executeRequestsAsync(client, postLoginFlow);
    }
}
