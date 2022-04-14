package com.thucnobita.autoapp.bots.instagram;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.widget.EditText;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.instagram4j.instagram4j.IGClient;
import com.github.instagram4j.instagram4j.exceptions.IGLoginException;
import com.github.instagram4j.instagram4j.utils.IGChallengeUtils;
import com.thucnobita.instaapi.IGConstants;
import com.thucnobita.instaapi.IGRequest;
import com.thucnobita.instaapi.InstaClient;
import com.thucnobita.instaapi.model.media.VideoVersion;
import com.thucnobita.instaapi.model.timeline.MediaOrAd;
import com.thucnobita.instaapi.processor.AccountProcessor;
import com.thucnobita.instaapi.response.*;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Utils {
    private Context context;
    protected IGClient client;

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

    @SuppressLint("CheckResult")
    public void loginForDonwload(String username, String password, Callback.Login callback){

        Callable<String> inputCodeTwoFactor = () -> showDialogInputCode("Login with two factor", "Enter your code?", "Confirm", "Cancel");
        // handler for two factor login
        IGClient.Builder.LoginHandler twoFactorHandler = (client, response) -> IGChallengeUtils.resolveTwoFactor(client, response, inputCodeTwoFactor);
        Callable<String> inputCodeChallenge = () -> showDialogInputCode("Login with challenge", "Enter your code?", "Confirm", "Cancel");
        // handler for challenge login
        IGClient.Builder.LoginHandler challengeHandler = (client, response) -> IGChallengeUtils.resolveChallenge(client, response, inputCodeChallenge);

        try{
            client = IGClient.builder().username(username).password(password)
                    .onTwoFactor(twoFactorHandler)
                    .onChallenge(challengeHandler)
                    .login();
            callback.successful("OK");
        }catch (IGLoginException igLoginException){
            callback.failed(igLoginException.getLoginResponse().getError_type());
        }
//        InstaClient instaClient = new InstaClient(context, username, password);
//        AccountProcessor accountProcessor = instaClient.accountProcessor;
//        accountProcessor.login()
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(igLoginResponse -> {
//                    if (igLoginResponse.getStatus().equals(IGConstants.STATUS_SUCCESS)) {
//                        callback.successful("OK");
//                    }else if (igLoginResponse.getStatus().equals(IGConstants.STATUS_FAIL)){
//                        if (igLoginResponse.isLock()){
//                            callback.failed("Account is blocked");
//                        }else if (igLoginResponse.isSpam()){
//                            callback.failed("Account is spam");
//                        }else if (igLoginResponse.isTwoFactorRequired()){
//                            final EditText txtCodeTwoAuth = new EditText(context);
//                            new AlertDialog.Builder(context)
//                                    .setTitle("Login with Two Auth in Instagram")
//                                    .setMessage("Enter your code? (8 digits)")
//                                    .setView(txtCodeTwoAuth)
//                                    .setPositiveButton("Confirm", (dialog, which) -> {
//                                        if (txtCodeTwoAuth.getText().length() == 8) {
//                                            accountProcessor.twoStepAuth(txtCodeTwoAuth.toString())
//                                                    .subscribe(igLoginResponseTwoStepAuth -> {
//                                                        if (igLoginResponseTwoStepAuth.getStatus().equals(IGConstants.STATUS_SUCCESS)) {
//                                                            callback.successful("Ok");
//                                                        }
//                                                        if (igLoginResponse.getStatus().equals(IGConstants.STATUS_FAIL)) {
//                                                            callback.failed(igLoginResponse.getMessage());
//                                                        }
//                                                    }, error -> {
//                                                        callback.failed(error.getLocalizedMessage());
//                                                    });
//                                        }
//                                        dialog.dismiss();
//                                    })
//                                    .setOnCancelListener(dialog -> {
//                                        callback.failed("Cancel Login");
//                                        dialog.dismiss();
//                                    })
//                                    .create()
//                                    .show();
//                        }else{
//                            callback.failed(igLoginResponse.getMessage());
//                        }
//                    }
//                }, error -> {
//                    callback.failed(error.getLocalizedMessage());
//                });
    }

    @SuppressLint("CheckResult")
    public void getMediaByCode(String code, Callback.Media callback){
//        String id = getIdFromCode(code);
//        InstaClient.getInstanceCurrentUser(context).mediaProcessor.getMediaById(id)
//                .subscribe(igMediaResponse -> {
//                    List<MediaOrAd> mediaOrAdList = igMediaResponse.getItems();
//                    if(mediaOrAdList.get(0).getMediaType() == 2){
//                        List<VideoVersion> videoVersionList = mediaOrAdList.get(0).getVideoVersions();
//                        callback.successful(videoVersionList.get(0).getUrl() + "");
//                    }
//                }, error -> {
//                    callback.failed(error.getLocalizedMessage());
//                });
    }

    private String showDialogInputCode(String title, String message, String textLeft, String textRight){
        final EditText txtCode = new EditText(context);
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setView(txtCode)
                .setPositiveButton(textLeft, (dialog, which) -> {
                    dialog.dismiss();
                })
                .setNegativeButton(textRight, (dialog, which) -> {
                    dialog.cancel();
                })
                .create()
                .show();
        return txtCode.getText().toString();
    }

    private String getIdFromCode(String code) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
        long id = 0;
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            id = id * 64 + alphabet.indexOf(c);
        }
        return id + "";
    }
}
