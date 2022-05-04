package com.thucnobita.autoapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.activities.AccountActivity;
import com.thucnobita.autoapp.activities.MainActivity;
import com.thucnobita.autoapp.databinding.ItemAccountBinding;
import com.thucnobita.autoapp.models.Account;
import com.thucnobita.autoapp.utils.Constants;
import com.thucnobita.autoapp.utils.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ItemAccountAdapter extends RecyclerView.Adapter<ItemAccountAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgActived;
        public ImageView imgRemove;
        public TextView txtAccountName;
        public ItemAccountBinding itemAccountBinding;

        public ViewHolder(View view){
            super(view);
            imgActived = view.findViewById(R.id.imgActivedAccount);
            imgRemove = view.findViewById(R.id.imgRemoveAccount);
            txtAccountName = view.findViewById(R.id.txtAccountName);
            itemAccountBinding = DataBindingUtil.bind(view);
        }

        public void setBinding(Account account){
            this.itemAccountBinding.setAccount(account);
        }
    }

    private final String fileFolderAccount = String.format("%s/%s/%s",
            Constants.FOLDER_ROOT,
            Constants.FOLDER_NAME_APP,
            Constants.FOLDER_NAME_ACCOUNT);
    private final ArrayList<Account> listAccount;

    public ItemAccountAdapter(ArrayList<Account> listAccount){
        this.listAccount = listAccount;
    }

    @NonNull
    @Override
    public ItemAccountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewItem = inflater.inflate(R.layout.item_account, parent, false);
        return new ItemAccountAdapter.ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(ItemAccountAdapter.ViewHolder holder, int position) {
        Account account = listAccount.get(position);
        holder.setBinding(account);
        // Set value default
        holder.imgActived.setTag(account.isActived());
        holder.imgActived.setImageResource(account.isActived() ?
                R.drawable.ic_baseline_check_circle_outline_24 :
                R.drawable.ic_baseline_radio_button_unchecked_24);
        // Event Click Actived
        holder.imgActived.setOnClickListener(v -> {
            if(v.getTag().equals(true)){
                v.setTag(false);
                account.setActived(false);
                holder.imgActived.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
            }else{
                v.setTag(true);
                account.setActived(true);
                holder.imgActived.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
            }
            File fileAccount = new File(fileFolderAccount, account.getUsername() + ".json");
            try {
                Util.object2File(fileAccount, account);
                notifyItemChanged(position);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        // Event Click Remove
        holder.imgRemove.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete account")
                    .setMessage("Are you sure you want to delete this account?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        try{
                            File fileAccount = new File(fileFolderAccount, account.getUsername() + ".json");
                            if(fileAccount.exists()){
                                if(fileAccount.delete()){
                                    listAccount.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, listAccount.size());
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_delete)
                    .show();
        });
        // Event Click Edit
        holder.txtAccountName.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AccountActivity.class);
            intent.putExtra("type", "edit");
            try {
                intent.putExtra("account", Util.object2String(account));
                v.getContext().startActivity(intent);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAccount.size();
    }
}
