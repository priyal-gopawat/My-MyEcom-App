package com.streamliners.my_ecom.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.streamliners.modules.Cart;
import com.streamliners.modules.Product;
import com.streamliners.modules.ProductType;
import com.streamliners.my_ecom.controllers.databinders.VBProductBinder;
import com.streamliners.my_ecom.controllers.databinders.WBProductBinder;
import com.streamliners.my_ecom.controllers.viewholders.VBProductViewHolder;
import com.streamliners.my_ecom.controllers.viewholders.WBProductViewHolder;
import com.streamliners.my_ecom.databinding.ItemVbProductBinding;
import com.streamliners.my_ecom.databinding.ItemWbProductBinding;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Product> products;

    private Cart cart;
    private AdapterCallbacksListener listener;

    private WBProductBinder wbProductBinder;
    private VBProductBinder vbProductBinder;

    public ProductsAdapter(Context context, List<Product> products, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.products = products;
        this.cart = cart;
        this.listener = listener;

        wbProductBinder = new WBProductBinder(context, cart, listener);
        vbProductBinder = new VBProductBinder(context, cart, listener);
    }

    @Override
    public int getItemViewType(int position) {
        return products.get(position).type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ProductType.TYPE_WB){
            ItemWbProductBinding binding = ItemWbProductBinding.inflate(
                    LayoutInflater.from(context)
                    , parent
                    , false
            );
            return new WBProductViewHolder(binding);
        } else {
            ItemVbProductBinding binding = ItemVbProductBinding.inflate(
                    LayoutInflater.from(context)
                    , parent
                    , false
            );
            return new VBProductViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = products.get(position);

        if(holder instanceof WBProductViewHolder){
            wbProductBinder.bind(((WBProductViewHolder) holder).b, product, position );
        } else {
            vbProductBinder.bind(((VBProductViewHolder) holder).b, product, position );
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

}
