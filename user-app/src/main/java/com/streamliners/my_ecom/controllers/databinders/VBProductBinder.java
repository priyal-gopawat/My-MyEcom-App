package com.streamliners.my_ecom.controllers.databinders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.streamliners.modules.Cart;
import com.streamliners.modules.Product;
import com.streamliners.modules.Variant;
import com.streamliners.my_ecom.MainActivity;
import com.streamliners.my_ecom.controllers.AdapterCallbacksListener;
import com.streamliners.my_ecom.databinding.ChipVariantBinding;
import com.streamliners.my_ecom.databinding.ItemVbProductBinding;
import com.streamliners.my_ecom.dialogs.VariantsQtyPickerDialog;

public class VBProductBinder {
    private Context context;
    private Cart cart;
    private AdapterCallbacksListener listener;

    public VBProductBinder(Context context, Cart cart, AdapterCallbacksListener listener){
        this.context = context;
        this.cart = cart;
        this.listener = listener;
    }

    @SuppressLint("SetTextI18n")
    public void bind(ItemVbProductBinding b, Product product, int position){

        //bind data
        b.vbProductName.setText(product.name);
        b.productVariants.setText(product.variants.size() + " variants");
        b.imageVbProduct.setImageURI(Uri.parse(product.imageURL));
        b.variants.setVisibility(View.GONE);

        cart = new Cart();
        //show and hide variant group
        showAndHideVariantGrp(b);
        inflateVariants(product, b);
        buttonEventHandler(product, b, position);
        checkVbProductInCart(product, b);

    }

    private void showAndHideVariantGrp(ItemVbProductBinding b) {
        b.btnShowVariants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check visibility
                if (b.variants.getVisibility() == View.GONE) {
                    b.variants.setVisibility(View.VISIBLE);
                    b.btnShowVariants.setRotation(180);
                } else {
                    b.variants.setVisibility(View.GONE);
                    b.btnShowVariants.setRotation(0);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void checkVbProductInCart(Product product, ItemVbProductBinding b) {
        //total qty from cart
        int qty = 0;

        for (Variant variant : product.variants) {
            //check qty present in cart
            if (cart.cartItems.containsKey(product.name + " " + variant.name)) {
                qty += cart.cartItems.get(product.name + " " + variant.name).qty;
            }
        }
        //update views
        if (qty > 0) {
            b.nonZeroQtyGroup.setVisibility(View.VISIBLE);
            b.currentQty.setText( qty + "");
        } else {
            b.nonZeroQtyGroup.setVisibility(View.INVISIBLE);
            b.currentQty.setText(0 + "");
        }
    }

    @SuppressLint("SetTextI18n")
    private void inflateVariants(Product product, ItemVbProductBinding b) {
        b.variants.removeAllViews();

        //for variants more than 1
        //check variant size
        if (product.variants.size() > 1) {
            b.vbProductName.setText(product.name);
            for (Variant variant : product.variants) {
                ChipVariantBinding binding = ChipVariantBinding.inflate(((MainActivity) context).getLayoutInflater());
                binding.getRoot().setText(variant.name + " - Rs." + variant.price);
                b.variants.addView(binding.getRoot());
            }
            return;
        }

        //for single variant
        b.btnShowVariants.setVisibility(View.GONE);
        b.productVariants.setText("Rs." + product.variants.get(0).price);
        b.vbProductName.setText(product.name + " " + product.variants.get(0).name);
    }

    private void buttonEventHandler(Product product, ItemVbProductBinding b, int position) {
        b.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for variants more than 1
                //check variant size
                if (product.variants.size() > 1)
                    showDialog(product, position);

                    //for single variant
                else {
                    int qty = Integer.parseInt(b.currentQty.getText().toString()) + 1;
                    cart.add(product, product.variants.get(0), qty);
                    listener.onCartUpdated(position);
                }

            }
        });

        b.btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //for variants more than 1
                //check variant size
                if (product.variants.size() > 1)
                    showDialog(product, position);

                    //for single variant
                else {
                    int qty = Integer.parseInt(b.vbProductName.getText().toString()) - 1;
                    cart.add(product, product.variants.get(0), qty);
                    listener.onCartUpdated(position);
                }
            }
        });
    }
    private void showDialog(Product product, int position) {

        new VariantsQtyPickerDialog(context, product, listener, cart, position).show();
    }
}
