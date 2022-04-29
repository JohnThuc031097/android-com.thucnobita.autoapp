package com.thucnobita.autoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.databinding.ItemAccountBinding;
import com.thucnobita.autoapp.models.Account;

import java.util.ArrayList;

public class ItemAccountAdapter extends RecyclerView.Adapter<ItemAccountAdapter.ViewHolder> {
    public Button btnAccountName;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView){
            super(itemView);
            btnAccountName = itemView.findViewById(R.id.btnAccountName);
        }
    }

    public ArrayList<Account> listAccount;

    public ItemAccountAdapter(ArrayList<Account> listAccount){
        this.listAccount = listAccount;
    }

    @NonNull
    @Override
    public ItemAccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View accountView = inflater.inflate(R.layout.item_account, parent, false);

        // Return a new holder instance
        return new ViewHolder(accountView);
    }

    @Override
    public void onBindViewHolder(ItemAccountAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Account account = listAccount.get(position);
        btnAccountName.setText(account.username.toString());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return listAccount.size();
    }
}
