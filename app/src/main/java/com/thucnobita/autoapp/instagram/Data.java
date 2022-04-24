package com.thucnobita.autoapp.instagram;

import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.ObjInfo;
import com.thucnobita.uiautomator.Selector;

import java.util.ArrayList;

class Data {
    private final AutomatorServiceImpl mAutomatorService;

    public Data(AutomatorServiceImpl automatorService){
        this.mAutomatorService = automatorService;
    }

    public ArrayList<Selector> copy_remove_link_video_saved(){
        ArrayList<Selector> selectors = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.FrameLayout");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/zoomable_view_container");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        if(!mAutomatorService.exist(selector)) {
            selector.setResourceId(Config.PACKAGE_NAME + ":id/media_content_location");
        }
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.ImageView");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/play_pause_button");
        selector.setDescription("Play or pause");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID | Selector.MASK_DESCRIPTION);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.ImageView");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/more_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.Button");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/action_sheet_row_text_view");
        selector.setText("Copy Link");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID | Selector.MASK_TEXT);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.FrameLayout");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/layout_container_main");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.Button");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/action_sheet_row_text_view");
        selector.setText("Remove from saved");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID | Selector.MASK_TEXT);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setResourceId(Config.PACKAGE_NAME + ":id/back_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setResourceId(Config.PACKAGE_NAME + ":id/action_bar_button_back");
        selector.setDescription("Back");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_RESOURCEID | Selector.MASK_DESCRIPTION);
        selectors.add(selector);
        return selectors;
    }

    public ArrayList<String> videos_saved() {
        ArrayList<String> idObjs = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.Button");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/image_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
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
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.TextView");
        selector.setText("Saved");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/menu_option_text");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_TEXT | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.FrameLayout");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/saved_collection_thumbnail");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.ScrollView");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/empty");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        return selectors;
    }

    public Selector options(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.Button");
        selector.setDescription("Options");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION);
        return selector;
    }

    public Selector profile(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Config.PACKAGE_NAME);
        selector.setClassName("android.widget.FrameLayout");
        selector.setDescription("Profile");
        selector.setResourceId(Config.PACKAGE_NAME + ":id/profile_tab");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION | Selector.MASK_RESOURCEID);
        return selector;
    }

}
