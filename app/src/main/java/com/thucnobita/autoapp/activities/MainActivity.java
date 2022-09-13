package com.thucnobita.autoapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.thucnobita.autoapp.BuildConfig;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.adapters.ViewPagerAdapter;
import com.thucnobita.autoapp.fragments.AccountFragment;
import com.thucnobita.autoapp.fragments.BotFragment;
import com.thucnobita.autoapp.fragments.HandmadeFragment;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity   {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayoutMain);
        viewPager = findViewById(R.id.viewPagerMain);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPagerAdapter.addFragment(new HandmadeFragment());
        viewPagerAdapter.addFragment(new BotFragment());
        viewPagerAdapter.addFragment(new AccountFragment());
        viewPager.setAdapter(viewPagerAdapter);

        String[] arrTabName = { "Handmade", "Bot", "Account"};
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(arrTabName[position])
        ).attach();

        askPermissions();

        initAction();
    }

    private void askPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q){
            if(!Environment.isExternalStorageManager()){
                new AlertDialog.Builder(this)
                        .setTitle("Check Permission")
                        .setPositiveButton("Ok", (dialog, which) -> {
                            viewPager.setCurrentItem(1);
                            viewPager.setCurrentItem(0);
                        })
                        .setCancelable(false)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
                startActivity(intent);
            }
        }else{
            String[] permissions = {
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
            };
            int requestCode = 200;
            requestPermissions(permissions, requestCode);
            viewPager.setCurrentItem(1);
            viewPager.setCurrentItem(0);
        }
    }

    private void initAction(){
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                executor.submit(() -> {
                    if(position == 0){ // 0 = HandmadeFragment
                        HandmadeFragment fragmentCrr = (HandmadeFragment) getSupportFragmentManager().getFragments().get(position);
                        fragmentCrr.loadData();
                    }else if(position == 1){ // 1 = BotFragment
                        BotFragment fragmentCrr = (BotFragment) getSupportFragmentManager().getFragments().get(position);
                        fragmentCrr.loadData();
//                        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//                            if (fragment.isVisible() && fragment instanceof BotFragment) {
//                                BotFragment fragmentCrr = (BotFragment) fragment;
//                                if(!fragmentCrr.isRunning){
//                                    fragmentCrr.loadData();
//                                }
//                                break;
//                            }
//                        }
                    }
                });
            }
        });
    }
}