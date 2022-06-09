package com.thucnobita.autoapp.instagram;

import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.ObjInfo;
import com.thucnobita.uiautomator.Selector;

import java.util.ArrayList;

class Data {
    private final AutomatorServiceImpl mAutomatorService;

    public Data(AutomatorServiceImpl automatorService){
        this.mAutomatorService = automatorService;
    }

    public Selector post_input_content(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText("Write a caption…");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT);
        return selector;
    }

    public ArrayList<Selector> post_click_upload(){
        ArrayList<Selector> selectors = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/share_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/next_button_imageview");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/post_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/bb_primary_action");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        return selectors;
    }

    public Selector post_wait_done_after_upload(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/row_pending_container");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        return selector;
    }

    public ArrayList<Selector> share_video_to_reel(String folderShare){
        ArrayList<Selector> selectors = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setClassName("android.widget.Button");
        selector.setDescription("Create New");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setClassName("android.widget.TextView");
        selector.setText("Reel");
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/label");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_TEXT | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/gallery_preview_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/gallery_grid_folder_picker_title");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setClassName("android.widget.TextView");
        selector.setText(folderShare != null ? folderShare : "Instagram");
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/folder_picker_text_view");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_TEXT | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setIndex(0);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/gallery_grid_item_thumbnail");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_INDEX | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText("Add");
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/trim_confirm_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText("Preview");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText("Next");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT);
        selectors.add(selector);
        return selectors;
    }

    public ArrayList<Selector> share_video_image_to_feed(){
        ArrayList<Selector> selectors = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setDescription("Next");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_DESCRIPTION);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText("Next");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT);
        selectors.add(selector);
        return selectors;
    }

    public ArrayList<Selector> browse_select_video_image_to_post(String folderShare){
        ArrayList<Selector> selectors = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setDescription("Create New");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_DESCRIPTION);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText("Post");
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/label");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText("GALLERY");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/gallery_folder_menu");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText(folderShare != null ? folderShare : "Instagram");
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/action_sheet_row_text_view");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT | Selector.MASK_RESOURCEID);
        selectors.add(selector);
//        selector = new Selector(mAutomatorService.getInstrumentation());
//        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
//        selector.setTextStartsWith("Choose from");
//        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/label");
//        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXTSTARTSWITH | Selector.MASK_RESOURCEID);
//        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setIndex(0);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/inner_container");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_INDEX | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        return selectors;
    }

    public int get_total_video_image_to_post() {
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setClassName("android.widget.CheckBox");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME);
        ObjInfo[] objInfos = mAutomatorService.objInfoOfAllInstances(selector);
        int tryAgain = 3;
        while (tryAgain-- > 0 && objInfos.length == 0){
            try {
                Thread.sleep(1000L);
                objInfos = mAutomatorService.objInfoOfAllInstances(selector);
            }catch (Exception e){
                tryAgain = 0;
            }
        }
        return objInfos.length;
    }

    public Selector btn_select_mutiple_file(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setDescription("Select multiple");
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/multi_select_slide_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_DESCRIPTION | Selector.MASK_RESOURCEID);
        return selector;
    }

    public Selector username_video_saved(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/row_feed_photo_profile_name");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        return selector;
    }

    public Selector get_media_group() {
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/media_group");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        ObjInfo[] objInfos = mAutomatorService.objInfoOfAllInstances(selector);
        int tryAgain = 3;
        while (tryAgain-- > 0 && objInfos.length == 0){
            try {
                Thread.sleep(1000);
                objInfos = mAutomatorService.objInfoOfAllInstances(selector);
            }catch (Exception e){
                tryAgain = 0;
            }
        }
        Selector selectorChild = null;
        if(objInfos.length == 1){
            selectorChild = new Selector(mAutomatorService.getInstrumentation());
            selectorChild.setPackageName(objInfos[0].getPackageName());
            selectorChild.setDescription(objInfos[0].getContentDescription());
            selectorChild.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_DESCRIPTION);
        }else if(objInfos.length == 2){
            selectorChild = new Selector(mAutomatorService.getInstrumentation());
            selectorChild.setPackageName(objInfos[1].getPackageName());
            selectorChild.setDescription(objInfos[1].getContentDescription());
            selectorChild.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_DESCRIPTION);
        }
        return selectorChild;
    }

    public ArrayList<Selector> copy_remove_link_video_saved(){
        ArrayList<Selector> selectors = new ArrayList<>();
        selectors.add(get_media_group());
//        Selector selector = new Selector(mAutomatorService.getInstrumentation());
//        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
//        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/media_group");
//        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
//        selectors.add(selector);
//        selector = new Selector(mAutomatorService.getInstrumentation());
//        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
//        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/video_attributes_location_for_tall_tower");
//        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
//        selectors.add(selector);
//        selector = new Selector(mAutomatorService.getInstrumentation());
//        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
//        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/zoomable_view_container");
//        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
//        selectors.add(selector);
//        selector = new Selector(mAutomatorService.getInstrumentation());
//        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
//        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/media_content_location");
//        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
//        selectors.add(selector);
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/play_pause_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setDescription("More");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_DESCRIPTION);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/more_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/action_sheet_row_text_view");
        selector.setText("Copy link");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID | Selector.MASK_TEXT);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/action_sheet_row_text_view");
        selector.setText("Copy Link");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID | Selector.MASK_TEXT);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText("Link");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/layout_container_main");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/action_sheet_row_text_view");
        selector.setText("Remove from saved");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID | Selector.MASK_TEXT);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/action_sheet_row_text_view");
        selector.setText("Remove from Saved");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID | Selector.MASK_TEXT);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText("Unsave");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT);
        selectors.add(selector);
        return selectors;
    }

    public ArrayList<String> videos_saved() {
        ArrayList<String> idObjs = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/image_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        ObjInfo[] objInfos = mAutomatorService.objInfoOfAllInstances(selector);
        int tryAgain = 3;
        while (tryAgain-- > 0 && objInfos.length == 0){
            try {
                Thread.sleep(1000L);
                objInfos = mAutomatorService.objInfoOfAllInstances(selector);
            }catch (Exception e){
                tryAgain = 0;
            }
        }
        if(objInfos.length > 0){
            for (ObjInfo objInfo: objInfos) {
                Selector selectorChild = new Selector(mAutomatorService.getInstrumentation());
                selectorChild.setPackageName(objInfo.getPackageName());
                selectorChild.setClassName(objInfo.getClassName());
                selectorChild.setDescription(objInfo.getContentDescription());
                selectorChild.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION);
                idObjs.add(mAutomatorService.getUiObject(selectorChild));
            }
        }
        return idObjs;
    }

    public ArrayList<Selector> saved(){
        ArrayList<Selector> selectors = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText("Saved");
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/menu_option_text");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/saved_collection_thumbnail");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setClassName("android.widget.ScrollView");
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/empty");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        return selectors;
    }

    public Selector btn_options(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setDescription("Options");
        selector.setMask(Selector.MASK_PACKAGENAME  | Selector.MASK_DESCRIPTION);
        return selector;
    }

    public Selector tab_feed(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/feed_tab");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        return selector;
    }

    public Selector tab_profile(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/profile_tab");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        return selector;
    }

    public ArrayList<Selector> switch_account(String username){
        ArrayList<Selector> selectors = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/action_bar_little_icon_container");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Constants.PACKAGE_NAME_INSTAGRAM);
        selector.setText(username);
        selector.setResourceId(Constants.PACKAGE_NAME_INSTAGRAM + ":id/row_user_textview");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_TEXT | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        return selectors;
    }

}
