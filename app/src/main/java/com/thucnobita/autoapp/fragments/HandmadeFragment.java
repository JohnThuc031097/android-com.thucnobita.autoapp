package com.thucnobita.autoapp.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import java.util.HashMap;
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
    private String textSaveClipbroad;

    public HandmadeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fileFolder = new File(String.format("%s/%s/%s",
                Constants.FOLDER_ROOT,
                Constants.FOLDER_NAME_APP,
                Constants.FOLDER_NAME_ACCOUNT));
        executor = Executors.newFixedThreadPool(5);
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
        btnCopyTextGetResult.setOnClickListener(v -> {
            try {
                ClipboardManager clipboard = (ClipboardManager) _context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("auto-app", textSaveClipbroad);
                clipboard.setPrimaryClip(clip);
                setLog("Copy post to clipboard success");
            }catch (Exception ex){
                ex.printStackTrace();
                setLog("Copy post to clipboard failed");
                setLog(ex.getMessage());
            }
        });
        btnGetHandmade.setOnClickListener(v -> {
            setLock(true);
            if(arrAccRun.size() > 0){
                if(arrAccLogin.size() > 0){
                    setLog("Clear folder upload...");
                    if(clearCache(_context)){
                        String usernameGetMedia = spnAccount.getSelectedItem().toString().trim();
                        Account userGetMedia = arrAccRun.get((int)spnAccount.getSelectedItemPosition());
                        String linkMedia = txtLinkMedia.getText().toString().trim();
                        String usernameLogin = arrAccLogin.size() > 1
                                ? arrAccLogin.get(Utils.randInt(arrAccLogin.size() - 1)).getUsername()
                                : arrAccLogin.get(0).getUsername();
                        int countMaxImage = 10;
                        textSaveClipbroad = "";

                        if(spnAccount.getSelectedItemPosition() > 0 && !linkMedia.isEmpty()){
                            setLog("Type: Get media + image");
                            executor.submit(() -> {
                                processGetMedia(usernameLogin, userGetMedia, linkMedia);
                                processGetImage(false, usernameGetMedia, userGetMedia, countMaxImage);
                            });
                        }else if(spnAccount.getSelectedItemPosition() > 0){
                            setLog("Type: Get image");
                            executor.submit(() -> processGetImage(true, usernameGetMedia, userGetMedia, countMaxImage));
                        }else {
                            setLog("Type: Get media");
                            executor.submit(() -> processGetMedia(usernameLogin, userGetMedia, linkMedia));
                        }
                    }else{
                        setLog("Clear folder upload failed");
                        setLock(false);
                    }
                }else{
                    setLog("Account list is empty [arrAccLogin]");
                    setLock(false);
                }
            }else{
                setLog("Account list is empty [arrAccRun]");
                setLock(false);
            }
        });
    }

    private void processGetImage(boolean createPost, String usernameGetMedia, Account userGetMedia, int countMaxImage){
        setLog("Get image...");
        int totalImage = botIG.copy_image(_context, usernameGetMedia, countMaxImage);
        if(totalImage > 0){
            setLog("Copy " + totalImage + " file image success");
            if(createPost){
                setLog("Create post random to upload...");
                textSaveClipbroad = randPost(1, null, null, userGetMedia);
                setLog(textSaveClipbroad);
            }
        }else{
            setLog("Copy file image failed");
        }
        setLock(false);
    }

    private void processGetMedia(String userLogin, Account userGetMedia, String linkMedia){
        if(botIG.loginWithCookie(userLogin)){
            setLog("Login account success");
            String urlLink = linkMedia.split("\\?")[0];
            String[] codeVideo = urlLink.split(Pattern.quote("/"));
            String textCodeVideo = codeVideo[codeVideo.length-1];
            setLog("Get link media...");
            HashMap<String, Object> jsonData = botIG.getDataByCodeVideo(textCodeVideo);
            String linkDownload  = (String) jsonData.get("link_video");
            if(linkDownload != null){
                setLog("Get username of media...");
                String usernameOfMedia = String.valueOf(jsonData.get("username_of_media"));
                setLog("Download media...");
                File fileVideo = botIG.download_video(_context, linkDownload, textCodeVideo);
                if(fileVideo.exists()){
                    setLog("Get caption media...");
                    String captionVideoOfUser = String.valueOf(jsonData.get("caption"));
                    if(!captionVideoOfUser.isEmpty()){
                        if(!captionVideoOfUser.startsWith("#")){
                            captionVideoOfUser = captionVideoOfUser.split(Pattern.quote("#"))[0];
                        }else{
                            captionVideoOfUser = "";
                        }
                    }
                    setLog("Create post random to upload...");
                    textSaveClipbroad = randPost(0, captionVideoOfUser, usernameOfMedia, userGetMedia);
                    setLog(textSaveClipbroad);
                }else{
                    setLog("Download media Failed");
                }
            }else{
                setLog("Link media is null");
            }
        }else{
            setLog("Login account failed");
        }
        setLock(false);
    }

    private void setLock(boolean isLock){
        requireActivity().runOnUiThread(() -> {
            spnAccount.setEnabled(!isLock);
            txtLinkMedia.setEnabled(!isLock);
            imgDropDownAccount.setEnabled(!isLock);
            btnGetHandmade.setEnabled(!isLock);
            btnCopyTextGetResult.setEnabled(!isLock);
        });
    }

    private void setLog(String text){
        requireActivity().runOnUiThread(() -> {
            txtGetResult.setText(String.format("%s\n%s\n", txtGetResult.getText(), text));
            scrollViewResult.post(() -> {
                scrollViewResult.fullScroll(View.FOCUS_DOWN);
            });
        });
    }

    private boolean clearCache(Context context){
        String[] listNameFolder = { Constants.FOLDER_NAME_APP, "Download", "Movies", "Pictures", "Music", "Documents" };
        for (String nameFolder: listNameFolder) {
            String pathFolderApp = String.format("%s/%s/%s",
                    Constants.FOLDER_ROOT,
                    nameFolder,
                    Constants.FOLDER_NAME_UPLOAD);
            File fileFolderApp = new File(pathFolderApp);
            if(!Utils.deleteDir(context, fileFolderApp)){
                return false;
            }
        }
        return true;
    }

    private String randPost(int type, String contentOfVideo, String usernameOfVideo, Account account){
        String charSplit = Pattern.quote(String.valueOf("|".charAt(0)));
        String[] typeData = { "Header", "Content", "Footer" };
        if(type == 0){ // Video (Reel) + Video (Feed)
            try {
                String[] contents = {
                        account.getHeader(),
                        type == 0 ? account.getContent1() : account.getContent2(),
                        "" };
                HashMap<String, String> resultContent = new HashMap<>();
                for (int i = 0; i < contents.length; i++) {
                    String[] temp = contents[i].split(charSplit);
                    String content = temp[Utils.randInt(0, temp.length-1)];
                    resultContent.put(typeData[i], content);
                    setLog("=> Random " + typeData[i] + " Ok");
                }
                return String.format("%s @%s\n%s\n%s\n%s",
                        resultContent.get(typeData[0]), usernameOfVideo,
                        contentOfVideo,
                        resultContent.get(typeData[1]),
                        resultContent.get(typeData[2]));
            }catch (Exception e){
                e.printStackTrace();
                setLog("=> Error [randPost]: " + e.getMessage());
            }
        }else if (type == 1){ // Image
            try {
                String[] contents = { "", account.getContent3(), "" };
                HashMap<String, String> resultContent = new HashMap<>();
                for (int i = 0; i < contents.length; i++) {
                    String[] temp = contents[i].split(charSplit);
                    String content = temp[Utils.randInt(0, temp.length-1)];
                    resultContent.put(typeData[i], content);
                    setLog("=> Random " + typeData[i] + " Ok");
                }
                return String.format("%s\n%s",
                        resultContent.get(typeData[1]),
                        resultContent.get(typeData[2]));
            }catch (Exception e){
                e.printStackTrace();
                setLog("=> Error [randPost]: " + e.getMessage());
            }
        }
        return null;
    }
}