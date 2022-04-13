package com.thucnobita.instaapi.model.story;

import com.google.gson.annotations.SerializedName;

public class StorySlider extends BaseStoryItem {

    @SerializedName("slider_sticker")
    private SliderSticker sliderSticker;

    public SliderSticker getSliderSticker() {
        return sliderSticker;
    }

    public void setSliderSticker(SliderSticker sliderSticker) {
        this.sliderSticker = sliderSticker;
    }
}
