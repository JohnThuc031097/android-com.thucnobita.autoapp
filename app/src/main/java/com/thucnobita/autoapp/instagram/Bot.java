package com.thucnobita.autoapp.instagram;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import android.widget.EditText;

import androidx.core.content.FileProvider;
import androidx.test.uiautomator.UiObjectNotFoundException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.exceptions.IGLoginException;
import com.thucnobita.api.instagram.requests.media.MediaInfoRequest;
import com.thucnobita.api.instagram.utils.IGChallengeUtils;
import com.thucnobita.api.instagram.utils.IGUtils;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Util;
import com.thucnobita.uiautomator.AutomatorServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class Bot {
    private final String TAG_NAME = "BOT_INSTAGRAM";

    private final Actions actions;
    private IGClient client = null;

    public Bot(AutomatorServiceImpl automatorService){
        actions = new Actions(automatorService);
    }

    public void recent_app() throws RemoteException, UiObjectNotFoundException {
        boolean result = actions.click_recent_app("Auto App");
        Log.i(TAG_NAME, "=> Click recent app => " +  result);
    }

    public boolean get_account(String username) throws InterruptedException, UiObjectNotFoundException, RemoteException {
        boolean result;
        result = actions.click_profile();
        Log.i(TAG_NAME, "=> Click profile => " + (boolean) result);
        result = actions.click_switch_account(username);
        Log.i(TAG_NAME, "=> Click switch user => " + result);
        return result;
    }

    public void get_link_video() throws UiObjectNotFoundException, InterruptedException {
        Object result;
        Thread.sleep(1000);
        result = actions.click_options();
        Log.i(TAG_NAME, "=> Click options => " + (boolean) result);
        Thread.sleep(1000);
        result = actions.click_saved();
        Log.i(TAG_NAME, "=> Click saved => " + (boolean) result);
        Thread.sleep(1000);
        result = actions.click_video_saved();
        Log.i(TAG_NAME, "=> Click video => " + (boolean) result);
        Thread.sleep(1000);
        result = actions.click_copy_link_video_saved();
        Log.i(TAG_NAME, "=> Click get link video => " + (boolean) result);
    }

    public File download_video(String link, String nameFile, String username) throws IOException {
        String pathFolder = String.format("%s/%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_VIDEO,
                username);
        if (!new File(pathFolder).exists()) {
            new File(pathFolder).mkdirs();
        }
        File pathFile = new File(pathFolder, nameFile + ".mp4");
        if(pathFile.exists()) return pathFile;
        InputStream input = new URL(link).openStream();
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

    public void share_video(Context context, File pathFile){
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());

//        Uri uri = Uri.fromFile(pathFile); // SDK < 24
        Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", pathFile);

        Intent intentVideo = new Intent();
        intentVideo.setAction(Intent.ACTION_SEND);
        intentVideo.setType("video/*"); // image/* or video/* or text/plain
        intentVideo.putExtra(Intent.EXTRA_STREAM, uri);
        intentVideo.setPackage(Constants.PACKAGE_NAME_INSTAGRAM);
        intentVideo.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.getApplicationContext().startActivity(intentVideo);
    }

    public void post_feed(String content) throws UiObjectNotFoundException, InterruptedException, RemoteException {
        Object result;
        result = actions.post_feed(content);
        Log.i(TAG_NAME, "=> Post feed => " + result);
        Thread.sleep(1000);
    }

    public boolean login(Context context, String username, String password) {
        if(client != null){
            return true;
        }else {
            client = loadClientCookie(username);
            if(client == null){
                IGClient.Builder.LoginHandler twoFactorHandler = (client, response) -> IGChallengeUtils.resolveTwoFactor(client, response, () -> {
                    AtomicBoolean accept = new AtomicBoolean(false);
                    EditText input = new EditText(context);
                    new AlertDialog.Builder(context)
                            .setTitle("Input digit code")
                            .setMessage("Login with two factor")
                            .setView(input)
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                if(input.length() > 3){
                                    accept.set(true);
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .setIcon(android.R.drawable.ic_secure)
                            .show();
                    while (!accept.get()){
                        Thread.sleep(1000);
                    }
                    return input.getText().toString();
                });
                IGClient.Builder.LoginHandler challengeHandler = (client, response) -> IGChallengeUtils.resolveChallenge(client, response, () -> {
                    AtomicBoolean accept = new AtomicBoolean(false);
                    EditText input = new EditText(context);
                    new AlertDialog.Builder(context)
                            .setTitle("Input digit code")
                            .setMessage("Login with two factor")
                            .setView(input)
                            .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                if(input.length() > 3){
                                    accept.set(true);
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .setIcon(android.R.drawable.ic_secure)
                            .show();
                    while (!accept.get()){
                        Thread.sleep(1000);
                    }
                    return input.getText().toString();
                });
                try{
                    client = IGClient.builder()
                            .username(username)
                            .password(password)
                            .onTwoFactor(twoFactorHandler)
                            .onChallenge(challengeHandler)
                            .login();
                    saveClientCookie(client, username);
                    return true;
                }catch (IGLoginException igLoginException){
                    igLoginException.printStackTrace();
                    return false;
                }
            }else{
                return false;
            }
        }
    }

    public void login(String username, String password, Callback.Login callback) {
        if(client != null){
            callback.success("Ok");
        }else {
            client = loadClientCookie(username, callback);
            if(client == null){
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
            }else{
                callback.success("Ok");
            }
        }
    }

    public String getLinkVideoByCode(String code){
        AtomicReference<String> result = new AtomicReference<>();
        new MediaInfoRequest(String.valueOf(IGUtils.fromCode(code)))
                .execute(client)
                .thenAccept(mediaInfoResponse -> {
                    try {
                        JsonNode jsonMedia = Util.string2Json(Util.object2String(mediaInfoResponse));
                        String linkVideo = jsonMedia
                                .get("items").get(0)
                                .get("video_versions").get(0)
                                .get("url").textValue();
                        result.set(linkVideo);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                })
                .join();
        return result.get();
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
                                .get("url").textValue();
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

    private IGClient loadClientCookie(String username){
        String pathFolder = String.format("%s/%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_SESSION,
                username);
        if(new File(pathFolder).exists()){
            File clientFile = new File(pathFolder,"client.cer");
            File cookieFile = new File(pathFolder,"cookie.cer");
            if(clientFile.exists() && cookieFile.exists()){
                try{
                    return IGClient.deserialize(clientFile, cookieFile);
                }catch (IOException | ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private IGClient loadClientCookie(String username, Callback.Login callback){
        String pathFolder = String.format("%s/%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_SESSION,
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

    private void saveClientCookie(IGClient client, String username) {
        String pathFolder = String.format("%s/%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_SESSION,
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
                }
            }
        }
    }

    private void saveClientCookie(IGClient client, String username, Callback.Login callback) {
        String pathFolder = String.format("%s/%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_SESSION,
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
