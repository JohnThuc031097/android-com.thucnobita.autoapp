package com.thucnobita.autoapp.fragments;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.test.uiautomator.UiObjectNotFoundException;

import android.os.Environment;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.PrecomputedText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thucnobita.autoapp.BuildConfig;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.activities.MainActivity;
import com.thucnobita.autoapp.instagram.Bot;
import com.thucnobita.autoapp.instagram.Callback;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Util;
import com.thucnobita.uiautomator.AutomatorServiceImpl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class BotFragment extends Fragment {
    private Button btnLoginTotal;
    private Button btnRunTotal;
    private Button btnStartBot;
    private Button btnStopBot;
    private Button btnConfirmCodeLogin;
    private Button btnGetClipbroad;
    private RadioButton radVideo;
    private RadioButton radVideoImage;
    private LinearLayout grpCodeLogin;
    private EditText txtCodeLogin;
    private TextView txtLogBot;
    private TextView txtLabelCodeLogin;
    private ScrollView scrollViewLog;

    private File fileFolder;
    private int totalAccLogin;
    private ArrayList<Account> arrAccLogin;
    private int totalAccRun;
    private ArrayList<Account> arrAccRun;

    private ExecutorService executor;
    public boolean isRunning;
    private String linkVideo;
    private boolean isConfirmCodeLogin = false;

    private AutomatorServiceImpl automatorService;
    private Bot botIG;

    public BotFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileFolder = new File(String.format("%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_ACCOUNT));
        executor = Executors.newFixedThreadPool(5);
        isRunning = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bot, container, false);

        btnLoginTotal = view.findViewById(R.id.btnTotalAccLogin);
        btnRunTotal = view.findViewById(R.id.btnTotalAccRun);
        btnStartBot = view.findViewById(R.id.btnStartBot);
        btnStopBot = view.findViewById(R.id.btnStopBot);
        radVideo = view.findViewById(R.id.radVideo);
        radVideoImage = view.findViewById(R.id.radVideoImage);
        txtCodeLogin = view.findViewById(R.id.txtCodeLogin);
        txtLogBot = view.findViewById(R.id.txtLogBot);
        txtLabelCodeLogin = view.findViewById(R.id.txtLabelCodeLogin);
        btnConfirmCodeLogin = view.findViewById(R.id.btnConfirmCodeLogin);
        btnGetClipbroad = view.findViewById(R.id.btnGetClipbroad);
        grpCodeLogin = view.findViewById(R.id.grpCodeLogin);
        scrollViewLog = view.findViewById(R.id.scrollViewLog);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addValueDefault();
        addEvents();
        loadData();
    }

    private void addValueDefault(){
        requireActivity().runOnUiThread(() -> {
            btnLoginTotal.setText("0");
            btnRunTotal.setText("0");
            txtLabelCodeLogin.setVisibility(View.GONE);
            grpCodeLogin.setVisibility(View.GONE);
            txtLogBot.setText(null);
        });
        setLock(false);
    }

    private void addEvents(){
        btnGetClipbroad.getViewTreeObserver().addOnWindowFocusChangeListener(hasFocus -> {
            if(hasFocus && automatorService != null){
                linkVideo = automatorService.getClipboard();
            }
        });
        btnStartBot.setOnClickListener(v -> {
            if(!isRunning){
                isRunning = true;
                setLock(true);
                executor.submit(() -> {
                    requireActivity().runOnUiThread(() -> {
                        txtLogBot.setText(null);
                    });
//                    try {
//                        GoogleAPI googleAPI = new GoogleAPI();
//                        if(googleAPI.build(v.getContext().getApplicationContext())){
//                            // Print the names and IDs for up to 10 files.
//                            FileList result = googleAPI.getService().files().list()
//                                    .setPageSize(10)
//                                    .setFields("nextPageToken, files(id, name)")
//                                    .execute();
//                            List<com.google.api.services.drive.model.File> files = result.getFiles();
//                            if (files == null || files.isEmpty()) {
//                                setLog("No files found.");
//                            } else {
//                                for (com.google.api.services.drive.model.File file : files) {
//                                    setLog(String.format("Files: %s (%s)\n", file.getName(), file.getId()));
//                                }
//                            }
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        setLog("Error:" + e.getMessage());
//                    }

                    if(initBot()){
                        botIG(v);
                    }
                    requireActivity().runOnUiThread(() -> {
                        scrollViewLog.post(() -> {
                            scrollViewLog.fullScroll(View.FOCUS_DOWN);
                        });
                    });
                    String pathFolderUploads = String.format("%s/%s/%s",
                            Constants.FOLDER_ROOT,
                            Constants.FOLDER_NAME_APP,
                            Constants.FOLDER_NAME_UPLOAD);
                    Util.deleteDir(new File(pathFolderUploads));
                    isRunning = false;
                    setLock(false);
                });
            }
        });
        btnStopBot.setOnClickListener(v -> {
            if(isRunning){
                isRunning = false;
                setLock(false);
            }
        });
        btnConfirmCodeLogin.setOnClickListener(v -> {
            if(txtCodeLogin.length() >= 3){
                setLog("=> Try Login with code:" + txtCodeLogin.getText().toString().trim());
                isConfirmCodeLogin = true;
            }
        });
    }

    private boolean initBot(){
        try{
            setLog("+ [App] [Init] ");

            Class<?> clsInstrumentation = Class.forName("com.thucnobita.autoapp.DeviceApp");
            Object objDeviceApp = clsInstrumentation.newInstance();
//                    .getConstructor(Context.class)
//                    .newInstance(getContext());
            Class<?> clsDeviceApp = objDeviceApp.getClass();
            Instrumentation instrumentation = (Instrumentation) clsDeviceApp
                    .getDeclaredMethod("getInstrumentation")
                    .invoke(objDeviceApp);
            automatorService = new AutomatorServiceImpl(instrumentation);
            setLog("=> UiAutomator OK");

            botIG = new Bot(automatorService);
            setLog("=> Bot Instagram OK");

            return true;
        }catch (Exception e){
            e.printStackTrace();
            setLog("=> Error: " + e);
        }
        return false;
    }

    private void botIG(View v){
        setLog("+ [App] [Bot] [Instagram]");
        setLog("=> Total acc login:" + arrAccLogin.size());
        if(arrAccLogin.size() > 0){
            Account accLogin = arrAccLogin.size() > 1
                    ? arrAccLogin.get(new Random().nextInt(arrAccLogin.size()-1))
                    : arrAccLogin.get(0);
            setLog("=> Login with username:" + accLogin.getUsername());
            boolean resultLogin = loginIG(accLogin.getUsername(), accLogin.getPassword());
            if(resultLogin){
                setLog("=> Total acc run:" + arrAccRun.size());
                if(arrAccRun.size() > 0){
                    for (Account accountRun: arrAccRun) {
                        if(!isRunning) break;
                        setLog("=> Begin with username:" + accountRun.getUsername());
                        setLog("=> Actived:" + accountRun.isActived());
                        if(accountRun.isActived()){
                            try {
                                String pathFolderUploads = String.format("%s/%s/%s",
                                        Constants.FOLDER_ROOT,
                                        Constants.FOLDER_NAME_APP,
                                        Constants.FOLDER_NAME_UPLOAD);
                                if(radVideoImage.isChecked()){
                                    String pathFolder = String.format("%s/%s/%s/%s",
                                            Constants.FOLDER_ROOT,
                                            Constants.FOLDER_NAME_APP,
                                            Constants.FOLDER_NAME_IMAGE,
                                            accountRun.getUsername());
                                    File[] fileImages = new File(pathFolder).listFiles();
                                    if(fileImages != null){
                                        if(fileImages.length > 0){
                                            setLog("=> Find " + fileImages.length + " file image in folder user ");
                                            int totalDefaultImageUpload = 9;
                                            int totalImageCanUpload = totalDefaultImageUpload;
                                            if(fileImages.length < totalDefaultImageUpload){
                                                totalImageCanUpload = fileImages.length;
                                            }
                                            // Copy image from folder images/<user> to folder uploads
                                            if (!new File(pathFolderUploads).exists()) {
                                                new File(pathFolderUploads).mkdirs();
                                            }
                                            try {
                                                for (int i = 0; i < totalImageCanUpload; i++) {
                                                    File pathImage = new File(pathFolderUploads, fileImages[i].getName());
                                                    if(fileImages[i].renameTo(pathImage)){
                                                        MediaScannerConnection.scanFile(v.getContext(),
                                                                new String[] { pathImage.getPath() },
                                                                new String[] { "image/*" },
                                                                null);
                                                    }else{
                                                        setLog("=> Copy file " + fileImages[i] + " to folder uploads Failed");
                                                        isRunning = false;
                                                        break;
                                                    }
                                                }
                                                setLog("=> Copy " + totalImageCanUpload + " file image to folder uploads Ok");
                                            }catch (Exception e){
                                                e.printStackTrace();
                                                isRunning = false;
                                                break;
                                            }
                                        }else{
                                            setLog("=> Not found image in path " + fileFolder);
                                            isRunning = false;
                                            break;
                                        }
                                    }else{
                                        setLog("=> Not found path " + fileFolder);
                                        isRunning = false;
                                        break;
                                    }
                                }
                                setLog("=> Open app " + Constants.PACKAGE_NAME_INSTAGRAM);
                                Util.openApp(v.getContext(), automatorService.getInstrumentation(), Constants.PACKAGE_NAME_INSTAGRAM, 5);
                                botIG.share_video_to_feed(Constants.FOLDER_NAME_UPLOAD);
//\                                setLog("=> Begin remote click on app");
//                                if(botIG.click_select_account(accountRun.getUsername())){
//                                    setLog("=> Click select account Ok");
//                                    if(botIG.click_get_link_video()){
//                                        setLog("=> Click get link video Ok");
//                                        String usernameVideo = botIG.get_username_video();
//                                        if(usernameVideo != null){
//                                            setLog("=> Get username video:" + usernameVideo);
//                                            Thread.sleep(500);
//                                            if(Util.recentMainActivity(v.getContext())){
//                                                Thread.sleep(1000);
//                                                setLog("=> Result link video:" + linkVideo);
//                                                String urlLink = linkVideo.split("\\?")[0];
//                                                String[] codeVideo = urlLink.split(Pattern.quote("/"));
//                                                String textCodeVideo = codeVideo[codeVideo.length-1];
//                                                setLog("=> Result Code video:" + textCodeVideo);
//                                                HashMap<String, Object> jsonData = botIG.getDataByCodeVideo(textCodeVideo);
//                                                String linkDownload  = (String) jsonData.get("link_video");
//                                                if(linkDownload != null){
//                                                    setLog("=> Result Size video:" + linkDownload.length());
//                                                    String pathFolderDownload = String.format("%s/%s/%s",
//                                                            Constants.FOLDER_ROOT,
//                                                            Constants.FOLDER_NAME_APP,
//                                                            Constants.FOLDER_NAME_UPLOAD);
//                                                    File fileVideo = botIG.download_video(
//                                                            linkDownload,
//                                                            textCodeVideo,
//                                                            pathFolderDownload);
//                                                    if(fileVideo.exists()){
//                                                        // Update file on system
//                                                        MediaScannerConnection.scanFile(v.getContext(),
//                                                                new String[] { fileVideo.getAbsolutePath() },
//                                                                new String[] { "video/*" },
//                                                                null);
//                                                        setLog("=> Download video " + fileVideo.getName() + " to folder uploads Ok");
//                                                        String captionText = String.valueOf(jsonData.get("caption"));
//                                                        if(!captionText.isEmpty()){
//                                                            if(!captionText.startsWith("#")){
//                                                                captionText = captionText.split(Pattern.quote("#"))[0];
//                                                                setLog("=> Get caption video of user Ok");
//                                                            }else{
//                                                                captionText = "";
//                                                            }
//                                                        }
//                                                        setLog("=> Random header Ok");
//                                                        String[] temp = accountRun.getHeader().split(Pattern.quote(accountRun.getSplitHeader()));
//                                                        String header = temp[Util.randInt(0, temp.length-1)];
//                                                        setLog("=> Random content Ok");
//                                                        temp = accountRun.getContent().split(Pattern.quote(accountRun.getSplitContent()));
//                                                        String content = temp[Util.randInt(0, temp.length-1)];
//                                                        setLog("=> Random footer Ok");
//                                                        temp = accountRun.getFooter().split(Pattern.quote(accountRun.getSplitFooter()));
//                                                        String footer = temp[Util.randInt(0, temp.length-1)];
//                                                        String contentPost = String.format("%s @%s\n%s\n%s\n%s",
//                                                                header, usernameVideo,
//                                                                captionText,
//                                                                content,
//                                                                footer);
//                                                        Util.openApp(
//                                                                v.getContext(),
//                                                                automatorService.getInstrumentation(),
//                                                                Constants.PACKAGE_NAME_INSTAGRAM,
//                                                                5);
//                                                        // Mode upload: Video
//                                                        if (radVideo.isChecked()){
//                                                            if(botIG.share_video_to_reel(Constants.FOLDER_NAME_UPLOAD)){
//                                                                setLog("=> Share video Ok");
//                                                                if(botIG.post_reel(contentPost)){
//                                                                    setLog("=> Post Ok");
//                                                                    if(Util.deleteDir(new File(pathFolderUploads))){
//                                                                        setLog("=> Delete folder uploads Ok");
//                                                                    }else{
//                                                                        setLog("=> Delete folder uploads Failed");
//                                                                        isRunning = false;
//                                                                    }
//                                                                }else{
//                                                                    setLog("=> Post Failed");
//                                                                    isRunning = false;
//                                                                }
//                                                            }else{
//                                                                setLog("=> Share video Failed");
//                                                                isRunning = false;
//                                                            }
//                                                        }else{ // Mode upload: Video + Image
//                                                            if(botIG.share_video_to_feed(Constants.FOLDER_NAME_UPLOAD)){
//                                                                setLog("=> Share video Ok");
//                                                                if(botIG.post_feed(contentPost)){
//                                                                    setLog("=> Post Ok");
//                                                                    if(Util.deleteDir(new File(pathFolderUploads))){
//                                                                        setLog("=> Delete folder uploads Ok");
//                                                                    }else{
//                                                                        setLog("=> Delete folder uploads Failed");
//                                                                        isRunning = false;
//                                                                    }
//                                                                }else{
//                                                                    setLog("=> Post Failed");
//                                                                    isRunning = false;
//                                                                }
//                                                            }else{
//                                                                setLog("=> Share video Failed");
//                                                                isRunning = false;
//                                                            }
//                                                        }
//                                                    }else{
//                                                        setLog("=> Download video Failed");
//                                                        isRunning = false;
//                                                    }
//                                                }else{
//                                                    setLog("=> Link video is null");
//                                                    isRunning = false;
//                                                }
//                                            }
//                                        }else{
//                                            setLog("=> Get username video Failed");
//                                            isRunning = false;
//                                        }
//                                    }else{
//                                        setLog("=> Click get link videos Failed");
//                                        isRunning = false;
//                                    }
//                                }else{
//                                    setLog("=> Click select account Failed");
//                                    isRunning = false;
//                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                setLog("=> Error:" + e);
                                isRunning = false;
                            }
                        }
                        try{
                            Util.recentMainActivity(v.getContext());
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            setLog("=> Error:" + e);
                            isRunning = false;
                        }
                    }
                }
                Util.recentMainActivity(v.getContext());
            }
        }
    }

    private boolean loginIG(String username, String password){
        return botIG.loginWithCallback(username, password, new Callback.Login() {
            @Override
            public Callable<String> getCode() {
                return () -> {
                    String codeLogin = null;
                    requireActivity().runOnUiThread(() -> {
                        txtLabelCodeLogin.setVisibility(View.VISIBLE);
                        grpCodeLogin.setVisibility(View.VISIBLE);
                    });
                    while (!isConfirmCodeLogin){
                        try {
                            Thread.sleep(500);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    codeLogin = txtCodeLogin.getText().toString().trim();
                    requireActivity().runOnUiThread(() -> {
                        txtCodeLogin.setText(null);
                        txtLabelCodeLogin.setVisibility(View.GONE);
                        grpCodeLogin.setVisibility(View.GONE);
                    });
                    return codeLogin;
                };
            }

            @Override
            public void success(String message) {
                setLog(message);
            }

            @Override
            public void fail(String message) {
                setLog(message);
            }
        });
    }

    private void setLog(String text){
        requireActivity().runOnUiThread(() -> {
            txtLogBot.setText(String.format("%s\n%s", txtLogBot.getText(), text));
            scrollViewLog.post(() -> {
               scrollViewLog.fullScroll(View.FOCUS_DOWN);
            });
        });
    }

    private void setLock(boolean isLock){
        requireActivity().runOnUiThread(() -> {
            btnStartBot.setVisibility(isLock ? View.GONE : View.VISIBLE);
            btnStopBot.setVisibility(isLock ? View.VISIBLE : View.GONE);
            isRunning = isLock;
        });
    }

    public void loadData() {
        if(!isRunning){
            String pathFolderImage = String.format("%s/%s/%s",
                    Constants.FOLDER_ROOT,
                    Constants.FOLDER_NAME_APP,
                    Constants.FOLDER_NAME_IMAGE);
            File fileFolderImage = new File(pathFolderImage);
            if(!fileFolderImage.exists()) fileFolderImage.mkdirs();
            if(fileFolder.exists()){
                File[] fileAccounts = fileFolder.listFiles(pathname -> pathname.getPath().endsWith(".json"));
                if(fileAccounts != null){
                    totalAccLogin = 0;
                    totalAccRun = 0;
                    arrAccLogin = new ArrayList<>();
                    arrAccRun = new ArrayList<>();
                    for (File src : fileAccounts) {
                        try {
                            Account account = Util.file2Object(src, Account.class);
                            if(account.getPassword() != null) {
                                totalAccLogin++;
                                if (account.isActived()) arrAccLogin.add(account);
                            }else{
                                totalAccRun++;
                                if (account.isActived()) arrAccRun.add(account);
                                File fileFolderImageAccount = new File(pathFolderImage + "/" + account.getUsername());
                                if(!fileFolderImageAccount.exists()) fileFolderImageAccount.mkdirs();
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                requireActivity().runOnUiThread(() -> {
                    btnLoginTotal.setText(String.format("%s/%s", arrAccLogin.size(), totalAccLogin));
                    btnRunTotal.setText(String.format("%s/%s", arrAccRun.size(), totalAccRun));
                });
            }
        }
    }
}