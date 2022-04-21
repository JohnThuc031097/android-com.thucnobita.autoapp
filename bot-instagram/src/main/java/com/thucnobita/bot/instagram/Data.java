package com.thucnobita.bot.instagram;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.ObjInfo;
import com.thucnobita.uiautomator.Selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class Data {
    private final AutomatorServiceImpl mAutomatorService;
    private final Utils utils;

    public Data(AutomatorServiceImpl automatorService){
        this.mAutomatorService = automatorService;
        this.utils = new Utils();
    }

    public ArrayList<String> get_videos_saved() {
        ArrayList<String> idObjs = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.widget.Button");
        selector.setResourceId(Configs.PACKAGE_NAME + ":id/image_button");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        ObjInfo[] objInfos = mAutomatorService.objInfoOfAllInstances(selector);
        if(objInfos.length > 0){
            for (ObjInfo objInfo: objInfos) {
                Selector selectorChild = new Selector(mAutomatorService.getInstrumentation());
                selectorChild.setPackageName(objInfo.getPackageName());
                selectorChild.setClassName(objInfo.getClassName());
                selectorChild.setDescription(objInfo.getContentDescription());
                selectorChild.setMask(
                        Selector.MASK_PACKAGENAME |
                        Selector.MASK_CLASSNAME |
                        Selector.MASK_DESCRIPTION);
                idObjs.add(mAutomatorService.getUiObject(selectorChild));
            }
        }
        return idObjs;
    }

    public List<Selector> selector_saved(){
        List<Selector> selectors = new ArrayList<>();
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.widget.TextView");
        selector.setDescription("Saved");
        selector.setResourceId(Configs.PACKAGE_NAME + ":id/menu_option_text");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.widget.FrameLayout");
        selector.setResourceId(Configs.PACKAGE_NAME + ":id/saved_collection_thumbnail");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.widget.HorizontalScrollView");
        selector.setResourceId(Configs.PACKAGE_NAME + ":id/save_collection_tab_layout");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.widget.ImageView");
        selector.setResourceId(Configs.PACKAGE_NAME + ":id/profile_tab_icon_view");
        selector.setDescription("Saved posts");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID | Selector.MASK_DESCRIPTION);
        selectors.add(selector);
        selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.view.View");
        selector.setResourceId(Configs.PACKAGE_NAME + ":id/save_collection_tabs_bottom_divider");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        selectors.add(selector);
        return selectors;
    }

    public Selector selector_options(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.widget.Button");
        selector.setDescription("Options");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION);
        return selector;
    }

    public Selector selector_profile(){
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.widget.FrameLayout");
        selector.setDescription("Profile");
        selector.setResourceId(Configs.PACKAGE_NAME + ":id/profile_tab");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION | Selector.MASK_RESOURCEID);
        return selector;
    }

}
