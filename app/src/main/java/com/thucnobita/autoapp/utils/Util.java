package com.thucnobita.autoapp.utils;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

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
import java.util.Random;

public class Util {
    private Util(){

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
                        context.getContentResolver().delete(file2Uri(context, fileRemove), null, null);
                        boolean success = deleteDir(context, fileRemove);
                        if (!success) {
                            return false;
                        }
                    }
                }
            }else{
                context.getContentResolver().delete(file2Uri(context, dir), null, null);
                return dir.delete();
            }
        }
        return true;
    }

    public static boolean fileDelete(Context context, Uri uri){
        try{
            final String docId;
            if (android.os.Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;

                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = "_id=?";
                String[] selectionArgs = new String[]{split[1]};


                String temp = getDataColumn(context, contentUri, selection,selectionArgs);
                if(temp != null){
                    File file = new File(temp);
                    if(file.exists()){
                        return file.delete();
                    }
                }
            }else {
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
                cursor.moveToFirst();
                @SuppressLint("Range") File file = new File(cursor.getString(cursor.getColumnIndex(filePathColumn[0])));
                cursor.close();
                if(file.exists()){
                    return file.delete();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);

            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        }
        finally {
            if (cursor != null)
                cursor.close();
        }

        return null;
    }

    private static Uri file2Uri(Context context, File file){
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
