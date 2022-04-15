package com.thucnobita.autoapp.bots.instagram;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.test.uiautomator.UiObjectNotFoundException;

import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.Selector;

public class Instagram {
    public enum ACTION{
        CLICK_PROFILE,
        CLICK_OPTIONS,
        CLICK_SAVED,
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
            case CLICK_OPTIONS:
                return clickOptions();
            case CLICK_SAVED:
                return clickSaved();
            default:
                return null;
        }
    }

    private boolean clickSaved() throws UiObjectNotFoundException {
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.INSTAGRAM_PACKAGE_NAME);
        selector.setClassName("android.widget.TextView");
        selector.setDescription("Saved");
        selector.setResourceId(Configs.INSTAGRAM_PACKAGE_NAME + ":id/menu_option_text");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION);
        return mAutomatorService.click(selector);
    }

    private boolean clickOptions() throws UiObjectNotFoundException {
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.INSTAGRAM_PACKAGE_NAME);
        selector.setClassName("android.widget.Button");
        selector.setDescription("Options");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_CLASSNAME | Selector.MASK_DESCRIPTION);
        return mAutomatorService.click(selector);
    }

    private boolean clickProfile() throws UiObjectNotFoundException {
        Selector selector = new Selector(mAutomatorService.getInstrumentation());
        selector.setPackageName(Configs.INSTAGRAM_PACKAGE_NAME);
        selector.setClassName("android.widget.FrameLayout");
        selector.setDescription("Profile");
        selector.setResourceId(Configs.INSTAGRAM_PACKAGE_NAME + ":id/profile_tab");
        selector.setMask(Selector.MASK_PACKAGENAME | Selector.MASK_DESCRIPTION | Selector.MASK_RESOURCEID);
        return mAutomatorService.click(selector);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void loginForDonwload(String username, String password, Callback.Login callback) {
        utils.loginForDonwload(username, password, callback);
    }

    public void getMediaByCode(String code, Callback.Media callback){
        utils.getMediaByCode(code, callback);
    }

}
