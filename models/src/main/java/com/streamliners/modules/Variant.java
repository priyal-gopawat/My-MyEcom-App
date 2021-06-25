package com.streamliners.modules;

/**
 * represents a variant with...
 *
 * name - name of the variant
 * price - price of the variant
 */
public class Variant {
     String name;
     float price;

    /**
     * parametrized constructor for variant
     *
     * @param name  name of the variant
     * @param price price of the variant
     */
    public Variant(String name, float price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Variant{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}

