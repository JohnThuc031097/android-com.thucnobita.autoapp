package com.thucnobita.api.instagram.responses.highlights;

import java.util.List;

import com.thucnobita.api.instagram.models.highlights.Highlight;
import com.thucnobita.api.instagram.models.igtv.Channel;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class HighlightsUserTrayResponse extends IGResponse {
    private List<Highlight> tray;
    private Channel tv_channel;
}
