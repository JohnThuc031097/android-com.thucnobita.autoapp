package com.thucnobita.autoapp.bot;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class Utils {
    public Utils(){

    }

    public Intent instaShareVideo(String videoPath){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File videoFile = new File(Environment.getExternalStorageDirectory().getPath() + videoPath);
        Uri uri = Uri.fromFile(videoFile);

        Intent intentVideo = new Intent();
        intentVideo.setAction(Intent.ACTION_SEND);
        intentVideo.setType("video/*");
        intentVideo.putExtra(Intent.EXTRA_STREAM, uri);
        intentVideo.setPackage(Configs.INSTAGRAM_PACKAGE_NAME);
        return intentVideo;
    }

    public String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }
}
