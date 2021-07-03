package com.streamliners.my_ecom.controllers.viewholders;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.streamliners.my_ecom.databinding.ItemWbProductBinding;

public class WBProductViewHolder extends RecyclerView.ViewHolder {
    public ItemWbProductBinding b;

    public WBProductViewHolder(@NonNull ItemWbProductBinding b) {
        super(b.getRoot());
        this.b = b;
    }
}
