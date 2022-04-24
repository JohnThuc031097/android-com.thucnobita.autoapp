package com.thucnobita.autoapp.instagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import androidx.test.uiautomator.UiObjectNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.exceptions.IGLoginException;
import com.thucnobita.api.instagram.requests.media.MediaInfoRequest;
import com.thucnobita.api.instagram.utils.IGChallengeUtils;
import com.thucnobita.api.instagram.utils.IGUtils;
import com.thucnobita.uiautomator.AutomatorServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

public class Bot {
    private final String TAG_NAME = "BOT_INSTAGRAM";

    private final Actions actions;
    private IGClient client = null;

    public Bot(AutomatorServiceImpl automatorService){
        actions = new Actions(automatorService);
    }

    public void get_link_video() throws UiObjectNotFoundException, InterruptedException {
        Object result = false;
        result = actions.click_profile();
        Log.i(TAG_NAME, "=> Click profile => " + (boolean) result);
        Thread.sleep(1000);
        result = actions.click_options();
        Log.i(TAG_NAME, "=> Click options => " + (boolean) result);
        Thread.sleep(1000);
        result = actions.click_saved();
        Log.i(TAG_NAME, "=> Click saved => " + (boolean) result);
        Thread.sleep(1000);
        result = (boolean) actions.click_video_saved(true);
        Log.i(TAG_NAME, "=> Click video => " + (boolean) result);
        Thread.sleep(1000);
        result = actions.click_copy_and_remove_link_video_saved();
        Log.i(TAG_NAME, "=> Click get link video => " + (boolean) result);
    }

    public File download_video(String link, String nameFile, String username) throws IOException {
        String pathFolder = String.format("%s/%s/%s/%s",
                Config.FOLDER_ROOT,
                Config.FOLDER_NAME_APP,
                Config.FOLDER_NAME_VIDEOS,
                username);
        if (!new File(pathFolder).exists()) {
            new File(pathFolder).mkdirs();
        }
        InputStream input = new URL(link).openStream();
        File pathFile = new File(pathFolder, nameFile);
        FileOutputStream output = new FileOutputStream(pathFile);
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        input.close();
        output.close();
        return pathFile;
    }

    public void upload_video(File pathFile){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Uri uri = Uri.fromFile(pathFile);

        Intent intentVideo = new Intent();
        intentVideo.setAction(Intent.ACTION_SEND);
        intentVideo.setType("video/*"); // image/* or video/* or text/plain
        intentVideo.putExtra(Intent.EXTRA_STREAM, uri);
        intentVideo.setPackage(Config.PACKAGE_NAME);
    }

    public void login(String username, String password, Callback.Login callback) {
        client = loadClientCookie(username, callback);
        if(client != null){
            callback.success("Ok");
        }else {
            IGClient.Builder.LoginHandler twoFactorHandler = (client, response) -> IGChallengeUtils.resolveTwoFactor(client, response, callback.getCode());
            IGClient.Builder.LoginHandler challengeHandler = (client, response) -> IGChallengeUtils.resolveChallenge(client, response, callback.getCode());
            try{
                client = IGClient.builder()
                        .username(username)
                        .password(password)
                        .onTwoFactor(twoFactorHandler)
                        .onChallenge(challengeHandler)
                        .login();
                saveClientCookie(client, username, callback);
                callback.success("Ok");
            }catch (IGLoginException igLoginException){
                callback.fail(igLoginException.getLoginResponse().getMessage());
            }
        }
    }

    public void getLinkVideoByCode(String code, Callback.Media callback){
        new MediaInfoRequest(IGUtils.fromCode(code) + "")
                .execute(client)
                .thenAccept(mediaInfoResponse -> {
                    try {
                        JsonNode jsonMedia = Util.string2Json(Util.object2String(mediaInfoResponse));
                        String linkVideo = jsonMedia
                                .get("items").get(0)
                                .get("video_versions").get(0)
                                .get("url").toString().replace("\\\"","");
                        callback.success(linkVideo);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        callback.fail(e.getMessage());
                    }
                })
                .exceptionally(throwable -> {
                    callback.fail(throwable.getMessage());
                    return null;
                })
                .join();
    }

    private IGClient loadClientCookie(String username, Callback.Login callback){
        String pathFolder = String.format("%s/%s/%s/%s",
                Config.FOLDER_ROOT,
                Config.FOLDER_NAME_APP,
                Config.FOLDER_NAME_SESSION,
                username);
        if(new File(pathFolder).exists()){
            File clientFile = new File(pathFolder,"client.cer");
            File cookieFile = new File(pathFolder,"cookie.cer");
            if(clientFile.exists() && cookieFile.exists()){
                try{
                    return IGClient.deserialize(clientFile, cookieFile);
                }catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                    callback.fail("Load session error:" + e.getMessage());
                }
            }
        }
        return null;
    }

    private void saveClientCookie(IGClient client, String username, Callback.Login callback) {
        String pathFolder = String.format("%s/%s/%s/%s",
                Config.FOLDER_ROOT,
                Config.FOLDER_NAME_APP,
                Config.FOLDER_NAME_SESSION,
                username);
        if(!new File(pathFolder).exists()){
            new File(pathFolder).mkdirs();
        }
        if(client != null){
            if(client.isLoggedIn()){
                try{
                    client.serialize(new File(pathFolder, "client.cer"),
                            new File(pathFolder,"cookie.cer"));
                }catch (IOException e){
                    e.printStackTrace();
                    callback.fail("Save session error:" + e.getMessage());
                }
            }
        }
    }
}
