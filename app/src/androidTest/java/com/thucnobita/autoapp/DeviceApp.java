package com.thucnobita.autoapp;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

public class DeviceApp {
    private final Instrumentation mInstrumentation;
    private final Context mContext;

    public DeviceApp(Context context){
        this.mInstrumentation = InstrumentationRegistry.getInstrumentation();
        this.mContext = context;
    }

    public Instrumentation getInstrumentation(){
        return mInstrumentation;
    }

    public void launchPackage(String packageName, long timeWait) {
        UiDevice device = UiDevice.getInstance(getInstrumentation());

        final Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeWait * 1000L);
    }
}
