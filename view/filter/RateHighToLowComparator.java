package com.flipkart.view.filter;

import com.flipkart.model.product.Product;

import java.util.Comparator;

/**
 * <p>
 * Responsible for comparing the product from price high to low
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class RateHighToLowComparator implements Comparator<Product> {

    /**
     * <p>
     * Compares two product based on their price and return the subtracted value
     * </p>
     *
     * @return the value got by subtracting the price of two products.
     */
    @Override
    public int compare(final Product product, final Product otherProduct) {
        return (int) (otherProduct.getPrice() - product.getPrice());
    }
}