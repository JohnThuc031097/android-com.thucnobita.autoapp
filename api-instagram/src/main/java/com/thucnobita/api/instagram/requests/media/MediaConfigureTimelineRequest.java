package com.thucnobita.api.instagram.requests.media;

import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.models.IGPayload;
import com.thucnobita.api.instagram.models.location.Location;
import com.thucnobita.api.instagram.models.media.UserTags.UserTagPayload;
import com.thucnobita.api.instagram.requests.IGPostRequest;
import com.thucnobita.api.instagram.responses.media.MediaResponse.MediaConfigureTimelineResponse;
import com.thucnobita.api.instagram.utils.IGUtils;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
public class MediaConfigureTimelineRequest extends IGPostRequest<MediaConfigureTimelineResponse> {
    @NonNull
    private MediaConfigurePayload payload;

    @Override
    protected IGPayload getPayload(IGClient client) {
        return payload;
    }

    @Override
    public String path() {
        return "media/configure/";
    }

    @Override
    public Class<MediaConfigureTimelineResponse> getResponseType() {
        return MediaConfigureTimelineResponse.class;
    }

    @Data
    @Accessors(fluent = true)
    @JsonAutoDetect(fieldVisibility = Visibility.ANY)
    @JsonInclude(Include.NON_NULL)
    @Setter
    public static class MediaConfigurePayload extends IGPayload {
        private String upload_id;
        private String caption = "";
        private String disable_comments;
        private String location;
        private String usertags;

        public MediaConfigurePayload location(Location loc) {
            Location payloadLoc = new Location();

            payloadLoc.setExternal_id(loc.getExternal_id());
            payloadLoc.setName(loc.getName());
            payloadLoc.setAddress(loc.getAddress());
            payloadLoc.setLat(loc.getLat());
            payloadLoc.setLng(loc.getLng());
            payloadLoc.setExternal_source(loc.getExternal_source());
            payloadLoc.put(payloadLoc.getExternal_source() + "_id", payloadLoc.getExternal_id());
            this.location = IGUtils.objectToJson(payloadLoc);
            this.put("geotag_enabled", "1");
            this.put("posting_latitude", payloadLoc.getLat().toString());
            this.put("posting_longitude", payloadLoc.getLng().toString());
            this.put("media_latitude", payloadLoc.getLat().toString());
            this.put("media_longitude", payloadLoc.getLng().toString());

            return this;
        }

        public MediaConfigurePayload usertags(UserTagPayload... tags) {
            this.usertags = IGUtils.objectToJson(Collections.singletonMap("in", tags));

            return this;
        }

        public String usertags() {
            return this.usertags;
        }

    }

}
