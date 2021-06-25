package com.streamliners.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Shop {
    Scanner sc = new Scanner(System.in);
    HashMap<String, Product> productList = new HashMap<>(); //Hashmap to store list of product in productList

    /**
     * Adding product to shop
     */
    public void add() {
        while (true) {
            System.out.println(Colors.YELLOW + "\nSelect type of the product you want to add " + Colors.RESET);
            System.out.println("====================" +
                    "\n0.Go Back" +
                    "\n1.Weight Based Product" +
                    "\n2.Variant Based Product" +
                    "\n====================");
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();
            if (choice == 0)
                break;
            else if (choice == 1)
                addWBP();
            else if (choice == 2)
                addVBP();
        }
    }

    /**
     * Adding weight Based Product to shop
     */
    public void addWBP() {
        System.out.println(Colors.YELLOW + "\nAdding Weight Based product to shop " + Colors.RESET);
        System.out.print("Enter the name of the product : ");
        String name = sc.nextLine();
        while (name.isEmpty()) name = sc.nextLine();
        System.out.print("Enter the image of the product : ");
        String imageUrl = sc.nextLine();
        System.out.print("Enter the minQty of the product : ");
        float minQty = sc.nextFloat();
        System.out.print("Enter the pricePerKg of the product : ");
        float pricePerKg = sc.nextFloat();

        Product product = new Product(name, imageUrl, minQty, pricePerKg);
        productList.put(product.name, product);
        System.out.println(Colors.CYAN + "Product successfully added to shop !" + Colors.RESET);
    }

    /**
     * Adding variant based product to shop
     */
    public void addVBP() {
        System.out.println(Colors.YELLOW + "\nAdding Variant Based product to shop " + Colors.RESET);
        System.out.print("Enter the name of the product : ");
        String name = sc.nextLine();
        while (name.isEmpty()) name = sc.nextLine();
        System.out.print("Enter the image of the product : ");
        String imageUrl = sc.nextLine();
        List<Variant> variants = new ArrayList<>();
        System.out.print("Enter no. of variants : ");
        int variant = sc.nextInt();
        while (variant != 0) {
            System.out.print("Enter name of the variant : ");
            String name1 = sc.nextLine();
            while (name1.isEmpty()) name1 = sc.nextLine();
            System.out.print("Enter price of the product : ");
            float price = sc.nextFloat();
            variant--;
            variants.add(new Variant(name1, price));
        }
        Product product = new Product(name, imageUrl, variants);
        productList.put(product.name, product);
        System.out.println(Colors.CYAN + "Product successfully added to shop ! " + Colors.RESET);
    }

    /**
     * Delete product from shop
     */
    public void deleteProduct() {
        System.out.println(Colors.YELLOW + "\nSelect type of the product you want to delete" + Colors.RESET);
        Object[] arrayList = productList.keySet().toArray();
        for (int i = 0; i < arrayList.length; i++)
            System.out.println(i + " " + arrayList[i]);
        System.out.print("Enter the product you want to delete : ");
        int choice = sc.nextInt();
        productList.remove(arrayList[choice]);
        System.out.println(Colors.CYAN + "Product successfully Deleted from shop ! " + Colors.RESET);
    }

    /**
     * edit product from shop
     */
    public void edit() {
        while (true) {
            System.out.println(Colors.YELLOW + "\nChoose from following product " + Colors.RESET);
            Object[] arrayList = productList.keySet().toArray();
            for (int i = 0; i < arrayList.length; i++)
                System.out.println(i + " " + arrayList[i]);
            System.out.println(arrayList.length + " Go Back ");
            System.out.print("Enter the product you want to edit : ");
            int choice = sc.nextInt();
            if (choice == arrayList.length)
                break;
            Product product = productList.get(arrayList[choice]);
            if (product.type == ProductType.TYPE_WB)
                editWBP(product);
            else if (product.type == ProductType.TYPE_VB)
                editVBP(product);

        }
    }

    /**
     * Edit weight based product from shop
     * @param product
     */
    public void editWBP(Product product) {
        while (true) {
            System.out.print("Choose property you want to edit : ");
            System.out.println("\n====================" +
                    "\n0.Go Back" +
                    "\n1.imageUrl" +
                    "\n2.minQty" +
                    "\n3.pricePerKg" +
                    "\n====================");
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();
            if (choice == 0)
                break;
            else if (choice == 1) {
                System.out.println("Enter new ImageUrl :");
                String imageUrl = sc.nextLine();
                while(imageUrl.isEmpty()) imageUrl = sc.nextLine();
                product.imageUrl = imageUrl;
                System.out.print(Colors.CYAN + "Image changed Successfully!" + Colors.RESET);
            } else if (choice == 2) {
                System.out.print("Enter new Min Quantity :");
                float minQty = sc.nextFloat();
                product.minQty = minQty;
                System.out.println(Colors.CYAN + "Min. quantity changed to " + product.minQty + " Successfully!" + Colors.RESET);
            } else if (choice == 3) {
                System.out.print("Enter new pricePerKg :");
                float pricePerKg = sc.nextFloat();
                product.pricePerKg = pricePerKg;
                System.out.println(Colors.CYAN + "pricePerKg changed to " + product.pricePerKg + " Successfully!" + Colors.RESET);
            }
        }
    }

    /**
     * Adding variant based product from shop
     * @param product
     */
    public void editVBP(Product product) {
        while (true) {
            System.out.print("Choose property you want to edit : ");
            System.out.println("\n====================" +
                    "\n0.Go Back" +
                    "\n1.imageUrl" +
                    "\n2.variant" +
                    "\n====================");
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();
            if (choice == 0)
                break;
            else if (choice == 1) {
                String imageUrl = sc.nextLine();
                while (imageUrl.isEmpty()) imageUrl = sc.nextLine();
                product.imageUrl = imageUrl;
            } else if (choice == 2) {
                for (int i = 0; i < product.variants.size(); i++)
                    System.out.println(i + " " + product.variants.get(i));
                System.out.print("Enter variant you want to edit : ");
                int variant = sc.nextInt();
                System.out.print("Enter new variant price");
                float price = sc.nextFloat();
                product.variants.get(variant).price = price;
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nMy Shop:\n");
        Object[] arr = productList.keySet().toArray();
        for (int i=0;i<productList.size();i++){
            sb.append(productList.get(arr[i].toString()) + "\n");
        }
        return sb.toString();
    }


}