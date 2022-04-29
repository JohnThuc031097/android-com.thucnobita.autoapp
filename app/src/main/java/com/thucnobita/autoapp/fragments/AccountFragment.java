package com.thucnobita.autoapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.adapters.ItemAccountAdapter;
import com.thucnobita.autoapp.adapters.ViewPagerAdapter;
import com.thucnobita.autoapp.models.Account;

import java.util.ArrayList;

public class AccountFragment extends Fragment {
    private RecyclerView rvAccount;

    private final ArrayList<Account> listAccount = new ArrayList<>();

    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewAccount = inflater.inflate(R.layout.fragment_account, container, false);
        rvAccount = viewAccount.findViewById(R.id.rvAccount);

        listAccount.add(new Account(
                "username_1",
                "password_1",
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()));
        listAccount.add(new Account(
                "username_2",
                "password_2",
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()));
        listAccount.add(new Account(
                "username_3",
                "password_3",
                false,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()));
        ItemAccountAdapter itemAccountAdapter = new ItemAccountAdapter(listAccount);
        rvAccount.setAdapter(itemAccountAdapter);
        rvAccount.setLayoutManager(new LinearLayoutManager(getContext()));
        return viewAccount;
    }
}