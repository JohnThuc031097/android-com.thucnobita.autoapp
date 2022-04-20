package com.thucnobita.autoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.test.uiautomator.UiObjectNotFoundException;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.thucnobita.bot.instagram.*;
import com.thucnobita.uiautomator.AutomatorServiceImpl;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    // UI
    private TextView txtOutput;
    private EditText txtInputCode;
    private Button btnRunBot;
    private Button btnLoginForDownload;
    private Button btnGetLink;
    private Spinner spnTypeBot;
    private LinearLayout grpInputcode;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private AutomatorServiceImpl automatorService;
    private boolean isConfirmInputCode = false;
    private String inputCode = "";
    // Bots
    private Instagram botInstagram;
    private Utils utilsInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtOutput = findViewById(R.id.txtOutput);
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

            setText("[App] [Init] [UiAutomator] ", false);
            Class<?> clsInstrumentation = Class.forName("com.thucnobita.autoapp.InstrumentationImpl");
            Object obj = clsInstrumentation.newInstance();
            Method mGetInstrumentation = obj.getClass().getDeclaredMethod("getmInstrumentation");
            Instrumentation instrumentation = (Instrumentation) mGetInstrumentation.invoke(obj);
            automatorService = new AutomatorServiceImpl(instrumentation);
            setText("=> OK", true);

            setText("[App] [Init] [Bot] ", false);
            botInstagram = new Instagram(automatorService);
            utilsInstagram = new Utils();
            setText("=> Instagram OK", true);

            setLockScreen(true);
            btnGetLink.setEnabled(false);
        }catch (Exception e){
            e.printStackTrace();
            setText("[Init] [Error] " + e, true);
        }
        // Debug Test
        botInstagram = new Instagram(automatorService);
        utilsInstagram = new Utils();
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

    private void startBot(int index) throws UiObjectNotFoundException {
        switch (index){
            case 0: // Instagram
                setText("[Bot] [Instagram] [Package] Open ", false);
                utilsInstagram.launchPackage(
                        this,
                        automatorService.getInstrumentation(),
                        "com.instagram.android",
                        5000L);
                setText("=> Ok", false);
                setText("[Bot] [Instagram] [Action] Click Profile", false);
                boolean clicked = (boolean) botInstagram.action(Instagram.ACTION.CLICK_PROFILE, null);
                setText("=> " + clicked, true);
            default:
                break;
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
            utilsInstagram.login(username, password, new Callback.Login(){
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
            utilsInstagram.getLinkVideoByCode("CcKOS7iA3oq", new Callback.Media() {
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

}