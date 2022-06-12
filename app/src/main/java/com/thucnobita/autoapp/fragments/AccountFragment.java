package com.thucnobita.autoapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.activities.AccountActivity;
import com.thucnobita.autoapp.adapters.ItemAccountAdapter;
import com.thucnobita.autoapp.databinding.FragmentAccountBinding;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AccountFragment extends Fragment {
    private FloatingActionButton facAddAccount;

    private FragmentAccountBinding fragmentAccountBinding;
    private final ArrayList<Account> listAccount = new ArrayList<>();

    public AccountFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File fileFolder = new File(String.format("%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_ACCOUNT));
        if(!fileFolder.exists()){
            fileFolder.mkdirs();
        }else{
            File[] fileAccounts = fileFolder.listFiles(pathname -> pathname.getAbsolutePath().endsWith(".json"));
            if(fileAccounts != null){
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
//                      Account account = Utils.file2Object(src, Account.class);
                        Account account = Utils.objectNode2Object(objectNode, Account.class);
                        listAccount.add(account);
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
//        for (int i = 0; i < 20; i++) {
//            boolean isPassword = (new Random().nextBoolean());
//            Account account = new Account(
//                    "username_" + i,
//                    isPassword ? "password_" + i : null,
//                    (new Random().nextBoolean()),
//                    "|", !isPassword ? "header_" + i : "",
//                    "|", !isPassword ? "content_" + i : "",
//                    "|", !isPassword ? "footer_" + i : ""
//            );
//            listAccount.add(account);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAccountBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        View view = fragmentAccountBinding.getRoot();

        facAddAccount = view.findViewById(R.id.facAddAccount);

        ItemAccountAdapter itemAccountAdapter = new ItemAccountAdapter(listAccount);
        fragmentAccountBinding.rvAccount.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentAccountBinding.rvAccount.setAdapter(itemAccountAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        facAddAccount.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AccountActivity.class);
            intent.putExtra("type", "create");
            startActivity(intent);
        });
    }
}