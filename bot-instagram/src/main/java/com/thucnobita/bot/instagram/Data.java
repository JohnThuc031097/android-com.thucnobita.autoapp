package com.thucnobita.bot.instagram;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.ObjInfo;
import com.thucnobita.uiautomator.Selector;

import java.util.ArrayList;
import java.util.List;

class Data {
    private final AutomatorServiceImpl mAutomatorService;
    private Utils utils;

    public Data(AutomatorServiceImpl automatorService){
        this.mAutomatorService = automatorService;
        this.utils = new Utils();
    }

    public String get_videos_of_saved() throws UiObjectNotFoundException, JsonProcessingException {
        Selector selectorParent = new Selector(mAutomatorService.getInstrumentation());
        selectorParent.setPackageName(Configs.PACKAGE_NAME);
        selectorParent.setClassName("androidx.recyclerview.widget.RecyclerView");
        selectorParent.setResourceId(Configs.PACKAGE_NAME + ":id/clips_tab_grid_recyclerview");
        selectorParent.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_RESOURCEID);
        Selector selectorChild = new Selector(mAutomatorService.getInstrumentation());
        selectorChild.setPackageName(Configs.PACKAGE_NAME);
        selectorChild.setClassName("android.widget.RelativeLayout");
        selectorChild.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME);
        return utils.object2String(mAutomatorService.objInfo(selectorParent));
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
