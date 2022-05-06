package com.thucnobita.autoapp.fragments;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.activities.MainActivity;
import com.thucnobita.autoapp.instagram.Bot;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Util;
import com.thucnobita.uiautomator.AutomatorServiceImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BotFragment extends Fragment {
    private Button btnLoginTotal;
    private Button btnRunTotal;
    private Button btnStartBot;
    private Button btnStopBot;
    private Button btnGetClipbroad;
    private TextView txtLogBot;

    private File fileFolder;
    private ArrayList<Account> arrAccLogin;
    private ArrayList<Account> arrAccRun;

    private ExecutorService executor;
    public boolean isRunning;
    private String linkDownloadVideo;
    private AutomatorServiceImpl automatorService;
    private Bot botInstagram;

    public BotFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileFolder = new File(String.format("%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_ACCOUNT));
        executor = Executors.newFixedThreadPool(2);
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
        btnGetClipbroad = view.findViewById(R.id.btnGetClipbroad);
        txtLogBot = view.findViewById(R.id.txtLogBot);

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
        btnLoginTotal.setText("0");
        btnRunTotal.setText("0");
        txtLogBot.setText(null);
        setLock(false);
    }

    private void addEvents(){
        btnGetClipbroad.getViewTreeObserver().addOnWindowFocusChangeListener(hasFocus -> {
            if(hasFocus && automatorService != null){
                linkDownloadVideo = automatorService.getClipboard();
            }
        });
        btnStartBot.setOnClickListener(v -> {
            setLock(true);
            // Code here
            executor.submit(() -> {
                initBot();
                remoteApp(v.getContext());
            });
        });
        btnStopBot.setOnClickListener(v -> {
            // Code here
            setLock(false);
        });
    }

    private void initBot(){
        try{
            setLog("+ [App] [Init] ");

            Class<?> clsInstrumentation = Class.forName("com.thucnobita.autoapp.DeviceApp");
            Object objDeviceApp = clsInstrumentation
                    .getConstructor(Context.class)
                    .newInstance(getContext());
            Class<?> clsDeviceApp = objDeviceApp.getClass();
            Instrumentation instrumentation = (Instrumentation) clsDeviceApp
                    .getDeclaredMethod("getInstrumentation")
                    .invoke(objDeviceApp);
            automatorService = new AutomatorServiceImpl(instrumentation);
            setLog("=> UiAutomator OK");

            botInstagram = new Bot(automatorService);
            setLog("=> Bot Instagram OK");

        }catch (Exception e){
            e.printStackTrace();
            setLog("=> Error: " + e);
        }
    }

    private void remoteApp(Context context){
        try {
            Util.openApp(context, automatorService.getInstrumentation(), Constants.PACKAGE_NAME_INSTAGRAM, 5);
            botInstagram.get_link_video();
            botInstagram.recent_app();
            requireActivity().runOnUiThread(() -> {
                btnGetClipbroad.forceLayout();
            });
            Thread.sleep(1000);
            setLog("=> Link download video:" + linkDownloadVideo);
        }catch (Exception e){
            e.printStackTrace();
            setLog("=> Error:" + e);
        }

    }

    private void setLog(String text){
        requireActivity().runOnUiThread(() -> {
            txtLogBot.setText(String.format("%s\n%s", txtLogBot.getText(), text));
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
            if(fileFolder.exists()){
                File[] fileAccounts = fileFolder.listFiles(pathname -> pathname.getPath().endsWith(".json"));
                if(fileAccounts != null){
                    arrAccLogin = new ArrayList<>();
                    arrAccRun = new ArrayList<>();
                    for (File src : fileAccounts) {
                        try {
                            Account account = Util.file2Object(src, Account.class);
                            if(account.getPassword() != null){
                                arrAccLogin.add(account);
                            }else{
                                arrAccRun.add(account);
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                btnLoginTotal.setText(String.valueOf(arrAccLogin.size()));
                btnRunTotal.setText(String.valueOf(arrAccRun.size()));
            }
        }
    }
}