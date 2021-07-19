package com.streamliners.my_ecom.tmp;

import android.net.Uri;

import com.streamliners.modules.Product;
import com.streamliners.modules.Variant;
import com.streamliners.my_ecom.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsHelper {

    public static List<Product> getProducts(){

        List<Product> products = new ArrayList<>();

        Product apple = new Product("Apple", Uri.parse("android.resource://com.streamliners.my_ecom/" + R.drawable.apple).toString(), 80, 1);

        Product orange = new Product("Orange", Uri.parse("android.resource://com.streamliners.my_ecom/" + R.drawable.orange).toString(), 100, 1.5f);

        Product tomato = new Product("Tomatoes", Uri.parse("android.resource://com.streamliners.my_ecom/" + R.drawable.tomato).toString(), 50, 1);

        Product mango = new Product("Mango", Uri.parse("android.resource://com.streamliners.my_ecom/" + R.drawable.mango).toString(), 120, 2);

        Product kiwi = new Product("Kiwi", Uri.parse("android.resource://com.streamliners.my_ecom/" + R.drawable.kiwi).toString(), new ArrayList<>
                (Arrays.asList(new Variant("500g",96), new Variant("1kg",180))
        ));

        Product surfExcel = new Product("Surf Excel", Uri.parse("android.resource://com.streamliners.my_ecom/" + R.drawable.surf_excel).toString(), new ArrayList<>
                (Arrays.asList(new Variant("2kg",80), new Variant("5kg",170))
        ));

        Product caneSugar = new Product("Cane Sugar", Uri.parse("android.resource://com.streamliners.my_ecom/" + R.drawable.cane_sugar).toString(), new ArrayList<>
                (Arrays.asList(new Variant("750g",110), new Variant("1.5kg",250))
        ));

        Product strawberries = new Product("Strawberries", Uri.parse("android.resource://com.streamliners.my_ecom/" + R.drawable.strawberries).toString(), new ArrayList<>
                (Arrays.asList(new Variant("250g",80), new Variant("750",160))
        ));


        products.add(apple);
        products.add(kiwi);
        products.add(orange);
        products.add(mango);
        products.add(strawberries);
        products.add(surfExcel);
        products.add(tomato);
        products.add(caneSugar);

        return products;
    }
}
