package com.thucnobita.api.instagram.actions.media;

import java.util.concurrent.CompletableFuture;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.actions.feed.FeedIterable;
import com.thucnobita.api.instagram.requests.media.MediaActionRequest;
import com.thucnobita.api.instagram.requests.media.MediaCommentRequest;
import com.thucnobita.api.instagram.requests.media.MediaConfigureSidecarRequest;
import com.thucnobita.api.instagram.requests.media.MediaConfigureSidecarRequest.MediaConfigureSidecarPayload;
import com.thucnobita.api.instagram.requests.media.MediaConfigureTimelineRequest;
import com.thucnobita.api.instagram.requests.media.MediaConfigureTimelineRequest.MediaConfigurePayload;
import com.thucnobita.api.instagram.requests.media.MediaConfigureToIgtvRequest;
import com.thucnobita.api.instagram.requests.media.MediaEditRequest;
import com.thucnobita.api.instagram.requests.media.MediaGetCommentsRequest;
import com.thucnobita.api.instagram.requests.media.MediaInfoRequest;
import com.thucnobita.api.instagram.responses.IGResponse;
import com.thucnobita.api.instagram.responses.media.MediaCommentResponse;
import com.thucnobita.api.instagram.responses.media.MediaGetCommentsResponse;
import com.thucnobita.api.instagram.responses.media.MediaInfoResponse;
import com.thucnobita.api.instagram.responses.media.MediaResponse;
import com.thucnobita.api.instagram.responses.media.MediaResponse.MediaConfigureSidecarResponse;
import com.thucnobita.api.instagram.responses.media.MediaResponse.MediaConfigureTimelineResponse;
import com.thucnobita.api.instagram.responses.media.MediaResponse.MediaConfigureToIgtvResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MediaAction {
    @NonNull
    private IGClient client;
    @NonNull
    private String media_id;
    
    public CompletableFuture<MediaCommentResponse> comment(String comment) {
        return new MediaCommentRequest(media_id, comment).execute(client);
    }
    
    public CompletableFuture<MediaResponse> editCaption(String caption) {
        return new MediaEditRequest(media_id, caption).execute(client);
    }
    
    public CompletableFuture<MediaInfoResponse> info() {
        return new MediaInfoRequest(media_id).execute(client);
    }
    
    public FeedIterable<MediaGetCommentsRequest, MediaGetCommentsResponse> comments() {
        return new FeedIterable<>(client, () -> new MediaGetCommentsRequest(media_id));
    }
    
    public CompletableFuture<IGResponse> action(MediaActionRequest.MediaAction action) {
        return new MediaActionRequest(media_id, action).execute(client);
    }
    
    public static MediaAction of(IGClient client, String media_id) {
        return new MediaAction(client, media_id);
    }
    
    public static CompletableFuture<MediaConfigureTimelineResponse> configureMediaToTimeline(IGClient client, String upload_id, MediaConfigurePayload payload) {
        return new MediaConfigureTimelineRequest(payload.upload_id(upload_id)).execute(client);
    }
    
    public static CompletableFuture<MediaConfigureSidecarResponse> configureAlbumToTimeline(IGClient client, MediaConfigureSidecarPayload payload) {
        return new MediaConfigureSidecarRequest(payload).execute(client);
    }
    
    public static CompletableFuture<MediaConfigureToIgtvResponse> configureToIgtv(IGClient client, String upload_id, String title, String caption, boolean postToFeed) {
        return new MediaConfigureToIgtvRequest(upload_id, title, caption, postToFeed).execute(client);
    }
}
