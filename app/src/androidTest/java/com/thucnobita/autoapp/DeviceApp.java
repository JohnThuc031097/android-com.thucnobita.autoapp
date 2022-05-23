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

    public DeviceApp(){
        this.mInstrumentation = InstrumentationRegistry.getInstrumentation();
    }

    public Instrumentation getInstrumentation(){
        return mInstrumentation;
    }

}
