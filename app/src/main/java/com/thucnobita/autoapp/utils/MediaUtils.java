package com.thucnobita.autoapp.utils;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class MediaUtils {
    private static final String TAG_NAME = "MediaUtils";

//    public static void updateMedia(Context context, File file){
//        try{
//            MediaScannerConnection.scanFile(context,
//                    new String[]{file.getAbsolutePath()},
//                    new String[]{getType(context, file) + "/*"},
//                    (path, uri) -> {
//                        Log.i(TAG_NAME, "path:" + path);
//                        Log.i(TAG_NAME, "uri:" + uri);
//                    });
//            Thread.sleep(1000);
//        }catch (Exception e){
//            Log.i(TAG_NAME, e.toString());
//        }
//    }

    public static void updateMedia(Context context, File file) {
        final MediaScannerConnection[] scannerConnection = new MediaScannerConnection[1];
        try {
            MediaScannerConnection.MediaScannerConnectionClient scannerClient = new MediaScannerConnection.MediaScannerConnectionClient() {
                public void onMediaScannerConnected() {
                    scannerConnection[0].scanFile(file.getAbsolutePath(), getType(context, file) + "/*");
                }
                public void onScanCompleted(String scanPath, Uri scanURI) {
                    scannerConnection[0].disconnect();
                    Log.i(TAG_NAME, "scanPath:" + scanPath);
                    Log.i(TAG_NAME, "scanURI:" + scanURI);
                }
            };
            Thread.sleep(2000);
            scannerConnection[0] = new MediaScannerConnection(context, scannerClient);
            scannerConnection[0].connect();
        } catch (Exception e) {
            Log.i(TAG_NAME, e.toString());
        }
    }

    public static int deleteMedia(Context context, File file){
        String type = getType(context, file);
        if(Objects.equals(type, "video")){
            return deleteFileVideoFromMediaProvider(context, file);
        }else if (Objects.equals(type, "image")){
            return deleteFileImageFromMediaProvider(context, file);
        }
        return 0;
    }

    public static int deleteFileVideoFromMediaProvider(Context context, File file) {
        if (file == null)
            return 0;

        ContentResolver contentResolver = context.getContentResolver();
        int count = 0;
        Cursor cursor = null;
        try {
            String select = MediaStore.Video.Media.DATA + "=?";
            String[] selectArgs = { file.getAbsolutePath() };

            String[] projection = { MediaStore.Video.VideoColumns._ID };
            cursor = contentResolver
                    .query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            projection, select, selectArgs, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                List<Uri> uriToDelete = new ArrayList<>();
                do {
                    @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID));

                    uriToDelete.add(Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,id));
                } while (cursor.moveToNext());

                for (Uri uri : uriToDelete) {
                    Log.i(TAG_NAME, "Attempting to delete video: " + uri);
                    count += contentResolver.delete(uri, null, null);
                }
            }
        } catch (Exception e) {
            Log.e(TAG_NAME, e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count;
    }

    public static int deleteFileImageFromMediaProvider(Context context, File file) {
        if (file == null)
            return 0;

        ContentResolver contentResolver = context.getContentResolver();
        int count = 0;
        Cursor cursor = null;
        try {
            String select = MediaStore.Images.Media.DATA + "=?";
            String[] selectArgs = { file.getAbsolutePath() };

            String[] projection = { MediaStore.Images.ImageColumns._ID };
            cursor = contentResolver
                    .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            projection, select, selectArgs, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                List<Uri> uriToDelete = new ArrayList<>();
                do {
                    @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));

                    uriToDelete.add(Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id));
                } while (cursor.moveToNext());

                for (Uri uri : uriToDelete) {
                    Log.i(TAG_NAME, "Attempting to delete image: " + uri);
                    count += contentResolver.delete(uri, null, null);
                }
            }
        } catch (Exception e) {
            Log.e(TAG_NAME, e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count;
    }

    private static String getType(Context context, File file){
        return context.getContentResolver().getType(Utils.file2Uri(context, file)).split(Pattern.quote("/"))[0];
    }
}
