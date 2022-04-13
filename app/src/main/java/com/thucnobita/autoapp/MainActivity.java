package com.thucnobita.autoapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.Until;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.thucnobita.autoapp.bots.instagram.Callback;
import com.thucnobita.autoapp.bots.instagram.Instagram;
import com.thucnobita.uiautomator.AutomatorServiceImpl;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private TextView txtOutput;
    private Button btnRunBot;
    private Button btnLoginForDownload;
    private Button btnGetLink;
    private Spinner spnTypeBot;

    private AutomatorServiceImpl automatorService;
    private Instagram botInstagram;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtOutput = findViewById(R.id.txtOutput);
        btnRunBot = findViewById(R.id.btnRunBot);
        btnLoginForDownload = findViewById(R.id.btnLoginForDownload);
        btnGetLink = findViewById(R.id.btnGetLink);
        spnTypeBot = findViewById(R.id.spnTypeBot);

        // Add Item into Spinner
        ArrayAdapter<CharSequence> adapterSpnTypeBot = ArrayAdapter.createFromResource(this,
                R.array.typeboy_array,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        spnTypeBot.setAdapter(adapterSpnTypeBot);

//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);


        // Show Dialog Permission
        askPermissions();

        // Init load class UiAutomator
        init();

    }

    private void init(){
        try{
            setLockScreen(false);
            setText("[Init] [UiAutomator] ", false);
            Class<?> clsInstrumentation = Class.forName("com.thucnobita.autoapp.InstrumentationImpl");
            Object obj = clsInstrumentation.newInstance();
            Method mGetInstrumentation = obj.getClass().getDeclaredMethod("getmInstrumentation");
            Instrumentation instrumentation = (Instrumentation) mGetInstrumentation.invoke(obj);
            automatorService = new AutomatorServiceImpl(instrumentation);
            setText("=> OK", true);
            setText("[Init] [Bot] [Instagram] ", false);
            botInstagram = new Instagram(this, automatorService);
            setText("=> OK", true);
            setLockScreen(true);
            btnGetLink.setEnabled(false);
        }catch (Exception e){
            e.printStackTrace();
            setText("[Init] [Error] " + e, true);
        }
        // Debug Test
        botInstagram = new Instagram(this, automatorService);
        setLockScreen(true);
        btnGetLink.setEnabled(false);
    }

    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    private void launchPackage(String packageName) {
        setText("[Launch] Start open app Instagram ", false);
        UiDevice device = UiDevice.getInstance(automatorService.getInstrumentation());

        final Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        startActivity(intent);

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), 5000L);
        setText("=> OK", true);
    }

    private void startBotInstagram() throws UiObjectNotFoundException {
        setText("[Bot] [Instagram] [Action] Click Profile", false);
        boolean clicked = (boolean) botInstagram.action(Instagram.ACTION.CLICK_PROFILE, null);
        setText("=> " + clicked, true);
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
        botInstagram.loginForDonwload(username, password, new Callback.Login(){
            @Override
            public void successful(String message) {
                setText("[Bot] [Instagram] [Login] [Result] " + message, true);
                setLockScreen(true);
            }

            @Override
            public void failed(String message) {
                setText("[Bot] [Instagram] [Login] [Result] " + message, true);
                setLockScreen(true);
                runOnUiThread(() -> {
                    btnGetLink.setEnabled(false);
                });
            }
        });

    }

    public void handleOnClickBtnGetLink(View v){
        setLockScreen(false);
        botInstagram.getMediaByCode("CcKOS7iA3oq", new Callback.Media() {
            @Override
            public void successful(String linkVideo) {
                setText("[Bot] [Instagram] [GetLink] [Result] " + linkVideo, true);
                setLockScreen(true);
            }

            @Override
            public void failed(String message) {
                setText("[Bot] [Instagram] [GetLink] [Result] " + message, true);
                setLockScreen(true);
            }
        });
    }

    public void handleOnClickBtnRunBot(View v){
        setLockScreen(false);
        try {
            launchPackage("com.instagram.android");
            if(spnTypeBot.getSelectedItemPosition() == 0){ // 0= Instagram
                startBotInstagram();
            }
        }catch (Exception e){
            e.printStackTrace();
            setText("[Bot] [Instagram] [Run] [Error]:" + e, true);
        }
        setLockScreen(true);
    }

}