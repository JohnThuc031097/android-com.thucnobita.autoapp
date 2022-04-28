package com.thucnobita.autoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.lzf.easyfloat.permission.PermissionUtils;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.adapters.ViewPagerAdapter;
import com.thucnobita.autoapp.services.FloatingViewService;


public class MainActivity extends AppCompatActivity   {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayoutMain);
        viewPager = findViewById(R.id.viewPagerMain);

        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        String[] arrTabName = { "Bot", "Account" };
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(arrTabName[position])
        ).attach();

    }

    private void initializeView() {
        if(!Settings.canDrawOverlays(this)){
            PermissionUtils.checkPermission(this);
            PermissionUtils.requestPermission(this, permissionResult -> {
                if(!permissionResult){
                    Toast.makeText(this,"Floating view permission validation failed", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            startService(new Intent(MainActivity.this, FloatingViewService.class));
            finish();
        }
    }

}