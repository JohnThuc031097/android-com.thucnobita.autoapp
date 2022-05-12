package com.thucnobita.autoapp.utils;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Util {
    private Util(){

    }
    public static String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public static <T> T file2Object(File src, Class<T> valueType) throws IOException {
        return new ObjectMapper().readValue(src, valueType);
    }

    public static void object2File(File resultFile, Object obj) throws IOException {
        new ObjectMapper().writeValue(resultFile, obj);
    }

    public static JsonNode string2Json(String src) throws JsonProcessingException {
        return new ObjectMapper().readTree(src);
    }

    public static <T> T string2Object(String data, Class<T> valueType) throws JsonProcessingException {
        return new ObjectMapper().readValue(data, valueType);
    }

    public static void openApp(Context context, Instrumentation instrumentation, String packageName, long timeWait) {
        UiDevice device = UiDevice.getInstance(instrumentation);
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_NO_ANIMATION |
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setPackage(packageName);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeWait * 1000L);
    }

}
