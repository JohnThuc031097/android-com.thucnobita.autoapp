package com.thucnobita.autoapp.bots.instagram;

import androidx.annotation.NonNull;
import androidx.test.uiautomator.UiObjectNotFoundException;

import com.thucnobita.autoapp.interfaces.RequestHandleCallback;
import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.Selector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Instagram {
    public enum ACTION{
        CLICK_PROFILE,
    }
    private final AutomatorServiceImpl mAutomatorService;
    private Utils utils;

    public Instagram(AutomatorServiceImpl automatorService){
        this.mAutomatorService = automatorService;
        this.utils = new Utils();
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

    public String getLinkVideo(String link, String[] account) throws IOException {
        if(link.matches("https://" + Configs.INSTAGRAM_DOMAIN + "/(.*)")){
            String strStep1 = link.split(Pattern.quote("?"))[0];
            String[] strStep2 = strStep1.split(Pattern.quote("/"));
            String code = strStep2[strStep2.length-1];
            return Utils.getLinkVideo(code, account);
        }
        return null;
    }

}
