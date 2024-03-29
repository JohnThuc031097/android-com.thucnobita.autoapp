package com.thucnobita.autoapp.fragments;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    private Context _context;
    private Context _contextApp;

    private Button btnLoginTotal;
    private Button btnRunTotal;
    private Button btnStartBot;
    private Button btnStopBot;
    private Button btnConfirmCodeLogin;
    private Button btnGetClipbroad;
    private Spinner spnTypeUpload;
    private ImageView imgDropDownTypeUpload;
    private LinearLayout grpCodeLogin;
    private LinearLayout grpTypeUpload;
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
        spnTypeUpload = view.findViewById(R.id.spnTypeUpload);
        imgDropDownTypeUpload = view.findViewById(R.id.imgDropDownTypeUpload);
        txtCodeLogin = view.findViewById(R.id.txtCodeLogin);
        txtLogBot = view.findViewById(R.id.txtLogBot);
        txtLabelCodeLogin = view.findViewById(R.id.txtLabelCodeLogin);
        btnConfirmCodeLogin = view.findViewById(R.id.btnConfirmCodeLogin);
        btnGetClipbroad = view.findViewById(R.id.btnGetClipbroad);
        grpCodeLogin = view.findViewById(R.id.grpCodeLogin);
        grpTypeUpload = view.findViewById(R.id.grpTypeUpload);
        scrollViewLog = view.findViewById(R.id.scrollViewLog);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _context = view.getContext();
        _contextApp = _context.getApplicationContext();
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
            String[] arraySpinner = new String[] {
                    "Video (Reel)",
                    "Video (Feed)",
                    "Video + Image (Feed)",
                    "Image (Story)",
                    "Image (Feed)"
            };
            ArrayAdapter<String> adapter = new ArrayAdapter<>(_contextApp, R.layout.spinner_item, arraySpinner);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnTypeUpload.setAdapter(adapter);
            spnTypeUpload.setSelection(0);
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
//                        if(googleAPI.build(_contextApp)){
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
        imgDropDownTypeUpload.setOnClickListener(v -> {
            spnTypeUpload.performClick();
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
        setLog("=>>>> START <<<<=");
        setLog("+ [App] [Bot] [Instagram] [v8.0]");
        setLog("=> Total acc login:" + arrAccLogin.size());
        // Check total account run and account login
        if(arrAccLogin.size() > 0 && isRunning){
            Account accLogin = arrAccLogin.size() > 1
                    ? arrAccLogin.get(new Random().nextInt(arrAccLogin.size()-1))
                    : arrAccLogin.get(0);
            setLog("=> Login with username:" + accLogin.getUsername());
            // Check account login
            boolean resultLogin = loginIG(accLogin.getUsername(), accLogin.getPassword());
            if(resultLogin){
                setLog("=> Total acc run:" + arrAccRun.size());
                if(arrAccRun.size() > 0){
                    for (Account accountRun: arrAccRun) {
                        if(!isRunning) break;
                        setLog("=> Begin with username:" + accountRun.getUsername());
                        setLog("=> Actived:" + accountRun.isActived());
                        setLog("=> Open app " + Constants.PACKAGE_NAME_INSTAGRAM);
                        // Check account is actived
                        if(accountRun.isActived()){
                            try {
                                // Remove folder upload before start
                                if(!clearCache(v.getContext())) isRunning = false;
                                if(!isRunning) break;
                                Thread.sleep(2000);

                                int countMaxImage = 0;
                                int totalImageCanUpload = 0;

                                // Check input data and set value default
                                if(spnTypeUpload.getSelectedItemPosition() == 0 || // Video (Reel)
                                        spnTypeUpload.getSelectedItemPosition() == 1) {  // Video (Feed)
                                    if (accountRun.getHeader() == null ||
                                            accountRun.getContent1() == null ||
                                            accountRun.getFooter() == null) {
                                        isRunning = false;
                                        setLog("=> Error input data: " + spnTypeUpload.getSelectedItem());
                                    }
                                }else if (spnTypeUpload.getSelectedItemPosition() == 2) {       // Video + Image (Feed) - (9 Image)
                                    if (accountRun.getHeader() != null &&
                                            accountRun.getContent2() != null &&
                                            accountRun.getFooter() != null) {
                                        countMaxImage = 9;
                                    } else {
                                        isRunning = false;
                                        setLog("=> Error input data: " + spnTypeUpload.getSelectedItem());
                                    }
                                } else if (spnTypeUpload.getSelectedItemPosition() == 3) { // Image (Story) - (6 Image)
                                    if(accountRun.getLink() != null){
                                        countMaxImage = 6;
                                    }else{
                                        isRunning = false;
                                        setLog("=> Error input data: " + spnTypeUpload.getSelectedItem());
                                    }
                                } else if (spnTypeUpload.getSelectedItemPosition() == 4) { // Image (Feed) - (10 Image)
                                    if (accountRun.getContent3() != null &&
                                            accountRun.getFooter() != null) {
                                        countMaxImage = 10;
                                    } else {
                                        isRunning = false;
                                        setLog("=> Error input data: " + spnTypeUpload.getSelectedItem());
                                    }
                                }
                                // Check isRunning = true Then continue;
                                if(!isRunning) break;
                                if(spnTypeUpload.getSelectedItemPosition() == 2 ||
                                        spnTypeUpload.getSelectedItemPosition() == 3 ||
                                        spnTypeUpload.getSelectedItemPosition() == 4 ){
                                    setLog("=> Count max image: " + countMaxImage);
                                    // Copy Image to folder Upload
                                    totalImageCanUpload = botIG.copy_image(_contextApp,
                                            accountRun.getUsername(),
                                            countMaxImage);
                                    setLog("=> Total image can upload: " + totalImageCanUpload);
                                    if(totalImageCanUpload == 0){
                                        isRunning = false;
                                    }
                                }
                                // Check isRunning = true Then continue;
                                if(!isRunning) break;

//                                setLog("=> Open app " + Constants.PACKAGE_NAME_INSTAGRAM);
//                                Utils.openApp(_context, automatorService.getInstrumentation(), Constants.PACKAGE_NAME_INSTAGRAM, 10);
//                                botIG.share_image_to_story(
//                                        Constants.FOLDER_NAME_UPLOAD,
//                                        "https://autoapp.vn",
//                                        totalImageCanUpload);

                                setLog("=> Begin remote click on app");
                                Utils.openApp(v.getContext(), automatorService.getInstrumentation(), Constants.PACKAGE_NAME_INSTAGRAM, 10);
                                Thread.sleep(2000);
                                if(botIG.click_select_account(accountRun.getUsername())){
                                    setLog("=> Click select account Ok");
                                    String captionVideoOfUser = null;
                                    String userOfVideo = null;
                                    if(spnTypeUpload.getSelectedItemPosition() == 0 ||
                                            spnTypeUpload.getSelectedItemPosition() == 1 ||
                                            spnTypeUpload.getSelectedItemPosition() == 2){
                                        if(botIG.click_get_link_video()){
                                            setLog("=> Click get link video Ok");
                                            userOfVideo = botIG.get_username_video();
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
                                                    HashMap<String, Object> jsonData = botIG.getDataByCode(textCodeVideo);
                                                    String linkDownload  = (String) jsonData.get("link_video");
                                                    if(linkDownload != null){
                                                        setLog("=> Result Size video:" + linkDownload.length());
                                                        File fileVideo = botIG.download_media(true, v.getContext(),linkDownload,textCodeVideo);
                                                        if(fileVideo.exists()){
                                                            setLog("=> Download video " + fileVideo.getName() + " to folder uploads Ok");
                                                            captionVideoOfUser = String.valueOf(jsonData.get("caption"));
                                                            if(!captionVideoOfUser.isEmpty()){
                                                                if(!captionVideoOfUser.startsWith("#")){
                                                                    captionVideoOfUser = captionVideoOfUser.split(Pattern.quote("#"))[0];
                                                                    setLog("=> Get caption video of user Ok");
                                                                }else{
                                                                    captionVideoOfUser = "";
                                                                }
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
                                    }
                                    // Check isRunning = true Then continue;
                                    if(!isRunning) break;

                                    Utils.openApp(
                                            _context,
                                            automatorService.getInstrumentation(),
                                            Constants.PACKAGE_NAME_INSTAGRAM,
                                            10);
                                    if (spnTypeUpload.getSelectedItemPosition() == 0) { // Video (Reel)
                                        if (botIG.share_video_to_reel(Constants.FOLDER_NAME_UPLOAD)) {
                                            setLog("=> Share video Ok");
                                            String content = randPost(0, captionVideoOfUser, userOfVideo, accountRun);
                                            if(content != null){
                                                automatorService.setClipboard("Content", content);
                                                setLog("=> Set content to clipboard Ok");
                                                if (botIG.post_to_timeline(content)) {
                                                    setLog("=> Post Ok");
                                                    Thread.sleep(2000);
//                                                    String comment = randComment(accountRun.getFooter());
//                                                    if(comment != null){
//                                                        if(botIG.comment_post(accountRun.getUsername(), comment)){
//                                                            setLog("=> Comment Ok");
//                                                        }else{
//                                                            setLog("=> Comment Failed");
//                                                            isRunning = false;
//                                                        }
//                                                    }
                                                } else {
                                                    setLog("=> Post Failed");
                                                    isRunning = false;
                                                }
                                            }else{
                                                isRunning = false;
                                            }
                                        } else {
                                            setLog("=> Share Failed");
                                            isRunning = false;
                                        }
                                    }else if (spnTypeUpload.getSelectedItemPosition() == 1){ // Video (Feed)
                                        if(botIG.share_video_to_feed(Constants.FOLDER_NAME_UPLOAD)){
                                            setLog("=> Share Ok");
                                            String content = randPost(0, captionVideoOfUser, userOfVideo, accountRun);
                                            if(content != null){
                                                automatorService.setClipboard("Content", content);
                                                setLog("=> Set content to clipboard Ok");
                                                if(botIG.post_to_timeline(content)){
                                                    setLog("=> Post Ok");
                                                    Thread.sleep(2000);
//                                                    String comment = randComment(accountRun.getFooter());
//                                                    if(comment != null){
//                                                        if(botIG.comment_post(accountRun.getUsername(), comment)){
//                                                            setLog("=> Comment Ok");
//                                                        }else{
//                                                            setLog("=> Comment Failed");
//                                                            isRunning = false;
//                                                        }
//                                                    }
                                                }else{
                                                    setLog("=> Post Failed");
                                                    isRunning = false;
                                                }
                                            }else{
                                                isRunning = false;
                                            }
                                        }else{
                                            setLog("=> Share Failed");
                                            isRunning = false;
                                        }
                                    }else if (spnTypeUpload.getSelectedItemPosition() == 2) { // Video + Image (Feed)
                                        if(botIG.share_tshirt_to_feed(Constants.FOLDER_NAME_UPLOAD, totalImageCanUpload)){
                                            setLog("=> Share video Ok");
                                            String content = randPost(1, captionVideoOfUser, userOfVideo, accountRun);
                                            if(content != null){
                                                automatorService.setClipboard("Content", content);
                                                setLog("=> Set content to clipboard Ok");
                                                if(botIG.post_to_timeline(content)){
                                                    setLog("=> Post Ok");
                                                    Thread.sleep(2000);
//                                                    String comment = randComment(accountRun.getFooter());
//                                                    if(comment != null){
//                                                        if(botIG.comment_post(accountRun.getUsername(), comment)){
//                                                            setLog("=> Comment Ok");
//                                                        }else{
//                                                            setLog("=> Comment Failed");
//                                                            isRunning = false;
//                                                        }
//                                                    }
                                                }else{
                                                    setLog("=> Post Failed");
                                                    isRunning = false;
                                                }
                                            }else{
                                                isRunning = false;
                                            }
                                        }else{
                                            setLog("=> Share Failed");
                                            isRunning = false;
                                        }
                                    }else if (spnTypeUpload.getSelectedItemPosition() == 3) { // Image (Story)
                                        String linkSticker = accountRun.getLink();
                                        if(botIG.share_image_to_story(Constants.FOLDER_NAME_UPLOAD, linkSticker,totalImageCanUpload)){
                                            setLog("=> Share Ok");
                                        }else{
                                            setLog("=> Share Failed");
                                            isRunning = false;
                                        }
                                    }else if (spnTypeUpload.getSelectedItemPosition() == 4) { // Image (Feed)
                                        if(botIG.share_image_to_feed(Constants.FOLDER_NAME_UPLOAD, totalImageCanUpload)){
                                            setLog("=> Share Ok");
                                            String content = randPost(2, null, null, accountRun);
                                            if(content != null){
                                                automatorService.setClipboard("Content", content);
                                                setLog("=> Set content to clipboard Ok");
                                                if(botIG.post_to_timeline(content)){
                                                    setLog("=> Post Ok");
                                                    Thread.sleep(2000);
//                                                String comment = randComment(accountRun.getFooter());
//                                                if(comment != null){
//                                                    if(botIG.comment_post(accountRun.getUsername(), comment)){
//                                                        setLog("=> Comment Ok");
//                                                    }else{
//                                                        setLog("=> Comment Failed");
//                                                        isRunning = false;
//                                                    }
//                                                }
                                                }else{
                                                    setLog("=> Post Failed");
                                                    isRunning = false;
                                                }
                                            }
                                        }else{
                                            setLog("=> Share Failed");
                                            isRunning = false;
                                        }
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

    private String randPost(int type, String contentOfVideo, String userOfVideo, Account account){
        String charSplit = Pattern.quote(String.valueOf("|".charAt(0)));
        String[] typeData = { "Header", "Content", "Footer" };
        if(type == 0 || type == 1){ // 0 = Video (Reel) + Video (Feed) | 1 = Video + Image
            try {
                String[] contents = {
                        account.getHeader(),
                        type == 0 ? account.getContent1() : account.getContent2(),
                        account.getFooter() };
                HashMap<String, String> resultContent = new HashMap<>();
                for (int i = 0; i < contents.length; i++) {
                    String[] temp = contents[i].split(charSplit);
                    String content = temp[Utils.randInt(0, temp.length-1)];
                    resultContent.put(typeData[i], content);
                    setLog("=> Random " + typeData[i] + " Ok");
                }
                return String.format("%s @%s\n%s\n%s\n%s",
                        resultContent.get(typeData[0]), userOfVideo,
                        contentOfVideo,
                        resultContent.get(typeData[1]),
                        resultContent.get(typeData[2]));
            }catch (Exception e){
                e.printStackTrace();
                setLog("=> Error [randPost]: " + e.getMessage());
            }
        }else if (type == 2){ // Image (Feed)
            try {
                String[] contents = { "", account.getContent3(), account.getFooter() };
                HashMap<String, String> resultContent = new HashMap<>();
                for (int i = 0; i < contents.length; i++) {
                    String[] temp = contents[i].split(charSplit);
                    String content = temp[Utils.randInt(0, temp.length-1)];
                    resultContent.put(typeData[i], content);
                    setLog("=> Random " + typeData[i] + " Ok");
                }
                return String.format("%s\n%s",
                        resultContent.get(typeData[1]),
                        resultContent.get(typeData[2]));
            }catch (Exception e){
                e.printStackTrace();
                setLog("=> Error [randPost]: " + e.getMessage());
            }
        }
        return null;
    }

    private String randComment(String data){
        String charSplit = Pattern.quote(String.valueOf("|".charAt(0)));
        try {
            String[] temp = data.split(charSplit);
            return temp[Utils.randInt(0, temp.length-1)];
        }catch (Exception e){
            e.printStackTrace();
            setLog("=> Error [randComment]: " + e.getMessage());
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
            grpTypeUpload.setEnabled(!isLock);
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
                File[] fileAccounts = fileFolder.listFiles(pathname -> pathname.getAbsolutePath().endsWith(".json"));
                if(fileAccounts != null){
                    totalAccLogin = 0;
                    totalAccRun = 0;
                    arrAccLogin = new ArrayList<>();
                    arrAccRun = new ArrayList<>();
                    for (File src : fileAccounts) {
                        try {
                            // Check node if fieldName not exist then add missing node
                            JsonNode accountJson = Utils.file2Json(src);
                            ObjectNode objectNode = ((ObjectNode)accountJson);
                            if(accountJson.findPath("link").isMissingNode()){
                                objectNode.put("link", (String) null);
                            }
                            if(!accountJson.findPath("splitHeader").isMissingNode()){
                                objectNode.remove("splitHeader");
                            }
                            if(!accountJson.findPath("splitContent").isMissingNode()){
                                objectNode.remove("splitContent");
                            }
                            if(!accountJson.findPath("splitFooter").isMissingNode()){
                                objectNode.remove("splitFooter");
                            }
                            if(!accountJson.findPath("splitLink").isMissingNode()){
                                objectNode.remove("splitLink");
                            }
                            if(!accountJson.findPath("splitCaption").isMissingNode()){
                                objectNode.remove("splitCaption");
                            }
                            if(!accountJson.findPath("caption").isMissingNode()){
                                objectNode.remove("caption");
                            }
                            if(accountJson.findPath("content1").isMissingNode()){
                                objectNode.put("content1", accountJson.get("content").textValue());
                                objectNode.remove("content");
                            }
                            if(accountJson.findPath("content2").isMissingNode()){
                                objectNode.put("content2", (String) null);
                            }
                            if(accountJson.findPath("content3").isMissingNode()){
                                objectNode.put("content3", (String) null);
                            }
//                            Account account = Utils.file2Object(src, Account.class);
                            Account account = Utils.objectNode2Object(objectNode, Account.class);
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