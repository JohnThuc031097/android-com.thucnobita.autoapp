package com.thucnobita.bot.instagram;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.Selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Instagram {
    private AutomatorServiceImpl automatorService;
    private com.thucnobita.bot.instagram.Data data;
    public enum ACTION{
        // Click
        click_profile,
        click_options,
        click_saved,
        click_video_saved,
        click_get_link_video_saved,
        // Get
        get_videos_saved,
    }

    public Instagram(AutomatorServiceImpl automatorService){
        this.automatorService = automatorService;
        this.data = new com.thucnobita.bot.instagram.Data(automatorService);
    }

    public Object action(ACTION action, Object ...value) throws UiObjectNotFoundException {
        try {
            switch (action) {
                case click_profile:
                    return click(data.get_selector_profile(), 5);
                case click_options:
                    return click(data.get_selector_options(), 5);
                case click_saved:
                    ArrayList<Selector> listSelectorSaved = data.get_selector_saved();
                    if (click(listSelectorSaved.get(0), 5)) {
                        Thread.sleep(1000);
                        if (click(listSelectorSaved.get(1), 2)) {
                            return waitGone(listSelectorSaved.get(2), 10);
                        }
                    }
                    return false;
                case click_video_saved:
                    if (value.length > 0) {
                        int index = (int) value[0];
                        ArrayList<String> idObjs = (ArrayList<String>) value[1];
                        if (index >= 0 && index < idObjs.size()) {
                            return click(idObjs.get(index), 5);
                        }
                    }
                    return false;
                case click_get_link_video_saved:
                    ArrayList<Selector> listSelectorLinkVideoSaved = data.get_link_video_saved();
                    if (click(listSelectorLinkVideoSaved.get(0), 5)) { // Select video
                        Thread.sleep(1000);
                        if (automatorService.exist(listSelectorLinkVideoSaved.get(1))) {
                            click(listSelectorLinkVideoSaved.get(1), 5);
                            Thread.sleep(1500);
                        }
                        if (click(listSelectorLinkVideoSaved.get(2), 5)) { // Show dialog
                            if (click(listSelectorLinkVideoSaved.get(3), 5)) { // 1.Copy link
                                Thread.sleep(2000);
                                if (click(listSelectorLinkVideoSaved.get(4), 5)) { // Again Select video
                                    if (click(listSelectorLinkVideoSaved.get(2), 5)) { // Show dialog
                                        if (click(listSelectorLinkVideoSaved.get(5), 5)) { // 2.Remove video
                                            Thread.sleep(2000);
                                            if (click(listSelectorLinkVideoSaved.get(4), 5)) { // Again Select video
                                                if (click(listSelectorLinkVideoSaved.get(6), 5)) { // Back 1
                                                    Thread.sleep(500);
                                                    return click(listSelectorLinkVideoSaved.get(7), 5); // Back 2
                                                } else {
                                                    if (click(listSelectorLinkVideoSaved.get(7), 5)) { // Back 2
                                                        Thread.sleep(500);
                                                        return click(listSelectorLinkVideoSaved.get(7), 5); // Back 2
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return false;
                case get_videos_saved:
                    return data.get_videos_saved();
                default:
                    return null;
            }
        } catch (InterruptedException e) {

        }
        return null;
    }

    private boolean click(Selector selector, long timeWaitExist) throws UiObjectNotFoundException {
        if(waitExist(selector, timeWaitExist)){
            return automatorService.click(selector);
        }
        return false;
    }

    private boolean click(String obj, long timeWaitExist) throws UiObjectNotFoundException {
        if(waitExist(obj, timeWaitExist)){
            return automatorService.click(obj);
        }
        return false;
    }

    private boolean waitExist(Selector selector, long timeWaitExist){
        return automatorService.waitForExists(selector, timeWaitExist * 1000L);
    }

    private boolean waitExist(String obj, long waitTime) throws UiObjectNotFoundException {
        return automatorService.waitForExists(obj, waitTime * 1000L);
    }

    private boolean waitGone(Selector selector, long timeWaitGone){
        return automatorService.waitUntilGone(selector, timeWaitGone * 1000L);
    }
}
