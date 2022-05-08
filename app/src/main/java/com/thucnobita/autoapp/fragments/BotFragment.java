package com.thucnobita.autoapp.fragments;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.test.uiautomator.UiObjectNotFoundException;

import android.os.RemoteException;
import android.text.PrecomputedText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;
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
import java.util.ArrayList;
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
    private Button btnGetClipbroad;
    private TextView txtLogBot;

    private File fileFolder;
    private int totalAccLogin;
    private ArrayList<Account> arrAccLogin;
    private int totalAccRun;
    private ArrayList<Account> arrAccRun;

    private ExecutorService executor;
    public boolean isRunning;
    private String linkVideo;
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
                linkVideo = automatorService.getClipboard();
            }
        });
        btnStartBot.setOnClickListener(v -> {
            if(!isRunning){
                isRunning = true;
                setLock(true);
                executor.submit(() -> {
                    if(initBot()){
                        botIG(v);
                    }
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
    }

    private boolean initBot(){
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
            boolean resultLogin = botIG.login(v.getContext().getApplicationContext(),
                    accLogin.getUsername(), accLogin.getPassword());
            if(resultLogin){
                setLog("=> Login Ok");
                setLog("=> Total acc run:" + arrAccRun.size());
                if(arrAccRun.size() > 0){
                    for (Account accountRun: arrAccRun) {
                        setLog("=> Begin with username:" + accountRun.getUsername());
                        setLog("=> Actived:" + accountRun.isActived());
                        if(accountRun.isActived() && isRunning){
                            try {
                                setLog("=> Open app " + Constants.PACKAGE_NAME_INSTAGRAM);
                                Util.openApp(v.getContext(), automatorService.getInstrumentation(), Constants.PACKAGE_NAME_INSTAGRAM, 5);
                                setLog("=> Begin remote click on app");
                                if(botIG.click_select_account(accountRun.getUsername())){
                                    setLog("=> Click select account Ok");
                                    if(botIG.click_get_link_video()){
                                        setLog("=> Click get link video Ok");
                                        String usernameVideo = botIG.get_username_video();
                                        if(usernameVideo != null){
                                            setLog("=> Get username video:" + usernameVideo);
                                            Thread.sleep(500);
                                            if(botIG.recent_app()){
                                                requireActivity().runOnUiThread(() -> {
                                                    btnGetClipbroad.forceLayout();
                                                });
                                                Thread.sleep(1000);
                                                setLog("=> Result link video:" + linkVideo);
                                                String urlLink = linkVideo.split("\\?")[0];
                                                String[] codeVideo = urlLink.split(Pattern.quote("/"));
                                                String textCodeVideo = codeVideo[codeVideo.length-1];
                                                setLog("=> Result Code video:" + textCodeVideo);
                                                String linkDownload  = botIG.getLinkVideoByCode(textCodeVideo);
                                                if(linkDownload != null){
                                                    setLog("=> Result link download video:" + linkDownload);
                                                    File fileVideo = botIG.download_video(linkDownload, textCodeVideo, accountRun.getUsername());
                                                    if(fileVideo.exists()){
                                                        setLog("=> Video path downloaded:" + fileVideo.getPath());
                                                        if(botIG.share_video(v.getContext().getApplicationContext(), fileVideo)){
                                                            setLog("=> Share video Ok");
                                                            setLog("=> Random header");
                                                            String[] temp = accountRun.getHeader().split(Pattern.quote(accountRun.getSplitHeader()));
                                                            String header = temp[temp.length > 0
                                                                    ? new Random().nextInt(temp.length-1)
                                                                    : 0];
                                                            setLog("=> Random content");
                                                            temp = accountRun.getContent().split(Pattern.quote(accountRun.getSplitContent()));
                                                            String content = temp[temp.length > 0
                                                                    ? new Random().nextInt(temp.length-1)
                                                                    : 0];
                                                            setLog("=> Random footer");
                                                            temp = accountRun.getFooter().split(Pattern.quote(accountRun.getSplitFooter()));
                                                            String footer = temp[temp.length > 0
                                                                    ? new Random().nextInt(temp.length-1)
                                                                    : 0];
                                                            if(botIG.post_feed(String.format("%s@%s\n%s\n%s",
                                                                    header, usernameVideo,
                                                                    content,
                                                                    footer))){
                                                                setLog("=> Post feed Ok");
                                                                botIG.recent_app();
                                                            }else{
                                                                setLog("=> Post feed Failed");
                                                            }
                                                        }else{
                                                            setLog("=> Share video Failed");
                                                        }
                                                    }else{
                                                        setLog("=> Download video Failed");
                                                    }
                                                }
                                            }
                                        }else{
                                            setLog("=> Get username video Failed");
                                        }
                                    }else{
                                        setLog("=> Click get link videos Failed");
                                    }
                                }else{
                                    setLog("=> Click select account Failed");
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                setLog("=> Error:" + e);
                            }
                        }
                    }
                }
            }else{
                setLog("=> Login Failed");
            }
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
                    totalAccLogin = 0;
                    totalAccRun = 0;
                    arrAccLogin = new ArrayList<>();
                    arrAccRun = new ArrayList<>();
                    for (File src : fileAccounts) {
                        try {
                            Account account = Util.file2Object(src, Account.class);
                            if(account.getPassword() != null){
                                totalAccLogin++;
                                if(account.isActived()) arrAccLogin.add(account);
                            }else{
                                totalAccRun++;
                                if(account.isActived()) arrAccRun.add(account);
                            }
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                btnLoginTotal.setText(String.format("%s/%s", arrAccLogin.size(), totalAccLogin));
                btnRunTotal.setText(String.format("%s/%s", arrAccRun.size(), totalAccRun));
            }
        }
    }
}