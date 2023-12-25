package com.flipkart.view.filter;

import com.flipkart.model.product.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Responsible for getting items filtered based on price high to low.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class RateHighToLowFilter implements ProductFilter {

    private static final List<Product> FILTERED_ITEMS = new ArrayList<>();
    private static RateHighToLowFilter rateHighToLowFilterInstance;

    /**
     * <p>
     * Default constructor of RateHighToLowFilter class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private RateHighToLowFilter() {}

    /**
     * <p>
     * Creates a single object of RateHighToLowFilter class and returns it.
     * </p>
     *
     * @return the single instance of RateHighToLowFilter class.
     */
    public static synchronized RateHighToLowFilter getInstance() {
        if ( null == rateHighToLowFilterInstance) {
            rateHighToLowFilterInstance = new RateHighToLowFilter();
        }

        return rateHighToLowFilterInstance;
    }

    /**
     * <p>
     * Filters the items present in the inventory based on price high to low and returns it.
     * </p>
     *
     * @return the items filtered by price high to low.
     */
    public List<Product> getFilteredItems(final List<Product> inventoryItems) {
        FILTERED_ITEMS.clear();
        FILTERED_ITEMS.addAll(inventoryItems);
        FILTERED_ITEMS.sort(new RateHighToLowComparator());

        return FILTERED_ITEMS;
    }
}