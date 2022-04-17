package com.thucnobita.autoapp.bots.instagram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.github.instagram4j.instagram4j.models.media.VideoVersionsMeta;
import com.github.instagram4j.instagram4j.models.media.timeline.TimelineVideoMedia;
import com.github.instagram4j.instagram4j.requests.media.MediaInfoRequest;
import com.github.instagram4j.instagram4j.requests.media.MediaPermalinkRequest;
import com.github.instagram4j.instagram4j.utils.IGChallengeUtils;
import com.github.instagram4j.instagram4j.utils.IGUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Utils {
    private Context context;
    private IGClient client = null;
    private String pathSaveClient = Environment.getExternalStorageDirectory().getPath() + "/auto-app/sessions";

    public Utils(Context context){
        this.context = context;
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
        intentVideo.setPackage(Configs.INSTAGRAM_PACKAGE_NAME);
        return intentVideo;
    }

    public String object2String(Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

    public JsonNode string2Json(String str) throws JsonProcessingException {
        return new ObjectMapper().readTree(str);
    }

    public void loginForDonwload(String username, String password, Callback.Login callback) {
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

    public void getMediaByCode(String code, Callback.Media callback){
        new MediaInfoRequest(IGUtils.fromCode(code) + "")
                .execute(client)
                .thenAccept(mediaInfoResponse -> {
                    try {
                        JsonNode jsonMedia = string2Json(object2String(mediaInfoResponse));
                        String linkVideo = jsonMedia.get("items").get(0).get("video_versions").get(0).get("url").toString();
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

}
