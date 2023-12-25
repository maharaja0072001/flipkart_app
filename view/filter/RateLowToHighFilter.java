package com.flipkart.view.filter;

import com.flipkart.model.product.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Responsible for getting items filtered based on price low to high.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */

public class RateLowToHighFilter implements ProductFilter {

    private static final ArrayList<Product> FILTERED_ITEMS = new ArrayList<>();
    private static RateLowToHighFilter rateLowToHighFilterInstance;

    /**
     * <p>
     * Default constructor of RateLowToHighFilter class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private RateLowToHighFilter() {}

    /**
     * <p>
     * Creates a single object of RateLowToHighFilter class and returns it.
     * </p>
     *
     * @return the single instance of RateLowToHighFilter class.
     */
    public static synchronized RateLowToHighFilter getInstance() {
        if (null == rateLowToHighFilterInstance) {
            rateLowToHighFilterInstance = new RateLowToHighFilter();
        }

        return rateLowToHighFilterInstance;
    }

    /**
     * <p>
     * Filters the items present in the inventory based on price low to high and returns it.
     * </p>
     *
     * @return all the products filtered by price from low to high.
     */
    public List<Product> getFilteredItems(final List<Product> inventoryItems) {
        FILTERED_ITEMS.clear();
        FILTERED_ITEMS.addAll(inventoryItems);
        FILTERED_ITEMS.sort(new RateLowToHighComparator());

        return FILTERED_ITEMS;
    }
}