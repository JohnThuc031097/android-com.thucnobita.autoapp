package com.thucnobita.autoapp.instagram;

import android.os.Build;
import android.os.RemoteException;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Utils;
import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.Selector;

import java.util.ArrayList;

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

    public boolean post_reel(String content) throws UiObjectNotFoundException, InterruptedException, RemoteException {
        ArrayList<Selector> arrSelector = selectors.post_reel();
        if(content != null){
            automatorService.setText(arrSelector.get(0), content);
        }
        Thread.sleep(2000);
        automatorService.pressKey("back"); // Hide keybroad
        Thread.sleep(1000);
        Selector selector = new Selector(automatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/tabs_viewpager");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        automatorService.swipe(selector, "u", 1000);
        Thread.sleep(2000);
        if(click(arrSelector.get(1), 5)){ // Click "Share" to begin post
            Thread.sleep(1000);
            if(click(arrSelector.get(2), 5)){ // Click return Home
//                if(waitGone(arrSelector.get(4), 60 * 5)){ // Wait for process done (time wait default: 5 min)
//                    return waitGone(arrSelector.get(5), 60 * 5);
//                }
                Thread.sleep(2000);
                return waitGone(arrSelector.get(3), 60 * 10); // Wait for process done (time wait default: 10 min)
            }
        }
        return false;
    }

    public boolean post_feed(String content) throws UiObjectNotFoundException, InterruptedException, RemoteException {
        ArrayList<Selector> arrSelector = selectors.post_feed();
        if(content != null){
            automatorService.setText(arrSelector.get(0), content);
        }
        Thread.sleep(2000);
        automatorService.pressKey("back"); // Hide keybroad
        Thread.sleep(1000);
        if(click(arrSelector.get(1), 5)){ // Click "Share" to begin post
            Thread.sleep(1000);
            if(click(arrSelector.get(2), 5)){ // Click return Home
//                if(waitGone(arrSelector.get(3), 60 * 5)){ // Wait for process done (time wait default: 5 min)
//                    return waitGone(arrSelector.get(5), 60 * 5);
//                }
                Thread.sleep(2000);
                return waitGone(arrSelector.get(3), 60 * 10); // Wait for process done (time wait default: 10 min)
            }
        }
        return false;
    }

    public boolean share_video_to_reel(String folderShare) throws UiObjectNotFoundException, InterruptedException {
        ArrayList<Selector> arrSelector = selectors.share_video_to_reel(folderShare);
        if(click_profile()){
            Thread.sleep(2000);
            if(click(arrSelector.get(0), 5)){
                Thread.sleep(2000);
                if(click(arrSelector.get(1), 5)){
                    Thread.sleep(2000);
                    if(click(arrSelector.get(2), 5)){
                        Thread.sleep(2000);
                        if(click(arrSelector.get(3), 5)){
                            Thread.sleep(2000);
                            if(click(arrSelector.get(4), 5)){ // Select folder
                                Thread.sleep(2000);
                                if(click(arrSelector.get(5), 5)){ // Select video index "0" (default)
                                    Thread.sleep(3000);
                                    if(click(arrSelector.get(6), 5)){ // Click "Add"
                                        Thread.sleep(3000);
                                        if(waitGone(arrSelector.get(6), 60 * 2)){
                                            Thread.sleep(3000);
                                            if(click(arrSelector.get(7), 5)){ // Click "Preview"
                                                Thread.sleep(3000);
                                                if(waitGone(arrSelector.get(7), 60 * 2)){
                                                    Thread.sleep(3000);
                                                    return click(arrSelector.get(8), 5); // Click "Next" to the end share video
                                                }
                                            }
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
    }

    public boolean share_video_image_to_feed(String folderShare, int totalImage) throws UiObjectNotFoundException, InterruptedException {
        ArrayList<Selector> arrSelector = selectors.browse_select_video_image_to_post(folderShare);
        if(click_profile()){
            Thread.sleep(2000);
            if(click(arrSelector.get(0), 5)){ // Click create post
                Thread.sleep(2000);
                if(click(arrSelector.get(1), 5)){ // Click type = post
                    Thread.sleep(2000);
                    if(click(arrSelector.get(2), 5)){ // Click tab Gallery
                        Thread.sleep(2000);
                        if(click(arrSelector.get(3), 5)){ // Click comboBox Gallery
                            Thread.sleep(2000);
                            if(click(arrSelector.get(4), 5)){ // Select folder share
                                Thread.sleep(3000);
                                if(click(selectors.btn_select_mutiple_file(), 5)){ // Click select check mutiple file
                                    Thread.sleep(2000);
                                    Selector selector = new Selector(automatorService.getInstrumentation());
                                    selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
                                    selector.setClassName("android.widget.CheckBox");
                                    selector.setDescriptionStartsWith("Photo thumbnail, Added on");
                                    selector.setMask(Selector.MASK_PACKAGENAME
                                            | Selector.MASK_CLASSNAME
                                            | Selector.MASK_DESCRIPTIONSTARTSWITH);
                                    int images = totalImage;// -1 for mode test
                                    while (images > 0){
                                        if(!click(selector, 5)){
                                            return false;
                                        }
                                        images--;
                                        Thread.sleep(2000);
                                    }
                                    Thread.sleep(3000);
                                    Selector selectorBtnNext = selectors.share_video_image_to_feed();
                                    if(click(selectorBtnNext, 5)){ // Click button Next
                                        Thread.sleep(5000);
                                        if(click(selectorBtnNext, 5)){  // Click button Next
                                            Thread.sleep(5000);
                                            return waitGone(selectorBtnNext, 60 * 5);
                                        }
                                    }
                                }
//                                int totalFile = selectors.get_total_video_image_to_post(); // Get all file in browse
                                // INDEX_MODE:
                                // + Test = 0
                                // + Build = 1
//                                int INDEX_MODE = 1;
//                                int INDEX_ADD = automatorService.exist(arrSelector.get(5)) ? 1 : 0;
//                                int INDEX_TOTAL = totalImage + INDEX_ADD + INDEX_MODE; // Get all file in browse
//                                int MAX_GIRD_WIDTH = 4;
//                                int INDEX_SKIP = 1;
//                                int INDEX_START = 1 + INDEX_ADD;
//                                boolean isReset = INDEX_ADD != 1;
//                                if(INDEX_TOTAL > 0){
//                                    if(click(selectors.btn_select_mutiple_file(), 5)){ // Click select check mutiple file
//                                        Thread.sleep(2000);
//                                        Selector selector = new Selector(automatorService.getInstrumentation());
//                                        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
//                                        selector.setClassName("android.widget.CheckBox");
//                                        selector.setDescriptionStartsWith("Photo thumbnail, Added on");
//                                        selector.setMask(Selector.MASK_PACKAGENAME
//                                                | Selector.MASK_CLASSNAME
//                                                | Selector.MASK_DESCRIPTIONSTARTSWITH);
//                                        int total = totalImage;// -1 for mode test
//                                        while (total > 0){
//                                            if(!click(selector, 5)){
//                                                return false;
//                                            }
//                                            total--;
//                                            Thread.sleep(2000);
//                                        }
//                                        for (int i = INDEX_START; i < INDEX_TOTAL; i++) {
//                                            if(!isReset){
//                                                if(i == 3){
//                                                    isReset = true;
//                                                    if(!automatorService.exist(arrSelector.get(5))){
//                                                        INDEX_TOTAL = INDEX_TOTAL - (i - 1);
//                                                        i = 1;
//                                                        continue;
//                                                    }
//                                                }
//                                            }
//                                            if(i == (MAX_GIRD_WIDTH + INDEX_SKIP)){
//                                                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.R){
//                                                    INDEX_TOTAL = INDEX_TOTAL - i;
//                                                    if(INDEX_TOTAL > 0){
//                                                        i = 1;
//                                                        INDEX_TOTAL = INDEX_TOTAL + 1;
//                                                    }
//                                                }
//                                            }
//                                            Selector selector = new Selector(automatorService.getInstrumentation());
//                                            selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
//                                            selector.setClassName("android.widget.CheckBox");
//                                            selector.setIndex(i);
//                                            selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_INDEX);
//                                            if(!click(selector, 5)){
//                                                return false;
//                                            }
//                                            Thread.sleep(2000);
//                                        }
//                                        Thread.sleep(3000);
//                                        Selector selectorBtnNext = selectors.share_video_image_to_feed();
//                                        if(click(selectorBtnNext, 5)){ // Click button Next
//                                            Thread.sleep(5000);
//                                            if(click(selectorBtnNext, 5)){  // Click button Next
//                                                Thread.sleep(5000);
//                                                return waitGone(selectorBtnNext, 60 * 5);
//                                            }
//                                        }
//                                    }
//                                }
                            }
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
            Thread.sleep(2000);
            if(click(arrSelector.get(1), 5)){
                Thread.sleep(2000);
                if(waitGone(arrSelector.get(1), 5)){
                    Thread.sleep(2000);
                    return true;
                }else{
                    Thread.sleep(2000);
                    return automatorService.pressKey("back");
                }
            }
        }
        return false;
    }

    public boolean click_profile() throws UiObjectNotFoundException, InterruptedException {
        Thread.sleep(2000);
        return click(selectors.profile(), 5);
    }

    public boolean click_options() throws UiObjectNotFoundException, InterruptedException {
        Thread.sleep(2000);
        return click(selectors.options(), 5);
    }

    public boolean click_saved() throws UiObjectNotFoundException, InterruptedException {
        ArrayList<Selector> arrSelector = selectors.saved();
        if (click(arrSelector.get(0), 5)) {
            Thread.sleep(2000);
            if (click(arrSelector.get(1), 5)) {
                Thread.sleep(2000);
                return waitGone(arrSelector.get(2), 60);
            }
        }
        return false;
    }

    public boolean click_video_saved() throws UiObjectNotFoundException, InterruptedException {
        ArrayList<String> idObjs = selectors.videos_saved();
        int size = idObjs.size();
        if(size > 0){
            Thread.sleep(1000);
            return click(idObjs.get(Utils.randInt(0, size - 1)), 5);
        }
        return false;
    }

    public String get_username_video_saved() throws UiObjectNotFoundException, InterruptedException {
        Thread.sleep(2000);
        return automatorService.getText(selectors.username_video_saved());
    }

    public boolean click_copy_link_video_saved() throws UiObjectNotFoundException, InterruptedException, RemoteException {
        ArrayList<Selector> arrSelector = selectors.copy_remove_link_video_saved();
        Thread.sleep(2000);
        Selector selectorSelectVideo = arrSelector.get(0);
        if(selectorSelectVideo != null){
            if (click(selectorSelectVideo, 5)) { // Select video
//            Thread.sleep(1000);
//            click(selectorSelectVideo, 5); // use for emulator
                Thread.sleep(1000);
                click(arrSelector.get(1), 1); // Pause video
                Thread.sleep(1000);
                Selector selectorMore = automatorService.exist(arrSelector.get(2))
                        ? arrSelector.get(2)
                        : arrSelector.get(3);
                if(click(selectorMore, 5)){ // Show More
                    Thread.sleep(3000);
                    Selector selectorCopyLink = automatorService.exist(arrSelector.get(4))
                            ? arrSelector.get(4)
                            : automatorService.exist(arrSelector.get(5))
                            ? arrSelector.get(5)
                            : arrSelector.get(6);
                    if (click(selectorCopyLink, 5)) { // 1.Copy link
                        Thread.sleep(3000);
//                        return automatorService.pressKey("back");
                        if (click(arrSelector.get(7), 5)) { // Again Select video
                            Thread.sleep(3000);
                            if (click(selectorMore, 5)) { // Show More
                                Thread.sleep(3000);
                                Selector selectorRemoveSaved = automatorService.exist(arrSelector.get(8))
                                        ? arrSelector.get(8)
                                        : automatorService.exist(arrSelector.get(9))
                                        ? arrSelector.get(9)
                                        : arrSelector.get(10);
                                if (click(selectorRemoveSaved, 5)){ // 2.Remove video
                                    Thread.sleep(3000);
                                    return automatorService.pressKey("back");
                                }
                            }
                        }
                    }
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
