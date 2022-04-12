package com.thucnobita.autoapp.bots.instagram;

import android.content.Context;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.Selector;

public class Instagram {
    public enum ACTION{
        CLICK_PROFILE,
    }
    private final AutomatorServiceImpl mAutomatorService;
    private Utils utils;

    public Instagram(Context context, AutomatorServiceImpl automatorService){
        this.mAutomatorService = automatorService;
        this.utils = new Utils(context);
    }

    public Object action(ACTION action, Object[] params) throws UiObjectNotFoundException {
        switch (action){
            case CLICK_PROFILE:
                return clickProfile();
            default:
                return null;
        }
    }

    private boolean clickProfile() throws UiObjectNotFoundException {
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.INSTAGRAM_PACKAGE_NAME);
        selector.setDescription("Profile");
        selector.setResourceId(Configs.INSTAGRAM_PACKAGE_NAME + ":id/profile_tab");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_DESCRIPTION | Selector.MASK_RESOURCEID);
        return mAutomatorService.click(selector);
    }

    public void loginForDonwload(String username, String password, Callback.Login callback) {
        utils.loginForDonwload(username, password, callback);
    }

    public void getMediaByCode(String code, Callback.Media callback){
        utils.getMediaByCode(code, callback);
    }

}
