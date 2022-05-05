package com.thucnobita.autoapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BotFragment extends Fragment {
    private Button btnLoginTotal;
    private Button btnRunTotal;
    private Button btnRunBot;

    private final File fileFolder = new File(String.format("%s/%s/%s",
            Constants.FOLDER_ROOT,
            Constants.FOLDER_NAME_APP,
            Constants.FOLDER_NAME_ACCOUNT));
    private ArrayList<Account> arrAccLogin;
    private ArrayList<Account> arrAccRun;

    public BotFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bot, container, false);

        btnLoginTotal = view.findViewById(R.id.btnTotalAccLogin);
        btnRunTotal = view.findViewById(R.id.btnTotalAccRun);
        btnRunBot = view.findViewById(R.id.btnRunBot);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void initAction(){

    }

    public void loadData() {
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
        }
        btnLoginTotal.setText(String.valueOf(arrAccLogin.size()));
        btnRunTotal.setText(String.valueOf(arrAccRun.size()));
    }
}