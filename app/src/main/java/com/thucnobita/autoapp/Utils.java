package com.thucnobita.autoapp;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import com.thucnobita.uiautomator.AutomatorServiceImpl;

class Utils {
    private Context context;
    private AutomatorServiceImpl automatorService;
    public Utils(Context context, AutomatorServiceImpl automatorService){
        this.context = context;
        this.automatorService = automatorService;
    }
    public void launchPackage(String packageName, long timeWait) {
        UiDevice device = UiDevice.getInstance(this.automatorService.getInstrumentation());

        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeWait);
    }
}
