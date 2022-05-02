package com.thucnobita.autoapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.thucnobita.autoapp.R;
import com.thucnobita.autoapp.databinding.ItemAccountBinding;
import com.thucnobita.autoapp.models.Account;

import java.util.ArrayList;

public class ItemAccountAdapter extends RecyclerView.Adapter<ItemAccountAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgActived;
        public ImageView imgRemove;
        public ItemAccountBinding itemAccountBinding;

        public ViewHolder(View itemView){
            super(itemView);
            imgActived = itemView.findViewById(R.id.imgActivedAccount);
            imgRemove = itemView.findViewById(R.id.imgRemoveAccount);
            itemAccountBinding = DataBindingUtil.bind(itemView);
        }

        public void setBinding(Account account){
            this.itemAccountBinding.setAccount(account);
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
        // Click Actived
        holder.imgActived.setOnClickListener(v -> {
            if(v.getTag().equals(true)){
                v.setTag(false);
                listAccount.get(position).setActived(false);
                holder.imgActived.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24);
            }else{
                v.setTag(true);
                listAccount.get(position).setActived(true);
                holder.imgActived.setImageResource(R.drawable.ic_baseline_check_circle_outline_24);
            }
            notifyItemChanged(position);
        });
        // Click Remove
        holder.imgRemove.setOnClickListener(v -> {
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Delete account")
                    .setMessage("Are you sure you want to delete this account?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        listAccount.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listAccount.size());
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_delete)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listAccount.size();
    }
}
