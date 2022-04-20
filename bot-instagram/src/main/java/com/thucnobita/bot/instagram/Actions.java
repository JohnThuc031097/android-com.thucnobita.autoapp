package com.thucnobita.bot.instagram;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.Selector;

class Actions {
    private final AutomatorServiceImpl mAutomatorService;

    public Actions(AutomatorServiceImpl automatorService){
        this.mAutomatorService = automatorService;
    }

    public boolean clickSaved() throws UiObjectNotFoundException {
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.widget.TextView");
        selector.setDescription("Saved");
        selector.setResourceId(Configs.PACKAGE_NAME + ":id/menu_option_text");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION);
        return mAutomatorService.click(selector);
    }

    public boolean clickOptions() throws UiObjectNotFoundException {
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.widget.Button");
        selector.setDescription("Options");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION);
        return mAutomatorService.click(selector);
    }

    public boolean clickProfile() throws UiObjectNotFoundException {
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.PACKAGE_NAME);
        selector.setClassName("android.widget.FrameLayout");
        selector.setDescription("Profile");
        selector.setResourceId(Configs.PACKAGE_NAME + ":id/profile_tab");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_DESCRIPTION | Selector.MASK_RESOURCEID);
        return mAutomatorService.click(selector);
    }

}
