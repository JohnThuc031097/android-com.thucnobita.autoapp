package com.thucnobita.autoapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lzf.easyfloat.EasyFloat;
import com.lzf.easyfloat.enums.ShowPattern;
import com.lzf.easyfloat.interfaces.OnFloatCallbacks;
import com.lzf.easyfloat.interfaces.OnTouchRangeListener;
import com.lzf.easyfloat.permission.PermissionUtils;
import com.lzf.easyfloat.utils.DragUtils;
import com.lzf.easyfloat.widget.BaseSwitchView;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.adapters.ViewPagerAdapter;
import com.thucnobita.autoapp.fragments.AccountFragment;
import com.thucnobita.autoapp.fragments.BotFragment;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Util;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends AppCompatActivity   {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchShowFloatingView;
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private static final String TAG_NAME_FLOATING_VIEW = "FloatingView";
    private boolean canClick = true;
    private boolean isFirstUpdateFragment = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchShowFloatingView = findViewById(R.id.switchShowFloatingView);
        tabLayout = findViewById(R.id.tabLayoutMain);
        viewPager = findViewById(R.id.viewPagerMain);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerAdapter.addFragment(new BotFragment());
        viewPagerAdapter.addFragment(new AccountFragment());
        viewPager.setAdapter(viewPagerAdapter);

        String[] arrTabName = { "Bot", "Account" };
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(arrTabName[position])
        ).attach();

        initAction();

        askPermissions();
    }

    private void initAction(){
        switchShowFloatingView.setOnClickListener(v -> {
            if(switchShowFloatingView.isChecked()){
                showFloatingView();
            }else{
                EasyFloat.dismiss(TAG_NAME_FLOATING_VIEW);
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 0){
                    if(isFirstUpdateFragment){
                        isFirstUpdateFragment = false; // skip first init
                    }else{
                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                            if (fragment.isVisible() && fragment instanceof BotFragment) {
                                BotFragment fragmentCrr = (BotFragment) fragment;
                                if(!fragmentCrr.isRunning){
                                    fragmentCrr.loadData();
                                }
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    private void showFloatingView() {
        AtomicBoolean isShow = new AtomicBoolean(true);
        if(!Settings.canDrawOverlays(this)){
            PermissionUtils.checkPermission(this);
            PermissionUtils.requestPermission(this, isShow::set);
        }
        if(isShow.get()){
            Util.createFloatingView(this, TAG_NAME_FLOATING_VIEW, new OnFloatCallbacks() {
                @Override
                public void hide(@NonNull View view) {

                }
                @Override
                public void createdResult(boolean b, @Nullable String s, @Nullable View view) {

                }
                @Override
                public void show(View view) {
                    runOnUiThread(() -> {
                        switchShowFloatingView.setChecked(true);
                    });
                }
                @Override
                public void dismiss() {
                    runOnUiThread(() -> {
                        switchShowFloatingView.setChecked(false);
                    });
                }
                @Override
                public void touchEvent(View view, MotionEvent motionEvent) {
                    if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                        if(canClick){
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                        }
                    }
                }
                @Override
                public void drag(View view, MotionEvent motionEvent) {
                    canClick = false;
                    DragUtils.INSTANCE.registerDragClose(motionEvent, new OnTouchRangeListener() {
                        @Override
                        public void touchInRange(boolean inRange, BaseSwitchView baseSwitchView) {
                            baseSwitchView.<TextView>findViewById(com.lzf.easyfloat.R.id.tv_delete).setText(
                                    inRange ? "Accept delete" : "Drag here"
                            );
                            baseSwitchView.<ImageView>findViewById(com.lzf.easyfloat.R.id.iv_delete).setImageResource(
                                    inRange ? com.lzf.easyfloat.R.drawable.icon_delete_selected : com.lzf.easyfloat.R.drawable.icon_delete_normal
                            );
                        }

                        @Override
                        public void touchUpInRange() {
                            EasyFloat.dismiss(TAG_NAME_FLOATING_VIEW);
                        }
                    }, com.lzf.easyfloat.R.layout.default_close_layout, ShowPattern.BACKGROUND);
                }
                @Override
                public void dragEnd(View view) {
                    canClick = true;
                }
            });
        }else{
            Toast.makeText(this,"Floating view permission validation failed", Toast.LENGTH_SHORT).show();
        }
    }
}