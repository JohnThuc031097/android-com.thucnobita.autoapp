package com.thucnobita.bot.instagram;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.ObjInfo;
import com.thucnobita.uiautomator.Selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Instagram {
    private AutomatorServiceImpl automatorService;
    private Data data;
    public enum ACTION{
        // Click
        click_profile,
        click_options,
        click_saved,
        click_video_saved,
        // Get
        get_count_videos_saved,
        // Test
        test_get_videos_saved,
    }

    public Instagram(AutomatorServiceImpl automatorService){
        this.automatorService = automatorService;
        this.data = new Data(automatorService);
    }

    public Object action(ACTION action, Object value) throws UiObjectNotFoundException {
        switch (action){
            case click_profile:
                return this.click(data.selector_profile());
            case click_options:
                return this.click(data.selector_options());
            case click_saved:
                List<Selector> listSelector = data.selector_saved();
                if(this.click(listSelector.get(0))){
                    if(this.click(listSelector.get(1))){
                        if(this.waitExist(listSelector.get(2), 2000L)){
                            if(this.click(listSelector.get(3))){
                                return this.waitGone(listSelector.get(4), 5000L);
                            }
                        }
                    }
                    return false;
                }
            case click_video_saved:
                if(value != null){
                    ArrayList<String> videos = data.get_videos_saved();
                    if(!videos.isEmpty()){
                        int index = (int) value;
                        if(index >= 0 && index < videos.size()){
                            return this.click(videos.get(index));
                        }
                    }
                }
                return false;
            case get_count_videos_saved:
                ArrayList<String> videos = data.get_videos_saved();
                return videos.isEmpty() ? 0 : videos.size();
            default:
                return null;
        }
    }

    private boolean click(Selector selector) throws UiObjectNotFoundException {
        return this.automatorService.click(selector);
    }

    private boolean click(String obj) throws UiObjectNotFoundException {
        return this.automatorService.click(obj);
    }

    private boolean waitExist(Selector selector, long waitTime){
        return this.automatorService.waitForExists(selector, waitTime);
    }

    private boolean waitGone(Selector selector, long waitTime){
        return this.automatorService.waitUntilGone(selector, waitTime);
    }
}
