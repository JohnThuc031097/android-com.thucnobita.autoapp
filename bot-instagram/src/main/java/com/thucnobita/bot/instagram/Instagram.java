package com.thucnobita.bot.instagram;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.thucnobita.uiautomator.AutomatorServiceImpl;

public class Instagram {
    private Actions actions;
    private Utils utils;
    public enum ACTION{
        CLICK_PROFILE,
        CLICK_OPTIONS,
        CLICK_SAVED,
    }

    public Instagram(AutomatorServiceImpl automatorService){
        this.actions = new Actions(automatorService);
    }

    public Object action(ACTION action, Object[] params) throws UiObjectNotFoundException {
        switch (action){
            case CLICK_PROFILE:
                return actions.clickProfile();
            case CLICK_OPTIONS:
                return actions.clickOptions();
            case CLICK_SAVED:
                return actions.clickSaved();
            default:
                return null;
        }
    }
}
