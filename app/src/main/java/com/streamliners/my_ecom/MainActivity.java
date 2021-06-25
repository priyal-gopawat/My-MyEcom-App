package com.streamliners.my_ecom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.streamliners.my_ecom.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        b=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

    }



}