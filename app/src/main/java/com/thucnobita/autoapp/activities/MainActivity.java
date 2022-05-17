package com.thucnobita.autoapp.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

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


public class MainActivity extends AppCompatActivity   {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    private boolean isFirstUpdateFragment = true;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void askPermissions() {
        if(!Environment.isExternalStorageManager()){
            Uri uri = Uri.parse("package:" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
            startActivity(intent);
        }else{
            String[] permissions = {
                    "android.permission.READ_EXTERNAL_STORAGE",
                    "android.permission.WRITE_EXTERNAL_STORAGE",
            };
            int requestCode = 200;
            requestPermissions(permissions, requestCode);
        }
    }
}