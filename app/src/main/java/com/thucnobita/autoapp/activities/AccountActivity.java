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
    private final String charSplit = Pattern.quote(String.valueOf("|".charAt(0)));

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
                        null, null,
                        true,
                        null,
                        null, null, null,
                        null,
                        null);
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
        binding.txtStrDataContent1.setVisibility(View.GONE);
        binding.txtStrDataContent2.setVisibility(View.GONE);
        binding.txtStrDataContent3.setVisibility(View.GONE);
        binding.txtStrDataFooter.setVisibility(View.GONE);
        runOnUiThread(() -> {
            if(account.getPassword() != null){
                binding.ckbUserLogin.setChecked(true);
                binding.grpPasswordLogin.setVisibility(View.VISIBLE);
                binding.grpPostContent.setVisibility(View.GONE);
            }else{
                binding.ckbUserLogin.setChecked(false);
                binding.grpPasswordLogin.setVisibility(View.GONE);
                binding.grpPostContent.setVisibility(View.VISIBLE);
                if(account.getHeader() != null){
                    try {
                        String[] data = account.getHeader().split(charSplit);
                        binding.txtTotalHeader.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(account.getContent1() != null){
                    try {
                        String[] data = account.getContent1().split(charSplit);
                        binding.txtTotalContent1.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(account.getContent2() != null){
                    try {
                        String[] data = account.getContent2().split(charSplit);
                        binding.txtTotalContent2.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(account.getContent3() != null){
                    try {
                        String[] data = account.getContent3().split(charSplit);
                        binding.txtTotalContent3.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(account.getFooter() != null){
                    try {
                        String[] data = account.getFooter().split(charSplit);
                        binding.txtTotalFooter.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void initAction(){
        // Handle click checkbox User login
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
        // Handle click button Show/Hidden
        // Header
        binding.btnShowHideDataHeader.setOnClickListener(v -> {
            if(binding.btnShowHideDataHeader.getText().toString().equals("Expand")){
                binding.btnShowHideDataHeader.setText("Collapse");
                binding.grpUser.setVisibility(View.GONE);
                binding.grpContent1.setVisibility(View.GONE);
                binding.grpContent2.setVisibility(View.GONE);
                binding.grpContent3.setVisibility(View.GONE);
                binding.grpFooter.setVisibility(View.GONE);
                binding.grpLink.setVisibility(View.GONE);
                binding.grpControl.setVisibility(View.GONE);
                binding.txtStrDataHeader.setVisibility(View.VISIBLE);
            }else{
                String strData = binding.txtStrDataHeader.getText().toString();
                if(!strData.isEmpty()){
                    try {
                        String[] data = strData.split(charSplit);
                        binding.txtTotalHeader.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    binding.txtTotalHeader.setText("0");
                }
                binding.btnShowHideDataHeader.setText("Expand");
                binding.grpUser.setVisibility(View.VISIBLE);
                binding.grpContent1.setVisibility(View.VISIBLE);
                binding.grpContent2.setVisibility(View.VISIBLE);
                binding.grpContent3.setVisibility(View.VISIBLE);
                binding.grpFooter.setVisibility(View.VISIBLE);
                binding.grpLink.setVisibility(View.VISIBLE);
                binding.grpControl.setVisibility(View.VISIBLE);
                binding.txtStrDataHeader.setVisibility(View.GONE);
            }
        });
        // Content 1
        binding.btnShowHideDataContent1.setOnClickListener(v -> {
            if(binding.btnShowHideDataContent1.getText().toString().equals("Expand")){
                binding.btnShowHideDataContent1.setText("Collapse");
                binding.grpUser.setVisibility(View.GONE);
                binding.grpHeader.setVisibility(View.GONE);
                binding.grpContent2.setVisibility(View.GONE);
                binding.grpContent3.setVisibility(View.GONE);
                binding.grpFooter.setVisibility(View.GONE);
                binding.grpLink.setVisibility(View.GONE);
                binding.grpControl.setVisibility(View.GONE);
                binding.txtStrDataContent1.setVisibility(View.VISIBLE);
            }else{
                String strData = binding.txtStrDataContent1.getText().toString();
                if(!strData.isEmpty()){
                    try {
                        String[] data = strData.split(charSplit);
                        binding.txtTotalContent1.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    binding.txtTotalContent1.setText("0");
                }
                binding.btnShowHideDataContent1.setText("Expand");
                binding.grpUser.setVisibility(View.VISIBLE);
                binding.grpHeader.setVisibility(View.VISIBLE);
                binding.grpContent2.setVisibility(View.VISIBLE);
                binding.grpContent3.setVisibility(View.VISIBLE);
                binding.grpFooter.setVisibility(View.VISIBLE);
                binding.grpLink.setVisibility(View.VISIBLE);
                binding.grpControl.setVisibility(View.VISIBLE);
                binding.txtStrDataContent1.setVisibility(View.GONE);
            }
        });
        // Content 2
        binding.btnShowHideDataContent2.setOnClickListener(v -> {
            if(binding.btnShowHideDataContent2.getText().toString().equals("Expand")){
                binding.btnShowHideDataContent2.setText("Collapse");
                binding.grpUser.setVisibility(View.GONE);
                binding.grpHeader.setVisibility(View.GONE);
                binding.grpContent1.setVisibility(View.GONE);
                binding.grpContent3.setVisibility(View.GONE);
                binding.grpFooter.setVisibility(View.GONE);
                binding.grpLink.setVisibility(View.GONE);
                binding.grpControl.setVisibility(View.GONE);
                binding.txtStrDataContent2.setVisibility(View.VISIBLE);
            }else{
                String strData = binding.txtStrDataContent2.getText().toString();
                if(!strData.isEmpty()){
                    try {
                        String[] data = strData.split(charSplit);
                        binding.txtTotalContent2.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    binding.txtTotalContent2.setText("0");
                }
                binding.btnShowHideDataContent2.setText("Expand");
                binding.grpUser.setVisibility(View.VISIBLE);
                binding.grpHeader.setVisibility(View.VISIBLE);
                binding.grpContent1.setVisibility(View.VISIBLE);
                binding.grpContent3.setVisibility(View.VISIBLE);
                binding.grpFooter.setVisibility(View.VISIBLE);
                binding.grpLink.setVisibility(View.VISIBLE);
                binding.grpControl.setVisibility(View.VISIBLE);
                binding.txtStrDataContent2.setVisibility(View.GONE);
            }
        });
        // Content 3
        binding.btnShowHideDataContent3.setOnClickListener(v -> {
            if(binding.btnShowHideDataContent3.getText().toString().equals("Expand")){
                binding.btnShowHideDataContent3.setText("Collapse");
                binding.grpUser.setVisibility(View.GONE);
                binding.grpHeader.setVisibility(View.GONE);
                binding.grpContent1.setVisibility(View.GONE);
                binding.grpContent2.setVisibility(View.GONE);
                binding.grpFooter.setVisibility(View.GONE);
                binding.grpLink.setVisibility(View.GONE);
                binding.grpControl.setVisibility(View.GONE);
                binding.txtStrDataContent3.setVisibility(View.VISIBLE);
            }else{
                String strData = binding.txtStrDataContent3.getText().toString();
                if(!strData.isEmpty()){
                    try {
                        String[] data = strData.split(charSplit);
                        binding.txtTotalContent3.setText(String.valueOf(data.length));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    binding.txtTotalContent3.setText("0");
                }
                binding.btnShowHideDataContent3.setText("Expand");
                binding.grpUser.setVisibility(View.VISIBLE);
                binding.grpHeader.setVisibility(View.VISIBLE);
                binding.grpContent1.setVisibility(View.VISIBLE);
                binding.grpContent2.setVisibility(View.VISIBLE);
                binding.grpFooter.setVisibility(View.VISIBLE);
                binding.grpLink.setVisibility(View.VISIBLE);
                binding.grpControl.setVisibility(View.VISIBLE);
                binding.txtStrDataContent3.setVisibility(View.GONE);
            }
        });
        // Footer
        binding.btnShowHideDataFooter.setOnClickListener(v -> {
            if(binding.btnShowHideDataFooter.getText().toString().equals("Expand")){
                binding.btnShowHideDataFooter.setText("Collapse");
                binding.grpUser.setVisibility(View.GONE);
                binding.grpHeader.setVisibility(View.GONE);
                binding.grpContent1.setVisibility(View.GONE);
                binding.grpContent2.setVisibility(View.GONE);
                binding.grpContent3.setVisibility(View.GONE);
                binding.grpLink.setVisibility(View.GONE);
                binding.grpControl.setVisibility(View.GONE);
                binding.txtStrDataFooter.setVisibility(View.VISIBLE);
            }else{
                String strData = binding.txtStrDataFooter.getText().toString();
                if(!strData.isEmpty()){
                    try {
                        String[] data = strData.split(charSplit);
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
                binding.grpContent1.setVisibility(View.VISIBLE);
                binding.grpContent2.setVisibility(View.VISIBLE);
                binding.grpContent3.setVisibility(View.VISIBLE);
                binding.grpLink.setVisibility(View.VISIBLE);
                binding.grpControl.setVisibility(View.VISIBLE);
                binding.txtStrDataFooter.setVisibility(View.GONE);
            }
        });
        // Handle click button Cancel
        binding.btnCancelAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        // Handle click button Save
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
                            binding.getAccount().setContent1(null);
                            binding.getAccount().setContent2(null);
                            binding.getAccount().setContent3(null);
                            binding.getAccount().setFooter(null);
                            binding.getAccount().setLink(null);
                            Utils.object2File(fileAccount, binding.getAccount());
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(v.getContext(), "Password is required", Toast.LENGTH_SHORT).show();
                            binding.txtPassword.forceLayout();
                        }
                    }else{
                        String dataHeader = binding.txtStrDataHeader.getText().toString();
                        String dataContent1 = binding.txtStrDataContent1.getText().toString();
                        String dataContent2 = binding.txtStrDataContent2.getText().toString();
                        String dataContent3 = binding.txtStrDataContent3.getText().toString();
                        String dataFooter = binding.txtStrDataFooter.getText().toString();
                        String dataLink = binding.txtStrDataLink.getText().toString();
                        binding.getAccount().setHeader(dataHeader.isEmpty() ? null : dataHeader);
                        binding.getAccount().setContent1(dataContent1.isEmpty() ? null : dataContent1);
                        binding.getAccount().setContent2(dataContent2.isEmpty() ? null : dataContent2);
                        binding.getAccount().setContent3(dataContent3.isEmpty() ? null : dataContent3);
                        binding.getAccount().setFooter(dataFooter.isEmpty() ? null : dataFooter);
                        binding.getAccount().setLink(dataLink.isEmpty() ? null : dataLink);
                        binding.getAccount().setPassword(null);
                        Utils.object2File(fileAccount, binding.getAccount());
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Handle scroll
        // Header
        binding.txtStrDataHeader.setOnTouchListener((v, event) -> {
            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
        // Content 1
        binding.txtStrDataContent1.setOnTouchListener((v, event) -> {
            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
        // Content 2
        binding.txtStrDataContent2.setOnTouchListener((v, event) -> {
            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
        // Content 3
        binding.txtStrDataContent3.setOnTouchListener((v, event) -> {
            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
        // Footer
        binding.txtStrDataFooter.setOnTouchListener((v, event) -> {
            v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
            if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
            }
            return false;
        });
    }
}