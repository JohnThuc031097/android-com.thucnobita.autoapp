package com.thucnobita.bot.instagram;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;

import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.exceptions.IGLoginException;
import com.thucnobita.api.instagram.requests.media.MediaInfoRequest;
import com.thucnobita.api.instagram.utils.IGChallengeUtils;
import com.thucnobita.api.instagram.utils.IGUtils;

import java.io.File;
import java.io.IOException;

public class Utils {
    private IGClient client = null;
    private final String pathSaveClient = Environment.getExternalStorageDirectory().getPath() + "/auto-app/sessions";

    public Utils() {

    }

    public Intent shareVideo(String type, String videoPath){
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        File videoFile = new File(Environment.getExternalStorageDirectory().getPath() + videoPath);
        Uri uri = Uri.fromFile(videoFile);

        Intent intentVideo = new Intent();
        intentVideo.setAction(Intent.ACTION_SEND);
        intentVideo.setType(type); // image/* or video/* or text/plain
        intentVideo.putExtra(Intent.EXTRA_STREAM, uri);
        intentVideo.setPackage(Configs.PACKAGE_NAME);
        return intentVideo;
    }

    public void launchPackage(Context context, Instrumentation instrumentation, String packageName, long timeWait) {
        UiDevice device = UiDevice.getInstance(instrumentation);

        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        context.startActivity(intent);

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeWait);
    }

    private String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    private JsonNode string2Json(String str) throws JsonProcessingException {
        return new ObjectMapper().readTree(str);
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
                        JsonNode jsonMedia = string2Json(object2String(mediaInfoResponse));
                        String linkVideo = jsonMedia
                                .get("items").get(0)
                                .get("video_versions").get(0)
                                .get("url").toString();
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
        String pathSaveUser = pathSaveClient + "/" + username;
        if(new File(pathSaveUser).exists()){
            File clientFile = new File(pathSaveUser,"client.cer");
            File cookieFile = new File(pathSaveUser,"cookie.cer");
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
        String pathSaveUser = pathSaveClient + "/" + username;
        if(!new File(pathSaveUser).exists()){
            new File(pathSaveUser).mkdirs();
        }
        if(client != null){
            if(client.isLoggedIn()){
                try{
                    client.serialize(new File(pathSaveUser, "client.cer"), new File(pathSaveUser,"cookie.cer"));
                }catch (IOException e){
                    e.printStackTrace();
                    callback.fail("Save session error:" + e.getMessage());
                }
            }
        }
    }

}
