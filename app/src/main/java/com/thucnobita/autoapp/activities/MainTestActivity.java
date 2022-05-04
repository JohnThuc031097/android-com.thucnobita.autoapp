package com.thucnobita.autoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.uiautomator.UiObjectNotFoundException;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.instagram.Bot;
import com.thucnobita.autoapp.instagram.Callback;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Util;
import com.thucnobita.uiautomator.AutomatorServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTestActivity extends AppCompatActivity {
    private TextView txtOutput;
    private TextView txtLabelClipboard;
    private EditText txtInputCode;
    private Button btnRunBot;
    private Button btnLogin;
    private Button btnGetLink;
    private Button btnDownload;
    private Button btnUpload;
    private Spinner spnTypeBot;
    private LinearLayout grpInputcode;

    private final ExecutorService executor = Executors.newFixedThreadPool(6);
    private String textUsername = "rowing_fan";
    private String textPassword = "UuyJYd5!";
    private boolean isConfirmInputCode = false;
    private String inputCode = null;
    private String textClipboard = null;
    private String textLinkVideo = null;
    private String textCodeVideo = null;
    private File fileVideo = null;
    // Data configs
    private JsonNode jsConfigs = null;
    // Class DeviceApp
    private Object objDeviceApp;
    private Class<?> clsDeviceApp;
    private AutomatorServiceImpl automatorService;
    // BOT
    private Bot botInstagram = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

        txtOutput = findViewById(R.id.txtOutput);
        txtLabelClipboard = findViewById(R.id.txtLabelClipboard);
//        txtInputCode = findViewById(R.id.txtInputCode);
        btnRunBot = findViewById(R.id.btnRunBot);
        btnLogin = findViewById(R.id.btnLogin);
        btnGetLink = findViewById(R.id.btnGetLink);
        btnDownload = findViewById(R.id.btnDownload);
        btnUpload = findViewById(R.id.btnUpload);
        spnTypeBot = findViewById(R.id.spnTypeBot);
        // Add Item into Spinner
        ArrayAdapter<CharSequence> adapterSpnTypeBot = ArrayAdapter.createFromResource(this,
                R.array.typeboy_array,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        spnTypeBot.setAdapter(adapterSpnTypeBot);

        grpInputcode.setVisibility(View.GONE);
        // Show Dialog Permission
        askPermissions();
        // Init load class UiAutomator
        init();
    }

    private void init(){
        try{
            setText("+ [App] [Init] ", true);

            loadConfigs();
            setText("=> Load Configs OK", true);

            Class<?> clsInstrumentation = Class.forName("com.thucnobita.autoapp.DeviceApp");
            objDeviceApp = clsInstrumentation
                    .getConstructor(Context.class)
                    .newInstance(getApplicationContext());
            clsDeviceApp = objDeviceApp.getClass();
            Instrumentation instrumentation = (Instrumentation) clsDeviceApp
                    .getDeclaredMethod("getInstrumentation")
                    .invoke(objDeviceApp);
            automatorService = new AutomatorServiceImpl(instrumentation);
            setText("=> UiAutomator OK", true);

            botInstagram = new Bot(automatorService);
            setText("=> Bot Instagram OK", true);

        }catch (Exception e){
            e.printStackTrace();
            setText("=> Error: " + e, true);
        }
    }

    private void loadConfigs() throws IOException {
        String folderConfigs = String.format("%s/%s", Constants.FOLDER_ROOT, Constants.FOLDER_NAME_APP);
        if(!new File(folderConfigs).exists())
            new File(folderConfigs).mkdirs();
        String pathConfigs = String.format("%s/%s", folderConfigs, Constants.FILE_CONFIGS);
        if(new File(pathConfigs).exists()){
            jsConfigs = Util.string2Json(new File(pathConfigs));

        }
    }

    private void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    private void startBot(int index) {
        executor.submit(() -> {
            try {
                if(index == 0){ // Instagram
                    setText("[Bot] [Instagram] [Action]", true);
                    clsDeviceApp.getDeclaredMethod("launchPackage", String.class, long.class)
                            .invoke(objDeviceApp, Constants.PACKAGE_NAME_INSTAGRAM, 5);
                    setText("=> Open app Ok", true);
                    botInstagram.get_link_video();
                    setText("=> Copy link video Ok", true);
                    txtLabelClipboard.forceLayout();
                }
            }catch (Exception e){
                e.printStackTrace();
                setText("=> Error: " + e, true);
            }
            try {
                botInstagram.recent_app();
            }catch (RemoteException | UiObjectNotFoundException re){
                re.printStackTrace();
                setText("=> Error: " + re, true);
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setText(String text, boolean eol){
        runOnUiThread(() -> {
            txtOutput.setText(txtOutput.getText() + (eol ? (text + "\n") : text));
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && automatorService != null){
            textClipboard = automatorService.getClipboard();
        }
    }

    public void handleOnClickBtnRunBot(View v){
        try {
            startBot(spnTypeBot.getSelectedItemPosition());
        }catch (Exception e){
            e.printStackTrace();
            setText("[Bot] [Instagram] [Run] [Error]:" + e, true);
        }
    }

    public void handleOnClickBtnLogin(View v) {
//        Intent intentLogin = new Intent(MainTestActivity.this, LoginActivity.class);
//        if(jsConfigs.isEmpty()){
//            if(jsConfigs.get("login").isArray()){
//                JsonNode jsLogin = jsConfigs.get("login");
//                intentLogin.putExtra("username", jsLogin.get("username").textValue());
//                intentLogin.putExtra("password", jsLogin.get("password").textValue());
//                intentLogin.putExtra("isChecked", jsLogin.get("isChecked").booleanValue());
//            }
//        }
//        startActivity(intentLogin);
//        executor.submit(() -> {
//            botInstagram.login(textUsername, textPassword, new Callback.Login(){
//                @Override
//                public Callable<String> getCode() {
//                    return () -> {
//                        runOnUiThread(() -> {
//                            grpInputcode.setVisibility(View.VISIBLE);
//                            txtInputCode.setShowSoftInputOnFocus(true);
//                        });
//                        while(!isConfirmInputCode){
//                            Thread.sleep(1000);
//                        }
//                        isConfirmInputCode = false;
//                        return inputCode;
//                    };
//                }
//
//                @Override
//                public void success(String message) {
//                    setText("[Bot] [Instagram] [Login] " + message, true);
//                }
//
//                @Override
//                public void fail(String message) {
//                    setText("[Bot] [Instagram] [Login]" + message, true);
//                }
//            });
//        });
    }

    public void handleOnClickBtnGetLink(View v){
        executor.submit(() -> {
            try {
                // Code "CcKOS7iA3oq"
                String urlLink = textClipboard.split("\\?")[0];
                String[] codeVideo = urlLink.split("/");
                textCodeVideo = codeVideo[codeVideo.length-1];
                botInstagram.getLinkVideoByCode(textCodeVideo, new Callback.Media() {
                    @Override
                    public void success(String linkVideo) {
                        try {
                            textLinkVideo = linkVideo;
                            setText("[Bot] [Instagram] [GetLink] Ok", true);
                        }catch (Exception e){
                            e.printStackTrace();
                            setText("[Bot] [Instagram] [GetLink] " + e, true);
                        }
                    }

                    @Override
                    public void fail(String message) {
                        setText("[Bot] [Instagram] [GetLink] " + message, true);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
                setText("[Bot] [Instagram] [GetLink] " + e, true);
            }
        });
    }

    public void handleOnClickBtnDownload(View v){
        executor.submit(() -> {
            try {
                fileVideo = botInstagram.download_video(textLinkVideo, textCodeVideo, textUsername);
                setText("[Bot] [Instagram] [DonwloadVideo] " + fileVideo.getAbsolutePath(), true);
            }catch (Exception e){
                setText("[Bot] [Instagram] [DonwloadVideo] " + e, true);
            }
        });
    }

    public void handleOnClickBtnUpload(View v){
        executor.submit(() -> {
           try {
               Intent intentShare = botInstagram.share_video(this, fileVideo);
               startActivity(intentShare);
               setText("[Bot] [Instagram] [ShareVideo] Ok", true);
               Thread.sleep(1000);
               botInstagram.post_feed(textClipboard);
               setText("[Bot] [Instagram] [UploadVideo] Ok", true);
           }catch (Exception e){
               e.printStackTrace();
               setText("[Bot] [Instagram] [UploadVideo] " + e, true);
           }
            try {
                botInstagram.recent_app();
            }catch (RemoteException | UiObjectNotFoundException re){
                re.printStackTrace();
                setText("=> Error: " + re, true);
            }
        });
    }

}