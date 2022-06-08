package com.thucnobita.autoapp.fragments;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.instagram.Bot;
import com.thucnobita.autoapp.instagram.Callback;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Utils;
import com.thucnobita.uiautomator.AutomatorServiceImpl;
import com.thucnobita.uiautomator.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class BotFragment extends Fragment {
    private final String TAG_NAME = "BotFragment";

    private Button btnLoginTotal;
    private Button btnRunTotal;
    private Button btnStartBot;
    private Button btnStopBot;
    private Button btnConfirmCodeLogin;
    private Button btnGetClipbroad;
    private RadioGroup grpRadModeUpload;
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
        grpRadModeUpload = view.findViewById(R.id.grpRadModeUpload);
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

    @SuppressLint("ClickableViewAccessibility")
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
                        if(clearCache(v.getContext())){
                            try {
                                Thread.sleep(2000);
                            }catch (Exception e){
                                Log.i(TAG_NAME , e.toString());
                            }
                            botIG(v);
                        }
                    }
                    requireActivity().runOnUiThread(() -> {
                        setLog("\n");
                        scrollViewLog.post(() -> {
                            scrollViewLog.fullScroll(View.FOCUS_DOWN);
                        });
                    });
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
//        txtLogBot.setOnTouchListener((v, event) -> {
//            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
//            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
//                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
//            }
//            return false;
//        });
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
        setLog("=>>>> START <<<<=");
        setLog("+ [App] [Bot] [Instagram] [v3.000]");
        setLog("=> Total acc login:" + arrAccLogin.size());
        if(arrAccLogin.size() > 0 && isRunning){
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
                                int totalImage = 0;
                                if(radVideoImage.isChecked()){
                                    totalImage = botIG.copy_image(v.getContext().getApplicationContext(),accountRun.getUsername());
                                    setLog("=> Total image for upload " + totalImage);
                                    if(totalImage == 0){
                                        isRunning = false;
                                        break;
                                    }
                                }

//                                setLog("=> Open app " + Constants.PACKAGE_NAME_INSTAGRAM);
//                                Utils.openApp(v.getContext(), automatorService.getInstrumentation(), Constants.PACKAGE_NAME_INSTAGRAM, 10);
//                                botIG.share_video_to_feed(Constants.FOLDER_NAME_UPLOAD, totalImage);

                                setLog("=> Begin remote click on app");
                                Utils.openApp(v.getContext(), automatorService.getInstrumentation(), Constants.PACKAGE_NAME_INSTAGRAM, 10);
                                Thread.sleep(2000);
                                if(botIG.click_select_account(accountRun.getUsername())){
                                    setLog("=> Click select account Ok");
                                    if(botIG.click_get_link_video()){
                                        setLog("=> Click get link video Ok");
                                        String userOfVideo = botIG.get_username_video();
                                        if(userOfVideo != null){
                                            setLog("=> Get username video:" + userOfVideo);
                                            Thread.sleep(500);
                                            if(Utils.recentMainActivity(v.getContext())){
                                                Thread.sleep(1000);
                                                setLog("=> Result link video:" + linkVideo);
                                                String urlLink = linkVideo.split("\\?")[0];
                                                String[] codeVideo = urlLink.split(Pattern.quote("/"));
                                                String textCodeVideo = codeVideo[codeVideo.length-1];
                                                setLog("=> Result Code video:" + textCodeVideo);
                                                HashMap<String, Object> jsonData = botIG.getDataByCodeVideo(textCodeVideo);
                                                String linkDownload  = (String) jsonData.get("link_video");
                                                if(linkDownload != null){
                                                    setLog("=> Result Size video:" + linkDownload.length());
                                                    File fileVideo = botIG.download_video(v.getContext(),linkDownload,textCodeVideo);
                                                    if(fileVideo.exists()){
                                                        setLog("=> Download video " + fileVideo.getName() + " to folder uploads Ok");
                                                        String caption = String.valueOf(jsonData.get("caption"));
                                                        if(!caption.isEmpty()){
                                                            if(!caption.startsWith("#")){
                                                                caption = caption.split(Pattern.quote("#"))[0];
                                                                setLog("=> Get caption video of user Ok");
                                                            }else{
                                                                caption = "";
                                                            }
                                                        }
                                                        String content = randPost(caption, userOfVideo, accountRun);
                                                        if(content != null){
                                                            Utils.openApp(
                                                                    v.getContext(),
                                                                    automatorService.getInstrumentation(),
                                                                    Constants.PACKAGE_NAME_INSTAGRAM,
                                                                    5);
                                                            if (radVideo.isChecked()){
                                                                if(botIG.share_video_to_reel(Constants.FOLDER_NAME_UPLOAD)){
                                                                    setLog("=> Share video Ok");
                                                                    if(botIG.post_reel(content)){
                                                                        clearCache(v.getContext());
                                                                        Thread.sleep(2000);
                                                                        setLog("=> Post Ok");
                                                                    }else{
                                                                        setLog("=> Post Failed");
                                                                        isRunning = false;
                                                                    }
                                                                }else{
                                                                    setLog("=> Share video Failed");
                                                                    isRunning = false;
                                                                }
                                                            }else{ // Mode upload: Video + Image
                                                                if(botIG.share_video_to_feed(Constants.FOLDER_NAME_UPLOAD, totalImage)){
                                                                    setLog("=> Share video Ok");
                                                                    if(botIG.post_feed(content)){
                                                                        clearCache(v.getContext());
                                                                        Thread.sleep(2000);
                                                                        setLog("=> Post Ok");
                                                                    }else{
                                                                        setLog("=> Post Failed");
                                                                        isRunning = false;
                                                                    }
                                                                }else{
                                                                    setLog("=> Share video Failed");
                                                                    isRunning = false;
                                                                }
                                                            }
                                                        }else{
                                                            isRunning = false;
                                                        }
                                                    }else{
                                                        setLog("=> Download video Failed");
                                                        isRunning = false;
                                                    }
                                                }else{
                                                    setLog("=> Link video is null");
                                                    isRunning = false;
                                                }
                                            }
                                        }else{
                                            setLog("=> Get username video Failed");
                                            isRunning = false;
                                        }
                                    }else{
                                        setLog("=> Click get link videos Failed");
                                        isRunning = false;
                                    }
                                }else{
                                    setLog("=> Click select account Failed");
                                    isRunning = false;
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                setLog("=> Error:" + e);
                                isRunning = false;
                            }
                        }
                        try{
                            Utils.recentMainActivity(v.getContext());
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            setLog("=> Error:" + e);
                            isRunning = false;
                        }
                    }
                }
                Utils.recentMainActivity(v.getContext());
                setLog("=>>>> END <<<<=");
            }
        }
    }

    private String randPost(String caption, String userOfVideo, Account account){
        try {
            String[] nameContents = { "Header", "Content", "Footer" };
            String[] charSplits = { account.getSplitHeader(), account.getSplitContent(), account.getSplitFooter() };
            String[] contents = { account.getHeader(), account.getContent(), account.getFooter() };
            HashMap<String, String> resultContent = new HashMap<>();
            for (int i = 0; i < contents.length; i++) {
                String[] temp = contents[i].split(Pattern.quote(charSplits[i]));
                String content = temp[Utils.randInt(0, temp.length-1)];
                resultContent.put(nameContents[i], content);
                setLog("=> Random " + nameContents[i] + " Ok");
            }
            return String.format("%s @%s\n%s\n%s\n%s",
                    resultContent.get(nameContents[0]), userOfVideo,
                    caption,
                    resultContent.get(nameContents[1]),
                    resultContent.get(nameContents[2]));
        }catch (Exception e){
            e.printStackTrace();
            setLog("=> Error [randPost]: " + e.getMessage());
        }
        return null;
    }

    private boolean clearCache(Context context){
        String[] listNameFolder = { Constants.FOLDER_NAME_APP, "Download", "Movies", "Pictures", "Music", "Documents" };
        for (String nameFolder: listNameFolder) {
            String pathFolderApp = String.format("%s/%s/%s",
                    Constants.FOLDER_ROOT,
                    nameFolder,
                    Constants.FOLDER_NAME_UPLOAD);
            File fileFolderApp = new File(pathFolderApp);
            if(Utils.deleteDir(context, fileFolderApp)){
                setLog("=> Remove folder <" + Constants.FOLDER_NAME_UPLOAD + "> in folder <" + nameFolder + "> Ok");
            }else{
                setLog("=> Remove folder <" + Constants.FOLDER_NAME_UPLOAD + "> in folder <" + nameFolder + "> Failed");
                return false;
            }
        }
        return true;
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
            txtLogBot.setText(String.format("%s\n%s\n", txtLogBot.getText(), text));
            scrollViewLog.post(() -> {
               scrollViewLog.fullScroll(View.FOCUS_DOWN);
            });
        });
    }

    private void setLock(boolean isLock){
        requireActivity().runOnUiThread(() -> {
            btnStartBot.setVisibility(isLock ? View.GONE : View.VISIBLE);
            btnStopBot.setVisibility(isLock ? View.VISIBLE : View.GONE);
            grpRadModeUpload.setVisibility(isLock ? View.GONE : View.VISIBLE);
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
                            Account account = Utils.file2Object(src, Account.class);
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