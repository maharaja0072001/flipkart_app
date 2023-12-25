package com.flipkart.view.filter;

import com.flipkart.InputHandler;
import com.flipkart.model.product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import com.flipkart.view.datavalidation.UserDataValidator;

/**
 * <p>
 * Provides filtered items from the inventory based on price range.
 * </p>
 *
 * @author Maharaja S
 * @version 1.0
 */
public class PriceFilter implements ProductFilter {

    private static final List<Product> FILTERED_ITEMS = new ArrayList<>();
    private static PriceFilter priceFilterInstance;
    private static final UserDataValidator USER_DATA_VALIDATOR = UserDataValidator.getInstance();

    /**
     * <p>
     * Default constructor of PriceFilter class. Kept private to restrict from creating object outside this class.
     * </p>
     */
    private PriceFilter() {}

    /**
     * <p>
     * Creates a single object of PriceFilter class and returns it.
     * </p>
     *
     * @return the single instance of PriceFilter class.
     */
    public static synchronized PriceFilter getInstance() {
        if (null == priceFilterInstance) {
            priceFilterInstance = new PriceFilter();
        }

        return priceFilterInstance;
    }

    /**
     * <p>
     * Filters the inventory based on the price range given by the user and returns the filtered items.
     * </p>
     *
     * @return the filtered items of given price range.
     */
    public List<Product> getFilteredItems(final List<Product> inventoryItems) {
        final Scanner scanner = InputHandler.getScanner();
        String minimumAmount;
        String maximumAmount;

        while (true) {
            System.out.println("Enter Minimum amount : [Enter '$' to go back]");
            minimumAmount = scanner.nextLine().trim();

            if (USER_DATA_VALIDATOR.containsToNavigateBack(minimumAmount)) {
                return null;
            }

            if (!validateAmount(minimumAmount)) {
                System.out.println("Input is invalid");
                continue;
            }
            System.out.println("Enter Maximum amount :");
            maximumAmount = scanner.nextLine().trim();

            if (USER_DATA_VALIDATOR.containsToNavigateBack(maximumAmount)) {
                return null;
            }

            if (!validateAmount(maximumAmount)) {
                System.out.println("Input is invalid");
                continue;
            }

            if (Integer.parseInt(maximumAmount) < (Integer.parseInt(minimumAmount))) {
                System.out.println("Maximum amount is smaller than minimum amount");
            } else {
                break;
            }
        }
        FILTERED_ITEMS.clear();

        for (final Product product : inventoryItems) {
            if ((product.getPrice() >= Integer.parseInt(minimumAmount))
                    && (product.getPrice() <= Integer.parseInt(maximumAmount))) {
                FILTERED_ITEMS.add(product);
            }
        }

        if (FILTERED_ITEMS.isEmpty()) {
            System.out.println("No items found!");
        }

        return FILTERED_ITEMS;
    }

    /**
     * <p>
     * Validates the amount entered by the user.
     * </p>
     *
     * @return true if user entered valid amount and false otherwise.
     */
    private boolean validateAmount(final String input) {
        return Pattern.matches("^\\d+$", input);
    }
}