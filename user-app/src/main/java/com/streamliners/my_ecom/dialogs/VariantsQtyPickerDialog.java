package com.streamliners.my_ecom.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.streamliners.modules.Cart;
import com.streamliners.modules.Product;
import com.streamliners.modules.Variant;
import com.streamliners.my_ecom.MainActivity;
import com.streamliners.my_ecom.R;
import com.streamliners.my_ecom.controllers.AdapterCallbacksListener;
import com.streamliners.my_ecom.databinding.DialogVariantsQtyPickerBinding;
import com.streamliners.my_ecom.databinding.ItemVariantBinding;

import java.util.HashMap;

public class VariantsQtyPickerDialog {
    private Context context;
    private Product product;
    private AdapterCallbacksListener listener;
    private Cart cart;
    private int position;
    private DialogVariantsQtyPickerBinding b;
    private AlertDialog dialog;
    private HashMap<String, Integer> saveVariantsQty = new HashMap<>();

    public VariantsQtyPickerDialog(Context context, Product product, AdapterCallbacksListener listener, Cart cart, int position) {

        this.context = context;
        this.product = product;
        this.listener = listener;
        this.cart = cart;
        this.position = position;
    }

    public void show(){
        b = DialogVariantsQtyPickerBinding.inflate(((MainActivity) context).getLayoutInflater());

        dialog = new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
                .setCancelable(false)
                .setView(b.getRoot())
                .show();

        b.titleVBPDialog.setText(product.name);

        inflateVariants();

        saveVariants();

        removeAllVariants();

    }

    @SuppressLint("SetTextI18n")
    private void inflateVariants() {
        for (Variant variant : product.variants) {

            ItemVariantBinding binding = ItemVariantBinding.inflate(((MainActivity) context).getLayoutInflater());
            binding.variantName.setText("₹" + variant.price + " - " + variant.name);
            b.variants.addView(binding.getRoot());

            //prefill selected variants
            prefillSelectedVariant(binding, variant.name);

            //add quantity
            addQuantityForEachVariant(binding, variant.name);

            //dec quantity
            decQuantityForEachVariant(binding, variant.name);
        }
    }

    @SuppressLint("SetTextI18n")
    private void prefillSelectedVariant(ItemVariantBinding binding, String variantName) {
        //check cart variant is prent or not
        if (cart.cartItems.containsKey(product.name + " " + variantName)) {
            //Save qty in saveVariantsQty
            saveVariantsQty.put(variantName, (int) cart.cartItems.get(product.name + " " + variantName).qty);

            binding.nonZeroQtyGroup.setVisibility(View.VISIBLE);
            binding.qty.setText(saveVariantsQty.get(variantName) + "");
        }
    }


    private void addQuantityForEachVariant(ItemVariantBinding binding, String variantName) {
        binding.incBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                //Save qty of variants
                //check variant present or not
                if (saveVariantsQty.containsKey(variantName)) {
                    saveVariantsQty.put(variantName, saveVariantsQty.get(variantName) + 1);

                } else {
                    saveVariantsQty.put(variantName, 1);
                }
                //update views
                binding.qty.setText(saveVariantsQty.get(variantName) + "");
                binding.nonZeroQtyGroup.setVisibility(View.VISIBLE);
            }
        });
    }


    private void decQuantityForEachVariant(ItemVariantBinding binding, String variantName) {
        binding.decBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save qty of variants
                if (saveVariantsQty.containsKey(variantName)) {
                    saveVariantsQty.put(variantName, saveVariantsQty.get(variantName) - 1);
                }
                //check variant quantity size
                if (saveVariantsQty.get(variantName) == 0) {
                    binding.nonZeroQtyGroup.setVisibility(View.GONE);
                }

                binding.qty.setText(saveVariantsQty.get(variantName) + "");
            }
        });
    }

    private void saveVariants() {
        b.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveVariantsQty.isEmpty()) {
                    for (Variant variant : product.variants) {
                        //check variant present in saveVariantsQty
                        if (saveVariantsQty.containsKey(variant.name)) {
                            cart.add(product, variant, saveVariantsQty.get(variant.name));
                        }
                    }
                    //update views
                    listener.onCartUpdated(position);
                }
                dialog.dismiss();
            }
        });
    }

    private void removeAllVariants() {
        b.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveVariantsQty.isEmpty()) {
                    cart.removeAllVariantsOfVBP(product);
                    //update views
                    listener.onCartUpdated(position);
                }
                dialog.dismiss();
            }
        });
    }
}
