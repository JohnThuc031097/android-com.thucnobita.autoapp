package com.thucnobita.autoapp.utils;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.PrecomputedText;
import android.util.Log;

import androidx.core.content.FileProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thucnobita.autoapp.activities.MainActivity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

public class Utils {
    private Utils(){

    }
    public static String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
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

    public static <T> T file2Object(File src, Class<T> valueType) throws IOException {
        return new ObjectMapper().readValue(src, valueType);
    }

    public static <T> T objectNode2Object(ObjectNode objectNode, Class<T> valueType) {
        return new ObjectMapper().convertValue(objectNode, valueType);
    }

    public static JsonNode file2Json(File src) throws IOException {
        return new ObjectMapper().readTree(src);
    }

    public static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    public static boolean deleteDir(Context context, File dir) {
        if(dir.exists()){
            if (dir.isDirectory()) {
                String[] children = dir.list();
                if(children != null){
                    for (String child : children) {
                        File fileRemove = new File(dir, child);
                        boolean success = deleteDir(context, fileRemove);
                        if (!success) {
                            return false;
                        }
                    }
                }else{
                    return true;
                }
            }else{
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.R){
                    return dir.delete();
                }else{
                    return dir.delete() && MediaUtils.deleteMedia(context, dir) > 0;
                }
            }
        }
        return true;
    }

    static Uri file2Uri(Context context, File file){
        return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
    }

    public static void openApp(Context context, Instrumentation instrumentation, String packageName, long timeWait) {
        UiDevice device = UiDevice.getInstance(instrumentation);
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_NO_ANIMATION |
                Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setPackage(packageName);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeWait * 1000L);
    }

    public static boolean recentMainActivity(Context context) {
        try{
            Intent intent = new Intent(context, MainActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(intent);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}
