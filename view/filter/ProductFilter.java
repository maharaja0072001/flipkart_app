package com.flipkart.view.filter;

import com.flipkart.model.product.Product;

import java.util.List;

/**
 * <p>
 * Provides a contract for the classes implementing it for filtering the products.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public interface ProductFilter {

    /**
     * <p>
     * Filters the products in the inventory and returns the filtered items.
     * </p>
     *
     * @return the filtered items.
     */
    List<Product> getFilteredItems(final List<Product> inventoryItems);
}
