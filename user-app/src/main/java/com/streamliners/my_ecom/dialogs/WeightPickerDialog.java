package com.streamliners.my_ecom.dialogs;

import android.content.Context;
import android.view.View;
import android.widget.NumberPicker;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.streamliners.modules.Cart;
import com.streamliners.my_ecom.MainActivity;
import com.streamliners.my_ecom.R;
import com.streamliners.my_ecom.controllers.AdapterCallbacksListener;
import com.streamliners.my_ecom.databinding.DialogWeightPickerBinding;
import com.streamliners.modules.Product;

public class WeightPickerDialog {

    private Context context;
    private Cart cart;
    private AlertDialog dialog;
    private Product product;
    private AdapterCallbacksListener listener;
    private int position;
    private int minValueKg;
    private int minValueG;
    private int selectedPosition = 0;
    private DialogWeightPickerBinding binding;

    public WeightPickerDialog(Context context, Cart cart,int position,Product product, AdapterCallbacksListener listener){
        this.context = context;
        this.cart = cart;
        this.product = product;
        this.listener = listener;
        this.position = position;
    }


    public void show() {
        binding = DialogWeightPickerBinding.inflate(((MainActivity)context).getLayoutInflater());

        dialog = new MaterialAlertDialogBuilder(context, R.style.CustomDialogTheme)
                .setCancelable(false)
                .setView(binding.getRoot())
                .show();

        binding.titleWBPDialog.setText(product.name);

        minQty();

        buttonEventHandlers();

        preSelectedQty();

    }

    private void minQty() {
        String[] minValues = String.valueOf(product.minQuantity).split("\\.");

        minValueKg = Integer.parseInt(minValues[0]);
        eventNumberPickerKg();

        String minQtyGram = "0." + minValues[1];

        minValueG = (int)(Float.parseFloat(minQtyGram) * 1000);
        eventNumberPickerG();
    }

    private void eventNumberPickerG() {
        int numberOfValues = 20 - (minValueG / 50);

        int pickerRange = minValueG;
        String[] ValueToDisplay = new String[numberOfValues];

        ValueToDisplay[0] = minValueG + "g";
        for(int i = 1; i < numberOfValues; i++){

            ValueToDisplay[i] = (pickerRange + 50) + "gm";
            pickerRange += 50;
        }

        binding.qtyInGrams.setDisplayedValues(null);
        binding.qtyInGrams.setMinValue(0);
        binding.qtyInGrams.setMaxValue(numberOfValues-1);
        binding.qtyInGrams.setDisplayedValues(ValueToDisplay);
        binding.qtyInGrams.setValue(selectedPosition);
    }

    private void eventNumberPickerKg() {
        int numberOfValues = 11 - minValueKg;

        int pickerRange = minValueKg;
        String[] ValueToDisplay = new String[numberOfValues];

        ValueToDisplay[0] = minValueKg +"Kg";
        for(int i = 1; i < numberOfValues; i++){

            ValueToDisplay[i] = (++pickerRange) + "Kg";
        }
        binding.qtyInKg.setMinValue(0);
        binding.qtyInKg.setMaxValue(ValueToDisplay.length-1);
        binding.qtyInKg.setDisplayedValues(ValueToDisplay);

        binding.qtyInKg.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if(picker.getValue() + minValueKg != minValueKg){
                    if(minValueG == 0){
                        return;
                    }
                    selectedPosition=((minValueG/50+binding.qtyInGrams.getValue())*50) / 50;
                    minValueG = 0;
                    eventNumberPickerG();
                }
                else if(picker.getValue()+minValueKg == minValueKg){
                    minValueG = (int)((product.minQuantity - minValueKg) * 1000);

                    selectedPosition = ((binding.qtyInGrams.getValue() * 50) - minValueG) / 50;
                    if(selectedPosition < 0){
                        selectedPosition = 0;
                    }
                    eventNumberPickerG();
                }
            }
        });
    }

    private void buttonEventHandlers() {
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float qty = (minValueKg + binding.qtyInKg.getValue())
                        + ((((minValueG / 50f) + binding.qtyInGrams.getValue()) * 50) / 1000f);

                if(cart.cartItems.containsKey(product.name) && (cart.cartItems.get(product.name).qty == qty)){
                    dialog.dismiss();
                    return;
                }
                cart.add(product, qty);

                listener.onCartUpdated(position);
                dialog.dismiss();
            }
        });

        binding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.cartItems.containsKey(product.name)){
                    cart.removeWBP(product);
                    listener.onCartUpdated(position);
                }
                dialog.dismiss();
            }

        });
    }

    private void preSelectedQty() {

        if(cart.cartItems.containsKey(product.name)){
            String[] minValues = String.valueOf(cart.cartItems.get(product.name).qty).split("\\.");

            String minQtyG = "0." + minValues[1];

            int gram=(int) (Float.parseFloat(minQtyG) * 1000);

            binding.qtyInGrams.setValue(Integer.parseInt(minValues[0]) - minValueKg);

            if(Integer.parseInt(minValues[0]) != minValueKg){
                if(minValueG != 0){
                    minValueG = 0;
                    eventNumberPickerG();
                }
            }
            binding.qtyInGrams.setValue((gram - minValueG) / 50);
        }
    }
}
