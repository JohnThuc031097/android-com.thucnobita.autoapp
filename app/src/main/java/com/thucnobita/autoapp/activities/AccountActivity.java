package com.thucnobita.autoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.databinding.ActivityAccountBinding;
import com.thucnobita.autoapp.fragments.AccountFragment;
import com.thucnobita.autoapp.models.Account;

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
        initAction();
    }

    private void initIntent(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            if(bundle.getString("type").equals("create")){
                account = new Account("", false, "", "", "");
                binding.setAccount(account);
            }else if(bundle.getString("type").equals("edit")){
                if(bundle.get("account") != null){
                    account = (Account) bundle.get("account");
                    binding.setAccount(account);
                }
            }
        }
    }

    private void initAction(){
        binding.grpPasswordLogin.setVisibility(View.VISIBLE);
        binding.grpPostContent.setVisibility(View.GONE);
        binding.ckbUserLogin.setChecked(true);
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
            Intent intent = new Intent(this, AccountFragment.class);
            intent.putExtra("account", false);
            startActivity(intent);
        });
    }
}