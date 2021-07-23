package com.streamliners.my_ecom.controllers.databinders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.streamliners.modules.Cart;
import com.streamliners.modules.Product;
import com.streamliners.my_ecom.controllers.AdapterCallbacksListener;
import com.streamliners.my_ecom.databinding.ItemWbProductBinding;
import com.streamliners.my_ecom.dialogs.WeightPickerDialog;

public class WBProductBinder {
    private Context context;
    private Cart cart;
    private AdapterCallbacksListener listener;

    public WBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

    @SuppressLint("DefaultLocale")
    public void bind(ItemWbProductBinding b, Product product, int position){
        b.wbProductName.setText(product.name);
        b.subtitle.setText(String.format("₹%.2f/kg", product.pricePerKg));
        b.imageWbProduct.setImageURI(Uri.parse(product.imageURL));

        cart = new Cart();
        buttonEventHandlers(b,product,position);

        checkWbProductInCart(b,product);
    }

    @SuppressLint("SetTextI18n")
    public void checkWbProductInCart(ItemWbProductBinding b, Product product) {
        if(cart.cartItems.containsKey(product.name)){
            b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
            b.btnAdd.setVisibility(View.GONE);
            b.qtyWb.setText(cart.cartItems.get(product.name).qty + "Kg");
        }
        else{
            b.nonZeroQtyGroup.setVisibility(View.GONE);
            b.btnAdd.setVisibility(View.VISIBLE);
        }

    }

    private void buttonEventHandlers(ItemWbProductBinding b,Product product,int position) {
        b.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(product, position);
            }
        });

        b.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(product, position);
            }
        });
    }

    private void showDialog(Product product, int position) {
        new WeightPickerDialog(context, cart, position, product, listener).show();
    }
}
