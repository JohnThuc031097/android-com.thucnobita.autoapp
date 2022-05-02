package com.thucnobita.autoapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.activities.AccountActivity;
import com.thucnobita.autoapp.adapters.ItemAccountAdapter;
import com.thucnobita.autoapp.databinding.FragmentAccountBinding;
import com.thucnobita.autoapp.models.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AccountFragment extends Fragment {
    private FloatingActionButton facAddAccount;

    private FragmentAccountBinding fragmentAccountBinding;
    private final ArrayList<Account> listAccount = new ArrayList<>();

    public AccountFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAccountBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        View view = fragmentAccountBinding.getRoot();

        facAddAccount = view.findViewById(R.id.facAddAccount);

        for (int i = 0; i < 20; i++) {
            Account account = new Account(
                    "username_" + i,
                    (new Random().nextBoolean()),
                    "header_" + i,
                    "content_" + i,
                    "footer_" + i
            );
            listAccount.add(account);
        }

        ItemAccountAdapter itemAccountAdapter = new ItemAccountAdapter(listAccount);
        fragmentAccountBinding.rvAccount.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentAccountBinding.rvAccount.setAdapter(itemAccountAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        facAddAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AccountActivity.class);
            intent.putExtra("type", "create");
            startActivity(intent);
        });
    }
}