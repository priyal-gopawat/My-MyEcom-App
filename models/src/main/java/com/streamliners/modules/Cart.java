package com.streamliners.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * HashMap to Store CartItem in the Cart corresponding to Product name as its key.
 * Total cost And no. of items in the cart
 */
public class Cart {
    Scanner sc = new Scanner(System.in);
    public HashMap<String, CartItem> cartItems = new HashMap<>();
    float total, noOfItems;


    /**
     * Adding Products to cart
     * @param shop
     */
    public void add(Shop shop) {
        while(true){
            System.out.println(Colors.YELLOW + "\nSelect type of the product you want to add " + Colors.RESET);
            Object[] arrayList = shop.productList.keySet().toArray();
            for (int i = 0; i < arrayList.length; i++)
                System.out.println(i + " " + arrayList[i]);
            System.out.println(arrayList.length + " Exit ");
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();
            if (choice == arrayList.length)
                break;
            Product product = shop.productList.get(arrayList[choice]);
            if (product.type == ProductType.TYPE_WB) {
                addWBP(product);
            } else {
                addVBP(product);
            }
            System.out.println(Colors.CYAN + "Product Successfully added to cart !" + Colors.RESET);
        }
    }

    /**
     * Adding Variant based product to cart
     * @param product
     */
    private void addVBP(Product product) {
        List<Variant> variants = product.variants;
        for (int i = 0; i < variants.size(); i++)
            System.out.println(i + " " + variants.get(i));
        System.out.print("Enter you choice : ");
        int n = sc.nextInt();
        Variant variant = variants.get(n);
        String key = product.name + " " + variant.name;
        //Already exist
        if (cartItems.containsKey(key)) {
            cartItems.get(key).qty++;
        }
        //Adding for the first time
        else {
            CartItem item = new CartItem(product.name, variant.price, 1);
            cartItems.put(key, item);
        }
        //update cart summary
        noOfItems++;
        total += variant.price;
    }

    /**
     * Adding Weight based product to cart
     * @param product
     */
    private void addWBP(Product product) {
        System.out.print("Enter qty you want to add : ");
        float qty = sc.nextFloat();
        //If item already exist in cart
        if (cartItems.containsKey(product.name)) {
            total -= cartItems.get(product.name).cost();
            cartItems.get(product.name).qty = qty;
        }
        //Adding for the first time
        else {
            CartItem item = new CartItem(product.name, product.pricePerKg, qty);
            cartItems.put(product.name, item);
            noOfItems++;
        }
        total += product.pricePerKg * qty;
    }

    /**
     * Removing product from cart
     * @param cart
     */
    public void remove(Cart cart) {
        while (true) {
            System.out.println(Colors.YELLOW + "\nSelect type of the product you want to remove" + Colors.RESET);
            Object[] cartKey = cartItems.keySet().toArray();
            for (int i = 0; i < cartKey.length; i++)
                System.out.println(i + "" + cartKey[i]);
            System.out.println(cartKey.length +" Exit ");
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();
            if(choice==cartKey.length)
                break;
            CartItem cartItem = cartItems.get(cartKey[choice]);
            cartItems.remove(cartKey[choice]);
            total -= cartItem.cost();
            String[] arr = cartKey[choice].toString().split(" ");
            if (arr.length == 1)
                noOfItems--;
            else
                noOfItems -= cartItem.qty;
            System.out.println(Colors.CYAN + "Product successfully Removed from cart ! " + Colors.RESET);
        }
    }


    /**
     * Removing all variants of VBP
     *
     * @param product
     */
    public void removeAllVariantsOfVBP(Product product) {
        for (Variant variant : product.variants) {
            String key = product.name + " " + variant.name;

            if (cartItems.containsKey(key)) {
                //update cart summary
                total -= cartItems.get(key).cost();
                noOfItems -= cartItems.get(key).qty;
                cartItems.remove(key);
            }
        }
    }

    /**
     * Edit product from cart
     * @param cart
     */
    public void edit(Cart cart) {
        while (true) {
            System.out.println(Colors.YELLOW + "\nChoose from following product " + Colors.RESET);
            Object[] cartKey = cartItems.keySet().toArray();
            for (int i = 0; i < cartKey.length; i++)
                System.out.println(i + " " + cartKey[i]);
            System.out.println(cartKey.length+" Exit ");
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();
            if(choice==cartKey.length)
                break;
            CartItem cartItem = cartItems.get(cartKey[choice]);
            String[] arr = cartKey[choice].toString().split(" ");
            if (arr.length == 1) {
                System.out.print("Enter new qty :");
                float qty = sc.nextFloat();
                cartItem.qty = qty;
            } else {
                while (true) {
                    String menu = "\n=================" +
                            "\n0.Go Back" +
                            "\n1.Increment" +
                            "\n2.Decrement" +
                            "\n=================";
                    System.out.println(menu);
                    System.out.print("Enter your option :");
                    int option = sc.nextInt();
                    if (option == 0)
                        break;
                    else if (option == 1) {
                        cartItem.qty++;
                        System.out.println(Colors.CYAN +"Incremented !" + Colors.RESET);
                    } else {
                        cartItem.qty--;
                        System.out.println(Colors.CYAN +"Decremented !" + Colors.RESET);
                    }
                }
            }
        }
    }


    @Override
    public String toString() {
        return cartItems.values() + "\n" + "Total No Of Items = " + noOfItems +"\nTotal Amount = "+total;
    }
}
