package com.thucnobita.autoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thucnobita.bot.instagram.Instagram;
import com.thucnobita.bot.instagram.*;
import com.thucnobita.uiautomator.AutomatorServiceImpl;

import java.lang.reflect.Method;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    // UI
    private TextView txtOutput;
    private TextView txtLabelClipboard;
    private EditText txtInputCode;
    private Button btnRunBot;
    private Button btnLoginForDownload;
    private Button btnGetLink;
    private Spinner spnTypeBot;
    private LinearLayout grpInputcode;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private boolean isConfirmInputCode = false;
    private String inputCode = "";
    private String textClipboard = null;
    // Class DeviceApp
    private Object objDeviceApp;
    private Class<?> clsDeviceApp;
    private AutomatorServiceImpl automatorService;

    // Bots
    // ==== Instagram ====
    private Instagram mInstagram;
    private com.thucnobita.bot.instagram.Utils utilsBotInstagram;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtOutput = findViewById(R.id.txtOutput);
        txtLabelClipboard = findViewById(R.id.txtLabelClipboard);
        txtInputCode = findViewById(R.id.txtInputCode);
        btnRunBot = findViewById(R.id.btnRunBot);
        btnLoginForDownload = findViewById(R.id.btnLoginForDownload);
        btnGetLink = findViewById(R.id.btnGetLink);
        spnTypeBot = findViewById(R.id.spnTypeBot);
        grpInputcode = findViewById(R.id.grpInputCode);
        // Add Item into Spinner
        ArrayAdapter<CharSequence> adapterSpnTypeBot = ArrayAdapter.createFromResource(this,
                R.array.typeboy_array,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        spnTypeBot.setAdapter(adapterSpnTypeBot);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
        grpInputcode.setVisibility(View.GONE);
        // Show Dialog Permission
        askPermissions();
        // Init load class UiAutomator
        init();
    }

    private void init(){
        try{
            setLockScreen(false);
            setText("+ [App] [Init] ", true);
            Class<?> clsInstrumentation = Class.forName("com.thucnobita.autoapp.DeviceApp");
            objDeviceApp = clsInstrumentation.getConstructor(Context.class).newInstance(getApplicationContext());
            clsDeviceApp = objDeviceApp.getClass();
            Instrumentation instrumentation = (Instrumentation) clsDeviceApp.getDeclaredMethod("getInstrumentation").invoke(objDeviceApp);
            automatorService = new AutomatorServiceImpl(instrumentation);
            setText("=> UiAutomator OK", true);
            mInstagram = new Instagram(automatorService);
            utilsBotInstagram = new com.thucnobita.bot.instagram.Utils();
            setText("=> Bot Instagram OK", true);
            setLockScreen(true);
            btnGetLink.setEnabled(false);
        }catch (Exception e){
            e.printStackTrace();
            setText("=> Error: " + e, true);
        }
    }

    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    private void startBot(int index) {
        try {
            if(index == 0){ // Instagram
                com.thucnobita.autoapp.bot.Instagram bot = new com.thucnobita.autoapp.bot.Instagram(mInstagram);
                bot.step1(new com.thucnobita.autoapp.utils.Callback.Log() {
                    @Override
                    public void begin() {
                        setText("=> Begin Step 1", true);
                    }

                    @Override
                    public void write(String message) {
                        setText(message, true);
                    }

                    @Override
                    public void done() {
                        txtLabelClipboard.forceLayout();
                        setText("=> Link: " + textClipboard, true);
                        setText("=> End Step 1", true);
                    }

                    @Override
                    public void error(String message) {
                        setText("=> Error: " + message, true);
                        setText("=> End Step 1", true);
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
            setText("=> Error: " + e, true);
            try {
                automatorService.pressKey("recent");
            }catch (Exception e1){
                e1.printStackTrace();
                setText("=> Error: " + e, true);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setText(String text, boolean eol){
        runOnUiThread(() -> {
            txtOutput.setText(txtOutput.getText() + (eol ? (text + "\n") : text));
        });
    }

    private void setLockScreen(boolean lock){
        runOnUiThread(() -> {
            spnTypeBot.setEnabled(lock);
            btnRunBot.setEnabled(lock);
            btnLoginForDownload.setEnabled(lock);
            btnGetLink.setEnabled(lock);
        });
    }

    public void handleOnClickBtnLoginForDownload(View v) {
        setLockScreen(false);
//        String username = "johnthuc03101997";
//        String password = "John@Thuc@0310";
        String username = "rowing_fan";
        String password = "UuyJYd5!";
        executor.submit(() -> {
            utilsBotInstagram.login(username, password, new Callback.Login(){
                @Override
                public Callable<String> getCode() {
                    return () -> {
                        runOnUiThread(() -> {
                            grpInputcode.setVisibility(View.VISIBLE);
                            txtInputCode.setShowSoftInputOnFocus(true);
                        });
                        while(!isConfirmInputCode){
                            Thread.sleep(1000);
                        }
                        isConfirmInputCode = false;
                        return inputCode;
                    };
                }

                @Override
                public void success(String message) {
                    setText("[Bot] [Instagram] [Login] " + message, true);
                    setLockScreen(true);
                }

                @Override
                public void fail(String message) {
                    setText("[Bot] [Instagram] [Login]" + message, true);
                    setLockScreen(true);
                    runOnUiThread(() -> {
                        btnGetLink.setEnabled(false);
                    });
                }
            });
        });
    }

    public void handleOnClickBtnConfirmInputCode(View v){
        runOnUiThread(() -> {
            if(txtInputCode.length() == 6){
                txtInputCode.clearFocus();
                txtInputCode.setShowSoftInputOnFocus(false);
                inputCode = txtInputCode.getText().toString();
                txtInputCode.setText(null);
                grpInputcode.setVisibility(View.GONE);
                isConfirmInputCode = true;
            }
        });
    }

    public void handleOnClickBtnGetLink(View v){
        setLockScreen(false);
        executor.submit(() -> {
            utilsBotInstagram.getLinkVideoByCode("CcKOS7iA3oq", new Callback.Media() {
                @Override
                public void success(String linkVideo) {
                    setText("[Bot] [Instagram] [GetLink] " + linkVideo, true);
                    setLockScreen(true);
                }

                @Override
                public void fail(String message) {
                    setText("[Bot] [Instagram] [GetLink] " + message, true);
                    setLockScreen(true);
                }
            });
        });
    }

    public void handleOnClickBtnRunBot(View v){
        setLockScreen(false);
        try {
            startBot(spnTypeBot.getSelectedItemPosition());
        }catch (Exception e){
            e.printStackTrace();
            setText("[Bot] [Instagram] [Run] [Error]:" + e, true);
        }
        setLockScreen(true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            textClipboard = automatorService.getClipboard();
        }
    }

}