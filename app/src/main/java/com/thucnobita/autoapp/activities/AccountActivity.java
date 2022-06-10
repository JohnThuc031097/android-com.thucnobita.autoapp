package com.thucnobita.autoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.databinding.ActivityAccountBinding;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class AccountActivity extends AppCompatActivity {
    private ActivityAccountBinding binding;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_account);

        initIntent();
        initValueDefault();
        initAction();
    }

    private void initIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            if(bundle.getString("type").equals("create")){
                account = new Account(
                        "",
                        null,
                        true,
                        "|",null,
                        "|", null,
                        "|", null,
                        "|", null,
                        "|", null);
                binding.setAccount(account);
            }else if(bundle.getString("type").equals("edit")){
                if(bundle.getString("account") != null){
                    try {
                        account = Utils.string2Object(bundle.getString("account"), Account.class);
                        binding.setAccount(account);
                    }catch (JsonProcessingException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void initValueDefault(){
        binding.grpPasswordLogin.setVisibility(View.VISIBLE);
        binding.grpPostContent.setVisibility(View.GONE);
        binding.txtStrDataHeader.setVisibility(View.GONE);
        binding.txtStrDataContent.setVisibility(View.GONE);
        binding.txtStrDataFooter.setVisibility(View.GONE);
        binding.txtStrDataLink.setVisibility(View.GONE);
        binding.txtStrDataCaption.setVisibility(View.GONE);
        runOnUiThread(() -> {
            if(account.getPassword() != null){
                binding.ckbUserLogin.setChecked(true);
                binding.grpPasswordLogin.setVisibility(View.VISIBLE);
                binding.grpPostContent.setVisibility(View.GONE);
            }else{
                binding.ckbUserLogin.setChecked(false);
                binding.grpPasswordLogin.setVisibility(View.GONE);
                binding.grpPostContent.setVisibility(View.VISIBLE);
                if(account.getSplitHeader() != null && account.getHeader() != null){
                    try {
                        String[] data = account.getHeader().split(Pattern.quote(String.valueOf(account.getSplitHeader().charAt(0))));
                        binding.txtTotalHeader.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(account.getSplitContent() != null && account.getContent() != null){
                    try {
                        String[] data = account.getContent().split(Pattern.quote(String.valueOf(account.getSplitContent().charAt(0))));
                        binding.txtTotalContent.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(account.getSplitFooter() != null && account.getFooter() != null){
                    try {
                        String[] data = account.getFooter().split(Pattern.quote(String.valueOf(account.getSplitFooter().charAt(0))));
                        binding.txtTotalFooter.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(account.getSplitLink() != null && account.getLink() != null){
                    try {
                        String[] data = account.getLink().split(Pattern.quote(String.valueOf(account.getSplitLink().charAt(0))));
                        binding.txtTotalLink.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(account.getSplitCaption() != null && account.getCaption() != null){
                    try {
                        String[] data = account.getCaption().split(Pattern.quote(String.valueOf(account.getSplitCaption().charAt(0))));
                        binding.txtTotalCaption.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initAction(){
        binding.ckbUserLogin.setOnClickListener(v -> {
            runOnUiThread(() -> {
                if(binding.ckbUserLogin.isChecked()){
                    binding.grpPasswordLogin.setVisibility(View.VISIBLE);
                    binding.grpPostContent.setVisibility(View.GONE);
                }else{
                    binding.grpPasswordLogin.setVisibility(View.GONE);
                    binding.grpPostContent.setVisibility(View.VISIBLE);
                }
            });
        });

        binding.btnShowHideDataHeader.setOnClickListener(v -> {
            if(binding.btnShowHideDataHeader.getText().toString().equals("Expand")){
                binding.btnShowHideDataHeader.setText("Collapse");
                binding.grpUser.setVisibility(View.GONE);
                binding.grpContent.setVisibility(View.GONE);
                binding.grpFooter.setVisibility(View.GONE);
                binding.grpLink.setVisibility(View.GONE);
                binding.grpCaption.setVisibility(View.GONE);
                binding.grpControl.setVisibility(View.GONE);
                binding.txtStrDataHeader.setVisibility(View.VISIBLE);
            }else{
                String strData = binding.txtStrDataHeader.getText().toString();
                String charSplit = binding.txtStrSplitHeader.getText().toString();
                if(!strData.isEmpty() && !charSplit.isEmpty()){
                    try {
                        String[] data = strData.split(Pattern.quote(String.valueOf(charSplit.charAt(0))));
                        binding.txtTotalHeader.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    binding.txtTotalHeader.setText("0");
                }
                binding.btnShowHideDataHeader.setText("Expand");
                binding.grpUser.setVisibility(View.VISIBLE);
                binding.grpContent.setVisibility(View.VISIBLE);
                binding.grpFooter.setVisibility(View.VISIBLE);
                binding.grpLink.setVisibility(View.VISIBLE);
                binding.grpCaption.setVisibility(View.VISIBLE);
                binding.grpControl.setVisibility(View.VISIBLE);
                binding.txtStrDataHeader.setVisibility(View.GONE);
            }
        });
        binding.btnShowHideDataContent.setOnClickListener(v -> {
            if(binding.btnShowHideDataContent.getText().toString().equals("Expand")){
                binding.btnShowHideDataContent.setText("Collapse");
                binding.grpUser.setVisibility(View.GONE);
                binding.grpHeader.setVisibility(View.GONE);
                binding.grpFooter.setVisibility(View.GONE);
                binding.grpLink.setVisibility(View.GONE);
                binding.grpCaption.setVisibility(View.GONE);
                binding.grpControl.setVisibility(View.GONE);
                binding.txtStrDataContent.setVisibility(View.VISIBLE);
            }else{
                String strData = binding.txtStrDataContent.getText().toString();
                String charSplit = binding.txtStrSplitContent.getText().toString();
                if(!strData.isEmpty() && !charSplit.isEmpty()){
                    try {
                        String[] data = strData.split(Pattern.quote(String.valueOf(charSplit.charAt(0))));
                        binding.txtTotalContent.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    binding.txtTotalContent.setText("0");
                }
                binding.btnShowHideDataContent.setText("Expand");
                binding.grpUser.setVisibility(View.VISIBLE);
                binding.grpHeader.setVisibility(View.VISIBLE);
                binding.grpFooter.setVisibility(View.VISIBLE);
                binding.grpLink.setVisibility(View.VISIBLE);
                binding.grpCaption.setVisibility(View.VISIBLE);
                binding.grpControl.setVisibility(View.VISIBLE);
                binding.txtStrDataContent.setVisibility(View.GONE);
            }
        });
        binding.btnShowHideDataFooter.setOnClickListener(v -> {
            if(binding.btnShowHideDataFooter.getText().toString().equals("Expand")){
                binding.btnShowHideDataFooter.setText("Collapse");
                binding.grpUser.setVisibility(View.GONE);
                binding.grpHeader.setVisibility(View.GONE);
                binding.grpContent.setVisibility(View.GONE);
                binding.grpLink.setVisibility(View.GONE);
                binding.grpCaption.setVisibility(View.GONE);
                binding.grpControl.setVisibility(View.GONE);
                binding.txtStrDataFooter.setVisibility(View.VISIBLE);
            }else{
                String strData = binding.txtStrDataFooter.getText().toString();
                String charSplit = binding.txtStrSplitFooter.getText().toString();
                if(!strData.isEmpty() && !charSplit.isEmpty()){
                    try {
                        String[] data = strData.split(Pattern.quote(String.valueOf(charSplit.charAt(0))));
                        binding.txtTotalFooter.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    binding.txtTotalFooter.setText("0");
                }
                binding.btnShowHideDataFooter.setText("Expand");
                binding.grpUser.setVisibility(View.VISIBLE);
                binding.grpHeader.setVisibility(View.VISIBLE);
                binding.grpContent.setVisibility(View.VISIBLE);
                binding.grpLink.setVisibility(View.VISIBLE);
                binding.grpCaption.setVisibility(View.VISIBLE);
                binding.grpControl.setVisibility(View.VISIBLE);
                binding.txtStrDataFooter.setVisibility(View.GONE);
            }
        });
        binding.btnShowHideDataLink.setOnClickListener(v -> {
            if(binding.btnShowHideDataLink.getText().toString().equals("Expand")){
                binding.btnShowHideDataLink.setText("Collapse");
                binding.grpUser.setVisibility(View.GONE);
                binding.grpHeader.setVisibility(View.GONE);
                binding.grpContent.setVisibility(View.GONE);
                binding.grpFooter.setVisibility(View.GONE);
                binding.grpCaption.setVisibility(View.GONE);
                binding.grpControl.setVisibility(View.GONE);
                binding.txtStrDataLink.setVisibility(View.VISIBLE);
            }else{
                String strData = binding.txtStrDataLink.getText().toString();
                String charSplit = binding.txtStrSplitLink.getText().toString();
                if(!strData.isEmpty() && !charSplit.isEmpty()){
                    try {
                        String[] data = strData.split(Pattern.quote(String.valueOf(charSplit.charAt(0))));
                        binding.txtTotalLink.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    binding.txtTotalLink.setText("0");
                }
                binding.btnShowHideDataLink.setText("Expand");
                binding.grpUser.setVisibility(View.VISIBLE);
                binding.grpHeader.setVisibility(View.VISIBLE);
                binding.grpContent.setVisibility(View.VISIBLE);
                binding.grpFooter.setVisibility(View.VISIBLE);
                binding.grpCaption.setVisibility(View.VISIBLE);
                binding.grpControl.setVisibility(View.VISIBLE);
                binding.txtStrDataLink.setVisibility(View.GONE);
            }
        });
        binding.btnShowHideDataCaption.setOnClickListener(v -> {
            if(binding.btnShowHideDataCaption.getText().toString().equals("Expand")){
                binding.btnShowHideDataCaption.setText("Collapse");
                binding.grpUser.setVisibility(View.GONE);
                binding.grpHeader.setVisibility(View.GONE);
                binding.grpContent.setVisibility(View.GONE);
                binding.grpFooter.setVisibility(View.GONE);
                binding.grpLink.setVisibility(View.GONE);
                binding.grpControl.setVisibility(View.GONE);
                binding.txtStrDataCaption.setVisibility(View.VISIBLE);
            }else{
                String strData = binding.txtStrDataCaption.getText().toString();
                String charSplit = binding.txtStrSplitCaption.getText().toString();
                if(!strData.isEmpty() && !charSplit.isEmpty()){
                    try {
                        String[] data = strData.split(Pattern.quote(String.valueOf(charSplit.charAt(0))));
                        binding.txtTotalCaption.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    binding.txtTotalCaption.setText("0");
                }
                binding.btnShowHideDataCaption.setText("Expand");
                binding.grpUser.setVisibility(View.VISIBLE);
                binding.grpHeader.setVisibility(View.VISIBLE);
                binding.grpContent.setVisibility(View.VISIBLE);
                binding.grpFooter.setVisibility(View.VISIBLE);
                binding.grpLink.setVisibility(View.VISIBLE);
                binding.grpControl.setVisibility(View.VISIBLE);
                binding.txtStrDataCaption.setVisibility(View.GONE);
            }
        });

        binding.btnCancelAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        binding.btnSaveAccount.setOnClickListener(v -> {
            try {
                String fileFolder = String.format("%s/%s/%s",
                        Constants.FOLDER_ROOT,
                        Constants.FOLDER_NAME_APP,
                        Constants.FOLDER_NAME_ACCOUNT);
                String username = binding.txtUsername.getText().toString().trim();
                if(username.length() > 0){
                    binding.getAccount().setUsername(username);
                    File fileAccount = new File(fileFolder, username + ".json");
                    if(binding.ckbUserLogin.isChecked()){
                        String password = binding.txtPassword.getText().toString().trim();
                        if(password.length() > 0){
                            binding.getAccount().setPassword(password);
                            binding.getAccount().setHeader(null);
                            binding.getAccount().setContent(null);
                            binding.getAccount().setFooter(null);
                            binding.getAccount().setLink(null);
                            binding.getAccount().setCaption(null);
                            Utils.object2File(fileAccount, binding.getAccount());
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(v.getContext(), "Password is required", Toast.LENGTH_SHORT).show();
                            binding.txtPassword.forceLayout();
                        }
                    }else{
                        String splitHeader = String.valueOf(binding.txtStrSplitHeader.getText().charAt(0));
                        String splitContent = String.valueOf(binding.txtStrSplitContent.getText().charAt(0));
                        String splitFooter = String.valueOf(binding.txtStrSplitFooter.getText().charAt(0));
                        String splitLink = String.valueOf(binding.txtStrSplitLink.getText().charAt(0));
                        String splitCaption = String.valueOf(binding.txtStrSplitCaption.getText().charAt(0));
                        String dataHeader = binding.txtStrDataHeader.getText().toString();
                        String dataContent = binding.txtStrDataContent.getText().toString();
                        String dataFooter = binding.txtStrDataFooter.getText().toString();
                        String dataLink = binding.txtStrDataLink.getText().toString();
                        String dataCaption = binding.txtStrDataCaption.getText().toString();
                        if(splitHeader.isEmpty()){
                            Toast.makeText(v.getContext(), "Split header is required", Toast.LENGTH_SHORT).show();
                            binding.txtStrSplitHeader.forceLayout();
                        }else if(dataHeader.isEmpty()){
                            Toast.makeText(v.getContext(), "Data header is required", Toast.LENGTH_SHORT).show();
                            binding.txtStrDataHeader.forceLayout();
                        }else if(splitContent.isEmpty()){
                            Toast.makeText(v.getContext(), "Split content is required", Toast.LENGTH_SHORT).show();
                            binding.txtStrSplitContent.forceLayout();
                        }else if(dataContent.isEmpty()){
                            Toast.makeText(v.getContext(), "Data content is required", Toast.LENGTH_SHORT).show();
                            binding.txtStrDataContent.forceLayout();
                        }else if(splitFooter.isEmpty()){
                            Toast.makeText(v.getContext(), "Split footer is required", Toast.LENGTH_SHORT).show();
                            binding.txtStrSplitFooter.forceLayout();
                        }else if(dataFooter.isEmpty()) {
                            Toast.makeText(v.getContext(), "Data footer is required", Toast.LENGTH_SHORT).show();
                            binding.txtStrDataFooter.forceLayout();
                        }else if(splitLink.isEmpty()){
                            Toast.makeText(v.getContext(), "Split link is required", Toast.LENGTH_SHORT).show();
                            binding.txtStrSplitLink.forceLayout();
                        }else if(dataLink.isEmpty()) {
                            Toast.makeText(v.getContext(), "Data link is required", Toast.LENGTH_SHORT).show();
                            binding.txtStrDataLink.forceLayout();
                        }else if(splitCaption.isEmpty()){
                            Toast.makeText(v.getContext(), "Split caption is required", Toast.LENGTH_SHORT).show();
                            binding.txtStrSplitCaption.forceLayout();
                        }else if(dataCaption.isEmpty()) {
                            Toast.makeText(v.getContext(), "Data caption is required", Toast.LENGTH_SHORT).show();
                            binding.txtStrDataCaption.forceLayout();
                        }else{
                            binding.getAccount().setSplitHeader(splitHeader);
                            binding.getAccount().setSplitContent(splitContent);
                            binding.getAccount().setSplitFooter(splitFooter);
                            binding.getAccount().setSplitLink(splitLink);
                            binding.getAccount().setSplitCaption(splitCaption);
                            binding.getAccount().setHeader(dataHeader);
                            binding.getAccount().setContent(dataContent);
                            binding.getAccount().setFooter(dataFooter);
                            binding.getAccount().setLink(dataLink);
                            binding.getAccount().setCaption(dataCaption);
                            binding.getAccount().setPassword(null);
                            Utils.object2File(fileAccount, binding.getAccount());
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        binding.txtStrDataHeader.setOnTouchListener((v, event) -> {
            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
        binding.txtStrDataContent.setOnTouchListener((v, event) -> {
            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
        binding.txtStrDataFooter.setOnTouchListener((v, event) -> {
            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
        binding.txtStrDataLink.setOnTouchListener((v, event) -> {
            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
        binding.txtStrDataCaption.setOnTouchListener((v, event) -> {
            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
    }
}