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

import com.thucnobita.autoapp.bots.instagram.Instagram;
import com.thucnobita.autoapp.interfaces.RequestHandleCallback;
import com.thucnobita.uiautomator.AutomatorServiceImpl;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView txtOutput;
    private Button btnRunBot;
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
        btnGetLink = findViewById(R.id.btnGetLink);
        spnTypeBot = findViewById(R.id.spnTypeBot);

        // Add Item into Spinner
        ArrayAdapter<CharSequence> adapterSpnTypeBot = ArrayAdapter.createFromResource(this,
                R.array.typeboy_array,
                androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item);
        spnTypeBot.setAdapter(adapterSpnTypeBot);

        // Show Dialog Permission
        askPermissions();

        // Init load class UiAutomator
        init();

    }

    private void init(){
        try{
            setText("[Init] [UiAutomator] ", false);
            Class<?> clsInstrumentation = Class.forName("com.thucnobita.autoapp.InstrumentationImpl");
            Object obj = clsInstrumentation.newInstance();
            Method mGetInstrumentation = obj.getClass().getDeclaredMethod("getmInstrumentation");
            Instrumentation instrumentation = (Instrumentation) mGetInstrumentation.invoke(obj);
            automatorService = new AutomatorServiceImpl(instrumentation);
            setText("=> OK", true);
            setText("[Init] [Bot] [Instagram] ", false);
            botInstagram = new Instagram(automatorService);
            setText("=> OK", true);
        }catch (Exception e){
            e.printStackTrace();
            setText("[Init] [Error] " + e, true);
        }
    }

    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.SYSTEM_ALERT_WINDOW",
                "android.permission.FOREGROUND_SERVICE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    public void handleOnClickBtnGetLink(View v){
        String linkCopied = automatorService.getClipboard();
        setText("[GetLink][Copy]:" + linkCopied, true);
        if(linkCopied != null){
            botInstagram.getLinks(linkCopied, new RequestHandleCallback() {
                @Override
                public void onSuccess(ArrayList<String> arrayList, String error) {
                    if(error != null){
                        setText("[GetLink][onSuccess][Error]:" + error, true);
                    }
                    if(arrayList.size() > 0){
                        for (String link:arrayList) {
                            setText("[GetLink][onSuccess][Result]:" + link, true);
                        }
                    }
                }

                @Override
                public void onFailure(ArrayList<String> arrayList, String error) {
                    if(error != null){
                        setText("[GetLink][onFailure][Error]:" + error, true);
                    }
                    if(arrayList.size() > 0){
                        for (String link:arrayList) {
                            setText("[GetLink][onFailure][Result]:" + link, true);
                        }
                    }
                }
            });
        }
    }

    public void handleOnClickBtnRunBot(View v){
        try {
            launchPackage("com.instagram.android");
            startBotInstagram();
        }catch (Exception e){
            e.printStackTrace();
            setText("[Bot] [Instagram] [Error]:" + e, true);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setText(String text, boolean eol){
        runOnUiThread(() -> {
            txtOutput.setText(txtOutput.getText() + (eol ? (text + "\n") : ("\n" + text)));
        });
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
        setText("[Bot] [Instagram] [Action] => Click Profile", false);
        boolean clicked = (boolean) botInstagram.action(Instagram.ACTION.CLICK_PROFILE, null);
        setText("=> " + clicked, true);
    }
}