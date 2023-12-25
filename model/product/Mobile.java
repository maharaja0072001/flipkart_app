package com.flipkart.model.product;

import com.flipkart.ProductCategory;

public class Mobile extends Product {

    private final String model;

    public Mobile(final String brandName, final String model, final float price, final int quantity) {
        super(ProductCategory.MOBILE, price, brandName, quantity);
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public String toString() {
        return String.format("%s : %s - Rs : %.2f", super.getBrandName(), model, super.getPrice());
    }
}