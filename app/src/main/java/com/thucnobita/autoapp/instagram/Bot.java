package com.thucnobita.autoapp.instagram;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
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
import com.thucnobita.autoapp.utils.MediaUtils;
import com.thucnobita.autoapp.utils.Utils;
import com.thucnobita.uiautomator.AutomatorServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bot {
    private final String TAG_NAME = "BOT_INSTAGRAM";

    private final Actions actions;
    private IGClient client = null;

    public Bot(AutomatorServiceImpl automatorService){
        actions = new Actions(automatorService);
    }

    public boolean click_select_account(String username){
        boolean result = false;
        try{
            result = actions.click_profile();
            Log.i(TAG_NAME, "=> Click profile => " + result);
            result = actions.click_switch_account(username);
            Log.i(TAG_NAME, "=> Click switch user => " + result);
        }catch (InterruptedException | UiObjectNotFoundException | RemoteException e){
            e.printStackTrace();
        }
        return result;
    }

    public String get_username_video() {
        String username = null;
        try{
            username = actions.get_username_video_saved();
            Log.i(TAG_NAME, "=> Username video:" + username);
        }catch (UiObjectNotFoundException | InterruptedException e){
            e.printStackTrace();
        }
        return username;
    }

    public boolean click_get_link_video() {
        boolean result = false;
        try{
            Thread.sleep(1000);
            result = actions.click_options();
            Log.i(TAG_NAME, "=> Click options => " + result);
            Thread.sleep(1000);
            result = actions.click_saved();
            Log.i(TAG_NAME, "=> Click saved => " + result);
            Thread.sleep(2000);
            result = actions.click_video_saved();
            Log.i(TAG_NAME, "=> Click video => " + result);
            Thread.sleep(3000);
            result = actions.click_copy_link_video_saved();
            Log.i(TAG_NAME, "=> Click get link video => " + result);
        }catch (UiObjectNotFoundException | InterruptedException | RemoteException e){
            e.printStackTrace();
        }
        return result;
    }

    public int copy_image(Context context, String account, int countMaxImage){
        try{
            String pathFolderImage = String.format("%s/%s/%s/%s",
                    Constants.FOLDER_ROOT,
                    Constants.FOLDER_NAME_APP,
                    Constants.FOLDER_NAME_IMAGE,
                    account);
            String pathFolderUploads = String.format("%s/%s/%s",
                    Constants.FOLDER_ROOT,
                    Constants.FOLDER_NAME_APP,
                    Constants.FOLDER_NAME_UPLOAD);
            File[] fileImages = new File(pathFolderImage).listFiles();
            if(fileImages != null){
                if(fileImages.length > 0){
                    Log.i(TAG_NAME, "=> Find " + fileImages.length + " file image in folder user ");
                    int totalImageUpload = countMaxImage;
                    if(fileImages.length < countMaxImage){
                        totalImageUpload = fileImages.length;
                    }
                    // Copy image from folder images/<user> to folder uploads
                    if(!new File(pathFolderUploads).exists()){
                        if(!new File(pathFolderUploads).mkdirs()) return 0;
                    }
                    for (int i = 0; i < totalImageUpload; i++) {
                        File pathImageNew = new File(pathFolderUploads, fileImages[i].getName());
                        Utils.copyFile(fileImages[i], pathImageNew);
                        MediaUtils.updateMedia(context, pathImageNew);
//                        if(fileImages[i].renameTo(pathImageNew)){
//                            MediaUtils.updateMedia(context, pathImageNew);
//                        }else{
//                            Log.i(TAG_NAME,"=> Copy file " + fileImages[i].getName() + " to folder <Instagram> Failed");
//                            return 0;
//                        }
                    }
                    Log.i(TAG_NAME,"=> Copy " + totalImageUpload + " file image to folder <Instagram> Ok");
                    return totalImageUpload;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i(TAG_NAME, "=> Error: " + e.getMessage());
        }
        return 0;
    }

    public File download_video(Context context, String link, String nameFile) {
        String pathFolderDownload = String.format("%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_UPLOAD);
        File pathFile = new File(pathFolderDownload, nameFile + ".mp4");
        if (!new File(pathFolderDownload).exists()) {
            if(!new File(pathFolderDownload).mkdirs()) return null;
        }
        try{
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
            // Update file on system
            MediaScannerConnection.scanFile(context,
                    new String[] { pathFile.getAbsolutePath() },
                    new String[] { "video/*" },
                    null);
            return pathFile;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public boolean share_video(Context context, File pathFile){
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        Uri uri = Uri.fromFile(pathFile); // SDK < 24
        try{
            Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", pathFile);
            Intent intentVideo = new Intent();
            intentVideo.setAction(Intent.ACTION_SEND);
            intentVideo.setType("video/*"); // image/* or video/* or text/plain
            intentVideo.putExtra(Intent.EXTRA_STREAM, uri);
            intentVideo.setPackage(Constants.PACKAGE_NAME_INSTAGRAM);
            intentVideo.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intentVideo);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean post_to_timeline(String content){
        boolean result = false;
        try{
            result = actions.post_to_timeline(content);
            Log.i(TAG_NAME, "=> Post feed => " + result);
        } catch (UiObjectNotFoundException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean post_reel(String content){
        boolean result = false;
        try{
            result = actions.click_profile();
            Log.i(TAG_NAME, "=> Click profile => " + result);
            result = actions.post_to_timeline(content);
            Log.i(TAG_NAME, "=> Post reel => " + result);
        } catch (UiObjectNotFoundException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean post_feed(String content){
        boolean result = false;
        try{
            result = actions.post_to_timeline(content);
            Log.i(TAG_NAME, "=> Post feed => " + result);
        } catch (UiObjectNotFoundException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean comment_post(String username, String content){
        boolean result = false;
        try{
            result = actions.click_profile();
            Log.i(TAG_NAME, "=> Click profile => " + result);
            Thread.sleep(5000);
            result = actions.comment_post(username, content);
            Log.i(TAG_NAME, "=> Comment post => " + result);
        } catch (UiObjectNotFoundException | InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean share_video_to_reel(String folderShare){
        boolean result = false;
        try{
            result = actions.share_video_to_reel(folderShare);
            Log.i(TAG_NAME, "=> Click share to reel => " + result);
        } catch (InterruptedException | UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean share_video_to_feed(String folderShare){
        boolean result = false;
        try{
            result = actions.share_video_image_to_feed(folderShare, 0, false);
            Log.i(TAG_NAME, "=> Click share to reel => " + result);
        } catch (InterruptedException | UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean share_tshirt_to_feed(String folderShare, int totalImage){
        boolean result = false;
        try{
            result = actions.share_video_image_to_feed(folderShare, totalImage, false);
            Log.i(TAG_NAME, "=> Click share to post => " + result);
        } catch (InterruptedException | UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean share_image_to_feed(String folderShare, int totalImage){
        boolean result = false;
        try{
            result = actions.share_video_image_to_feed(folderShare, totalImage, true);
            Log.i(TAG_NAME, "=> Click share to post => " + result);
        } catch (InterruptedException | UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean share_image_to_story(String folderShare, String linkSticker, int totalImage){
        boolean result = false;
        try{
            result = actions.share_image_to_story(folderShare, linkSticker, totalImage);
            Log.i(TAG_NAME, "=> Click share image to post => " + result);
        } catch (InterruptedException | UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean login(Context context, String username, String password) {
        if(client == null){
            client = loadClientCookie(username);
            // Try Again
            if(client == null){
                client = loadClientCookie(username);
            }
            // If error then New login
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
                }catch (IGLoginException igLoginException){
                    igLoginException.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean loginWithCallback(String username, String password, Callback.Login callback) {
        client = loadClientCookie(username);
        // If error then New login
        if(client == null) {
            IGClient.Builder.LoginHandler twoFactorHandler = (client, response) -> IGChallengeUtils.resolveTwoFactor(client, response, callback.getCode());
            IGClient.Builder.LoginHandler challengeHandler = (client, response) -> IGChallengeUtils.resolveChallenge(client, response, callback.getCode());
            try {
                client = IGClient.builder()
                        .username(username)
                        .password(password)
                        .onTwoFactor(twoFactorHandler)
                        .onChallenge(challengeHandler)
                        .login();
                saveClientCookie(client, username, callback);
            } catch (IGLoginException igLoginException) {
                igLoginException.printStackTrace();
                callback.fail("=> Login failed:" + igLoginException.getLoginResponse().getMessage());
                return false;
            }
        }
        callback.success("=> Login ok");
        return true;
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

    public HashMap<String, Object> getDataByCodeVideo(String code){
        HashMap<String, Object> result = new HashMap<>();
        new MediaInfoRequest(String.valueOf(IGUtils.fromCode(code)))
                .execute(client)
                .thenAccept(mediaInfoResponse -> {
                    try {
                        JsonNode jsonMedia = Utils.string2Json(Utils.object2String(mediaInfoResponse));
                        JsonNode linkVideo = jsonMedia
                                .get("items").get(0)
                                .get("video_versions").get(0)
                                .get("url");
                        result.put("link_video", linkVideo.textValue());
                        JsonNode captionText = jsonMedia
                                .get("items").get(0)
                                .get("caption");
                        result.put("caption", captionText.isEmpty() ? "" : captionText.get("text").textValue());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                })
                .join();
        return result;
    }

    public void getLinkVideoByCode(String code, Callback.Media callback){
        new MediaInfoRequest(IGUtils.fromCode(code) + "")
                .execute(client)
                .thenAccept(mediaInfoResponse -> {
                    try {
                        JsonNode jsonMedia = Utils.string2Json(Utils.object2String(mediaInfoResponse));
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
                    new File(pathFolder).delete();
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
