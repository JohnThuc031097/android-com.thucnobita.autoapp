package com.thucnobita.api.instagram.requests.highlights;

import com.thucnobita.api.instagram.requests.IGGetRequest;
import com.thucnobita.api.instagram.responses.highlights.HighlightsUserTrayResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HighlightsUserTrayRequest extends IGGetRequest<HighlightsUserTrayResponse> {
    @NonNull
    private Long pk;

    @Override
    public String path() {
        return "highlights/" + pk + "/highlights_tray/";
    }

    @Override
    public Class<HighlightsUserTrayResponse> getResponseType() {
        return HighlightsUserTrayResponse.class;
    }

}
