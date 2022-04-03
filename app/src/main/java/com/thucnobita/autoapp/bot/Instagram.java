package com.thucnobita.autoapp.bot;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.Selector;

public class Instagram {
    public enum ACTION{
        LICK_PROFILE
    }
    private final AutomatorServiceImpl mAutomatorService;

    public Instagram(AutomatorServiceImpl automatorService){
        this.mAutomatorService = automatorService;
    }

    public Object action(ACTION action) throws UiObjectNotFoundException {
        switch (action){
            case LICK_PROFILE:
                return clickProfile();
            default:
                return null;
        }
    }

    private boolean clickProfile() throws UiObjectNotFoundException {
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.INSTAGRAM_PACKAGE_NAME);
        selector.setDescription("Profile");
        selector.setResourceId("com.instagram.android:id/profile_tab");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_DESCRIPTION | Selector.MASK_RESOURCEID);
        return mAutomatorService.click(selector);
    }

}
