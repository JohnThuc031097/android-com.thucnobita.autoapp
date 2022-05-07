package com.thucnobita.autoapp.instagram;

import android.os.RemoteException;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.Selector;

import java.util.ArrayList;
import java.util.Random;

public class Actions {
    private AutomatorServiceImpl automatorService;
    private Data selectors;

    public Actions(AutomatorServiceImpl automatorService){
        this.automatorService = automatorService;
        this.selectors = new Data(automatorService);
    }

    public boolean click_recent_app(String name) throws RemoteException, UiObjectNotFoundException {
        return click(selectors.app_floating_view(), 5) || automatorService.pressKey("recent") && click(selectors.app_current(name), 5);
    }

    public boolean post_feed(String content) throws UiObjectNotFoundException, InterruptedException, RemoteException {
        ArrayList<Selector> arrSelector = selectors.post_feed();
        if(click(arrSelector.get(0), 5)){
            if(click(arrSelector.get(1), 60)){
                if(waitExist(arrSelector.get(2), 30)){
                    if(automatorService.setText(arrSelector.get(2), content)){
                        Thread.sleep(2500);
                        automatorService.pressKey("back");
                        if(click(arrSelector.get(3), 2)){
                            return waitExist(arrSelector.get(4), 60 * 10);
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean click_switch_account(String username) throws UiObjectNotFoundException, InterruptedException, RemoteException {
        ArrayList<Selector> arrSelector = selectors.switch_account(username);
        if(click(arrSelector.get(0), 5)){
            Thread.sleep(1000);
            if(click(arrSelector.get(1), 5)){
                if(waitGone(arrSelector.get(1), 5)){
                }else{
                    automatorService.pressKey("back");
                }
                Thread.sleep(1000);
                return true;
            }
        }
        return false;
    }

    public boolean click_profile() throws UiObjectNotFoundException {
        return click(selectors.profile(), 5);
    }

    public boolean click_options() throws UiObjectNotFoundException {
        return click(selectors.options(), 5);
    }

    public boolean click_saved() throws UiObjectNotFoundException, InterruptedException {
        ArrayList<Selector> arrSelector = selectors.saved();
        if (click(arrSelector.get(0), 5)) {
            Thread.sleep(1000);
            if (click(arrSelector.get(1), 2)) {
                return waitGone(arrSelector.get(2), 10);
            }
        }
        return false;
    }

    public boolean click_video_saved() throws UiObjectNotFoundException {
        ArrayList<String> idObjs = selectors.videos_saved();
        int index = 0;
        if(idObjs.size() > 1) {
            index = (new Random().nextInt(idObjs.size()-1));
        }
        return click(idObjs.get(index), 5);
    }

    public boolean click_copy_link_video_saved() throws UiObjectNotFoundException, InterruptedException {
        ArrayList<Selector> listSelectorLinkVideoSaved = selectors.copy_remove_link_video_saved();
        if (click(listSelectorLinkVideoSaved.get(0), 5)) { // Select video
            Thread.sleep(1000);
            if (automatorService.exist(listSelectorLinkVideoSaved.get(1))) {
                click(listSelectorLinkVideoSaved.get(1), 5);
                Thread.sleep(1500);
            }
            if (click(listSelectorLinkVideoSaved.get(2), 5)) { // Show dialog
                if (click(listSelectorLinkVideoSaved.get(3), 5)) { // 1.Copy link
                    Thread.sleep(1000);
                    return true;
//                    if (click(listSelectorLinkVideoSaved.get(4), 5)) { // Again Select video
//                        if (click(listSelectorLinkVideoSaved.get(2), 5)) { // Show dialog
//                            if (click(listSelectorLinkVideoSaved.get(5), 5)) { // 2.Remove video
//                                Thread.sleep(1000);
////                                if (click(listSelectorLinkVideoSaved.get(4), 5)) { // Again Select video
////                                    if (click(listSelectorLinkVideoSaved.get(6), 5)) { // Back 1
////                                        return click(listSelectorLinkVideoSaved.get(7), 5); // Back 2
////                                    } else {
////                                        if (click(listSelectorLinkVideoSaved.get(7), 5)) { // Back 2
////                                            return click(listSelectorLinkVideoSaved.get(7), 5); // Back 2
////                                        }
////                                    }
////                                }
//                                return click(listSelectorLinkVideoSaved.get(4), 5);
//                            }
//                        }
//                    }
                }
            }
        }
        return false;
    }

    private boolean click(Selector selector, long timeWaitExist) throws UiObjectNotFoundException {
        return waitExist(selector, timeWaitExist) && automatorService.click(selector);
    }

    private boolean click(String obj, long timeWaitExist) throws UiObjectNotFoundException {
        return waitExist(obj, timeWaitExist) && automatorService.click(obj);
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
