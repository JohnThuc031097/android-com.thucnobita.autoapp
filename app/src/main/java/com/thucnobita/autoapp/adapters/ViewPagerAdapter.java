package com.thucnobita.autoapp.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.thucnobita.autoapp.fragments.BotFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final ArrayList<Fragment> arrFragment = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void addFragment(Fragment fragment){
        this.arrFragment.add(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return arrFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return arrFragment.size();
    }
}
