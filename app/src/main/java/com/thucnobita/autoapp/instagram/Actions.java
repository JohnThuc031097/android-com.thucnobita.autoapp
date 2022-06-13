package com.thucnobita.autoapp.instagram;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Utils;
import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.ObjInfo;
import com.thucnobita.uiautomator.Selector;

import java.util.ArrayList;

public class Actions {
    private AutomatorServiceImpl automatorService;
    private Data selectors;

    public Actions(AutomatorServiceImpl automatorService){
        this.automatorService = automatorService;
        this.selectors = new Data(automatorService);
    }

    public boolean comment_post(String username, String data) throws UiObjectNotFoundException, RemoteException, InterruptedException {
        if(click(selectors.btn_view_posts(), 5)){
            Selector selectorPostsRow1 = new Selector(automatorService.getInstrumentation());
            selectorPostsRow1.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
            selectorPostsRow1.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/media_set_row_content_identifier");
            selectorPostsRow1.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
            ObjInfo[] objInfoPosts = automatorService.objInfoOfAllInstances(selectorPostsRow1);
            Thread.sleep(2000);
            if(objInfoPosts.length > 0){
                ObjInfo objInfoPost1 = objInfoPosts[0];
                if(!automatorService.click(objInfoPost1.getBounds().getLeft(), objInfoPost1.getBounds().getTop())) return false;
                Thread.sleep(5000);
                String dataValidateComment = String.format("%s %s", username, data);
                boolean findSelectorComment = false;
                Selector selectorCommentInputData;
                Selector selectorCommentValidateData;
                Thread.sleep(2000);
                if(!click(findSelector(selectors.btn_comment_post()), 5)){
                    Thread.sleep(2000);
                    if(click(selectors.get_media_group(), 5)){
                        Thread.sleep(2000);
                        if(click(findSelector(selectors.btn_comment_post()), 5)){
                            findSelectorComment = true;
                        }
                    }
                }
                Thread.sleep(5000);
                if(findSelectorComment){
                    selectorCommentInputData = selectors.comment_input_data();
                    if(automatorService.exist(selectorCommentInputData)){
                        Thread.sleep(2000);
                        if(automatorService.setText(selectorCommentInputData, data)){
                            Thread.sleep(5000);
                            if(automatorService.pressKey("enter")){
                                Thread.sleep(5000);
                                selectorCommentValidateData = selectors.comment_validate_data(null);
                                if(automatorService.exist(selectorCommentValidateData)){
                                    Thread.sleep(2000);
                                    automatorService.setText(selectorCommentValidateData, dataValidateComment);
                                    Thread.sleep(5000);
                                    return automatorService.exist(selectors.comment_validate_data(dataValidateComment));
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean post_to_timeline(String content) throws UiObjectNotFoundException, InterruptedException, RemoteException {
        Selector selectorInputContent = selectors.post_input_content();
        if(content != null){
            Thread.sleep(2000);
            automatorService.setText(selectorInputContent, content);
        }
        Thread.sleep(2000);
        automatorService.pressKey("back"); // Hide keybroad
        Thread.sleep(2000);
        Selector selector = new Selector(automatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/tabs_viewpager");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        if(automatorService.exist(selector)){
            automatorService.swipe(selector, "u", 1000);
            Thread.sleep(2000);
        }
        // Click "Share" to begin post
        Selector selectorClickUpload = findSelector(selectors.post_click_upload());
        if(click(selectorClickUpload, 5)){
            Thread.sleep(2000);
//            if(click(selectors.tab_feed(), 5)){
//                Thread.sleep(2000);
//                // Wait for process done (time wait default: 10 min)
//                Selector selectorWaitDone = selectors.post_wait_done_after_upload();
//                return waitGone(selectorWaitDone, 60 * 10);
//            }
            click(selectors.tab_feed(), 5); // Click return tab feed
            Thread.sleep(2000);
            // Wait for process done (time wait default: 10 min)
            Selector selectorWaitDone = selectors.post_wait_done_after_upload();
            return waitGone(selectorWaitDone, 60 * 10);

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

    public boolean share_image_to_story(String folderShare, String linkSticker, int totalImage) throws UiObjectNotFoundException, InterruptedException {
        ArrayList<Selector> arrSelector = selectors.share_story();
        if(click(arrSelector.get(0), 5)){ // Choose mode create post
            Thread.sleep(2000);
            if(click(arrSelector.get(1), 5)){ // Select mode "Story"
                Thread.sleep(2000);
                if(click(findSelector(selectors.share_story_gallery_folder()), 5)) { // Choose folder
                    Thread.sleep(2000);
                    if(click(selectors.share_story_select_folder(folderShare), 5)) { // Select folderShare
                        Thread.sleep(2000);
                        int images = totalImage;
                        if(images > 0){
                            if (click(selectors.share_story_btn_multiple_select(), 5)) {
                                Thread.sleep(2000);
                                ObjInfo[] items = selectors.share_story_get_items();
                                Thread.sleep(1000);
                                if(items.length > 0){
                                    for (ObjInfo item: items) {
                                        if(!automatorService.click(item.getBounds().getLeft(), item.getBounds().getTop())) return false;
                                        Thread.sleep(1500);
                                    }
                                }
                            }
                        }
                        Thread.sleep(3000);
                        if(click(selectors.share_story_btn_next(), 5)){
                            Thread.sleep(2000);
                            if(click(selectors.share_story_select_type(), 5)){
                                Thread.sleep(5000);
                                if(click(selectors.share_story_select_type_sticker(), 5)){
                                    Thread.sleep(2000);
                                    if(automatorService.exist(selectors.share_story_search_sticker())){
                                        Thread.sleep(2000);
                                        if(automatorService.setText(selectors.share_story_search_sticker(), "link")){
                                            Thread.sleep(5000);
                                            if(click(selectors.share_story_select_type_link_sticker(), 5)){
                                                Thread.sleep(2000);
                                                if(automatorService.exist(selectors.share_story_add_link_sticker())){
                                                    Thread.sleep(2000);
                                                    if(automatorService.setText(selectors.share_story_add_link_sticker(), linkSticker)){
                                                        Thread.sleep(5000);
                                                        if(click(selectors.share_story_done_add_link_sticker(), 5)){
                                                            Thread.sleep(2000);
                                                            if(click(selectors.share_story_share(), 5)){ // Click share
                                                                Thread.sleep(5000);
                                                                if(click(selectors.tab_your_story(), 5)){ // Click tab your story
                                                                    Thread.sleep(2000);
                                                                    // Wait for share to story donw (default: 10 min)
                                                                    return waitGone(selectors.share_story_wait_done(), 60 * 10);
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
                    }
                }else{

                }
            }
        }
        return false;
    }

    public boolean share_video_image_to_feed(String folderShare, int totalImage, boolean onlyImage) throws UiObjectNotFoundException, InterruptedException {
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
                                int images = onlyImage ? (totalImage - 1) : totalImage;
                                    if(images > 0) {
                                        if (click(selectors.btn_select_multiple_file(), 5)) { // Click select check mutiple file
                                            Selector selector = new Selector(automatorService.getInstrumentation());
                                            selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
                                            selector.setClassName("android.widget.CheckBox");
                                            selector.setDescriptionStartsWith("Photo thumbnail, Added on");
                                            selector.setMask(Selector.MASK_PACKAGENAME
                                                    | Selector.MASK_CLASSNAME
                                                    | Selector.MASK_DESCRIPTIONSTARTSWITH);
                                            Thread.sleep(2000);
                                            while (images != 0){
                                                if(!click(selector, 5)){
                                                    return false;
                                                }
                                                images--;
                                                Thread.sleep(1500);
                                            }
                                        }
                                    }
                                    Thread.sleep(3000);
                                    if(click(findSelector(selectors.share_video_image_to_feed()), 5)){ // Click button Next
                                        Thread.sleep(2500);
                                        click(findSelector(selectors.btn_continue()), 5);
                                        Thread.sleep(2500);
                                        Selector selectorBtnNext = findSelector(selectors.share_video_image_to_feed());
                                        if(click(selectorBtnNext, 5)){  // Click button Next
                                            Thread.sleep(5000);
                                            return waitGone(selectorBtnNext, 60 * 5);
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
        return click(selectors.tab_profile(), 5);
    }

    public boolean click_options() throws UiObjectNotFoundException, InterruptedException {
        Thread.sleep(2000);
        return click(selectors.btn_options(), 5);
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
//                Thread.sleep(1000);
//                click(selectorSelectVideo, 5); // use for emulator
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

    private Selector findSelector(ArrayList<Selector> arrSelector){
        for (Selector selector: arrSelector) {
            if(automatorService.exist(selector)) return selector;
        }
        return null;
    }

    private boolean click(Selector selector, long timeWaitExist) throws UiObjectNotFoundException {
        return selector != null && (waitExist(selector, timeWaitExist) && automatorService.click(selector));
    }

    private boolean click(String obj, long timeWaitExist) throws UiObjectNotFoundException {
        return waitExist(obj, timeWaitExist) && automatorService.click(obj);
    }

    private boolean waitExist(Selector selector, long timeWaitExist){
        return selector != null && (automatorService.waitForExists(selector, timeWaitExist * 1000));
    }

    private boolean waitExist(String obj, long waitTime) throws UiObjectNotFoundException {
        return automatorService.waitForExists(obj, waitTime * 1000);
    }

    private boolean waitGone(Selector selector, long timeWaitGone){
        return selector != null && (automatorService.waitUntilGone(selector, timeWaitGone * 1000));
    }
}
