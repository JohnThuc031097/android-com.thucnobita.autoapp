package com.thucnobita.api.instagram.responses.creatives;

import java.util.List;

import com.thucnobita.api.instagram.models.creatives.StaticSticker;
import com.thucnobita.api.instagram.responses.IGResponse;

import lombok.Data;

@Data
public class CreativesAssetsResponse extends IGResponse {
    private List<StaticSticker> static_stickers;
}
