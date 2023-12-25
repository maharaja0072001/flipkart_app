package com.flipkart.model.product;

import com.flipkart.ProductCategory;

public abstract class Product {

    private static int uniqueProductId = 1;
    private int productId;
    private final ProductCategory productCategory;
    private float price;
    private final String brandName;
    private int quantity;

    public Product(final ProductCategory productCategory, final float price, final String brandName, final int quantity) {
        this.productCategory = productCategory;
        this.price = price;
        this.brandName = brandName;
        productId = uniqueProductId++;
        this.quantity = quantity;
    }

    public void setProductId(final int productId) {
        this.productId = productId;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public float getPrice() {
        return price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void changePrice(final float price) {
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }
}