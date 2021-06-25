package com.streamliners.modules;

public class CartItem {

    //name of cart item
    String name;

    // unitPrice and quantity of cart item
    float unitPrice,qty;

    /**
     * @return cost of cart item
     */
    float cost(){
        return unitPrice*qty;
    }

    /**
     * parametrized constructor for cartItem
     * @param name  contains name of the product
     * @param unitPrice contains unitPrice of the product
     * @param qty  contains qty of the product
     */
    public CartItem(String name, float unitPrice, float qty) {
        this.name = name;
        this.unitPrice = unitPrice;
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "\n" + name + String.format("(%f X %f = %f)",unitPrice,qty,cost());
    }
}
