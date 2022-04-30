package com.thucnobita.autoapp.adapters;

import android.content.Context;
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
    public interface ItemClickListener {
        void onClick(View view, int position,boolean isLongClick);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageViewActived;
        public ItemAccountBinding itemAccountBinding;
        private ItemAccountAdapter.ItemClickListener itemClickListener;

        public ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            imageViewActived = itemView.findViewById(R.id.imgIsActivedAccount);
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
        holder.imageViewActived.setImageResource(account.isActived() ?
                R.drawable.ic_baseline_check_circle_outline_24 :
                R.drawable.ic_baseline_radio_button_unchecked_24);
        holder.setBinding(account);
    }

    @Override
    public int getItemCount() {
        return listAccount.size();
    }
}
