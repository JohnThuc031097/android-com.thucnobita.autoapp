package com.thucnobita.autoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.databinding.ActivityAccountBinding;
import com.thucnobita.autoapp.fragments.AccountFragment;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Util;

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
                        null);
                binding.setAccount(account);
            }else if(bundle.getString("type").equals("edit")){
                if(bundle.getString("account") != null){
                    try {
                        account = Util.string2Object(bundle.getString("account"), Account.class);
                        binding.setAccount(account);
                    }catch (JsonProcessingException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void initValueDefault(){
        binding.grpLinkTShirt.setVisibility(View.GONE);
        binding.grpPasswordLogin.setVisibility(View.VISIBLE);
        binding.grpPostContent.setVisibility(View.GONE);
        binding.txtStrDataHeader.setVisibility(View.GONE);
        binding.txtStrDataContent.setVisibility(View.GONE);
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
                binding.grpLinkTShirt.setVisibility(View.VISIBLE);
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
            }
        });
    }

    @SuppressLint("SetTextI18n")
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
                binding.grpContent.setVisibility(View.GONE);
                binding.grpFooter.setVisibility(View.GONE);
                binding.txtStrDataHeader.setVisibility(View.VISIBLE);
                binding.btnSaveAccount.setVisibility(View.GONE);
                binding.btnCancelAccount.setVisibility(View.GONE);
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
                binding.grpContent.setVisibility(View.VISIBLE);
                binding.grpFooter.setVisibility(View.VISIBLE);
                binding.txtStrDataHeader.setVisibility(View.GONE);
                binding.btnSaveAccount.setVisibility(View.VISIBLE);
                binding.btnCancelAccount.setVisibility(View.VISIBLE);
            }
        });

        binding.btnShowHideDataContent.setOnClickListener(v -> {
            if(binding.btnShowHideDataContent.getText().toString().equals("Expand")){
                binding.btnShowHideDataContent.setText("Collapse");
                binding.grpHeader.setVisibility(View.GONE);
                binding.grpFooter.setVisibility(View.GONE);
                binding.txtStrDataContent.setVisibility(View.VISIBLE);
                binding.btnSaveAccount.setVisibility(View.GONE);
                binding.btnCancelAccount.setVisibility(View.GONE);
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
                binding.grpHeader.setVisibility(View.VISIBLE);
                binding.grpFooter.setVisibility(View.VISIBLE);
                binding.txtStrDataContent.setVisibility(View.GONE);
                binding.btnSaveAccount.setVisibility(View.VISIBLE);
                binding.btnCancelAccount.setVisibility(View.VISIBLE);
            }
        });

        binding.btnShowHideDataFooter.setOnClickListener(v -> {
            if(binding.btnShowHideDataFooter.getText().toString().equals("Expand")){
                binding.btnShowHideDataFooter.setText("Collapse");
                binding.grpHeader.setVisibility(View.GONE);
                binding.grpContent.setVisibility(View.GONE);
                binding.txtStrDataFooter.setVisibility(View.VISIBLE);
                binding.btnSaveAccount.setVisibility(View.GONE);
                binding.btnCancelAccount.setVisibility(View.GONE);
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
                binding.grpHeader.setVisibility(View.VISIBLE);
                binding.grpContent.setVisibility(View.VISIBLE);
                binding.txtStrDataFooter.setVisibility(View.GONE);
                binding.btnSaveAccount.setVisibility(View.VISIBLE);
                binding.btnCancelAccount.setVisibility(View.VISIBLE);
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
                            binding.getAccount().setLinkTShirt(null);
                            Util.object2File(fileAccount, binding.getAccount());
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
                        String dataHeader = binding.txtStrDataHeader.getText().toString();
                        String dataContent = binding.txtStrDataContent.getText().toString();
                        String dataFooter = binding.txtStrDataFooter.getText().toString();
                        String linkTShirt = binding.txtLinkTShirt.getText().toString().trim();
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
                        }else{
                            binding.getAccount().setSplitHeader(splitHeader);
                            binding.getAccount().setSplitContent(splitContent);
                            binding.getAccount().setSplitFooter(splitFooter);
                            binding.getAccount().setHeader(dataHeader);
                            binding.getAccount().setContent(dataContent);
                            binding.getAccount().setFooter(dataFooter);
                            binding.getAccount().setLinkTShirt(linkTShirt.isEmpty() ? null : linkTShirt);
                            binding.getAccount().setPassword(null);
                            Util.object2File(fileAccount, binding.getAccount());
                            Intent intent = new Intent(this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}