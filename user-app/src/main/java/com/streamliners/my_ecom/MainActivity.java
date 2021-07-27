package com.streamliners.my_ecom;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.streamliners.modules.Cart;
import com.streamliners.modules.Product;
import com.google.gson.Gson;
import com.streamliners.my_ecom.controllers.ProductsAdapter;
import com.streamliners.my_ecom.controllers.AdapterCallbacksListener;
import com.streamliners.my_ecom.databinding.ActivityMainBinding;
import com.streamliners.my_ecom.tmp.ProductsHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding b;
    private ProductsAdapter adapter;
    private Cart cart = new Cart();
    List<Product> products = new ArrayList<>();
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        setTitle("Products");

        //Handle Shared preferences
        sharedPrefs = getPreferences(MODE_PRIVATE);
        loadSharedPreferences();

        setupAdapter();
    }


    private void setupAdapter() {
        this.products = ProductsHelper.getProducts();

        AdapterCallbacksListener listener = new AdapterCallbacksListener() {
            @Override
            public void onCartUpdated(int itemPosition) {
                updateCartSummary();
                adapter.notifyItemChanged(itemPosition);
            }
        };

        adapter = new ProductsAdapter(this
                , products
                , cart
                , listener);

        b.list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        b.list.setAdapter(adapter);
        b.list.setLayoutManager(new LinearLayoutManager(this));
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void updateCartSummary() {

        if(!cart.cartItems.isEmpty()){
            b.totalItems.setText(cart.noOfItems + " items");
            b.totalPrice.setText("₹" + String.format("%.2f", cart.total));
            b.cartSummary.setVisibility(View.VISIBLE);
        }
        else {
            b.cartSummary.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPrefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(cart);
        editor.putString("CART", json);
        editor.apply();
    }

    private void loadSharedPreferences() {
        Gson gson = new Gson();
        String json = sharedPrefs.getString("CART", "");
        cart = gson.fromJson(json, Cart.class);
        if(cart == null){
            this.cart = new Cart();
            return;
        }
        updateCartSummary();
    }

}