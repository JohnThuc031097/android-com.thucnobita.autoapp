package com.thucnobita.autoapp.fragments;

import android.content.Context;
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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.instagram.Bot;
import com.thucnobita.autoapp.instagram.Callback;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class HandmadeFragment extends Fragment {

    private Spinner spnAccount;
    private EditText txtLinkMedia;
    private ImageView imgDropDownAccount;
    private ScrollView scrollViewResult;
    private TextView txtGetResult;
    private Button btnGetHandmade;
    private Button btnCopyTextGetResult;

    private ExecutorService executor;
    private File fileFolder;
    private ArrayList<Account> arrAccRun;
    private ArrayList<Account> arrAccLogin;
    private Context _context;
    private Bot botIG;

    public HandmadeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileFolder = new File(String.format("%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_ACCOUNT));
        executor = Executors.newFixedThreadPool(2);
        botIG = new Bot(null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handmade, container, false);

        spnAccount = view.findViewById(R.id.spnAccount);
        txtLinkMedia = view.findViewById(R.id.txtLinkMedia);
        imgDropDownAccount = view.findViewById(R.id.imgDropDownAccount);
        scrollViewResult = view.findViewById(R.id.scrollViewResult);
        txtGetResult = view.findViewById(R.id.txtGetResult);
        btnGetHandmade = view.findViewById(R.id.btnGetHandmade);
        btnCopyTextGetResult = view.findViewById(R.id.btnCopyTextGetResult);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _context = view.getContext().getApplicationContext();
        addHandle();
        loadData();
    }

    public void loadData(){
        if(fileFolder.exists()){
            File[] fileAccounts = fileFolder.listFiles(pathname -> pathname.getAbsolutePath().endsWith(".json"));
            if(fileAccounts != null){
                ArrayList<String> arrAccount = new ArrayList<>();
                arrAccRun = new ArrayList<>();
                arrAccLogin = new ArrayList<>();
                arrAccount.add("None");
                for (File src : fileAccounts) {
                    try {
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
                        Account account = Utils.objectNode2Object(objectNode, Account.class);
                        if(account.getPassword() != null) {
                            if(account.isActived()) arrAccLogin.add(account);
                        }else{
                            if (account.isActived()){
                                arrAccount.add(account.getUsername());
                                arrAccRun.add(account);
                            }
                        }
                    }catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                requireActivity().runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(_context, R.layout.spinner_item, arrAccount);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnAccount.setAdapter(adapter);
                    spnAccount.setSelection(0);
                });
            }
        }
    }

    private void addHandle(){
        imgDropDownAccount.setOnClickListener(v -> {
            spnAccount.performClick();
        });
        btnGetHandmade.setOnClickListener(v -> {
            String username = spnAccount.getSelectedItem().toString().trim();
            String linkMedia = txtLinkMedia.getText().toString().trim();
            Account accLogin = arrAccLogin.size() > 1
                    ? arrAccLogin.get(new Random().nextInt(arrAccLogin.size()-1))
                    : arrAccLogin.get(0);
            if(!username.equals("None")){
                setLog("Type: Account");
            }else if (!linkMedia.isEmpty()){
                setLog("Type: Link Media");
                executor.submit(() -> process(accLogin.getUsername(), linkMedia));
            }else{
                setLog("Type: Account + Link Media");
                executor.submit(() -> process(accLogin.getUsername(), linkMedia));
            }
        });
    }

    private void process(String username, String linkMedia){
        if(botIG.loginWithCookie(username)){
            setLog("Login Ok");
            String urlLink = linkMedia.split("\\?")[0];
            String[] codeVideo = urlLink.split(Pattern.quote("/"));
            String textCodeVideo = codeVideo[codeVideo.length-1];
            botIG.getLinkVideoByCode(textCodeVideo, new Callback.Media() {
                @Override
                public void success(String linkMedia) {
                    setLog(linkMedia);
                }

                @Override
                public void fail(String message) {
                    setLog(message);
                }
            });
        }else{
            setLog("Login failed");
        }
    }

    private void setLog(String text){
        requireActivity().runOnUiThread(() -> {
            txtGetResult.setText(String.format("%s\n%s\n", txtGetResult.getText(), text));
            scrollViewResult.post(() -> {
                scrollViewResult.fullScroll(View.FOCUS_DOWN);
            });
        });
    }

}