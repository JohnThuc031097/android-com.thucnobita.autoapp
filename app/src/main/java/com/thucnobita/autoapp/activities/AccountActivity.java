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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.databinding.ActivityAccountBinding;
import com.thucnobita.autoapp.fragments.AccountFragment;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Util;

import java.util.regex.Pattern;

public class AccountActivity extends AppCompatActivity {
    private ActivityAccountBinding binding;
    private Account account;

    @SuppressLint("SetTextI18n")
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
                        "|","",
                        "|", "",
                        "|", "");
                binding.setAccount(account);
            }else if(bundle.getString("type").equals("edit")){
                if(bundle.getString("account") != null){
                    try {
                        account = Util.string2Object(bundle.getString("account"), Account.class);
                        binding.setAccount(account);
                    }catch (JsonProcessingException jsonpe){
                        jsonpe.printStackTrace();
                    }
                }
            }
        }
    }

    private void initValueDefault(){
        binding.grpPasswordLogin.setVisibility(View.VISIBLE);
        binding.grpPostContent.setVisibility(View.GONE);
        runOnUiThread(() -> {
            if(account.getPassword() != null){
                binding.ckbUserLogin.setChecked(true);
            }else{
                binding.ckbUserLogin.setChecked(false);
            }
            if(binding.ckbUserLogin.isChecked()){
                binding.grpPasswordLogin.setVisibility(View.VISIBLE);
                binding.grpPostContent.setVisibility(View.GONE);
            }else{
                binding.grpPasswordLogin.setVisibility(View.GONE);
                binding.grpPostContent.setVisibility(View.VISIBLE);
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
        binding.txtStrDataHeader.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE){
                CharSequence strData = binding.txtStrDataHeader.getText();
                CharSequence charSplit = binding.txtStrSplitHeader.getText();
                if(strData != null && charSplit != null){
                    try {
                        String[] data = strData.toString().split(Pattern.quote(String.valueOf(charSplit.charAt(0))));
                        binding.txtTotalHeader.setText(data.length + "");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            return true;
        });
        binding.txtStrDataContent.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE){
                CharSequence strData = binding.txtStrDataContent.getText();
                CharSequence charSplit = binding.txtStrSplitContent.getText();
                if(strData != null && charSplit != null){
                    try {
                        String[] data = strData.toString().split(Pattern.quote(String.valueOf(charSplit.charAt(0))));
                        binding.txtTotalContent.setText(data.length + "");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            return true;
        });
        binding.txtStrDataFooter.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE){
                CharSequence strData = binding.txtStrDataFooter.getText();
                CharSequence charSplit = binding.txtStrSplitFooter.getText();
                if(strData != null && charSplit != null){
                    try {
                        String[] data = strData.toString().split(Pattern.quote(String.valueOf(charSplit.charAt(0))));
                        binding.txtTotalFooter.setText(data.length + "");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
            return true;
        });
        binding.btnCancelAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        });
        binding.btnSaveAccount.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            try {
                intent.putExtra("account", Util.object2String(account));
                startActivity(intent);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }
}