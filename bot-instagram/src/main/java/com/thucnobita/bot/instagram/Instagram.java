package com.thucnobita.bot.instagram;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.Selector;

import java.util.List;

public class Instagram {
    private AutomatorServiceImpl automatorService;
    private Data data;
    public enum ACTION{
        click_profile,
        click_options,
        click_saved,
        get_videos_of_saved,
    }

    public Instagram(AutomatorServiceImpl automatorService){
        this.automatorService = automatorService;
        this.data = new Data(automatorService);
    }

    public Object action(ACTION action, Object[] params) throws UiObjectNotFoundException, JsonProcessingException {
        switch (action){
            case click_profile:
                return this.click(data.selector_profile());
            case click_options:
                return this.click(data.selector_options());
            case click_saved:
                List<Selector> listSelector = data.selector_saved();
                if(this.click(listSelector.get(0))){
                    if(this.click(listSelector.get(1))){
                        if(this.waitExist(listSelector.get(3), 2000L)){
                            return this.click(listSelector.get(4));
                        }
                    }
                    return false;
                }
            case get_videos_of_saved:
                return data.get_videos_of_saved();
            default:
                return null;
        }
    }

    private boolean click(Selector selector) throws UiObjectNotFoundException {
        return this.automatorService.click(selector);
    }

    private boolean waitExist(Selector selector, long waitTime){
        return this.automatorService.waitForExists(selector, waitTime);
    }
}
